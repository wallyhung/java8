package org.pojo;

public class Imei {
	
	String imei;
	long time;
	String day;
	
	public Imei() {
	}
	

	public Imei(String imei, long time) {
		this.imei = imei;
		this.time = time;
	}


	public Imei(String imei, String day) {
		super();
		this.imei = imei;
		this.day = day;
	}


	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}


	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
}
