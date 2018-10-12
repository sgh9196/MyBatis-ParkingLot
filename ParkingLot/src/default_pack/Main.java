package default_pack;
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

	private static String[] carType = { "소형", "중형", "승합" };
	
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

	// 몇번 차량 몇층 프런트로 이동합니다.
	// 몇번 차량 몇층 프런트로 출차합니다.
	
	/* 입차 Menu */
	public static boolean carIn() {
		
		
		int num = 0;
		String type = "";
		boolean tmp = false;
		
		System.out.print("1. 소형   2. 중형   3. 승합\n>> ");
		num = sc.nextInt();
		
		if(num<=3) {
			type = carType[num-1];
			boolean parkingTmp = parkingLot.updateCarIn(type, rear, front, head);
			
			if(parkingTmp) {
				tmp = true;
			}
		}
		else {
			System.out.println("존재하지 않은 차량 종류입니다.");
		}
		
		return tmp;
		
	}

	public static String typeCalculate() {
		
		System.out.print("1. 소형   2. 중형   3. 승합\n>> ");
		
		return carType[sc.nextInt()-1];
		
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