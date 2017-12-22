<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>application2</title>
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
			},
			clientUser : function(){
				$.ajax({
					url:"http://www.client2.com/product/queryProduct.action",
					dataType:"json",
					type:"post",
					success:function(data){
						if(data.status == "0"){
							$("#client").html("已登录")
						}else{
							if(data.code == 'CF0004'){
								$("#client").html("未登录");
								window.location.href=data.data;
								return;
							}
						}
						if(data.status == 200){
							$("#client").html(data.data)
						}
					}
				});
			},
			
	}
	welcome.checkLogin();
	welcome.clientUser();
</script>
</head>
<body>
	<h1>${errorMsg}</h1>
	<h1>application2</h1>
	<h1><a href="/ssoLoginHandler">登录</a></h1>
	<h1><a href="/ssoLogoutHandler">退出</a></h1>
	<li id="welcome"></li>
	<li id="client"></li>
</body>
</html>