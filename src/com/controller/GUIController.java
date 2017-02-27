package com.controller;
import com.view.GenerateSignal;
import com.view.InputPanel;
import com.view.MainMenu;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import static com.model.DataStorage.*;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class GUIController {
    public GenerateSignal generateSignal;
    public    MainMenu mainMenu;
    public   InputPanel inputPanel;
    public static GUIController gui;

  public GUIController(){
      this.inputPanel=new InputPanel();
      this.mainMenu=new MainMenu();
      this.generateSignal=new GenerateSignal();
  }

  public static void main(String[] args) {
      //need for javafx plotting,
      // see https://docs.oracle.com/javase/8/javafx/interoperability-tutorial/swing-fx-interoperability.htm
      SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
              gui=new GUIController();
              gui.mainMenuWindow();
          }
      });
    }

    public void mainMenuWindow(){
        windowNumber=0;
        generateSignal.setVisible(false);
        mainMenu.informPanel.setVisible(true);
        mainMenu.frame.getContentPane().add(mainMenu.informPanel, BorderLayout.NORTH);
    }
    public void generateSignalWindow(){
        windowNumber=1;
        mainMenu.informPanel.setVisible(false);
        generateSignal.setVisible(true);
        mainMenu.frame.getContentPane().add(generateSignal, BorderLayout.NORTH);
    }

    public void exit(){
        showMessageDialog(mainMenu.frame,"Good Bye!!");
    }

    //loop for entering parameters
    public boolean parametersEntered(){
        boolean isParametersEntered=false;
        String[] params=new String[signalParameters.length];
        while(!isParametersEntered){
            for(int i=0;i<params.length;i++){
                params[i]=showInputDialog(mainMenu.frame,messages[i]);
            }
            if(parametersValidated(params)){isParametersEntered=true;break;}
        }
      return isParametersEntered;
    }
    public void generateSawTooth() {
        if (parametersEntered()) {
//            Frequency, Hz                         is signalParameters[0]
//            Amplitude peak-to-peak, V             is signalParameters[1]
//            Phase shift, ms                       is signalParameters[2]
//            Vertical shift, V                     is signalParameters[3]
            signalParameters[2] *= 0.001;
            //tangent of angle of sawtooth:  k= Amplitude/Period
            double k = signalParameters[1] * signalParameters[0];

            int counter = 0;
            for (int i = 0; i <= 200; i++) {
                timeArray[i]=0.01*i;
                valuesArray[i] = k*(timeArray[i]+signalParameters[2])-signalParameters[1]*counter+signalParameters[3];
                if (valuesArray[i] > (signalParameters[1] / 2 + signalParameters[3])) {
                    valuesArray[i] =-signalParameters[1]/2 + signalParameters[3];
                    timeArray[i] = i == 0 ? 0 : timeArray[i - 1];
                    counter++;
                }
            }
            valuesAreReady =true;
        }
    }
    public void generateSquare(){
        if(parametersEntered()){
//            Frequency, Hz                         is signalParameters[0]
//            Amplitude peak-to-peak, V             is signalParameters[1]
//            Phase shift, ms                       is signalParameters[2]
//            Vertical shift, V                     is signalParameters[3]
            //taking  period from frequency (T=1/F)
            signalParameters[0]=1/signalParameters[0];
            //half size of peak-to-peak amplitude
            signalParameters[1]*=0.5;
            //milliseconds to seconds
            signalParameters[2]*=0.001;
            //how many pieces of time are in 1 period of signal
            int stepQty=(int)(signalParameters[0]/0.02);
            //the same with phase shift
            int stepQtyInPhaseShift=(int)(signalParameters[2]/0.01);
            //if true then function has it's maximum value else - minimum
            boolean up=false;
            for(int i=0;i<=200;i++) {
                if(up)valuesArray[i]=signalParameters[1]+signalParameters[3];else
                    valuesArray[i]=-signalParameters[1]+signalParameters[3];
                if(((i-stepQtyInPhaseShift)%stepQty)==0){
                    up=up==true?false:true;
                    if(i<200)timeArray[i+1]=timeArray[i];
                }
            }
            valuesAreReady =true;
        }
    }

     public void generateSinusoid(){
//            Frequency, Hz                         is signalParameters[0]
//            Amplitude peak-to-peak, V             is signalParameters[1]
//            Phase shift, ms                       is signalParameters[2]
//            Vertical shift, V                     is signalParameters[3]
     if(parametersEntered()){

        //half size of peak-to-peak amplitude
            signalParameters[1]*=0.5;
         //milliseconds to seconds
            signalParameters[2]*=0.001;
             for(int i=0;i<=200;i++) {
                 valuesArray[i]=signalParameters[1]*
                         Math.sin((signalParameters[0]*timeArray[i]-signalParameters[2])*2*Math.PI)+
                         signalParameters[3];
             }
            valuesAreReady =true;
        }
    }
    /*From task:
    Place the code to get the frequency, amplitude, phase shift and vertical shift in a method that verifies that the
    frequency and amplitude is positive before “returning” all four values back to main method.
    This method should be called three times (one for each type of signal to be generated)
     */
    public boolean parametersValidated(String [] arg){
        for(int i=0;i<arg.length;i++){
            signalParameters[i]=Double.parseDouble(arg[i]);
        }
        return (signalParameters[0]>=1)&&(signalParameters[1]>=0);
    }
//exporting data to D:\waveform.csv
    public void exportSignal(){
        if(!valuesAreReady){showMessageDialog(mainMenu.frame,"No signal to output. Generate a signal first.");return;}
        try{
        try(FileWriter writer=new FileWriter("d:/waveform.csv")){
            for(int i=0;i<=200;i++){
                writer.append(timeArray[i]+","+valuesArray[i]+"\n");
            }
            writer.flush();
        }}catch (IOException e){showMessageDialog(mainMenu.frame,"Something is wrong with exporting!!!!");}

    }
//creating new frame with javafx panel for plotting data
    public void plottingXY(){
        if(!valuesAreReady){showMessageDialog(mainMenu.frame,"No signal to plot. Generate a signal first.");return;}
        JFrame frame = new JFrame("");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }
   //plotting using LineChart
    private static void initFX(JFXPanel fxPanel) {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        LineChart<Number, Number> numberLineChart = new LineChart<>(x,y);
        numberLineChart.setCreateSymbols(false);
        numberLineChart.setTitle("WaveForm Plot");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("time(ms)");
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();

        for(int i=0; i<=200; i++){
            datas.add(new XYChart.Data(timeArray[i],valuesArray[i]));
        }

        series1.setData(datas);
        Scene scene = new Scene(numberLineChart, 600,600);
        numberLineChart.getData().add(series1);
        fxPanel.setScene(scene);
    }

}
