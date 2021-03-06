package net.store.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.catalina.Store;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sun.javafx.fxml.BeanAdapter;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolverException;

import net.ceo.db.CeoBean;
import net.menu.db.MenuBean;


public class StoreDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	
	private Connection getConnection() throws Exception{
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/jspmodel2");
		con = ds.getConnection();
		
		return con;
	}
	
	public void resourceClose(){
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(con!=null) con.close();
		} catch (Exception e) {
			System.out.println("resourceClose! : " + e);
		}
	} // resourceClose()
	/***********************************************************************/
	public StoreBean getStoreInfo(int storeNo) {
		StoreBean storeInfo = new StoreBean();
		
		try {
			 con = getConnection();
			 
			 sql = "select* from store where storeNo=?";
			 
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, storeNo);
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()){
				storeInfo.setStoreNo(rs.getString("storeNo"));
				storeInfo.setCeoNo(rs.getString("ceoNo"));
				storeInfo.setName(rs.getString("name"));
				storeInfo.setRoadAddress(rs.getString("roadAddress"));
				storeInfo.setDetailAddress(rs.getString("detailAddress"));
				storeInfo.setCategory(rs.getString("category"));
				storeInfo.setPhone(rs.getString("phone"));
				storeInfo.setStoreHours(rs.getString("storeHours"));
				storeInfo.setMessage(rs.getString("message"));
				storeInfo.setImage(rs.getString("image"));
				storeInfo.setPoints(rs.getString("points"));
				storeInfo.setOrderCount(rs.getString("orderCount"));
				storeInfo.setDeliveryArea(rs.getString("deliveryArea"));
				storeInfo.setRegNo(rs.getString("regNo"));
				storeInfo.setSido(rs.getString("sido"));
			 }
			
		} catch (Exception e) {
			System.out.println("getStoreInfo() Inner Error : " + e);
			e.printStackTrace();
		} finally {
			resourceClose();
		}
		
		return storeInfo;
	}
	/***********************************************************************/
	//insertStore 硫����� 
	
	public int insertStore(StoreBean sbean) {
	
		int storeNo = 0;
		
		try {
			 getConnection();

			 sql="insert into store(ceoNo, name, roadAddress, detailAddress, category, phone, "
			 		+ "storeHours, message, image, deliveryArea, regNo, sido) "
			 			     + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 
			 pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			 
			 pstmt.setString(1, sbean.getCeoNo());
			 pstmt.setString(2, sbean.getName());
			 pstmt.setString(3, sbean.getRoadAddress());
			 pstmt.setString(4, sbean.getDetailAddress());
			 pstmt.setString(5, sbean.getCategory());
			 pstmt.setString(6, sbean.getPhone());
			 pstmt.setString(7, sbean.getStoreHours());
			 pstmt.setString(8, sbean.getMessage());
			 pstmt.setString(9, sbean.getImage());
			 pstmt.setString(10,sbean.getDeliveryArea());
			 pstmt.setString(11, sbean.getRegNo());
			 pstmt.setString(12, sbean.getSido());
			 
			
			 pstmt.executeUpdate();
			 
			 rs = pstmt.getGeneratedKeys();
			 
			 
			 if(rs.next()) {
				 storeNo = rs.getInt(1);
			 }
			 
			
		} catch (Exception e){
			System.out.println("insertStore inner Error : " + e);
		} finally {
			resourceClose();
		}
		
		return storeNo;
		
		
	} // method
	
	/***********************************************************************/
	public List<StoreBean> GetStore(String sido){ // 평상시 스토어 받아오는 함수
												 // more버튼 눌러서 가져오는 함수는 별도로 밑에 있음
		
		List<StoreBean> storelist = new ArrayList<StoreBean>();
		
		try {
			 con = getConnection();
			 
			 sql = "select * from store where sido=? limit 0,4"; // 초반부터 4개씩
			 
			 pstmt = con.prepareStatement(sql);
			 
			 pstmt.setString(1, sido);
			 System.out.println("시도 스토어 받은값 : DAO (sido) : " + sido);
			 
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()){
				 StoreBean mBean = new StoreBean();
					 mBean.setStoreNo(rs.getString("storeNo"));
					 mBean.setCeoNo(rs.getString("ceoNo"));
					 mBean.setName(rs.getString("name"));
					 mBean.setRoadAddress(rs.getString("roadAddress"));
					 mBean.setCategory(rs.getString("category"));
					 mBean.setStoreHours(rs.getString("storeHours"));
					 mBean.setSido(rs.getString("sido"));
					 mBean.setImage(rs.getString("image"));
					 mBean.setMessage(rs.getString("message"));
					 System.out.println("Get 스토어 호출");
					 // ��癒몄��� �ㅼ���� 媛��몄�ㅻ�� 嫄몃� ^^;;;;;;;;;;;;;;;;;;;;;;;;;
					 storelist.add(mBean);
			 }
			
		} catch (Exception e) {
			System.out.println("List<StoreBean> GetStore Error : "+e);
		} finally {
			resourceClose();
		}
		
		
		
		return storelist;
		
	}
	/***********************************************************************/

	public List<StoreBean>  getCeoStore(String ceoNo) {
		
		List<StoreBean> list = new ArrayList();
		
		try {
			con =getConnection();
			
			sql= "select storeNo, name , image from store  where ceoNo = ? ";
			
			pstmt = con.prepareStatement(sql);
			 
			 pstmt.setString(1, ceoNo);
			 
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()){
				 StoreBean mBean = new StoreBean();
					 mBean.setStoreNo(rs.getString("storeNo"));
//					 mBean.setCeoNo(rs.getString("ceoNo"));
					 mBean.setName(rs.getString("name"));
//					 mBean.setRoadAddress(rs.getString("roadAddress"));
//					 mBean.setCategory(rs.getString("category"));
//					 mBean.setStoreHours(rs.getString("storeHours"));
//					 mBean.setSido(rs.getString("sido"));
					 mBean.setImage(rs.getString("image"));
//					 mBean.setMessage(rs.getString("message"));
				
					 
					list.add(mBean);
			 }
			
		} catch (Exception e) {
			System.out.println("getCeoStore inner error"+e);
			
		}finally {
			resourceClose();
		}
		
	
		
		return list;
	}

	
	
	///
	public void updateStore(StoreBean bean) {
		try {
			con = getConnection();

			sql = "update store set name=?, roadAddress=?, detailAddress=?, category=?, phone=?, storeHours=?, message=?, image=?, deliveryArea=?, regNo=?, sido=? " +
					"where storeNo=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getRoadAddress());
			pstmt.setString(3, bean.getDetailAddress());
			pstmt.setString(4, bean.getCategory());
			pstmt.setString(5, bean.getPhone());
			pstmt.setString(6, bean.getStoreHours());
			pstmt.setString(7, bean.getMessage());
			pstmt.setString(8, bean.getImage());
			pstmt.setString(9, bean.getDeliveryArea());
			pstmt.setString(10, bean.getRegNo());
			pstmt.setString(11, bean.getSido());
			pstmt.setString(12, bean.getStoreNo());
			 
			pstmt.executeUpdate();
			 
			 
			
		} catch (Exception e) {
			System.out.println("updateStore inner error"+e);
			
		}finally {
			resourceClose();
		}
	}

//delete 


	public void deleteStore(String storeNo) {
		
		 try {
			con = getConnection();
			
			sql= "delete from store where storeNo=?";
			
			pstmt =  con.prepareStatement(sql);
			pstmt.setString(1, storeNo);
			
			pstmt.executeUpdate();
			
			
			
			
		} catch (Exception e) {
			System.out.println("deleteStore inner error" +e );
			e.printStackTrace();
		}finally {
			resourceClose();
		}
	
		
		
		
		
		
		
		
	}

	public int getStoreCount(String ceoNo) {
		
		int storeCount = 0;
		
		try {
			con =	getConnection();
			sql="select  count(storeNo) as storecount from store where ceoNo=? ";
		
			pstmt =  con.prepareStatement(sql);
			pstmt.setString(1,ceoNo);
			
			rs=	pstmt.executeQuery();
		
			if(rs.next()){
				storeCount = rs.getInt("storecount");
				System.out.println(storeCount+"랄랄라");
			}
			
		
		
		} catch (Exception e) {
			System.out.println(" getStoreCount inner error" +e );
			e.printStackTrace();
		}finally {
			resourceClose();
		}
	
		
		return storeCount;
		
		
		
	}




	public List<StoreBean> UserGetStore(String sido, String search){ // 검색용
		List<StoreBean> storelist = new ArrayList<StoreBean>();
		
		try {
			 con = getConnection();
			 
			 sql = "select * from store where sido like ? and name like ?";
			 
			 pstmt = con.prepareStatement(sql);
			 
			 pstmt.setString(1, "%" + sido + "%");
			 pstmt.setString(2, "%" + search + "%");
			 System.out.println("유저 검색 시도 스토어 받은값 : DAO (sido) : " + sido);
			 System.out.println("유저 검색 시도 검색 받은값 : DAO (search) : " + search);
			 
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()){
				 StoreBean mBean = new StoreBean();
					 mBean.setStoreNo(rs.getString("storeNo"));
					 mBean.setCeoNo(rs.getString("ceoNo"));
					 mBean.setName(rs.getString("name"));
					 mBean.setRoadAddress(rs.getString("roadAddress"));
					 mBean.setCategory(rs.getString("category"));
					 mBean.setStoreHours(rs.getString("storeHours"));
					 mBean.setSido(rs.getString("sido"));
					 mBean.setImage(rs.getString("image"));
					 mBean.setMessage(rs.getString("message"));
					 System.out.println("Get 스토어 호출");
					 // ��癒몄��� �ㅼ���� 媛��몄�ㅻ�� 嫄몃� ^^;;;;;;;;;;;;;;;;;;;;;;;;;
					 storelist.add(mBean);
			 }
			
		} catch (Exception e) {
			System.out.println("User GetStore Error : " + e);
		} finally {
			resourceClose();
		}
		
		
		
		return storelist;
		
	}


/*****************************************************************/
	public JSONArray GetMoreStore(String sido, int limit){ // 스토어에서 more버튼 눌렀을때
		 JSONArray StoreMoreArr = new JSONArray();
		 JSONObject StoreMoreJsonObj;
	// 	List<StoreBean> storelist = new ArrayList<StoreBean>();
		
		try {
			 con = getConnection();
			 
			 sql = "select * from store where sido = ? limit ?,4";
			 
			 pstmt = con.prepareStatement(sql);
			 
			 pstmt.setString(1, sido);
			 pstmt.setInt(2, limit);
			 
			 System.out.println("GetMoreStore sido값 : " + sido);
			 System.out.println("GetMoreStore limit값 : " + limit);
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()){
				 StoreMoreJsonObj = new JSONObject();
				 StoreMoreJsonObj.put("storeNo", rs.getString(1));
				 StoreMoreJsonObj.put("ceoNo", rs.getString(2));
				 StoreMoreJsonObj.put("name", rs.getString(3));
				StoreMoreJsonObj.put("roadAddress", rs.getString("roadAddress"));
				 StoreMoreJsonObj.put("category", rs.getString("category"));
				 StoreMoreJsonObj.put("storeHours", rs.getString("storeHours"));
				 StoreMoreJsonObj.put("sido",rs.getString("sido"));
				 StoreMoreJsonObj.put("image", rs.getString("image"));
			     StoreMoreJsonObj.put("message", rs.getString("message"));
				System.out.println("GetMoreStore Json스토어 호출");
					 
				StoreMoreArr.add(StoreMoreJsonObj);
				System.out.println("Json Object 내용 : " + StoreMoreJsonObj);
			
			 }
			
		} catch (Exception e) {
			System.out.println("GetMoreStore 에러발생 : " + e);
		} finally {
			resourceClose();
		}
		
		
		
		return StoreMoreArr;
		
	}

	public List<StoreBean> getStoreList(String category, String startNo, String currentHour, String orderSido, String orderBname) {
		//System.out.println("ajaxAction -> StoreDAO.getStoreList() 호출");
		//System.out.println("category : " + category + " / orderSido : " + orderSido + " / orderBname : " + orderBname + " / currentHour : " + currentHour);
		//System.out.println("startNo : " + startNo);
		List<StoreBean> storeList = new ArrayList<>();
		
		try {
			con = getConnection(); 
			sql = "select * from store where sido = ? and deliveryArea like ? and category = ? " +
				  "and ( (substr(storeHours,1,2) <= ? and substr(storeHours,5,2) > ?) or ( substr(storeHours,1,2) <= ? and substr(storeHours,1,2) > substr(storeHours,5,2) ) or (storeHours = '00시~00시') )" + " limit ?, 10";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, orderSido);
			pstmt.setString(2, "%"+orderBname+"%");
			pstmt.setString(3, category);
			pstmt.setInt(4, Integer.parseInt(currentHour));
			pstmt.setInt(5, Integer.parseInt(currentHour));
			pstmt.setInt(6, Integer.parseInt(currentHour));
			pstmt.setInt(7, Integer.parseInt(startNo));
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				StoreBean storeBean = new StoreBean();
				storeBean.setStoreNo(rs.getString(1));
				storeBean.setCeoNo(rs.getString(2));
				storeBean.setName(rs.getString(3));
				storeBean.setRoadAddress(rs.getString(4));
				storeBean.setDetailAddress(rs.getString(5));
				storeBean.setCategory(rs.getString(6));
				storeBean.setPhone(rs.getString(7));
				storeBean.setStoreHours(rs.getString(8));
				storeBean.setMessage(rs.getString(9));
				storeBean.setImage(rs.getString(10));
				storeBean.setPoints(rs.getString(11));
				storeBean.setOrderCount(rs.getString(12));
				storeBean.setDeliveryArea(rs.getString(13));
				storeBean.setRegNo(rs.getString(14));
				storeBean.setSido(rs.getString(15));
				storeBean.setReviewCount(rs.getString(16));
				storeList.add(storeBean);
			}
		} catch (Exception e) {
			System.out.println("getStoreList 에러 발생 : " + e);
		} finally {
			resourceClose();
		}
		
		return storeList;
	}

	// MyReview.jsp에서 가게이름을 얻어오기 위한 메소드
	public StoreBean getStoreName(String storeNo) {
		
		StoreBean sBean=null;
		try {
			con = getConnection();
			sql="select * from store where storeNo=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, storeNo);
			rs = pstmt.executeQuery();
			if(rs.next()){
				sBean = new StoreBean();
				
				sBean.setStoreNo(rs.getString(1));
				sBean.setCeoNo(rs.getString(2));
				sBean.setName(rs.getString(3));
				sBean.setRoadAddress(rs.getString(4));
				sBean.setDetailAddress(rs.getString(5));
				sBean.setCategory(rs.getString(6));
				sBean.setPhone(rs.getString(7));
				sBean.setStoreHours(rs.getString(8));
				sBean.setMessage(rs.getString(9));
				sBean.setImage(rs.getString(10));
				sBean.setPoints(rs.getString(11));
				sBean.setOrderCount(rs.getString(12));
				sBean.setDeliveryArea(rs.getString(13));
				sBean.setRegNo(rs.getString(14));
				sBean.setSido(rs.getString(15));
				
			}
		} catch (Exception e) {
			System.out.println("getStoreName() 내에서 예외 발생 ");
			e.printStackTrace();
		} finally {
			resourceClose();
		}
		return sBean;
		
	}

	public int CeoorderCheck(int orderNo, String ceoNo, int prepareTime) {
	
		int result = 0;
		try {
			con=getConnection();
			sql="UPDATE orderList a, store b SET "
					+ "a.orderCheck = 'T' ,a.prepareTime=? "
					+ "WHERE a.storeNo = b.storeNo and a.orderNo = ? "
					+ "and a.storeNo = "
					+ "(select storeNo from  (select distinct(a.storeNo) "
					+ "from orderList a, store b where a.storeNo = "
					+ "b.storeNo and b.ceoNo = ? and a.orderNo = ?) tmp)";
			// 이미 T값으로 바뀌어있는 주문에 대해서는 별도의 처리 안되어있음
			// 계속해서 서브쿼리 사용해야 될시 별도로 함수에서 빼서 우선실행후 다음작업 되게하도록 할예정
			
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, prepareTime);
			pstmt.setInt(2, orderNo);
			pstmt.setString(3, ceoNo);
			pstmt.setInt(4, orderNo);
		

			result = pstmt.executeUpdate();
			System.out.println("Ceo주문체크 감지 결과 : " + result);

		} catch (Exception e) {
			System.out.println("CeoorderCheck() 내에서 예외 발생 " + e);
			e.printStackTrace();
		} finally {
			resourceClose();
		}
		
		return result;

	}
	
	
	public int DeliveryCheck(int orderNo, String ceoNo) { // 배달 완료처리
		
		int result = 0;
		try {
			con=getConnection();
			sql="UPDATE orderList a, store b SET "
					+ "a.deliveryCheck = 'T'  "
					+ "WHERE a.storeNo = b.storeNo and a.orderNo = ? "
					+ "and a.storeNo = "
					+ "(select storeNo from  (select distinct(a.storeNo) "
					+ "from orderList a, store b where a.storeNo = "
					+ "b.storeNo and b.ceoNo = ? and a.orderNo= ? ) tmp)";
			// 이미 T값으로 바뀌어있는 주문에 대해서는 별도의 처리 안되어있음
			// 계속해서 서브쿼리 사용해야 될시 별도로 함수에서 빼서 우선실행후 다음작업 되게하도록 할예정
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderNo);
			pstmt.setString(2, ceoNo);
			pstmt.setInt(3, orderNo);
			
			result = pstmt.executeUpdate();
			System.out.println("Ceo주문체크 감지 결과 : " + result);

		} catch (Exception e) {
			System.out.println("DeliveryCheck() 내에서 예외 발생");
			e.printStackTrace();
		} finally {
			resourceClose();
		}
		
		return result;

	}
/*****************************************************************************/
	/*ceo번호가 접근하는 가게 번호에 대해서 ceo의 소유 것이 맞는지 확인하는 메서드*/
	public boolean CheckCeo(int orderNo, String ceoNo) {
	boolean checkceo = false;
	try {
		con = getConnection();
/*		sql="select distinct(a.storeNo) from orderList a, store b "
				+ "where a.storeNo =  b.storeNo and b.ceoNo = ?";*/
		sql ="select a.orderNo, b.storeNo, b.ceoNo from orderList a, store b "
				+ "where a.orderNo = ? and b.storeNo = a.storeNo and "
				+ "b.storeNo =  "
				+ "(select distinct(a.storeNo) from orderList a, "
				+ "store b where a.storeNo =  b.storeNo and b.ceoNo = ? and a.orderNo =  ?)";
		
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, orderNo);
		pstmt.setString(2, ceoNo);
		pstmt.setInt(3, orderNo);
		
		rs = pstmt.executeQuery();
		if(rs.next()){
			checkceo = true;
			System.out.println("CheckCeo rs.next 여부 " + checkceo);
		
	
		}else{
			System.out.println("checkCeo 실패  " + checkceo);
		}
	} catch (Exception e) {
		System.out.println("CheckCeo() 내에서 예외 발생 ");
		e.printStackTrace();
	} finally {
		resourceClose();
	}

	
	
		return checkceo;
	}

	public boolean isMine(int storeNo, int ceoNo) {
		boolean result = false;
		
		try {
			con = getConnection();
			sql = "select * from store where storeNo = ? and ceoNo = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, storeNo);
			pstmt.setInt(2, ceoNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = true;
			}
			
		} catch (Exception e) {
			System.out.println("isMine 메소드 내에서 에러 : " + e);
		} finally {
			resourceClose();
		}
		
		return result;
	}
	
	/*****************************************************************************/
	

	
	
}
