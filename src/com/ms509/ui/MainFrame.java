package com.ms509.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame {
    public static TabFrame tab;
    public static JFrame main;

    public MainFrame() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension d = t.getScreenSize();
        main = new JFrame("Just do it!!");
        main.setIconImage(new ImageIcon(getClass().getResource("/com/ms509/images/main.png")).getImage());
        main.setSize(1200, 680);
        main.setLocation((d.width - main.getWidth()) / 2, (d.height - main.getHeight()) / 2);
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main.getContentPane().setLayout(new BorderLayout(0, 0));
        tab = new TabFrame();
        tab.addPanel("list");
        main.add(tab);
        main.setVisible(true);
    }
}