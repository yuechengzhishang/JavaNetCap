package netcap.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import common.Assert;

public class ViewModules {
	
	/**
	 * 
	 * @param jMenuBar
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	public static JMenu addMenu(JMenuBar jMenuBar, String name, int width, int height) {
		return jMenuBar.add(ViewModules.getMenu(name, width, height));
	}

	/**
	 * 
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	public static JMenu getMenu(String name, int width, int height){
		JMenu menu = new JMenu();
		menu.setText(name);//抓包
		menu.setPreferredSize(new java.awt.Dimension(width, height));
		return menu;
	}
	
	/**
	 * 
	 * @param menu
	 * @param name
	 * @param command
	 * @param listener
	 * @return
	 */
	public static JMenuItem addSimpleMenuItem(JMenu menu, String name, String command, ActionListener listener) {
		return menu.add(ViewModules.createSimpleMenuItem(name, command, listener));
	}
	
	/**
	 * 
	 * @param menu
	 * @param me
	 * @param listener
	 * @return
	 */
	public static JMenuItem addSimpleMenuItem(JMenu menu, MenuEnum me, ActionListener listener) {
		return ViewModules.addSimpleMenuItem(menu, me.getDescription(), me.getCommand(), listener);
	}
	
	/**
	 * 创建JMenuItem
	 * @param name
	 * @param command
	 * @param listener
	 * @return
	 */
	public static JMenuItem createSimpleMenuItem(String name, String command, ActionListener listener){
		JMenuItem startMenuItem = new JMenuItem();
		startMenuItem.setText(name);
		startMenuItem.setActionCommand(command);
		startMenuItem.addActionListener(listener);
		return startMenuItem;
	}

	/**
	 * @param orientation  0:HORIZONTAL  1:VERTICAL
	 * @return
	 */
	public static JSeparator createSeparator(int orientation){
		Assert.verify(orientation != 0 || orientation != 1, "orientation 取值为0或1，0:HORIZONTAL  1:VERTICAL");
		return new JSeparator(orientation);
	}
	
	/**
	 * 创建JTable
	 * @param dataVector  rows
	 * @param columnIdentifiers  column
	 * @return
	 */
	public static JTable createTable(Vector<?> dataVector, Vector<?> columnIdentifiers){
		DefaultTableModel tabModel = new DefaultTableModel();
		tabModel.setDataVector(dataVector, columnIdentifiers);
		return new JTable(tabModel);
	}
	
	/**
	 * 
	 * @param values
	 * @return
	 */
	public static Vector<Object> createVector(Object[] values) {
		Vector<Object> v = new Vector<Object>();
		for(Object title:values){
			v.addElement(title);
		}
		return v;
	}
	
	/**
	 * 创建JButton
	 * @param name
	 * @param command
	 * @param listener
	 * @return
	 */
	public static JButton createButton(String name, String command, ActionListener listener){
		JButton button = new JButton();
		button.setText(name);
		button.setActionCommand(command);
		button.addActionListener(listener);
		return button;
	}
	
	/**
	 * 
	 * @param panel
	 * @param name
	 * @param command
	 * @param listener
	 * @return
	 */
	public static Component addButton(JPanel panel, String name, String command, ActionListener listener){
		JButton button = ViewModules.createButton(name, command, listener);
		return panel.add(button);
	}
	
	/**
	 * 创建JRadioButton
	 * @param content
	 * @param listener
	 * @return
	 */
	public static JRadioButton createRadioButton(String name, String command, ActionListener listener){
		JRadioButton rb = new JRadioButton();
		rb.setText(name);
		rb.setActionCommand(command);
		rb.addActionListener(listener);
		return rb;
	}
	
	/**
	 * 
	 * @param panel
	 * @param content
	 * @param listener
	 * @return
	 */
	public static Component addRadioButton(JPanel panel, String name, String command, ActionListener listener){
		JRadioButton rb = ViewModules.createRadioButton(name, command, listener);
		return panel.add(rb);
	}
	
	/**
	 * 创建JTextField
	 * @param width
	 * @param content
	 * @param enabled
	 * @return
	 */
	public static JTextField createTextField(int width, String content,  boolean enabled){
		JTextField textField = new JTextField(width);
		textField.setText(content);//1514
		textField.setEnabled(enabled);
		return textField;
	}
	
	/**
	 * 创建JTextPane
	 * @param content
	 * @return
	 */
	public static JTextPane createTextPane(String content){
		JTextPane textPane = new JTextPane();
		textPane.setText(content);
		return textPane;
	}
	
	/**
	 * 创建JPanel
	 * @param title
	 * @param alignmentX   the new vertical alignment,can be one of: -1f(not set), Component.CENTER_ALIGNMENT, Component.TOP_ALIGNMENT, Component.BOTTOM_ALIGNMENT, Component.LEFT_ALIGNMENT, Component.RIGHT_ALIGNMENT
	 * @param alignmentY   the new horizontal alignment, the value is same as alignmentX
	 * @param axis  the axis to lay out components along. Can be one of: BoxLayout.X_AXIS, BoxLayout.Y_AXIS, BoxLayout.LINE_AXIS or BoxLayout.PAGE_AXIS
	 * @return
	 */
	public static JPanel createSimplePanel(String title, float alignmentX, float alignmentY, int axis){
		JPanel panel = new JPanel();
		if(null != title)
			panel.setBorder(BorderFactory.createTitledBorder(title));
		if(-1f != alignmentX)
			panel.setAlignmentX(alignmentX);
		if(-1f != alignmentY)
			panel.setAlignmentY(alignmentY);
		if(-1 != axis)
			panel.setLayout(new BoxLayout(panel, axis));
		return panel;
	}
	
	public static JPanel createSimplePanel(String title, float alignmentX, float alignmentY, LayoutManager mgr){
		JPanel panel = ViewModules.createSimplePanel(title, alignmentX, alignmentY, -1);
		if(null != mgr)
			panel.setLayout(mgr);
		return panel;
	}
	
	public static JPanel createSimplePanel(String title, float alignmentX, float alignmentY, boolean alignOnBaseline){
		JPanel panel = ViewModules.createSimplePanel(title, alignmentX, alignmentY, -1);
		panel.setLayout(ViewModules.createFlowLayout(alignOnBaseline, -1, -1, -1));
		return panel;
	}
	
	/**
	 * 创建JCheckBox
	 * @param content
	 * @param mgr
	 * @return
	 */
	public static JCheckBox createCheckBox(String content, LayoutManager mgr){
		JCheckBox checkBox = new JCheckBox();
		checkBox.setText(content);
		if(null != mgr)
			checkBox.setLayout(mgr);
		return checkBox;
	}
	
	/**
	 * 创建下拉列表JComboBox
	 * @param items
	 * @return
	 */
	public static JComboBox<Object> createComboBox(Object[] items){
		return new JComboBox<Object>(items);
	}
	
	/**
	 * 创建FlowLayout
	 * @param alignOnBaseline
	 * @return
	 */
	public static FlowLayout createFlowLayout(boolean alignOnBaseline, int align, int hgap, int vgap){
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignOnBaseline(alignOnBaseline);
		if(-1 == align)
			flowLayout.setAlignment(align);
		if(-1 == hgap)
			flowLayout.setHgap(hgap);
		if(-1 == vgap)
			flowLayout.setVgap(vgap);
		return flowLayout;
	}
	
	/**
	 * 
	 * @param target
	 * @param axis
	 * @return
	 */
	public static BoxLayout createBoxLayout(Container target, int axis){
		BoxLayout boxLayout = new BoxLayout(target, axis);
		return boxLayout;
	}
	
	/**
	 * 在给定画板中添加多个组件
	 * @param panel
	 * @param comps
	 */
	public static void addComponent(JPanel panel, Component... comps){
		for(Component comp : comps){
			panel.add(comp);
		}
	}
	
	/**
	 * 创建ButtonGroup，一般用于单选时的JRadioButton组
	 * @param buttons
	 * @return
	 */
	public static ButtonGroup createButtonGroup(AbstractButton... buttons){
		ButtonGroup group = new ButtonGroup();
		for(AbstractButton button : buttons){
			group.add(button);
		}
		return group;
	}
	
	/**
	 * 创建提示框
	 * @param parentComponent
	 * @param message
	 * @param title
	 * @param messageType  Can be one of:
	 * 		0:JOptionPane.ERROR_MESSAGE
	 * 		1:JOptionPane.INFORMATION_MESSAGE
	 * 		2:JOptionPane.WARNING_MESSAGE
	 * 		3:JOptionPane.QUESTION_MESSAGE
	 */
	public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType){
		JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
	}
	
	/**
	 * 创建提示框
	 * @param parentComponent
	 * @param message
	 */
	public static void showMessageDialog(Component parentComponent, Object message){
		JOptionPane.showMessageDialog(parentComponent, message);
	}
	
	/**
	 * 创建确认框
	 * @param parentComponent
	 * @param message
	 * @param title
	 * @param optionType Can be one of:
	 * 			-1:DEFAULT_OPTION
	 * 			0:YES_NO_OPTION
	 * 			1:YES_NO_CANCEL_OPTION
	 * 			2:OK_CANCEL_OPTION
	 * @return 
	 * 		-1:CLOSED_OPTION
	 * 		0:OK/YES is chosen
	 * 		1:NO is chosen
	 * 		2:CANCEL is chosen
	 */
	public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType){
		return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType);
	}
}
