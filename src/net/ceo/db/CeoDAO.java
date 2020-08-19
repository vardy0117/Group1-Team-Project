package net.ceo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.customer.db.CustomerBean;


public class CeoDAO {
	 Connection con = null;
	 String sql = "";
	 PreparedStatement pstmt = null;
	 ResultSet rs = null;
	
	private Connection getConnection() throws Exception {
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/jspmodel2");
		con = ds.getConnection();
		return con;
	}
	
	private void resourceClose() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) rs.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ڿ����� ���� ���� �߻�!");
		}
	}
	
	public void connectTest() {
		try {
			con = getConnection();
			resourceClose();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CeoDAO.java -> connectTest() �޼��� ���� ����");
		}
		
	}

		public String joinCheckEmail(String email) {
		String result="";
		try {
			 getConnection();
			
			 sql = "select email from ceo where email=?";
			 
			 pstmt  = con.prepareStatement(sql);
			 pstmt.setString(1, email);
			
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()){
				 result = "notUsable";
			 } else {
				 result = "useable";			 				
			 }
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			resourceClose();
		}
		return result; 		
	} // method
	
	public boolean insertCeo(CeoBean cb) {
		
		int result = 0;
		
		try {
			 getConnection();

			 sql="insert into ceo(email, password, name, phone) "
			 			     + "values(?, ?, ?, ?)";
			 
			 pstmt = con.prepareStatement(sql);
			 
			 pstmt.setString(1, cb.getEmail());
			 pstmt.setString(2, cb.getPassword());
			 pstmt.setString(3, cb.getName());
			 pstmt.setString(7, cb.getPhone());
	
			 result = pstmt.executeUpdate();
			 
			 if(result != 0) {
				 return true;
			 }
			
		} catch (Exception e){
			System.out.println("insertCustomer inner Error : " + e);
		} finally {
			resourceClose();
		}
		

		
		
		return false;
		
		
	} // method
	/****************************************************************************/
	
	public boolean CheckCeo(CeoBean cbs) { // �α��� �˻� 
		
		boolean result = false;

		try {
			 getConnection();
			 
//			sql ="select aes_decrypt(unhex(password), ?) as email from customer where email = ? ";
			 sql ="select email from ceo where password = ? and email = ?";
			  pstmt = con.prepareStatement(sql);
			  pstmt.setString(1, cbs.getPassword());
			  pstmt.setString(2, cbs.getEmail());

			 rs = pstmt.executeQuery();		 

			  System.out.println("CEO DAO �Ѿ�� ��" +  cbs.getPassword());
			  System.out.println("CEO DAO �Ѿ�� ��" + cbs.getEmail());
			 
			// System.out.println("����� rs.next��" + rs.next());
			 
			if (rs.next()) {
				System.out.println("����� �α��� ����");
				result = true;

			}
	
			 

			
		} catch (Exception e){
			System.out.println("-----------------------------");
			System.out.println("check CEO����: " + e);
			System.out.println("cb.getPassword() : " + cbs.getPassword());
			System.out.println("cb.email() : " + cbs.getEmail());
			System.out.println("-----------------------------");
		} finally {
			resourceClose();
		}
		
		return result;
	} // method

	/****************************************************************************/

	

public CeoBean CeoInformation (String email) {
		
		CeoBean cb = new CeoBean();
		 try {
			getConnection();
			String sql2 = "select * from ceo where email=?";
			pstmt = con.prepareStatement(sql2);
			pstmt.setString(1, email);
			System.out.println("sql2 CustomerInformation ���� ����");
			rs = pstmt.executeQuery();
			rs.next();
			/************************************************************************************/
			
			cb.setCeoNo(rs.getString("ceoNo"));
			cb.setEmail(rs.getString("email"));

			/************************************************************************************/
			// �α��ν� ���� select �ؼ� ��������
			System.out.println("-----------------------Ceo Information ------------------------------------");
			System.out.println("customer dao���� ������ �г��� :  " + cb.getCeoNo());
			System.out.println("customer dao���� ������ customerNo :  " + cb.getEmail());
			
			
		} catch (Exception e) {
			System.out.println("����� ���� �Լ����� ������ �߻��ߴ� �޸� " + e);
			e.printStackTrace();
		}
		 
		
		return cb;
		
	}
	
}	
