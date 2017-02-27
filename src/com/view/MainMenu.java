package com.view;

import javax.swing.*;
import java.awt.*;
import static java.awt.Component.*;

public  class MainMenu {

  public JFrame frame;
  public JPanel informPanel =new JPanel();
  public JPanel inputPanel=new InputPanel();

    public MainMenu(){
        frame=new JFrame();
        informPanel.setLayout(new BoxLayout(informPanel,BoxLayout.Y_AXIS));
        JLabel label1=new JLabel("FUNCTION GENERATOR");
        label1.setAlignmentX(CENTER_ALIGNMENT);
        informPanel.add(label1);

        JLabel label2=new JLabel("______________________________");
        label2.setAlignmentX(CENTER_ALIGNMENT);
        informPanel.add(label2);

        JLabel label3=new JLabel("1.Generate Signal");
        informPanel.add(label3);

        JLabel label4=new JLabel("2.Plot Signal");
        informPanel.add(label4);

        informPanel.add(new JLabel("3.Export Signal"));
        informPanel.add(new JLabel("4.Exit"));

        frame.getContentPane().add(informPanel, BorderLayout.NORTH);
        frame.getContentPane().add(inputPanel,BorderLayout.CENTER);
        frame.setSize(400,200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}