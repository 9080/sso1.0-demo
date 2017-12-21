<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
	<center>
		<h1>登录</h1>
		<font color="red">${errorMsg}</font>
		<form id="loginform" action="/user/login.action" method="post">
			<table border="0">
				<tr>
					<td>账号：</td>
					<td><input type="text" name="accountNo"></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" name="pwd"></td>
				</tr>
			</table>
			<br>
			<input type="submit" value="登录">
		</form>
	</center>
</body>
</html>