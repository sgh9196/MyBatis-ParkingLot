import java.util.Random;
import java.util.Scanner;

import org.mybatis.domain.Node;

public class Main {

	private static Scanner sc = new Scanner(System.in);

	private static LinkedList linkedList;
	private static Node head;

	private static Node[] front = new Node[5];
	private static Node[] rear = new Node[5];

	private static ParkingLot parkingLot;

	public static void mybatisSet() {

		try {

			MyBatisController mybatis = new MyBatisController();
			mybatis.setMyBatis();
			mybatis.dropTable();
			MyBatisController.createTable();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setQueue() {

		linkedList = new LinkedList();
		head = new Node();

		head = linkedList.createNode();

		for (int i = 0; i < 50; i++) {
			linkedList.AddNode(head, i);
		}

	}

	/* fornt, rear 연결 */
	public static void setLayer() {

		for (int i = 0; i < rear.length; i++) {

			front[i] = new Node();
			rear[i] = new Node();

			front[i] = linkedList.createNode();
			rear[i] = linkedList.createNode();

		}

		linkedList.layerHead(head, front, rear);

	}

	/* 랜덤 주차장 자리 찾기 */
	public static int rand(int value) {

		Random rd = new Random();

		return rd.nextInt(value);

	}

	/* 입차 Menu */
	public static void carIn() {

		int parking = 0;
		String carType = "";
		boolean overLap = false;

		while (!overLap) {

			System.out.print("1. 소형   2. 중형   3. 승합\n>> ");

			switch (sc.nextInt()) {
			case 1:
				parking = rand(20);
				carType = "소형";
				break;
			case 2:
				parking = rand(20) + 20;
				carType = "중형";
				break;
			case 3:
				parking = rand(10) + 40;
				carType = "승합";
				break;
			}

			int index = (int) (parking * 0.1);

			overLap = (parking % 10 >= 5) ? parkingLot.updateCarIn(rear[index], parking, carType)
					: parkingLot.updateCarIn(front[index], parking, carType);

		}

	}

	public static String typeCalculate() {
		
		String[] type = { "소형", "중형", "승합" };
		
		System.out.print("1. 소형   2. 중형   3. 승합\n>> ");
		
		return type[sc.nextInt()-1];
		
	}
	
	public static void Calculate() {
		
		while(true) {
			
			System.out.print("정산\n1. 전체   2. 차량별   3. 차량번호별   4. 이전으로\n>> ");
			
			switch(sc.nextInt()) {
				case 1:
					parkingLot.allCalculate();
					break;
				case 2:
					parkingLot.typeCalculate(typeCalculate());
					break;
				case 3:
					parkingLot.numberCalculate();
					break;
				default:
					return;
			}
			
		}
		
	}
	
	public static void systemExit() {
		
		if(linkedList.nullSearch(head)) {
			System.exit(0);
		}
		else {
			System.out.println("아직 빠져나가지 않은 차량이 존재합니다.");
		}
		
	}
	
	public static void main(String[] args) {

		mybatisSet();
		setQueue();
		setLayer();

		parkingLot = new ParkingLot();

		while (true) {

			linkedList.PrintNode(head);
			System.out.print("1. 입차   2. 출차   3. 정산   4. 종료\n>> ");

			switch (sc.nextInt()) {
			case 1:
				carIn();
				break;
			case 2:
				parkingLot.updateOutCar(head);
				break;
			case 3:
				Calculate();
				break;
			case 4:
				systemExit();
				break;
			}

		}

	}

}



// 전체, 자량별, 차량번호별