package netcap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.*;
import javax.swing.*;
import dao.HttpDataBean;
import netcap.view.MenuEnum;
import netcap.view.ViewModules;

import java.util.*;

public class JFrameMain extends javax.swing.JFrame implements ActionListener {

	private static final long serialVersionUID = -6936337706153702144L;

	private JTable tabledisplay = null;
	private Vector<Object> rows;

	private Netcaptor captor;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		JFrameMain inst = new JFrameMain();
		inst.setVisible(true);
	}

	public JFrameMain() {
		super();
		initGUI();
	}

	private void initGUI() {
		captor = new Netcaptor(this);
		try {
			setSize(400, 300);
			setMenu();
			rows = new Vector<Object>();
			
			String[] httpTitles = {"ID", "URL", "Method", "ReqHeader", "ReqParams", "RspCode", "RspMsg", "RspHeader", "RspBody"};
			//String[] titles = {"数据报时间", "协议类型", "源IP地址", "目的IP地址", "首部长度", "数据长度", "是否分段", "分段偏移量", "首部内容", "数据内容"};
			Vector<Object> columns = ViewModules.createVector(httpTitles);

			tabledisplay = ViewModules.createTable(rows, columns);
			this.getContentPane().add(new JScrollPane(tabledisplay), BorderLayout.CENTER);

			JLabel statusLabel = new JLabel("zhouyelin@cmhi.chinamobile.com");
			this.getContentPane().add(statusLabel, BorderLayout.SOUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		setJMenuBar(jMenuBar);
		JMenu mainMenu = ViewModules.addMenu(jMenuBar, "抓包", 35, 21);
		for(MenuEnum menu : MenuEnum.values()){
			if("conf".equals(menu.getCommand())) continue;
			ViewModules.addSimpleMenuItem(mainMenu, menu, this);
		}
		
		JMenu confMenu = ViewModules.addMenu(jMenuBar, "配置", 35, 21);
		ViewModules.addSimpleMenuItem(confMenu, MenuEnum.CONF, this);
	}

	public void actionPerformed(ActionEvent event) {
		MenuEnum cmd = MenuEnum.getMenuEnum(event.getActionCommand());
		switch(cmd){
		case START:
			captor.startCaptureThread();
			break;
		case STOP:
			captor.stopCapture();
			break;
		case SAVE:
			break;
		case SAVEAS:
			break;
		case EXIT:
			System.exit(0);
			break;
		case CONF:
			captor.captureConfiguration();
			break;
		default:
			break;
		}
	}

	public void updateView(HttpDataBean dataBean){
		Object[] values = getValues(dataBean);
		Vector<Object> r = ViewModules.createVector(values);

		rows.addElement(r);
		tabledisplay.addNotify();
	}

	private Object[] getValues(HttpDataBean data) {
		Object[] values = {data.getId(), data.getUrl(), data.getMethod(), data.getRequestHeader(), data.getRequestParamsStr(), data.getResponseCode(), data.getResponseMsg(), data.getResponseHeader(), data.getResponseBody()};
		return values;
	}
}