package com.bbcnews.automation.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CrunchifyCSVtoArrayList
{
    static String[] country = null;
    static String cvssplit = "&";
    public static void main(String[] args)
    {

        BufferedReader crunchifyBuffer = null;

        try {
            String crunchifyLine;
            crunchifyBuffer = new BufferedReader(new FileReader("/Users/ramakh02/Desktop/AppiumBBCNewsAndroid/session.csv"));

            // How to read file in java line by line?
            while ((crunchifyLine = crunchifyBuffer.readLine()) != null)
            {
                country = crunchifyLine.split(cvssplit);
                //System.out.println("Raw CSV data: " + crunchifyLine);
                System.out.println("Converted ArrayList data: " + crunchifyCSVtoArrayList(crunchifyLine) + "\n");


            }

            for(int i=0;i<=country.length;i++) {
                System.out.println("Stat's URL " + country[i]);
            }

        } catch (IOException e) {

        } finally {
            try {
                if (crunchifyBuffer != null) crunchifyBuffer.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }
    }




    // Utility which converts CSV to ArrayList using Split Operation
    public static ArrayList<String> crunchifyCSVtoArrayList(String crunchifyCSV) {
        ArrayList<String> crunchifyResult = new ArrayList<String>();

        if (crunchifyCSV != null) {
           // String[] splitData = crunchifyCSV.split("\\s*,\\s*");
            String[] splitData = crunchifyCSV.split(",");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    crunchifyResult.add(splitData[0].trim());
                }
            }
            //crunchifyResult.add(splitData[0]);
        }

        return crunchifyResult;
    }

}