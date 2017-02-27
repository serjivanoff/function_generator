package com.model;

public class DataStorage {
    /*
  from task:
  Show an appropriate message if user tries to plot or export data before it is generated
 so valuesAreReady =false if data not generated yet
     */
   public static boolean valuesAreReady =false;
    //using for navigating through menu
    public static int windowNumber;

    public static final String[]messages={"Frequency (in Hertz; minimum of 1Hz)",
                           "Amplitude (peak-to-peak) (in Volts; minimum of 0V)",
                           "Phase shift (in milliseconds; minimum of 0ms)",
                           "Vertical shift (in Volts); minimum of 0V"};
    /*
                        User-defined parameters
       from task:
        1. Frequency (in Hertz; minimum of 1Hz)
        2. Amplitude (in Volts; minimum of 0V)
        3. Phase shift (in milliseconds; minimum of 0ms)
        4. Vertical shift (in Volts)

    Frequency, Hz                         is signalParameters[0]
    Amplitude peak-to-peak, V             is signalParameters[1]
    Phase shift, ms                       is signalParameters[2]
    Vertical shift, V                     is signalParameters[3]
     */
    public static double[] signalParameters=new double[4];
//Array of time pieces from 0 to 2s with step of 0.01s

    public static final double[] timeArray=new double[201];
    static{
        for(int i=0;i<=200;i++)timeArray[i]=0.01*i;
    }
//Array of computed signal values. Each of it is corresponding to it's time in timeArray
    public static double[] valuesArray =new double[201];


}
