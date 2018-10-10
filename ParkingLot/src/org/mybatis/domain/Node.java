package org.mybatis.domain;
import org.mybatis.domain.Car;

public class Node {
	
	private int Number;
	private Car carInfo;
	private Node L_Link;
	private Node R_Link;
	
	public int getNumber() { return Number; }
	public void setNumber(int number) { Number = number; }
	
	public Car getCarInfo() { return carInfo; }
	public void setCarInfo(Car carInfo) { this.carInfo = carInfo; }
	
	public Node getL_Link() { return L_Link; }
	public void setL_Link(Node l_Link) { L_Link = l_Link; }
	
	public Node getR_Link() { return R_Link; }
	public void setR_Link(Node r_Link) { R_Link = r_Link; }
	
	@Override
	public String toString() {
		return "Node [Number=" + Number + ", carInfo=" + carInfo + ", L_Link=" + L_Link + ", R_Link=" + R_Link + "]";
	}

}
