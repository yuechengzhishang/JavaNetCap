package netcap;

import common.util.LogUtil;
import jpcap.JpcapCaptor;
import netcap.view.ViewModules;

@SuppressWarnings("restriction")
public class Netcaptor {

	private final Class<?> cl = Netcaptor.class;
	
	private JpcapCaptor jpcap = null;
	private JFrameMain frame;
	private Thread captureThread;
	private String domain;

	public Netcaptor(JFrameMain frame) {
		this.frame = frame;
	}

	/**
	 * 输入配置信息
	 */
	public void captureConfiguration() {
		//打开网卡选择框
		jpcap = JcaptureConfiguration.getJpcap(this.frame);
		domain = JcaptureConfiguration.getDomainFilter();
	}

	/**
	 * 开始抓包
	 */
	public void startCaptureThread() {
		LogUtil.debug(cl, "Capture Thread is started...");
		String msgTitle = "提示信息";
		if (captureThread != null){
			String message = "抓包已经处于启动状态！";
			ViewModules.showMessageDialog(this.frame.getContentPane(), message, msgTitle, 1);
			return;
		}
		if (jpcap == null) {
			String message = "请先对网卡和过滤器进行配置！";
			ViewModules.showMessageDialog(this.frame.getContentPane(), message, msgTitle, 1);
			return;
		}
		captureThread = new Thread(new Runnable() {
			public void run() {
				while (captureThread != null) {
					jpcap.processPacket(-1, new PacketReceiverImpl(frame, domain));
				}
			}
		});
		captureThread.setPriority(Thread.MIN_PRIORITY);
		captureThread.start();
	}

	/**
	 * 停止抓包
	 */
	public void stopCapture() {
		captureThread = null;
		LogUtil.debug(cl, "Capture Thread is stoped...");
	}
	
}