package com.bank.model;

import java.time.LocalDate;

public class Account {
  private int num;
  private double balance;
  private String accountType;
  private LocalDate accdate;
  private String status; 
  private int customerId;
public Account() {
	super();
}

public Account(int num, double balance, String accountType, int customerId) {
	super();
	this.num = num;
	this.balance = balance;
	this.accountType = accountType;
	this.customerId = customerId;
}

public Account(double balance, String accountType, LocalDate accdate, String status) {
	super();
	this.balance = balance;
	this.accountType = accountType;
	this.accdate = accdate;
	this.status = status;
}
public Account(int num, double balance, String accountType, LocalDate accdate, String status, int customerId) {
	super();
	this.num = num;
	this.balance = balance;
	this.accountType = accountType;
	this.accdate = accdate;
	this.status = status;
	this.customerId = customerId;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public double getBalance() {
	return balance;
}
public void setBalance(double balance) {
	this.balance = balance;
}
public String getAccountType() {
	return accountType;
}
public void setAccountType(String accountType) {
	this.accountType = accountType;
}
public LocalDate getAccdate() {
	return accdate;
}
public void setAccdate(LocalDate accdate) {
	this.accdate = accdate;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public int getCustomerId() {
	return customerId;
}
public void setCustomerId(int customerId) {
	this.customerId = customerId;
}
public void setDeposit(double amount) {
	this.balance = this.balance + amount;
}
public void setWithDrawal(double amount) {
	this.balance = this.balance - amount;
}
@Override
public String toString() {
	return "Account [num=" + num + ", balance=" + balance + ", accountType=" + accountType + ", accdate=" + accdate
			+ ", status=" + status + ", customerId=" + customerId + "]";
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((accdate == null) ? 0 : accdate.hashCode());
	result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
	long temp;
	temp = Double.doubleToLongBits(balance);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + customerId;
	result = prime * result + num;
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Account other = (Account) obj;
	if (accdate == null) {
		if (other.accdate != null)
			return false;
	} else if (!accdate.equals(other.accdate))
		return false;
	if (accountType == null) {
		if (other.accountType != null)
			return false;
	} else if (!accountType.equals(other.accountType))
		return false;
	if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
		return false;
	if (customerId != other.customerId)
		return false;
	if (num != other.num)
		return false;
	if (status == null) {
		if (other.status != null)
			return false;
	} else if (!status.equals(other.status))
		return false;
	return true;
}


}
