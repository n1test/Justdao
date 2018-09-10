package com.ms509.model;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/*
 * 通过单元格内容不同，设置不同图标
 * 未使用
 * @author Chora
 */
public class ExtendedTableCellRenderer extends JPanel implements TableCellRenderer {
	private String name;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		this.name = value.toString();
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
		if ("isdict".equals(this.name)) {
			g.drawImage(new ImageIcon(getClass().getResource("/com/ms509/images/folder.png")).getImage(), 0, 0, null);
		} else {
			g.drawImage(new ImageIcon(getClass().getResource("/com/ms509/images/file.png")).getImage(), 0, 0, null);
		}
	}
}
