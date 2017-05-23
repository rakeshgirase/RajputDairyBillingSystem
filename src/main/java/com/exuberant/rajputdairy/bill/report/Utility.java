/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exuberant.rajputdairy.bill.report;

import java.util.Calendar;

/**
 *
 * @author 13411
 */
public class Utility {

    public static String addCharacter(String str, int occurance) {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < occurance; i++) {
            content.append(str);
        }
        return content.toString();
    }  
    public static String getLastMonth(){
        String DATE_FORMAT = "MMM-yy";
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, -1);
        return sdf.format(c1.getTime());
    }
    
    public static String getMonth(){
        String DATE_FORMAT = "MMM-yy";
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance();
        return sdf.format(c1.getTime());
    }
    
    public static void main(String[] args) {
		System.out.println(getCurrentMonth());
	}
    public static String getCurrentMonth(){
        String DATE_FORMAT = "MMM-yy";
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat(DATE_FORMAT);
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, 0);
        return sdf.format(c1.getTime());
    }
}
