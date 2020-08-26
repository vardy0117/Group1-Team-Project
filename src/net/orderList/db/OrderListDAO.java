package net.orderList.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class OrderListDAO {
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
			System.out.println("resourceClose Error! : " + e);
		}
	} // resourceClose()
	
	public int insertOrderList(OrderListBean oBean) {
int storeNo = 0;
		
		try {
			 getConnection();

			 sql="insert into orderList( , name, roadAddress, detailAddress, category, phone, "
			 		+ "storeHours, message, image, deliveryArea, regNo, sido) "
			 			     + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			 
			 pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			 
			
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
		
	}

}