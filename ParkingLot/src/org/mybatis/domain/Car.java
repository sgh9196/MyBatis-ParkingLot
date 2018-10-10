package org.mybatis.domain;

import java.util.Random;

public class Car {

	private String carNumber;
	private String carType;
	private String carInTime;
	private String carOutTime;
	private int Pay;
	private int parkingNumber;

	/* 랜덤 차량 번호 */
	public void setCarRandNumber() {

		Random rd = new Random();

		String[] midArray = { "가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하" };

		this.carNumber = String.valueOf(rd.nextInt(99) + 1);
		this.carNumber += midArray[rd.nextInt(midArray.length - 1)];
		this.carNumber += String.valueOf(rd.nextInt(9000) + 1000);
		
		
		
		if(this.carNumber.length()==6) {
			this.carNumber = "0" + carNumber;
		}
		
		
		
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarInTime() {
		return carInTime;
	}

	public void setCarInTime(String carInTime) {
		this.carInTime = carInTime;
	}

	public String getCarOutTime() {
		return carOutTime;
	}

	public void setCarOutTime(String carOutTime) {
		this.carOutTime = carOutTime;
	}

	public int getPay() {
		return Pay;
	}

	public void setPay(int pay) {
		Pay = pay;
	}

	public int getParkingNumber() {
		return parkingNumber;
	}

	public void setParkingNumber(int parkingNumber) {
		this.parkingNumber = parkingNumber;
	}

	@Override
	public String toString() {
		return "Car [carNumber=" + carNumber + ", carType=" + carType + ", carInTime=" + carInTime + ", carOutTime="
				+ carOutTime + ", Pay=" + Pay + ", parkingNumber=" + parkingNumber + "]";
	}

}