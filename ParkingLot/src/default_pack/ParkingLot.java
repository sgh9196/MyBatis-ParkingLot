package default_pack;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.mybatis.domain.Car;
import org.mybatis.domain.Node;

public class ParkingLot {

	private Scanner sc = new Scanner(System.in);

	/* 현재 시간 */
	public String nowTime() {

		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("hh:mm:ss");
		String str = dayTime.format(new Date(time));

		return str;

	}

	/* 차종 별 기본 금액 */
	public int defaultPrice(String carType) {

		int pay = 0;

		switch (carType) {
		case "소형":
			pay = 500;
			break;
		case "중형":
			pay = 1000;
			break;
		case "승합":
			pay = 1500;
			break;
		}

		return pay;

	}

	/* 자리 검사 */
	public Node overLapCheck(Node p, int mod) {

		System.out.println();

		LinkedList link = new LinkedList();

		p = (mod >= 5) ? link.frontMove(p, mod) : link.rearMove(p, mod);

		return p;

	}
	
	/* 랜덤 주차장 자리 찾기 */
	public static int rand(String carType, int parking) {

		Random rd = new Random();

		switch(carType) {
			case "소형":
				parking = rd.nextInt(20);
				break;
			case "중형":
				parking = rd.nextInt(20) + 20;
				break;
			case "승합":
				parking = rd.nextInt(10) + 40;
				break;
		}
		
		return parking;

	}

	public boolean updateCarIn(String carType, Node[] rear, Node[] front, Node head) {
		
		int parking = 0;
		
		parking = rand(carType, parking);
		
		Node p = null;
		int mod = parking % 10;
		int value = (int) (parking * 0.1);
		
		while(true) {
			
			p = (mod >= 5) ? rear[value] : front[value];
			p = overLapCheck(p, mod);
			
			if(p.getNumber()==parking) {
				
				Car car = new Car();
				
				car.setCarRandNumber();
				
				if (new LinkedList().carNumberSearch(head, car.getCarNumber())) {
					System.out.println("차량 번호 중복");
					return false;
				}
				else {
					car.setCarType(carType);
					car.setCarInTime(nowTime());
					car.setPay(defaultPrice(carType));
					car.setParkingNumber(parking);
				}
				
				if(p.getCarInfo().getCarNumber()==null) {
					
					System.out.println("[" + car.getCarNumber() + "]차량 " + "[" + (4-value+1) + "]층 [" + (mod+1) + "]번째 자리");
					System.out.println("1. 확정   2. 취소\n>> ");
					
					if(sc.nextInt()==1) {
						p.setCarInfo(car);
						return true;
					}
					else {
						System.out.println("취소");
						return false;
					}
					
					
					
				}
				else {
					parking = rand(carType, parking);
					mod = parking % 10;
					value = (int) (parking * 0.1);
					System.out.println("중복이므로 다시 랜덤을 돌립니다.");
				}
			}
		}
		
	}
	
	/* 출차 */
	public void updateOutCar(Node head) {

		Node p = null;
		boolean tmp = false;

		System.out.print("차량번호 >> ");
		String carNumber = sc.next();

		LinkedList link = new LinkedList();

		p = link.searchNode(head, carNumber);

		if (p != null) {

			System.out.println("차량이 존재합니다. 출차 하시겠습니까?");
			System.out.println("1. 출차   2. 취소");

			tmp = (sc.nextInt() == 1) ? true : false;

		}

		if (tmp) {

			Node n = p;
			int inTime = Integer.parseInt(n.getCarInfo().getCarInTime().substring(3, 5));
			int outTime = Integer.parseInt(nowTime().substring(3, 5));
			
			//System.out.println("inTime >> " + inTime); System.out.println("outTime >> " +
			//outTime); System.out.println("Pay >> " + n.getCarInfo().getPay());
			 
			p.getCarInfo().setPay((((outTime - inTime)) + 1) * n.getCarInfo().getPay());
			p.getCarInfo().setCarOutTime(nowTime());
			new MyBatisController().insertAccounts(p.getCarInfo());

			// System.out.println(Integer.parseInt(car.getCarInTime().substring(3, 5)) -
			// Integer.parseInt(car.getCarOutTime().substring(3, 5)));
			
			int parking = p.getNumber();
			
			int mod = parking % 10;
			int value = (int) (parking * 0.1);
			
			String layer = "";
			
			layer = (mod<=4) ? "front" : "rear";
			
			System.out.println("[" + p.getCarInfo().getCarNumber() + "]차량 " + "[" + (4-value+1) + "]층 [" + layer + "]로 출차");
			
			p.setCarInfo(new Car());

			System.out.println("출차 완료!");

		}

		else {
			System.out.println("존재하는 차량이 없습니다.");
		}

	}
	
	/* 전체 정산 */
	public void allCalculate() {
		
		try {
			
			MyBatisController mybatis = new MyBatisController();
			
			mybatis.allSelectCalculate();
			
		} catch(Exception e) {
			System.out.println("allCalculate Err >> " + e.getMessage());
		}
		
	}
	
	/* 차량별 정산 */
	public void typeCalculate(String type) {
		
		try {
			
			MyBatisController mybatis = new MyBatisController();
			
			Car car = new Car();
			car.setCarType(type);
			
			mybatis.typeSelectCalculate(car);
			
		} catch(Exception e){ 
			System.out.println("typeCalculate Err >> " + e.getMessage());
		}
		
}
	
	public void numberCalculate() {
		
		System.out.print("CarNumber >> ");
		String number = "%" + sc.next();
		
		try {
			
			MyBatisController mybatis = new MyBatisController();
			
			Car car = new Car();
			car.setCarNumber(number);
			
			mybatis.numberSelectCalculate(car);
			
		} catch(Exception e) {
			System.out.println("numberCalculate Err >> " + e.getMessage());
		}
		
		
	}

}