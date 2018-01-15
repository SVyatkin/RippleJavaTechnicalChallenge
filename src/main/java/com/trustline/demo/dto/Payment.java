package com.trustline.demo.dto;

import java.util.Date;

public class Payment {
	String confirmationNumber;
	String confirmationNumberBeneficar;
	Date date;
	double payment;
	double balance;
	
	public Payment(String confirmationNumber, String confirmationNumberBeneficar, double payment, double balance, Date date) {
		this.confirmationNumber = confirmationNumber;
		this.confirmationNumberBeneficar = confirmationNumberBeneficar;
		this.payment = payment;
		this.balance = balance;
		this.date = date;
	}
	public String getConfirmationNumber() {
		return confirmationNumber;
	}
	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}
	public String getConfirmationNumberBeneficar() {
		return confirmationNumberBeneficar;
	}
	public void setConfirmationNumberBeneficar(String confirmationNumberBeneficar) {
		this.confirmationNumberBeneficar = confirmationNumberBeneficar;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
