package com.huarui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class GUI {
    int check = 0;
    public void GUI() {
        JFrame frame = new JFrame("Fastjson_fileread");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭操作
        frame.setSize(500, 600); // 设置窗口大小



        // 创建 JPanel 对象
        //JPanel panel = new JPanel();
        //panel.setLayout(null); // 设置为绝对布局

        // 创建选项卡面板
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);

/////////////////////////////////////////////////////////////////////

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // 设置为不可编辑
        // 创建滚动面板
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50, 50, 400, 200);
        //frame.add(scrollPane, BorderLayout.CENTER);
        panel1.add(scrollPane);
        printToTextArea(textArea, "Plsase chose chain and enter your commond");
//tab1 tab2
        JTextArea textArea2 = new JTextArea();
        textArea2.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        scrollPane2.setBounds(50, 50, 400, 200);
        panel2.add(scrollPane2);
        printToTextArea(textArea2, "Plsase chose chain and enter your commond");




/////////////////////////////////////////////////////////////////////////////////////////
        // 创建单选按钮
        JRadioButton radio1 = new JRadioButton("commons-io 2.x");
        radio1.setBounds(50, 260, 200, 20); // 设置按钮的位置和大小
        // 创建按钮组，确保单选按钮的互斥性
        ButtonGroup group = new ButtonGroup();
        group.add(radio1);
        // 为单选按钮添加事件监听器
        radio1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printToTextArea(textArea, "Chose commons-io 2.x");
                check = 1;
            }
        });
        // 将组件添加到面板
        //panel.add(radio1);
        panel1.add(radio1);
//////////////////////////////////////////////////////////////////////
        JLabel label1 = new JLabel("chain:");
        label1.setBounds(10, 255, 200, 30);
        //frame.add(label1);
        panel1.add(label1);
        JLabel label2 = new JLabel("file:");
        label2.setBounds(10, 300, 200, 30);
        //frame.add(label2);
        panel1.add(label2);
        JLabel label3 = new JLabel("如 /flag");
        label3.setBounds(50, 330, 200, 30);
        //frame.add(label3);
        panel1.add(label3);
        JLabel label4 = new JLabel("URL:");
        label4.setBounds(10, 365, 200, 30);
        //frame.add(label4);
        panel1.add(label4);
        JLabel label5 = new JLabel("如 http://127.0.0.1:8080/json");
        label5.setBounds(50, 395, 200, 30);
        //frame.add(label5);
        panel1.add(label5);
/////////////////////////////////////////////////////////////////////

        JTextField filename = new JTextField();
        filename.setBounds(50, 300, 400, 30); // 设置输入框的位置和大小
        //frame.add(filename);
        panel1.add(filename);



        JTextField url = new JTextField();
        url.setBounds(50, 370, 400, 30); // 设置输入框的位置和大小
        //frame.add(url);
        panel1.add(url);
/////////////////////////////////////////////////////////////////////

        // 创建 JButton 对象
        JButton button = new JButton("Click Me!");
        button.setBounds(200, 450, 100, 30); // 设置按钮的位置和大小
        button.addActionListener(new ActionListener() { // 为按钮添加事件监听器
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check == 1) {
                    coi coi = new coi();
                    String filenametext = filename.getText();
                    String Urltext = url.getText();
                    coi.runcoi(textArea,filenametext,Urltext);
                }
                System.out.println("Running...");
            }
        });
        panel1.add(button); // 将按钮添加到 JPanel
///////////////////////////////////////////////////////////////////////////////////
        // 将面板添加到选项卡
        tabbedPane.addTab("Fileread", panel1);
        tabbedPane.addTab("RCE", panel2);
        // 将 JPanel 添加到 JFrame 的内容面板
//        frame.getContentPane().add(panel);
//        // 设置窗口可见
//        frame.setVisible(true);
        frame.add(tabbedPane);

        // 设置窗口可见
        frame.setVisible(true);

    }
    public static void printToTextArea(JTextArea textArea, String text) {
        textArea.append(text + "\n");
    }


}
