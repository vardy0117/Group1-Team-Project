<%@page import="net.store.db.StoreBean"%>
<%@page import="net.store.db.StoreDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<style>
	#mainDiv{
		clear: both;
		border: 1px solid #d9d9d9;
	}
</style>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>


		<c:set var="info" value="${requestScope.storeInfo}"/>
		<c:set var="star" value="${requestScope.storereview}"/>
		
		<div id="mainDiv">

	<img src="${pageContext.request.contextPath }/images/house.jpg" height="50" width="50" alt="업체정보"> <font size="6">업체정보 </font>
	<hr>
		스토어 넘버 : ${info.storeNo} <br>
		사장님 넘버 : ${info.ceoNo} <br>
		영업 시간 : ${info.storeHours} <br>
		폰 : ${info.phone} <br>	
		도로명 : ${info.roadAddress} <br>
		상세주소 : ${info.detailAddress} <br>
		
		스토어 이름 : ${info.name} <br>
	<img src="${pageContext.request.contextPath }/images/house.jpg" height="50" width="50" alt="사업자정보"> <font size="6">사업자정보 </font>
	<hr>
		카테고리 : ${info.category} <br>
		가게이름 : ${info.storeHours} <br>
	

		

		<%-- 사업자 등록 번호 : ${info.regNo} --%>
		<%--사업자 등록번호 JSTL로 하려니까 계속 안되서 스크립틀딱 기호씀 --%>
		
		<%
		StoreDAO storedao = new StoreDAO();
		StoreBean storebean = new StoreBean();
		
		String regNo,high,middle,end,regNoChange = "";
	
try {
		int storeNo = Integer.parseInt(request.getParameter("storeNo"));
		System.out.printf("사용자가 요청한 사업자 스토어 번호 값  : " + storeNo + "\n");
		
		storebean = storedao.getStoreInfo(storeNo);
	
		System.out.printf("스크립 틀딱 기호로 가져온 사업자 등록번호값  : " + storebean.getRegNo() + "\n");
		
			// 자리수 변환
		
		 regNo = storebean.getRegNo();
			
			// 유효성 체크여부 아님 
		/*****************************************************/
		  if (regNo.contains("-")){ // 사업자 등록번호에 이미 -가 포함되어있으면
			  System.out.println("하이픈 발견 기존 출력문에 사업자 번호 그대로  사용함 변환 안함 : " + regNo);
			  regNoChange = regNo;
	      }else{
	  	high = regNo.substring(0,3) + "-"; // 0~3자리면 
	     middle = regNo.substring(3,5) + "-"; // 3~5자리면 
	     end = regNo.substring(5,10);  // 5~10자리면, 이건 왜했지? 
	      regNoChange = high + middle + end;
	      System.out.println("숫자만 된 사업자 등록번호 : " + regNo);
	      System.out.println("변환된 번호 : " + regNoChange);
	      }
			/*****************************************************/

}catch(Exception e) {
			System.out.println("스토어 정보가 널임 " + e);
			regNoChange = null;
	}
%>
		
사업자 등록번호 : <%=(regNoChange!=null) ? regNoChange : "사업자번호 자릿수가 잘못되었습니다 자릿수를 확인해주세요 000-00-00000 순입니다"  %>
<c:if test="${ info.regNo eq null}">
그딴 정보가 없습니다
		<script>

			alert("정보가 없는 페이지 입니다 \n 으~딜 ^^");
			location.href="index.jsp";
		</script>
		</c:if>

		<br> <img src="${pageContext.request.contextPath }/images/house.jpg" height="50" width="50" alt="기타"> <font size="6">기타 </font>
		<br>
	<hr>	
	<c:set var="po" value="${star.points}" /> <!-- 포인트 전용 변수 -->
		
		사장님 한마디 : ${info.message} <br>
		가게 사진: ${info.image} <br>
		
		<c:if test="${po != null }">
		평균별점: ${star.points}점 
		<c:forEach var="rBean" varStatus="status" begin="1" end="${ star.points}">
				<c:if test="${star.points ne null}">
					<i id="star">★</i>
				</c:if> <!-- 별찍는 if문 -->	
		</c:forEach>
		
		<c:forEach var="rBean" varStatus="status" begin="1" end="${ 5-star.points}">
				<i id="star">☆</i>
				</c:forEach>
		</c:if>
		<br>
		
		
		<c:if test="${ po eq null}">
			평균별점 : 등록된 평균 별점이 없네요 <br>
		</c:if>

		누적주문수 : ${info.orderCount} <br>
		
		</div>

	<img src="${pageContext.request.contextPath }/images/house.jpg" height="50" width="50" alt="업체정보"> <font size="6">업체정보 </font>
	<hr>
	<div id="map" style="width:400px;height:400px;"></div>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c8aab9da70f9c5bec74d9ebf3d0437a3&libraries=services"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c8aab9da70f9c5bec74d9ebf3d0437a3&libraries=LIBRARY"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c8aab9da70f9c5bec74d9ebf3d0437a3&libraries=services,clusterer,drawing"></script>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c8aab9da70f9c5bec74d9ebf3d0437a3"></script>




<script type="text/javascript">
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
};  

//지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 
var data = '<c:out value="${info.roadAddress}"/>'; // jstl변수 여기로 보냄
var data2 ='<c:out value="${info.detailAddress}"/>';   

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

//주소로 좌표를 검색합니다
geocoder.addressSearch(data + " "+ data2, function(result, status) {

// 정상적으로 검색이 완료됐으면 
 if (status === kakao.maps.services.Status.OK) {

    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

    // 결과값으로 받은 위치를 마커로 표시합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: coords
    });

    // 인포윈도우로 장소에 대한 설명을 표시합니다
    var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:6px 0;">위치</div>'
    });
    infowindow.open(map, marker);

    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
    map.setCenter(coords);
} 
});    

	
</script>

</body>
</html>