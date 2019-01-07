package com.bbcnews.automation.testutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandPrompt {

    Process p;
    ProcessBuilder builder;

    /**
     * This method run command on windows and mac
     * @param command to run
     */
    public String runCommand(String command) throws InterruptedException, IOException
    {
        String os = System.getProperty("os.name");
        //System.out.println(os);


        p = Runtime.getRuntime().exec(command);

        // get std output
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line="";
        String allLine="";
        int i=1;
        while((line=r.readLine()) != null){
            //		System.out.println(i+". "+line);
            allLine=allLine+""+line+"\n";
            if(line.contains("Console LogLevel: debug"))
                break;
            i++;
        }
        return allLine;
    }


}
