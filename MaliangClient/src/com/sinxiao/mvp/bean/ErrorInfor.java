package com.sinxiao.mvp.bean;

public class ErrorInfor {
	private int type;
	private boolean isConnected = true;
	private int descriptionRes, enameRes;
	private String description, ename;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public int getDescriptionRes() {
		return descriptionRes;
	}

	public void setDescriptionRes(int descriptionRes) {
		this.descriptionRes = descriptionRes;
	}

	public int getEnameRes() {
		return enameRes;
	}

	public void setEnameRes(int enameRes) {
		this.enameRes = enameRes;
	}

}
