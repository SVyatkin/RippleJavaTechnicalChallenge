package com.trustline.demo.dto;

import java.util.Date;

public class MoneyTransfer {
	double payment;
	String confirmationNumber;
	Date date;
	
	public MoneyTransfer(){
	}
	
	public MoneyTransfer(double payment, String confirmationNumber, Date date) {
		this.payment = payment;
		this.confirmationNumber = confirmationNumber;
		this.date = date;
	}
	
	public String getConfirmationNumber() {
		return confirmationNumber;
	}
	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
