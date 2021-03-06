<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	div {
		box-sizing: border-box;
	}
	#mainDiv {
		width: 1000px;
		min-height: 600px;
		margin: 0 auto;
		margin-top: 80px;
		padding: 120px;
	}
	#formDiv {
		width: 600px;
		height: 350px;
		margin: 0 auto;
		border: 2px solid #c7c7c7;
		border-radius: 15px;
		box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
	}
	.textBox {
		width: 200px;
		height: 30px;
		border-radius: 12px;
		border: 2px solid gray;
		padding-left: 15px;
		font-family: Binggrae-Bold;
		font-size: 1rem;
	}
	
	input[type=password] {
		font-family: 'pass', 'Roboto', Helvetica, Arial, sans-serif ;
	}
	input[type=password]::placeholder {
		font-family: Binggrae-Bold;
	}
	input:focus { outline: none; }
	#roadAddress, #detailAddress {
		width: 256px;
	}
	.btn {
		background-color: black;
		font-family: Binggrae-Bold;
		font-size: 1rem;
		color: white;
		border: none;
		border-radius: 12px;
		width: 150px;
		height: 35px;
		transition-duration: 1s;
		opacity: 0.7;
	}
	.btn:hover {
		cursor: pointer;
		opacity: 1;
	}
	form > table {
		margin: 0 auto;
	}
</style>
</head>
<body>
	<div id="mainDiv">
		<div id="formDiv">
			<h1 style="text-align: center;">사장님 정보 수정</h1>
			<form action="./CeoModifyAction.do" method="post">
				<table>
					<tr>
						<th>이메일</th>
						<td><input class="textBox" type="text" value="${cBean.email }" name="email" readonly></td>
					</tr>
					<tr>
						<th>새 비밀번호</th>
						<td><input class="textBox" type="password" placeholder="공백시 기존값" name="password"></td>
					</tr>
					<tr>
						<th>이름</th>
						<td><input class="textBox" type="text" value="${cBean.name }" name="name"></td>
					</tr>
					<tr>
						<th>연락처</th>
						<td><input class="textBox" type="text" value="${cBean.phone }" name="phone"></td>
					</tr>
					<tr>
						<th colspan="2">
							<br>
							<input class="btn" type="submit" value="수정하기">
							<input class="btn" type="button" value="탈퇴하기" onclick="deleteCeo()">
						</th>
					</tr>
					<input type="hidden" value="${cBean.ceoNo }" name="ceoNo">
					<input type="hidden" value="${cBean.password }" name="oldPass">
 				</table>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
		function deleteCeo() {
			var inputString = prompt('"탈퇴하기"를 입력해주세요',""); 
			if(inputString == "탈퇴하기"){
				alert("탈퇴가 완료되었습니다.");
				location.href="./deleteCeo.do";
			}else{
				alert("잘못입력하셨습니다.");
			}
		}
	</script>
	
</body>
</html>