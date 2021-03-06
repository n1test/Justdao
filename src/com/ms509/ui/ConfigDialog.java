package com.ms509.ui;

import com.ms509.ui.config.panel.ProxyPanel;
import com.ms509.ui.config.panel.RequestPanel;

import javax.swing.*;
import java.awt.*;

public class ConfigDialog extends JDialog{
	public static ConfigDialog cdialog;
	public ConfigDialog() {
		super(MainFrame.main,"Cknife 设置",true);
		this.setComponent();
		cdialog = this;
		this.setVisible(true);
	}
	private void setComponent()
	{
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		this.setResizable(false);
		this.setSize(450, 240);
		this.setLocation((d.width - this.getWidth()) / 2,
				(d.height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("代理", new ProxyPanel());
		pane.addTab("请求头", new RequestPanel());
		this.getContentPane().add(pane);
	}
}
