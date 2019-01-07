package com.bbcnews.automation.base;

import java.io.IOException;

public class CharlesProxy {

    static String charlesconfig = "/charles/CharlesConfig.xml";
    static String charlesRecord = "curl -v -x http://localhost:8888 http://control.charles/recording/start";
    static String charlesSessionDownload = "curl -o session.chls -x http://localhost:8888 http://control.charles/session/download";
    static String charlesStop = "curl -v -x http://localhost:8888 http://control.charles/recording/stop";
    static String startCharles = "Charles -config";
    static String stopCharles = "killall Charles";

    Runtime rt = Runtime.getRuntime();


    public void startCharles() {

        try {
            //Process pr = rt.exec("Charles -config /Users/ramakh02/Desktop/AppiumBBCNewsAndroid/charles/CharlesConfig.xml");
            Process pr = rt.exec("Charles");
        } catch (IOException e) {

        }
    }


    public void stopCharles() {
        try {
            Process pr1 = rt.exec("killall Charles");
        } catch (IOException e) {

        }
    }

    public void converttoCSV() {
        try {
            Process pr6 = rt.exec("Charles convert session.chls session.csv");
            Thread.sleep(5000);
        } catch (IOException | InterruptedException e) {
        }

    }

    public void downloadCharlesSession() throws InterruptedException {
        try {
            Process pr4 = rt.exec("curl -o session.chls -x http://localhost:8888 http://control.charles/session/download");
            Thread.sleep(5000);
        } catch (IOException e) {
        }
    }

    public void charlesSessionStart() {
        try {
            Process pr2 = rt.exec("curl -v -x http://localhost:8888 http://control.charles/recording/start");
            Thread.sleep(5000);
        } catch (Exception e) {
        }
    }

    public void stopCharlesSession() {
        try {
            //Process p5 = Runtime.getRuntime().exec(charlesStop);
            Process pr5 = rt.exec("curl -v -x http://localhost:8888 http://control.charles/recording/stop");
        } catch (IOException e) {
        }

    }

//    public static void main(String args[]) throws InterruptedException {
//        CharlesProxy charlesproxy = new CharlesProxy();
//        charlesproxy.startCharles();
//        Thread.sleep(12000);
//        charlesproxy.charlesSessionStart();
//        Thread.sleep(12000);
//        charlesproxy.downloadCharlesSession();
//        charlesproxy.stopCharlesSession();
//        charlesproxy.converttoCSV();
//        charlesproxy.stopCharles();
//    }
}


//        try {
//
//
//
//            Process p = Runtime.getRuntime().exec("curl -v -x http://192.168.1.221:8888 http://control.charles/recording/start");
//
//
//            Process p1 = Runtime.getRuntime().exec("curl -o session.chls -x http://192.168.1.221:8888 http://control.charles/session/download");
//            p1.waitFor();
//            Thread.sleep(10000);
////            BufferedReader reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
////            String line = "";
////            while ((line = reader.readLine()) != null) {
////                System.out.println(line + "\n");
////            }
//            Process p2 = Runtime.getRuntime().exec("curl -v -x http://192.168.1.221:8888 http://control.charles/recording/stop");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

