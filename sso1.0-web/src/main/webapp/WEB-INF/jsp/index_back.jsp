<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sso1.0</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.4.js"></script>
<script type="text/javascript">
	var welcome = {
			checkLogin : function(){
				$.ajax({
					url:"http://www.sso1.com/user/getUserByToken.action",
					dataType:"jsonp",
					type:"GET",
					success:function(data){
						if(data.status == 200){
							if(data.data.status==1){
								var html = "欢迎"+data.data.userName+"!";
								$("#welcome").html(html)
							}else{
								$("#welcome").html("未登录")
							}
						}
					}
				});
			}
	}
	welcome.checkLogin();
</script>
</head>
<body>
	<h1>${errorMsg}</h1>
	<h1><a href="/user/toLogin.action">登录</a></h1>
	<li id="welcome"></li>
</body>
</html>