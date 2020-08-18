package net.customer.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CustomerDAO {
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
			System.out.println("자원 해제 실패! : " + e);
		}
	} // resourceClose()

	public String joinCheckEmail(String email) {
		String result="";
		try {
			 getConnection();
			
			 sql = "select email from customer where email=?";
			 
			 pstmt  = con.prepareStatement(sql);
			 pstmt.setString(1, email);
			
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()){
				 result = "notUsable";
			 } else {
				 result = "useable";			 				
			 }
		} catch (Exception e) {
			System.out.println("joinCheckEmail메소드 내부에서 예외 발생 : " + e);
		} finally {
			resourceClose();
		}
		return result; 		
	} // method

	public String joinCheckPhone(String phone) {
		String result="";
		try {
			 getConnection();
			
			 sql = "select phone from customer where phone=?";
			 
			 pstmt  = con.prepareStatement(sql);
			 pstmt.setString(1, phone);
			
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()){
				 result = "notUsable";
			 } else {
				 result = "useable";			 				
			 }
		} catch (Exception e) {
			System.out.println("joinCheckPhone메소드 내부에서 예외 발생 : " + e);
		} finally {
			resourceClose();
		}
		return result; 		
	} // method()
	
	public boolean insertCustomer(CustomerBean cb) {
		
		int result = 0;
		
		try {
			 getConnection();

			 sql="insert into customer(email, password, nickname, roadAddress, detailAddress, bname, phone, agreeAD) "
			 			     + "values(?, ?, ?, ?, ?, ?, ?,?)";

/*********암호 잠시 보류**************/			 
/* sql="insert into customer(customerNo, email, password, nickname, address, bname, phone, agreeAD) "
	 			     + "values(null, ?, hex(aes_encrypt(?,?)), ?, ?, ?, ?, ?)";
*/

			 
			
			 pstmt = con.prepareStatement(sql);
			 
			 pstmt.setString(1, cb.getEmail());
			 pstmt.setString(2, cb.getPassword());
			 pstmt.setString(3, cb.getNickname());
			 pstmt.setString(4, cb.getRoadAddress());
			 pstmt.setString(5, cb.getDetailAddress());
			 pstmt.setString(6, cb.getBname());
			 pstmt.setString(7, cb.getPhone());
			 pstmt.setString(8, cb.getAgreeAD());
			 
			/*// 암호화 전용
			 pstmt.setString(1, cb.getEmail());
			 pstmt.setString(2, cb.getEmail());
			 pstmt.setString(3, cb.getPassword());
			 pstmt.setString(4, cb.getNickname());
			 pstmt.setString(5, cb.getAddress());
			 pstmt.setString(6, cb.getBname());
			 pstmt.setString(7, cb.getPhone());
			 pstmt.setString(8, cb.getAgreeAD());
			 */
			 result = pstmt.executeUpdate();
			 
			 if(result != 0) {
				 return true;
			 }
			
		} catch (Exception e){
			System.out.println("insertCustomer메소드 내부에서 예외 발생 : " + e);
		} finally {
			resourceClose();
		}
		
		return false;
	} // method
/***********************************************************************************************/
	public boolean CheckCustomer(CustomerBean cb) { // 로그인 검사 
		
		boolean result = false;
		


		try {
			 getConnection();
			 
//			sql ="select aes_decrypt(unhex(password), ?) as email from customer where email = ? ";
			 sql ="select ? from customer where email = ? ";
			// mysql에 aes 암호 알고리즘(?) 사용 해서 원본 아이디 비밀번호 검사


			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, cb.getPassword());
			 pstmt.setString(2, cb.getEmail());
			 
			// rs.getString("email");
			 
			 
			 rs = pstmt.executeQuery();		 
			 rs.next();
	
//			 if(rs.getString("email") != null) { // 쿼리가 정상적으로 작동하면 실행, 암호가 다른상태로 select문 되었을때 안맞으면 
				 								// 무조건 null이 나옴  비밀번호 틀린것으로 간주,   
				 								// 그리고 암호화가 안된 데이터, 
				 								// 즉 개발자가 임의로 넣은 테스트 데이터는 이 검사 명령이 안먹힘
				 	
		if(rs.next() == true)		  {
				 System.out.println("유저 DB내용 : " +  cb.getEmail() );
				 
				 System.out.println("비밀번호 : " + cb.getPassword() + " 아이디 : " + cb.getEmail());
				 System.out.println("아이디 검사 성공! 사용자가 정보를 맞게 입력함");
				 System.out.println("검사 성공 rs.next내용" + rs.next());
				 
				/* *//************************************************************************************//*
				String sql2 = "select * from customer where email=?";
				pstmt = con.prepareStatement(sql2);
				pstmt.setString(1, cb.getEmail());
				System.out.println("sql2 이메일 쿼리 성공");
				rs = pstmt.executeQuery();
				rs.next();
				*//************************************************************************************//*
				
				cb.setNickname(rs.getString("nickname"));
				cb.setCustomerNo(rs.getString("customerNo"));

				*//************************************************************************************//*
				// 로그인시 정보 select 해서 가져오기
				System.out.println("customer dao에서 가져온 닉네임 :  " + cb.getNickname());
				System.out.println("customer dao에서 가져온 customerNo :  " + cb.getCustomerNo());
				*//************************************************************************************/
					 
				 result = true;
			 }else{
				 System.out.println("사용자가 비밀번호 틀림");
				 System.out.println("비밀번호 : " + cb.getPassword() + " 아이디 : " + cb.getEmail());
				 System.out.println("검사 실패 rs.next내용" + rs.next());
				 result = false;
			 }
			
		} catch (Exception e){
			System.out.println("CheckCustomer메소드 내부에서 예외 발생 : " + e);
		} finally {
			resourceClose();
		}
		
		return result;
	} // method

	public CustomerBean CustomerInformation (String email) {
		
		CustomerBean cb = new CustomerBean();
		 try {
			getConnection();
			String sql2 = "select * from customer where email=?";
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, email);
			System.out.println("sql2 CustomerInformation 쿼리 성공");
			rs = pstmt.executeQuery();
			rs.next();
			/************************************************************************************/
			
			cb.setNickname(rs.getString("nickname"));
			cb.setCustomerNo(rs.getString("customerNo"));

			/************************************************************************************/
			// 로그인시 정보 select 해서 가져오기
			System.out.println("-----------------------CustomerInformation ------------------------------------");
			System.out.println("customer dao에서 가져온 닉네임 :  " + cb.getNickname());
			System.out.println("customer dao에서 가져온 customerNo :  " + cb.getCustomerNo());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		return cb;
		
	}
/***********************************************************************************************/
	
	
} // class
