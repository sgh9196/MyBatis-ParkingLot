package default_pack;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.domain.Car;

public class MyBatisController {

	private static final String resource = "resources/mybatis/config-mybatis.xml";
	private static final String parameter = "org.mybatis.persistence.";

	private static SqlSessionFactory sqlSessionFactory;
	private static SqlSession sqlSession;
	
	private static List<Car> list;

	public MyBatisController() {

	}

	/* 마이바티스 셋팅 */
	public void setMyBatis() {

		try {
			/* 마이바티스 설정 XML 파일 경로 */
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* 트랜잭션 Open */
	public static void transactionOpen() {
		sqlSession = sqlSessionFactory.openSession();
	}

	/* 트랜잭션 Close */
	public static void transactionClose() {
		sqlSession.close();
	}

	/* 출자 데이터 Insert */
	public void insertAccounts(Car car) {

		try {

			transactionOpen();

			sqlSession.insert(parameter + "ChargeMapper.insert", car);
			sqlSession.commit();
			transactionClose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 전체 정산 List */
	public void allSelectCalculate() {
		
		try {
			
			transactionOpen();
			
			list = sqlSession.selectList(parameter+"ChargeMapper.allSelect");

			transactionClose();
			
			listPrint();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void typeSelectCalculate(Car car) {
		
		try {
			
			transactionOpen();
			
			list = sqlSession.selectList(parameter+"ChargeMapper.typeSelect", car);
			
			transactionClose();
			
			listPrint();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void numberSelectCalculate(Car car) {
		
		try {
			
			transactionOpen();
			
			list = sqlSession.selectList(parameter+"ChargeMapper.numberSelect", car);
			
			transactionClose();
			
			listPrint();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/* 조회 된 목록 출력 */
	public void listPrint() {
		
		for(Car car:list) {
			
			System.out.print("\tCarNumber : " + car.getCarNumber() + "\t");
			System.out.print("\tCarType : " + car.getCarType() + "\t");
			System.out.print("\tPay : " + car.getPay() + "\t");
			System.out.print("\tCarOutTime : " + car.getCarOutTime() + "\n");
			
		}
		
	}
	
	/* ACCOUNTS TABLE DROP */
	public static void dropTable() {
		
		transactionOpen();
		sqlSession.update(parameter + "ChargeMapper.dropTable");
		transactionClose();
		
	}
	
	
	/* 정산 Table 생성 */
	public static void createTable() {
		
		transactionOpen();
		sqlSession.update(parameter + "ChargeMapper.createParkingTable");
		transactionClose();

	}
}
