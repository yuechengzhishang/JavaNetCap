package netcap;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.http.util.TextUtils;
import jpcap.NetworkInterface;
import jpcap.JpcapCaptor;
import netcap.view.ViewModules;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("restriction")
public class JcaptureConfiguration extends javax.swing.JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Auto-generated main method to display this JDialog
	 */
	private static JpcapCaptor jpcap = null;
	private JTextField caplenTextField, proFilterField, domainFilterField;
	private JCheckBox checkBox;
	private JComboBox<?> netJComboBox;
	private JFrame frame;

	private NetworkInterface[] devices;
	private static String domain;

	public JcaptureConfiguration(JFrame frame) {
		super(frame, "选择要检测的网卡并设置参数", true);
		this.frame = frame;
		initGUI();
	}
	
	private void initGUI(){
		Container container = getContentPane();
		container.setLayout(ViewModules.createBoxLayout(container, BoxLayout.X_AXIS));
		try {
			JPanel jPanel_west = ViewModules.createSimplePanel(null, -1f, -1f, BoxLayout.Y_AXIS);
			JPanel netPanel = getNetDevicesPanel();
			checkBox = getPromiscCheckBox();
			JPanel filterPanel = getProtocolFilterPanel();
			ViewModules.addComponent(jPanel_west, netPanel, checkBox, filterPanel);
			
			JPanel jPanel_east = ViewModules.createSimplePanel(null, -1f, -1f, BoxLayout.Y_AXIS);
			JPanel caplenPanel = getCaplenPanel();
			JPanel buttonPanel = getOkCanelPanel();
			ViewModules.addComponent(jPanel_east, caplenPanel, buttonPanel);
			
			container.add(jPanel_west);
			container.add(jPanel_east);
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent evt) {
		Command cmd = Command.getCommand(evt.getActionCommand());
		switch(cmd){
		case WHOLE:
			caplenTextField.setText("1514");
			caplenTextField.setEnabled(false);
			break;
		case HEAD:
			caplenTextField.setText("68");
			caplenTextField.setEnabled(false);
			break;
		case OTHER:
			caplenTextField.setText("");
			caplenTextField.setEnabled(true);
			caplenTextField.requestFocus();
			break;
		case OK:
			saveConfiguration();
			break;
		case CANCEL:
			dispose();
			break;
		default:
			break;
		}
	}

	public static JpcapCaptor getJpcap(JFrame parent) {
		new JcaptureConfiguration(parent).setVisible(true);
		return jpcap;
	}
	
	/**
	 * 获取要过滤的域名
	 * @return
	 */
	public static String getDomainFilter() {
		return domain;
	}
	
	/**
	 * 保存配置
	 */
	private void saveConfiguration(){
		try {
			int caplen = Integer.parseInt(caplenTextField.getText());
			if (caplen < 68 || caplen > 1514) {
				ViewModules.showMessageDialog(null, "捕获长度必须介于 68 和 1514之间");
				return;
			}
			NetworkInterface netInter = devices[netJComboBox.getSelectedIndex()];
			boolean isPromisc = checkBox.isSelected();
			jpcap = JpcapCaptor.openDevice(netInter, caplen, isPromisc, 50);
			if (!TextUtils.isEmpty(proFilterField.getText())) 
				jpcap.setFilter(proFilterField.getText(), true);
			if(!TextUtils.isEmpty(domainFilterField.getText()))
				domain = domainFilterField.getText().trim();
		} catch (NumberFormatException e) {
			ViewModules.showMessageDialog(null, "捕获长度必须是正整数");
		} catch (java.io.IOException e) {
			ViewModules.showMessageDialog(null, e.toString());
			jpcap = null;
		} finally {
			dispose();
		}
	}
	
	/**
	 * 网卡选择区块
	 * @return
	 */
	private JPanel getNetDevicesPanel(){
		JPanel netPanel = ViewModules.createSimplePanel("选择网卡", Component.LEFT_ALIGNMENT, -1f, true);
		String[] names = getNetDeviceList();
		if(names.length == 0)
			ViewModules.showMessageDialog(frame, "没有找到网卡");
		netJComboBox = ViewModules.createComboBox(names);
		netPanel.add(netJComboBox);
		return netPanel;
	}
	
	/**
	 * 获取所有网卡的名称
	 * @return
	 */
	private String[] getNetDeviceList(){
		devices = JpcapCaptor.getDeviceList();
		if (devices == null) {
			dispose();
			return new String[0];
		} else {
			String[] names = new String[devices.length];
			for (int i = 0; i < names.length; i++) {
				names[i] = (devices[i].description == null ? devices[i].name : devices[i].description);
			}
			return names;
		}
	}
	
	/**
	 * 获取混杂模式区块
	 * @return
	 */
	private JCheckBox getPromiscCheckBox(){
		FlowLayout checkBoxLayout = new FlowLayout();
		checkBoxLayout.setAlignOnBaseline(true);
		JCheckBox checkBox = ViewModules.createCheckBox("是否设置为混杂模式", checkBoxLayout);
		return checkBox;
	}
	
	/**
	 * 获取协议过滤器区块
	 * @return
	 */
	private JPanel getProtocolFilterPanel(){
		JPanel filterPanel = ViewModules.createSimplePanel("捕获过滤器", Component.LEFT_ALIGNMENT, -1f, BoxLayout.Y_AXIS);
		JTextPane proType = ViewModules.createTextPane("捕获的协议类型:");
		proFilterField = ViewModules.createTextField(20, "tcp", true);
		ViewModules.createSeparator(0);
		JTextPane domain = ViewModules.createTextPane("捕获的域名/IP:");
		domainFilterField = ViewModules.createTextField(20, "", true);
		ViewModules.addComponent(filterPanel, proType, proFilterField, domain, domainFilterField);
		return filterPanel;
	}
	
	/**
	 * 获取捕获数据长度区块
	 * @return
	 */
	private JPanel getCaplenPanel(){
		JPanel caplenPanel = ViewModules.createSimplePanel("最长字长", Component.CENTER_ALIGNMENT, -1f, BoxLayout.Y_AXIS);
		caplenTextField = ViewModules.createTextField(20, "1514", false);
		JRadioButton wholeRadioButton = ViewModules.createRadioButton("整个数据报", Command.WHOLE.getName(), this);
		wholeRadioButton.setSelected(true);
		JRadioButton headRadioButton = ViewModules.createRadioButton("仅首部", Command.HEAD.getName(), this);
		JRadioButton otherRadioButton = ViewModules.createRadioButton("其他部分", Command.OTHER.getName(), this);
		ViewModules.addComponent(caplenPanel, caplenTextField, wholeRadioButton, headRadioButton, otherRadioButton);
		// 把单选框加到一个组中以确保一个组中的单选框只能单选
		ViewModules.createButtonGroup(wholeRadioButton, headRadioButton, otherRadioButton);
		return caplenPanel;
	}
	
	/**
	 * 获取确定取消按钮区块
	 * @return
	 */
	private JPanel getOkCanelPanel(){
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton ok = ViewModules.createButton("确定", Command.OK.getName(), this);
		JButton cancel = ViewModules.createButton("取消", Command.CANCEL.getName(), this);
		ViewModules.addComponent(buttonPanel, ok, cancel);
		return buttonPanel;
	}
	
	private enum Command{
		WHOLE("WHOLE"), HEAD("HEAD"), OTHER("OTHER"), OK("OK"), CANCEL("CANCEL");
		private String name;
		private Command(String name){
			this.name = name;
		}
		public String getName(){
			return name;
		}
		
		public static Command getCommand(String name){
			for (Command e : Command.values()) {
	            if(e.getName().equals(name))
	            	return e;
	        }
			return null;
		}
	}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		JcaptureConfiguration inst = new JcaptureConfiguration(frame);
//		inst.setVisible(true);
//	}

}