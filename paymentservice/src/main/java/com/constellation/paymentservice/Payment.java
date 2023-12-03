package com.constellation.paymentservice;

public class Payment {
	
	private long cardNo;
	private String name;
	private int expMo;
	private int expYe;
	private int ccv;
	private int userId;
	

	public Payment(long cardNo, String name, int expMo, int expYe, int ccv, int userId) {
		super();
		this.cardNo = cardNo;
		this.name = name;
		this.expMo = expMo;
		this.expYe = expYe;
		this.ccv = ccv;
		this.userId = userId;
	}
	public Payment() {
		
	}
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	@Override
	public String toString() {
		return "Payment information is [cardNo=" + cardNo + ", name=" + name + ", expMo=" + expMo + ", expYe=" + expYe + ", ccv=" + ccv
				+ ", userId=" + userId + "]";
	}
	

}
