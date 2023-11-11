package com.constellation.backend.payment;

public class Payment {
	
	private long cardNo;
	private String name;
	private int expMo;
	private int expYe;
	private int ccv;
	private String userName;
	

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getCardNo() {
		return cardNo;
	}
	public void setCardNo(long cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getExpMo() {
		return expMo;
	}
	public void setExpMo(int expMo) {
		this.expMo = expMo;
	}
	public int getExpYe() {
		return expYe;
	}
	public void setExpYe(int expYe) {
		this.expYe = expYe;
	}
	public int getCcv() {
		return ccv;
	}
	public void setCcv(int ccv) {
		this.ccv = ccv;
	}

}
