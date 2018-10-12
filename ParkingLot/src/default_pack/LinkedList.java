package default_pack;
import org.mybatis.domain.Car;
import org.mybatis.domain.Node;

public class LinkedList {

	/* 노드 생성 */
	public Node createNode() {

		Node n = new Node();

		n.setNumber(0);
		n.setCarInfo(null);
		n.setL_Link(null);
		n.setR_Link(null);

		return n;

	}

	/* 노드 추가 */
	public void AddNode(Node head, int number) {

		Node L, p, n;

		n = createNode();
		n.setNumber(number);
		n.setCarInfo(new Car());
		L = p = head;

		if (L.getR_Link() == null) {

			L.setR_Link(n);
			n.setR_Link(L.getR_Link());
			n.setL_Link(L.getR_Link());

		} else {

			do {
				p = p.getR_Link();
			} while (p.getR_Link() != L.getR_Link());

			p.setR_Link(n);
			n.setL_Link(p);
			n.setR_Link(L.getR_Link());
			L.getR_Link().setL_Link(n);

		}

	}
	
	public boolean carNumberSearch(Node head, String carNumber) {
		
		boolean tmp = false;
		
		Node p = head;
		
		do {
			
			p = p.getR_Link();
			
			if(p.getCarInfo()!=null) {
				
				String num = p.getCarInfo().getCarNumber();
				
				if(num!=null && num.equals(carNumber)) {
					tmp = true;
				}
			}
			
		} while(p.getR_Link()!=head.getR_Link());
		
		return tmp;
		
	}
	
	/* 이중 큐 검색 */
	public Node searchNode(Node head, String carNumber) {

		Node p = head;

		do {

			p = p.getR_Link();
			
			
			//System.out.println(carNumber.substring(3, 7));
			
			if(p.getCarInfo().getCarNumber()!=null) {
				if(carNumber.equals(p.getCarInfo().getCarNumber().substring(3, 7))) {
					return p;
				}
			}
			
			
			
		} while (p.getR_Link() != head.getR_Link());

		return null;

	}
	
	/* 끝나기 위한 메서드 */
	public boolean nullSearch(Node head) {
		
		Node p;
		
		p = head;
		
		do {
			
			p = p.getR_Link();
			
			if(p.getCarInfo().getCarNumber()!=null) {
				return false;
			}
			
		} while(p.getR_Link() != head.getR_Link());
		
		return true;
		
	}
	
	/* 층별 rear, front 연결 */
	public void layerHead(Node head, Node[] front, Node[] rear) {

		Node L, p;

		L = p = head;

		int cFront = 0;
		int cRear = 0;

		do {

			p = p.getR_Link();

			if (p.getNumber() % 10 == 0) {
				front[cFront++].setR_Link(p);
			} else if (p.getNumber() % 10 == 9) {
				rear[cRear++].setL_Link(p);
			}

		} while (p.getR_Link() != L.getR_Link());

	}

	/* front에서 이동 */
	public Node frontMove(Node p, int mod) {

		System.out.println("GoTo Rear Index ... ");
		/* parking 자리 까지 이동 (Left) */
		for (int i = 0; i < (10 - mod); i++) {
			p = p.getL_Link();
			// System.out.print(p.getNumber() + " ");
		}
		// System.out.println();

		return p;

	}
	
	/* rear에서 이동 */
	public Node rearMove(Node p, int mod) {

		System.out.println("GoTo Front Index ... ");
		/* parking 자리 까지 이동 (Right) */
		for (int i = 0; i <= mod; i++) {
			p = p.getR_Link();
			// System.out.print(p.getNumber() + " ");
		}
		// System.out.println();

		return p;

	}

	/* Node 출력 */
	public void PrintNode(Node head) {

		Node L, p;
		int count = 5;

		L = p = head;

		do {

			p = p.getR_Link();

			int val = p.getNumber();

			if (val % 10 == 0) {
				System.out.println();
				System.out.print(count + "F\t");
				count--;
			}

			if (p.getCarInfo().getCarNumber() == null) {
				System.out.print("0\t\t");
			} else {
				System.out.print("* " + p.getCarInfo().getCarNumber() + "\t");
			}

		} while (p.getR_Link() != L.getR_Link());

		System.out.println("\n");

	}

	/* 층별 Head 확인 */
	public void rightPrint(Node head) {

		Node p;

		p = head;

		for (int i = 0; i < 10; i++) {
			p = p.getR_Link();
			System.out.print(p.getNumber() + "\t");
		}
		System.out.println("");
	}

	/* 층별 Head 확인 */
	public void leftPrint(Node head) {

		Node L, p;

		L = p = head;

		for (int i = 0; i < 10; i++) {
			p = p.getL_Link();
			System.out.print(p.getNumber() + "\t");
		}

		System.out.println("");

	}

}