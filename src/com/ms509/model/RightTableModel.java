package com.ms509.model;

import java.awt.Rectangle;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.ms509.ui.panel.FileManagerPanel;
import com.ms509.util.Common;
import com.ms509.util.Safe;
import com.ms509.util.TreeMethod;

/**
 * 通过重写getColumnClass方法，在单元格直接设置图标
 * @author Chora
 */
public class RightTableModel extends AbstractTableModel {
	private boolean isEdit = false;
	private FileManagerPanel filemanagerpanel;
	private String ret = "-1";

	public FileManagerPanel getFilemanagerpanel() {
		return filemanagerpanel;
	}

	public void setFilemanagerpanel(FileManagerPanel filemanagerpanel) {
		this.filemanagerpanel = filemanagerpanel;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	private Vector<String> title;
	private Vector<Vector<Object>> datas;

	public RightTableModel(String[] filedicts) {

		datas = new Vector<>();
		title = new Vector<>();
		title.add("是否");
		title.add("文件");
		title.add("时间");
		title.add("大小");
		title.add("属性");
		for (String tmp : filedicts) {
			String[] s;
			String[] t = tmp.split("\t");
			if (t.length == 4) {
				s = t;
			} else {
				s = "./	1970-00-00 00:00:00	0	0\n../	1970-00-00 00:00:00	0	0".split("\t");
			}
			String name = s[0];
			Vector<Object> data = new Vector<>();
			if (!"./".equals(name) && !"../".equals(name)) {
				if (name.charAt(s[0].length() - 1) == '/') {
					// data.add("isdict");
					data.add(new ImageIcon(getClass().getResource("/com/ms509/images/folder.png")));
					data.add(name.substring(0, name.length() - 1));
					data.add(s[1]);
					data.add(s[2]);
					data.add(s[3]);
				} else {
					// data.add("isfile");
					data.add(new ImageIcon(getClass().getResource("/com/ms509/images/file.png")));
					data.add(name);
					data.add(s[1]);
					data.add(s[2]);
					data.add(s[3]);
				}
				datas.add(data);
			}
		}

	}

	@Override
	public Class<? extends Object> getColumnClass(int columnIndex) {
		if (this.getRowCount() == 0) {
			return super.getColumnClass(columnIndex);
		} else {
			return getValueAt(0, columnIndex).getClass();
		}
	}

	@Override
	public String getColumnName(int column) {
		return this.title.get(column);
	}

	@Override
	public int getColumnCount() {
		return this.title.size();
	}

	@Override
	public int getRowCount() {
		return datas.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return datas.get(row).get(column);
	}

	public void addRow(Vector<Object> vector) {
		this.datas.add(vector);
		this.fireTableRowsInserted(vector.size() - 1, vector.size() - 1);
	}

	@Override
	public void setValueAt(Object aValue, final int rowIndex, int columnIndex) {
		if (columnIndex == 1) {
			AtomicReference<Vector<Object>> data = new AtomicReference<>(new Vector<>());
			String oldname = "";
			String newname = "";
			String newfolder = "";
			final int col = columnIndex;
			final Object newdata = aValue;
			String tmp = filemanagerpanel.getPath().getText();
			String path = Common.getAbsolutePath(tmp);
			for (int i = 0; i < this.getColumnCount(); i++) {
				if (i == 1) {
					oldname = this.datas.get(rowIndex).get(i).toString();
					newname = aValue.toString();
					newfolder = oldname;
					data.get().add(aValue);
				} else {
					data.get().add(this.datas.get(rowIndex).get(i));
				}
			}
			if (newfolder.equals("newFolder")) {
				if (aValue.equals(newfolder) || aValue.equals("")) {
					this.remove(this.getRowCount() - 1);
				} else {
					Vector<Object> exists = new Vector<>();
					for (Vector<Object> vec : this.datas) {
						exists.add(vec.get(1));
					}
					if (exists.contains(aValue)) {
						this.remove(this.getRowCount() - 1);
						JTable list = filemanagerpanel.getList();
						int row = exists.indexOf(aValue);
						list.setRowSelectionInterval(row, row);
						Rectangle rect = list.getCellRect(row, 0, true);
						list.scrollRectToVisible(rect);
						filemanagerpanel.getStatus().setText("目录已存在");
					} else {
						final String np = path + aValue.toString() + Safe.SYSTEMSP;
						Runnable newrun = () -> {
							ret = "-1";
							filemanagerpanel.getStatus().setText("正在新建文件夹...请稍等");
							ret = filemanagerpanel.getFm().doAction("newdict", np);
							SwingUtilities.invokeLater(() -> {
								if (ret.equals("1")) {
									datas.get(rowIndex).setElementAt(newdata, col);
									fireTableCellUpdated(rowIndex, col);
									JTree tree = filemanagerpanel.getTree();
									String[] trees = new String[] { getValueAt(getRowCount() - 1, 1).toString() };
									TreePath tp = tree.getSelectionPath();
									DefaultMutableTreeNode select = (DefaultMutableTreeNode) tp.getLastPathComponent();
									select.setAllowsChildren(true);
									TreeMethod.addTree(trees, select, filemanagerpanel.getModel());
									int row = getRowCount() - 1;
									filemanagerpanel.getList().setRowSelectionInterval(row, row);
									filemanagerpanel.getStatus().setText("新建文件夹成功");
								} else {
									remove(getRowCount() - 1);
									filemanagerpanel.getStatus().setText("新建文件夹失败");
								}
							});
						};
						new Thread(newrun).start();
					}
				}
			} else {
				final String op = path + oldname;
				final String np = path + newname;
				final String roldname = oldname;
				final String rnewname = newname;
				Runnable rerun = () -> {
					ret = "-1";
					filemanagerpanel.getStatus().setText("正在重命名...请稍等");
					ret = filemanagerpanel.getFm().doAction("rename", op, np);
					SwingUtilities.invokeLater(() -> {
						if (ret.equals("1")) {
							datas.get(rowIndex).setElementAt(newdata, col);
							fireTableCellUpdated(rowIndex, col);
							try {
								JTree tree = filemanagerpanel.getTree();
								DefaultMutableTreeNode dmt = TreeMethod.searchNode(filemanagerpanel.getRoot(),
										roldname);
								assert dmt != null;
								filemanagerpanel.getModel()
										.valueForPathChanged(tree.getSelectionPath().pathByAddingChild(dmt), rnewname);
								// 非model模式更新界面需要执行updateUI
								// dmt.setUserObject(rnewname);
								// tree.updateUI();
								filemanagerpanel.getList().setRowSelectionInterval(rowIndex, rowIndex);
							} catch (Exception ignored) {
							} finally {
								filemanagerpanel.getStatus().setText("重命名成功");
							}
						} else {
							filemanagerpanel.getStatus().setText("重命名失败");
						}
					});
				};
				new Thread(rerun).start();
			}
		} else {
			datas.get(rowIndex).setElementAt(aValue, columnIndex);
			fireTableCellUpdated(rowIndex, columnIndex);
		}
		// SwingUtilities.updateComponentTreeUI(null);
		filemanagerpanel.updateUI();
	}

	private void remove(int id) {
		this.datas.remove(id);
		this.fireTableRowsDeleted(id, id);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return this.isEdit;
	}
}
