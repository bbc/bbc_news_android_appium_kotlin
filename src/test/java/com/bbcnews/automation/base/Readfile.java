package com.bbcnews.automation.base;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Readfile {
    static BufferedReader br;

    public static void main(String argv[]) throws Exception {
        try {
            br = new BufferedReader(new FileReader("/Users/ramakh02/Desktop/AppiumBBCNewsAndroid/session.csv"));
            String s = null;
            while ((s = br.readLine()) != null && (s = s.trim()).length() > 0) {
                String f[] = s.split("\t");
                System.out.println(s);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Readfile.class.getName()).log(Level.SEVERE, null, ex);
        }


        // BufferedReader r = new BufferedReader(new FileReader("employeeData.txt"));
        br = new BufferedReader(new FileReader("/Users/ramakh02/Desktop/AppiumBBCNewsAndroid/session.csv"));
        int i = 1;
        try {

            // "Prime" the while loop
            String line = br.readLine();
            while (line != null) {

                // Print a single line of input file to console
                System.out.print("Line " + i + ": " + line);

                // Prepare for next loop iteration
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                i++;
            }
        } finally {
            // Free up file descriptor resources
            br.close();
        }
    }
}