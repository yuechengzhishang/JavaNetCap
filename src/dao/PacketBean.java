package dao;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;

import jpcap.packet.ARPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.EthernetPacket;

@SuppressWarnings("restriction")
public class PacketBean {
	private Packet packet;
	private String srcIp = ""; // 源IP地址
	private String dstIp = ""; // 目的IP地址
	private String dontFrag = "不分段"; // 是否不分段
	private short offset = 0; // 数据长度
	private String proType = "其他"; // 协议类型
	private Timestamp timestamp = null;
	private int headerLength = 0;
	private int dataLength = 0;
	private String header = "";
	private byte[] data = null;
	private long sequence;
	private long ackNum;
	private String srcMac;
	private String dstMac;
	
	
	public PacketBean(Packet packet) {
		this.packet = packet;
		init();
	}
	
	private void init(){
		try {
			this.timestamp = new Timestamp((this.packet.sec * 1000) + (this.packet.usec / 1000));
			if(this.packet instanceof IPPacket) {
				this.proType = getIPProtocol((IPPacket) this.packet);
				EthernetPacket ethernetPacket = (EthernetPacket) this.packet.datalink;                 
                this.setSrcMac(ethernetPacket.getSourceAddress());                 //SRC_MAC：  
                this.setDstMac(ethernetPacket.getDestinationAddress());            //DST_MAC  
				this.srcIp = ((IPPacket) this.packet).src_ip.toString().substring(1);
				this.dstIp = ((IPPacket) this.packet).dst_ip.toString().substring(1);
				this.dontFrag = ((IPPacket) this.packet).dont_frag == true ? "分段" : "不分段";
				this.offset = ((IPPacket) this.packet).offset;
			} else if(this.packet instanceof ARPPacket) {
				this.proType = "ARP";
				this.srcIp = ((ARPPacket) this.packet).getSenderProtocolAddress().toString();
				this.dstIp = ((ARPPacket) this.packet).getTargetProtocolAddress().toString();
			} else {
				System.out.println("协议类型 ：GGP、EGP、JGP协议或OSPF协议或ISO的第4类运输协议TP4");
			}
			String header = "";
			for (int i = 0; i < this.packet.header.length; i++) {
				header += Byte.toString(this.packet.header[i]);
			}
			this.setTimestamp(timestamp);
			this.setProType(proType);
			this.setSrcIp(srcIp);
			this.setDstIp(dstIp);
			this.setHeaderLength(this.packet.header.length);
			this.setDataLength(this.packet.data.length);
			this.setDontFrag(dontFrag);
			this.setOffset(offset);
			this.setHeader(header);
			this.setData(this.packet.data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取IPPacket的协议子类型
	 * @param packet
	 * @return
	 */
	private String getIPProtocol(IPPacket packet){
		String protocol = null;
		switch (new Integer(((IPPacket) packet).protocol)) {
		case 1:
			protocol = "ICMP";
			break;
		case 2:
			protocol = "IGMP";
			break;
		case 6:
			protocol = "TCP";
			this.sequence = ((TCPPacket) packet).sequence;
			this.ackNum = ((TCPPacket) packet).ack_num;
			break;
		case 8:
			protocol = "EGP";
			break;
		case 9:
			protocol = "IGP";
			break;
		case 17:
			protocol = "UDP";
			break;
		case 41:
			protocol = "IPv6";
			break;
		case 89:
			protocol = "OSPF";
			break;
		default:
			protocol = "IP";
			break;
		}
		return protocol;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}

	public String getDontFrag() {
		return dontFrag;
	}

	public void setDontFrag(String dontFrag) {
		this.dontFrag = dontFrag;
	}

	public short getOffset() {
		return offset;
	}

	public void setOffset(short offset) {
		this.offset = offset;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(int headerLength) {
		this.headerLength = headerLength;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public byte[] getData() {
		return data;
	}
	
	public String getData(String charsetName){
		String HttpData = new String(data);
		if(null != charsetName){
			try {
				HttpData = new String(data, charsetName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return HttpData;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getAckNum() {
		return ackNum;
	}

	public void setAckNum(long ackNum) {
		this.ackNum = ackNum;
	}

	public String getSrcMac() {
		return srcMac;
	}

	public void setSrcMac(String srcMac) {
		this.srcMac = srcMac;
	}

	public String getDstMac() {
		return dstMac;
	}

	public void setDstMac(String dstMac) {
		this.dstMac = dstMac;
	}
	
}
