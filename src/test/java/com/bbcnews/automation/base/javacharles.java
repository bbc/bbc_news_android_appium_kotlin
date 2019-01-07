package com.bbcnews.automation.base;

import org.testng.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class javacharles {



    public static <string> void main(String[] args) throws FileNotFoundException {

         String newsstats[]=
        {
                    "ns_site=test",
                    "ns_ap_an=news",
                    "ns_ap_pn=android",
                    "ns_ap_pv=7.0",
                    "name=news.appstart.page",
                    "ns_ap_bi=bbc.mobile.news.uk.internal",
                    "ns_ap_ver=5.5.0.47",
                    "app_name=news"
        };

        String csvFile = "/Users/ramakh02/Desktop/AppiumBBCNewsAndroid/session.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String cvssplit = "&";
        String[] country = null;
        String[] staturl = null;
        ArrayList<String> list = new ArrayList();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null)
            {
                // use comma as separator
                country = line.split(cvsSplitBy);
                staturl = country[0].split("&");
                System.out.println("Stat's URL " + country[0]);
                for(int i=3;i<staturl.length;i++)
                {
                    //System.out.println("The New Generated Stats " + staturl[i]);
                    for (int j = 0; j < newsstats.length; j++) {
                        if (staturl[i].equalsIgnoreCase(newsstats[j]))
                        {
                            list.add(staturl[i].toString());
                            String matchedstats = staturl[i].toString();
                            System.out.println("The New Generated Stats " + matchedstats);//list.add(staturl[i].toString()));
                        }
                    }

                }

            }
           // retrieveValuesFromListMethod1(list);






        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

       // retrieveValuesFromListMethod2(list);


    }


    public static void retrieveValuesFromListMethod1(ArrayList list)
    {
        Iterator itr = list.iterator();
        while(itr.hasNext())
        {
            System.out.println("Names retireved are "+itr.next());
        }
    }

    public static void retrieveValuesFromListMethod2(ArrayList list)
    {
        //Retrieving values from list
        int size = list.size();
        for(int i=0;i<size;i++)
        {
            System.out.println("ns_ap_an are"+ list.get(3));
            System.out.println("ns_ap_pn are"+ list.get(4));
            System.out.println("name"+ list.get(8));
            System.out.println("ns_ap_ev"+ list.get(10));
        }
    }

}