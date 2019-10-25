package com.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.utils.BaseHolder;
import com.view.MainWindow;

import javax.swing.*;

/**
 * 扫雷程序运行入口
 *
 * @author zumin
 */

public class MainApp {

    /**
     * 创建MainWindowSwing窗口进行显示
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        //获得核心容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        //将核心容器存到BaseHolder中
        ac.getBean("baseHolder", BaseHolder.class).setApplicationContext(ac);
        //显示swing窗口
        SwingUtilities.invokeLater(() -> ac.getBean("viewWindow", MainWindow.class).setVisible(true));
    }
}
