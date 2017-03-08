package netcap;

import java.util.ArrayList;
import java.util.List;

import common.tools.StringTools;
import common.util.LogUtil;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

@SuppressWarnings("restriction")
public class PacketReceiverImpl implements PacketReceiver {
	private static Class<?> cl = PacketReceiverImpl.class;

	private static long id;
	private static byte[] reqData;
	private static byte[] rspData;
	private static List<byte[]> rspDataList = new ArrayList<byte[]>();
	private JFrameMain frame;
	private String domain;
	
	public PacketReceiverImpl(JFrameMain frame, String domain){
		this.frame = frame;
		this.domain = domain;
	}
	
	public void receivePacket(Packet paramPacket) {
		TCPPacket packet = (TCPPacket) paramPacket;
		String dstIP = packet.dst_ip.toString().substring(1);
		String srcIP = packet.src_ip.toString().substring(1);
		if(null != domain && !dstIP.contains(domain) && !srcIP.contains(domain)){
			LogUtil.debug(cl, "=====>dst_ip or src_ip is not contain " + domain);
			return;
		}
		
		boolean isFin = (packet.sequence == id && packet.fin);
		LogUtil.debug(cl, "sequence:" + packet.sequence + " id---->" + id + " fin---->" + packet.fin);
		LogUtil.debug(cl, "isFin:" + isFin + " packet---->" + packet);
		if(PacketUtils.isHttpRequest(packet)){
			if(null != reqData && null != rspData)
				dealData();
			id = packet.sequence + packet.data.length;
			reqData = packet.data;
		} else if(packet.ack_num == id){
			rspDataList.add(packet.data);
		} else if(isFin){
			dealData();
		}
	}

	private void dealData() {
		LogUtil.debug(cl, "begin to show and save data...");
		rspData = StringTools.byteMerger(rspDataList);
		AsyncHandler handler = new AsyncHandler();
		handler.setFrame(this.frame);
		//开启线程执行队列中的任务，那就是先到先得了
		new Thread(handler).start();
        //添加一个任务
		HttpDataQueues.Task t =new HttpDataQueues.Task(id, reqData, rspData);
		HttpDataQueues.add(t); //执行该方法，激活所有对应队列，那两个线程就会开始执行啦
		initData();
	}

	private void initData() {
		rspDataList = new ArrayList<byte[]>();
		id = -1;
		reqData = null;
		rspData = null;
	}
	
}
