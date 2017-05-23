/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exuberant.rajputdairy.bill.agency;

import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;

/**
 * 
 * @author 13411
 */
public class Customer {

	public static HashMap<Headers, Integer> indexes = new HashMap<Headers, Integer>();

	static {
		indexes.put(Headers.TITLE, 6);
		indexes.put(Headers.NAME, 7);
		indexes.put(Headers.DATE, 1);
		indexes.put(Headers.TOTAL_MILK, 8);
		indexes.put(Headers.RATE, 9);
		indexes.put(Headers.AMOUNT, 10);
		indexes.put(Headers.BALANCE, 11);
		indexes.put(Headers.TOTAL_BILL, 12);
	}
	public String cust_name = "";
	public String bill_date = "";
	public String total_milk = "";
	public String rate = "";
	public String amount = "";

	public String getBill_date() {
		return bill_date;
	}

	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
	}

	public String balance = "";
	public String total_bill = "";

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getTotal_bill() {
		return total_bill;
	}

	public void setTotal_bill(String total_bill) {
		this.total_bill = total_bill;
	}

	public String getTotal_milk() {
		return total_milk;
	}

	public void setTotal_milk(String total_milk) {
		this.total_milk = total_milk;
	}

	public Customer(ResultSet rs) {
		try {
			this.setCust_name(rs.getString("Title") + " "
					+ rs.getString("Name"));
			this.setBill_date(rs.getString("Date"));
			this.setTotal_milk(rs.getString("total_milk"));
			this.setRate(rs.getString("Rate"));
			this.setAmount(rs.getString("Amount"));
			this.setBalance(rs.getString("Balance"));
			this.setTotal_bill(rs.getString("Total Bill"));
		} catch (Exception e) {
			System.out.println("Exception while setting the Cust prop"
					+ e.getMessage());
		}
	}

	public Customer(Row row) {
		try {
			this.setCust_name(row.getCell(indexes.get(Headers.TITLE))
					.getRichStringCellValue().toString()
					+ " "
					+ row.getCell(indexes.get(Headers.NAME))
							.getRichStringCellValue().toString());
			this.setBill_date(row.getCell(indexes.get(Headers.DATE))
					.getRichStringCellValue().toString());
			this.setTotal_milk(Double.toString(row.getCell(
					indexes.get(Headers.TOTAL_MILK)).getNumericCellValue()));
			this.setRate(Double.toString(row.getCell(indexes.get(Headers.RATE))
					.getNumericCellValue()));
			this.setAmount(Double.toString(row.getCell(
					indexes.get(Headers.AMOUNT)).getNumericCellValue()));
			this.setBalance(Double.toString(row.getCell(
					indexes.get(Headers.BALANCE)).getNumericCellValue()));
			this.setTotal_bill(Double.toString(row.getCell(
					indexes.get(Headers.TOTAL_BILL)).getNumericCellValue()));
		} catch (Exception e) {
			System.out.println("Exception while setting the Cust prop"
					+ e.getMessage());
			e.printStackTrace();
		}
	}

}
