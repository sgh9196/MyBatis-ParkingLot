import java.text.SimpleDateFormat;
import java.util.Date;
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

	/* 입차 */
	public boolean updateCarIn(Node head, int parking, String carType) {

		Node p = null;

		boolean tmp = false;

		int mod = parking % 10;
		int value = (int) (parking * 0.1);

		p = head;
		p = overLapCheck(head, mod);

		if ((p.getNumber() == parking) && (p.getCarInfo().getCarNumber() == null)) {

			System.out.println("1. 확정   2. 취소\n>> ");

			if (sc.nextInt() == 1) {

				Car car = new Car();

				car.setCarRandNumber();
				car.setCarType(carType);
				car.setCarInTime(nowTime());
				System.out.println(car.getCarInTime());
				car.setPay(defaultPrice(carType));
				car.setParkingNumber(parking);

				p.setCarInfo(car);

				tmp = true;

			} else {
				tmp = false;
			}

		}

		return tmp;

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