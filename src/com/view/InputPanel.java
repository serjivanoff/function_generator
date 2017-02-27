package com.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.model.DataStorage.*;
import static com.controller.GUIController.*;

public class InputPanel extends JPanel{
    private int actionNumber;
    public JPanel panelButton;
    public  JTextField textField;
    JButton buttonOk=new JButton("Ok"), buttonCancel=new JButton("Cancel");

    public InputPanel(){
        actionNumber=0;
        panelButton =new JPanel();
        textField=new JTextField(3);

        panelButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //ActionListener for Ok button
        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            actionNumber = selectedAction();
             processSelectedAction();
        }});
        panelButton.add(buttonOk);
//        Listener for  "Cancel"
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                if(windowNumber!=0){gui.mainMenuWindow();}else{gui.exit();System.exit(0);}
            }
        });
        panelButton.add(buttonCancel);
        setLayout(new BorderLayout());
        add(textField,BorderLayout.NORTH);
        add(panelButton,BorderLayout.SOUTH);
    }
//        Retrieving int value from textField
    public int selectedAction() {
        int result=0;
        result=Integer.parseInt(textField.getText());
        return result;
    }

    public void processSelectedAction(){
        int action=actionNumber+10*windowNumber;
            switch(action){
//              Main menu window
                case 1:{gui.generateSignalWindow();break;}
                case 2:{gui.plottingXY();break;}
                case 3:{gui.exportSignal();break;}
                case 4:{gui.exit();System.exit(0);}
//              Generating signal window
                case 11:{gui.generateSinusoid();break;}
                case 12:{gui.generateSquare();break;}
                case 13:{gui.generateSawTooth();break;}
                case 14:{gui.mainMenuWindow();break;}
                default:{JOptionPane.showMessageDialog(gui.mainMenu.frame,
                        "\""+textField.getText()+"\""+" is not a valid choice. Please choose a value between 1 and 4",
                        "Message",
                        JOptionPane.WARNING_MESSAGE);}
            }
        textField.setText("");
    }
}
