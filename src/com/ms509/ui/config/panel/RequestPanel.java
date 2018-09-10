package com.ms509.ui.config.panel;

import com.ms509.ui.ConfigDialog;
import com.ms509.util.Common;
import com.ms509.util.Configuration;
import com.ms509.util.Safe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * 设置请求头的panel
 * */
public class RequestPanel extends JPanel {
    private JTextPane content;
    private JRadioButton close;

    public RequestPanel() {
        this.setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        content = new JTextPane();
        JScrollPane scontent = new JScrollPane(content);
        contentPanel.add(scontent);
        JPanel buttonPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        JRadioButton open = new JRadioButton("开启");
        close = new JRadioButton("关闭");
        group.add(open);
        group.add(close);
        Configuration config = new Configuration();
        String status = config.getValue("REQUEST_STATUS");
        String data = config.getValue("REQUEST_DATA");
        if ("1".equals(status)) {
            open.setSelected(true);
        } else {
            close.setSelected(true);
        }
        content.setText(data);
        JButton ok = new JButton("确定");
        JButton cancel = new JButton("取消");
        buttonPanel.add(open);
        buttonPanel.add(close);
        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        cancel.addActionListener(e -> ConfigDialog.cdialog.setVisible(false));
        ok.addActionListener(new ButtonAction());
        this.add(contentPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    class ButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Configuration config = new Configuration();
            String status;
            String data = content.getText().trim();
            if (close.isSelected() || "".equals(data)) {
                status = "0";
            } else {
                status = "1";
            }
            Safe.REQUEST_DATA = data;
            Safe.REQUEST_STATUS = status;
            config.setValue("REQUEST_DATA", data);
            config.setValue("REQUEST_STATUS", status);
            Common.map.clear();    // 静态Map，如果不清空，则会一直往Map里注入数据。
            Common.getData();
            JOptionPane.showMessageDialog(ConfigDialog.cdialog, "请求头设置成功", "提示", JOptionPane.PLAIN_MESSAGE);
        }

    }
}
