<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
       <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
       
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>마이페이지 주문내역</title>
</head>
<style>
	#mainDiv{
		margin-top: 80px;
		width:1000px;
		min-height:600px;
		    width: auto;
	}
	#mainDiv li{
		list-style: none;
		float: left;
		border: 1px solid blue;
		margin-right: 50px;
	}
	
	#storeBox {
		float: left;
		width: 49%;
		border: 1px solid #d9d9d9;
		border-radius: 12px;
		/*cursor: pointer; */
		margin-right: 10px;
	}
	
	#storeBox td {
		padding-left: 15px;
	}
	
</style>
<body>	

<div id="mainDiv">
<%-- 	<c:set var="customer" value="${orderlist}"/> --%>
	
	 고객번호 : ${customerNo} <br>
	 
	 <c:forEach var="orderlistarray" items="${orderlist}" varStatus="status"> 
	<div id="storeBox">
	 <table>
	 	<tr>
	 	<td>
	 
	
	 주문번호 : ${orderlistarray.orderNo} <br>
	 주문메뉴 : ${orderlistarray.name} <br>
	 가격 : ${orderlistarray.price} <br>


		</td>
	</tr>
	</table>
	</div>
		</c:forEach>



	
</div>
	
</body>
</html>