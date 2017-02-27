package com.view;
import javax.swing.*;

public class GenerateSignal extends JPanel{
    public GenerateSignal() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label1 = new JLabel("GENERATE     SIGNAL");
        label1.setAlignmentX(CENTER_ALIGNMENT);
        add(label1);

        JLabel label2 = new JLabel("___________________________________________");
        label2.setAlignmentX(CENTER_ALIGNMENT);
        add(label2);

        JLabel label3 = new JLabel("1.Generate Sinusoid Signal");
        add(label3);

        JLabel label4 = new JLabel("2.Generate a Square Signal");
        add(label4);

        add(new JLabel("3.Generate a Sawtooth Signal"));
        add(new JLabel("4.Return"));
    }
}
