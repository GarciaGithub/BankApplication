package com.bank.model;

import java.time.LocalDate;

public class FundsTransfer {
	private int accountFrom;
	private int accountTo;
	private LocalDate dates;
	private double amount;
	private String status;
	public FundsTransfer() {
		super();
	}
	public FundsTransfer(int accountFrom, int accountTo, LocalDate dates, double amount, String status) {
		super();
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.dates = dates;
		this.amount = amount;
		this.status = status;
	}
	public int getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}
	public int getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}
	public LocalDate getDates() {
		return dates;
	}
	public void setDates(LocalDate dates) {
		this.dates = dates;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "FundsTransfer [accountFrom=" + accountFrom + ", accountTo=" + accountTo + ", dates=" + dates
				+ ", amount=" + amount + ", status=" + status + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountFrom;
		result = prime * result + accountTo;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dates == null) ? 0 : dates.hashCode());
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
		FundsTransfer other = (FundsTransfer) obj;
		if (accountFrom != other.accountFrom)
			return false;
		if (accountTo != other.accountTo)
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (dates == null) {
			if (other.dates != null)
				return false;
		} else if (!dates.equals(other.dates))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
