<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 로그인</title>
<link rel="stylesheet" href="css/reset.css" />
<link rel="stylesheet" href="css/style.css" />
<style>
	<%@ include file="css/reset.css" %>
	<%@ include file="css/style.css" %>
</style>

</head>
<body>
	<div class="container">
		<div class="row align-items-center flex-column">
			<h1 class="text-center login-title">관리자 로그인</h1>
			<c:if test="${param.error }">
				<p class="login-txt">관리자 전용 사이트 입니다. <a href="javascript:history.go(-1);">관리자가 아닌 분은 되돌아가 주시기 바랍니다.</a></p>
			</c:if>
			<div class="login-box">
				<form action="login" class="loginform" name="loginform" method="post">
					<input type="text" name="mid" placeholder="관리자 아이디" />
					<input type="password" name="mpass" placeholder="관리자 비밀번호" />
					<div class="btn-box text-center">
						<button type="reset" class="btn-reset">취소</button>
						<button type="submit" class="btn-submit">로그인</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>