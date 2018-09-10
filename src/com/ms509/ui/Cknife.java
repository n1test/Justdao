package com.ms509.ui;

import com.ms509.util.函数_初始化配置文件;

import java.awt.*;

/**
 *
 */
public class Cknife {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new 函数_初始化配置文件();
//			函数_设置程序皮肤();
            new MainFrame();
        });
    }

//    private static void 函数_设置程序皮肤() {
//        try {
//            Configuration config = new Configuration();
//            String skin = config.getValue("SKIN");
//            JFrame.setDefaultLookAndFeelDecorated(true);
//            JDialog.setDefaultLookAndFeelDecorated(true);
//            if (!"".equals(skin)) {
//                UIManager.setLookAndFeel(skin);
//            } else {
//                // substance皮肤带LookAndFeel结尾的其实与不带的是一样的，只是实现方式不同而已。
//                // 即SubstanceGraphiteLookAndFeel与GraphiteSkin是同一款皮肤
//
//                // 带LookAndFeel结尾的皮肤使用UIManager.setLookAndFeel
//                // UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
//
//                // 不带LookAndFeel结尾的皮肤使用SubstanceLookAndFeel.setSkin
//                SubstanceLookAndFeel.setSkin(new GraphiteSkin());
//                // SubstanceLookAndFeel.setSkin("org.pushingpixels.substance.api.skin.NebulaSkin");
//                String os = System.getProperty("os.name");
//                if (os.startsWith("Mac")) {
//                    SubstanceLookAndFeel.setFontPolicy(new DefaultMacFontPolicy());
//                } else if (os.startsWith("Linux")) {
//                    SubstanceLookAndFeel.setFontPolicy(new DefaultKDEFontPolicy());
//                }
//            }
//        } catch (Exception e) {
//            try {
//                JFrame.setDefaultLookAndFeelDecorated(true);
//                JDialog.setDefaultLookAndFeelDecorated(true);
//                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//            } catch (Exception e1) {
//            }
//        }
//
//    }
}
