package com.ms509.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * 通过重写方法实现直接读取数据并显示在表格
 * 继承AbstractTableModel没有add,remove等方法
 * 添加了add,remove方法
 * @author Chora
 */
public class ResultSetTableModel extends AbstractTableModel {
	private Vector<String> title;
	private Vector<Vector<String>> datas;

	public ResultSetTableModel(ResultSet rs) {
		try {
			ResultSetMetaData rsmd;
			rsmd = rs.getMetaData();
			int colcount = rsmd.getColumnCount();
			title = new Vector<>();
			for (int j = 1; j <= colcount; j++) {
				title.add(rsmd.getColumnName(j));
			}
			title.set(1, "Url");
			title.set(6, "Ip");
			title.set(7, "Time");
			datas = new Vector<>();
			while (rs.next()) {
				Vector<String> data;
				data = new Vector<>();
				for (int i = 1; i <= colcount; i++) {
					data.add(rs.getString(i));
				}
				datas.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getColumnName(int column) {
		return title.get(column);

	}

	@Override
	public int getColumnCount() {
		return title.size();
	}

	@Override
	public int getRowCount() {
		return datas.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return datas.get(row).get(column);
	}

	public void addRow(Vector<String> vector) {
		this.datas.add(vector);
		this.fireTableRowsInserted(datas.size() - 1, datas.size() - 1);
	}

	public void update(String id, Vector<String> vector) {
		this.datas.set(this.getId(id), vector);
		this.fireTableDataChanged();
	}

	public void remove(String id) {
		try {
			int row = this.getId(id);
			this.datas.remove(row);
			this.fireTableRowsDeleted(row, row);
		} catch (Exception e) {
		}
	}

	private int getId(String id) {
		int i = 0;
		for (Vector<String> data : this.datas) {
			if (data.get(0).equals(id)) {
				return i;
			}
			i++;
		}
		return i;
	}
}
