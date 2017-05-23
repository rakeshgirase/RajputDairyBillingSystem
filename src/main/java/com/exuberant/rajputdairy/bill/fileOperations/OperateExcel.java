package com.exuberant.rajputdairy.bill.fileOperations;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.sql.*;

public class OperateExcel {

    static {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static void triggerCode(String args[]) {
        OperateExcel oe = new OperateExcel();
        oe.getExcelDataByQuery("select * from [Sheet1$]","O.T.");
        OperateTxt.triggerCode();
    }

    public ResultSet getExcelDataByQuery(String query, String excelName) {
        Connection conn;
        Statement stmt;
        ResultSet rs;

        try {
            /*After jdbc:odbc: we have written exceltest which is nothing but the 
            ODBC Connection Created with the excel File using following steps
             * Control panel -> Administrative Tools -> 
            system dsn -> Select Microsoft Excel driver -> Select Workbook
             * -> name to the dsn with datasource name to be given afer "jdbc:odbc:"      
             */
            excelName = "jdbc:odbc:" + excelName;
            conn = DriverManager.getConnection(excelName, "", "");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);            
            return rs;            
        } catch (Exception e) {
            System.err.println(e);
            return null;
        } /*finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
                rs = null;
                stmt = null;
                conn = null;
            } catch (Exception e) {
                System.out.println("Exception Occurred " + e.toString());
            }
        }*/
    }
    
    public void printExcelDataByQuery(String sql, String excelName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            /*After jdbc:odbc: we have written exceltest which is nothing but the 
            ODBC Connection Created with the excel File using following steps
             * Control panel -> Administrative Tools -> 
            system dsn -> Select Microsoft Excel driver -> Select Workbook
             * -> name to the dsn with datasource name to be given afer "jdbc:odbc:"      
             */
            String fileName = "jdbc:odbc:" + excelName;
            conn = DriverManager.getConnection(fileName, "", "");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString("Name") +
                        " " + rs.getString("Total_Milk") + " " +
                        rs.getString("Amount"));
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("Exception Occurred " + e.toString());
            }
        }
    }
    /*
    Argument Format
    filepath = E:/Learn/Java_Test/book1.xls
    cellAddr = A3
     */

    public void getExcelDataByCell(String filepath, String cellAddr) {
        try {
            File file = new File(filepath);
            Workbook wb = Workbook.getWorkbook(file);
            Sheet sheet = wb.getSheet(0);
            Cell a3 = sheet.getCell("A3");
            System.out.println(a3.getContents());
            wb.close();
        } catch (IOException e) {
            System.out.println("IOException");
        } catch (BiffException e) {
            System.out.println("BiffException");
        }

    }
}    
