package com.ms509.util;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class TreeMethod {

	public static String makePath(TreePath tp) {

		String[] deal = tp.toString().substring(1, tp.toString().length() - 1).split(", ");
		StringBuilder path = new StringBuilder();
		for (String tmp : deal) {
			path.append(tmp).append(Safe.SYSTEMSP);
		}
		return path.substring(1, path.length());
	}

	public static void makeIndexTree(String paths[], String[] adds, DefaultMutableTreeNode root, JTree tree) {
		DefaultMutableTreeNode node = root;
		root.setAllowsChildren(true);
		int i;
		for (i = 0; i < paths.length; i++) {
			if (!"".equals(paths[i])) {
				DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(paths[i]);
				node.add(tmp);
				node = tmp;
				if (i == paths.length - 1) {
					for (String add : adds) {
						DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(add);
						dmtn.setAllowsChildren(false);
						node.add(new DefaultMutableTreeNode(dmtn));
					}
				}
			}
		}
		// 根目录获得焦点
		DefaultTreeSelectionModel dsmodel = new DefaultTreeSelectionModel();
		dsmodel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		dsmodel.setSelectionPath(new TreePath(node.getPath()));
		tree.setSelectionModel(dsmodel);
	}

	public static void addTree(String[] trees, DefaultMutableTreeNode parent, DefaultTreeModel model) {
		ArrayList<String> al = new ArrayList<>();
		for (Enumeration<?> e = parent.children(); e.hasMoreElements();) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();
			al.add(n.getUserObject().toString());
		}

		for (String tree : trees) {
			if (!al.contains(tree)) {
				DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(tree);
				tmp.setAllowsChildren(false);
				model.insertNodeInto(tmp, parent, parent.getChildCount());
			}
		}
	}

	public static void expandAll(JTree tree, TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		if (expand) {
			// tree.expandPath(parent);
			tree.scrollPathToVisible(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	public static DefaultMutableTreeNode searchNode(DefaultMutableTreeNode root, String name) {
		DefaultMutableTreeNode node;
		Enumeration<?> e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			node = (DefaultMutableTreeNode) e.nextElement();
			if (name.equalsIgnoreCase(node.getUserObject().toString())) {
				return node;
			}
		}
		return null;
	}
}