package com.ms509.model;

import javax.swing.table.DefaultTableModel;

public class DatabaseTableModel extends DefaultTableModel {

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (this.getRowCount() == 0) {
			return super.getColumnClass(columnIndex);
		} else {
			return getValueAt(0, columnIndex).getClass();
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return column != 0;
	}
}
