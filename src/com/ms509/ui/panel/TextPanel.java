package com.ms509.ui.panel;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import com.ms509.ui.MainFrame;
import com.ms509.util.Common;
import com.ms509.util.GBC;

public class TextPanel extends JPanel {
	private FileManagerPanel filemanagerpanel;
	private JTextPane text;
	private JLabel status;

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}

	private JButton button;

	public JLabel getStatus() {
		return status;
	}

	public void setStatus(JLabel status) {
		this.status = status;
	}

	private JTextField path;

	public JTextField getPath() {
		return path;
	}

	public void setPath(JTextField path) {
		this.path = path;
	}

	public JTextPane getText() {
		return text;
	}

	public void setText(JTextPane text) {
		this.text = text;
	}

	public TextPanel() {
		filemanagerpanel = (FileManagerPanel) MainFrame.tab.getSelectedComponent();
		this.setLayout(new GridBagLayout());
		GBC gbcload = new GBC(0, 0);
		GBC gbcpath = new GBC(1, 0, 4, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0);
		GBC gbcread = new GBC(5, 0);
		GBC gbctext = new GBC(0, 1, 6, 1).setFill(GBC.BOTH).setWeight(0, 100);
		GBC gbcbar = new GBC(0, 2, 6, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0);
		JButton load = new JButton("载入");
		button = new JButton("保存");
		TextAction action = new TextAction();
		load.addActionListener(action);
		button.addActionListener(action);
		JToolBar bar = new JToolBar();
		path = new JTextField();
		text = new JTextPane();
		text.setText("123");
		JScrollPane textpane = new JScrollPane(text);
		status = new JLabel("完成");
		bar.add(status);
		bar.setFloatable(false);
		this.add(path, gbcpath);
		this.add(load, gbcload);
		this.add(button, gbcread);
		this.add(textpane, gbctext);
		this.add(bar, gbcbar);
	}

	class TextAction implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			if ("载入".equals(e.getActionCommand())) {
				text.setText("载入中...");
				status.setText("正在载入...请稍等");
				Runnable rrun = () -> {
					final String data = filemanagerpanel.getFm().doAction("readfile", getPath().getText());
					SwingUtilities.invokeLater(() -> {
						text.setText(data);
						status.setText("载入完成");
					});
				};
				new Thread(rrun).start();
			} else {
				status.setText("正在保存...请稍等");
				Runnable nrun = () -> {
					final String data1 = filemanagerpanel.getFm().doAction("savefile", getPath().getText(),
							text.getText());
					if ("1".equals(data1)) {
						SwingUtilities.invokeLater(() -> filemanagerpanel
								.showRight(Common.getAbsolutePath(getPath().getText()), filemanagerpanel.getList()));
						while (true) {
							Thread.yield();
							if (filemanagerpanel.isRstatus()) {
								SwingUtilities.invokeLater(() -> {
									filemanagerpanel.getStatus().setText("保存成功");
									status.setText("保存成功");
								});
								break;
							}
						}
					} else {
						SwingUtilities.invokeLater(() -> {
							filemanagerpanel.getStatus().setText("保存失败");
							status.setText("保存失败");
						});
					}
				};
				new Thread(nrun).start();
			}
		}
	}
}
