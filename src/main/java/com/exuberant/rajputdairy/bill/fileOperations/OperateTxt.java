package com.exuberant.rajputdairy.bill.fileOperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OperateTxt {

    public static void triggerCode() {
        OperateTxt eo = new OperateTxt();
        String textFilepath = "E:\\Learn\\Java_Test\\Excel Operation\\ExcelOperations\\Test_Files\\";
        String writeFileName = "writeTest.txt";
        //String readFileName = "Test.txt";
        System.out.println("before calling");
        eo.appendTextFile(textFilepath + writeFileName, "Hello Rakesh");
    //eo.readAndPrintTextFile(textFilepath + readFileName);
    //eo.writeTextFile(textFilepath + writeFileName, "Hi");
    }

    /*Function to Read text File Row Wise*/
    public void readAndPrintTextFile(String filePathWithName) {
        BufferedReader rd;
        String line;

        try {
            System.out.println("FilePath" + filePathWithName);
            rd = new BufferedReader(new FileReader(filePathWithName));

            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
            rd.close();
        } catch (final IOException e) {
            System.out.println("Error reading file " + e.toString());
        }

    }
    /*Function to Read text File Row Wise*/

    public void writeTextFile(String filePathWithName, String content) {

        BufferedWriter bufferedWriter = null;

        try {

            //Construct the BufferedWriter object
            bufferedWriter = new BufferedWriter(new FileWriter(filePathWithName));

            //Start writing to the output stream
            bufferedWriter.append("Hello");
        /*bufferedWriter.write("Writing line one to file");
        bufferedWriter.newLine();
        bufferedWriter.write("Writing line two to file");
        bufferedWriter.append("Hello");*/

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void appendTextFile(String filePathWithName, String content) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePathWithName, true));
            out.write(content);
            this.addNewLine(filePathWithName, 1);
            out.close();
        } catch (IOException ignored) {
        }
    }

    public void addNewLine(String filePathWithName, int noOfNewLines) {
        try {

            BufferedWriter out = new BufferedWriter(new FileWriter(filePathWithName, true));
            for (int i = 0; i < noOfNewLines; i++) {
                out.write("\n");
            }
            out.close();
        } catch (IOException ignored) {
        }
    }
}

    
