<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8" http-equiv="Content-Type"
	content="text/html:charset=UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/styles.css" />
<title>首页</title>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>玩具修理厂</h1>
		</div>
		<div id="content">
			<form action="${pageContext.request.contextPath}/LoginServlet"
				method="post">
				<table>
					<tr>
						<td>用户名</td>
						<td><input type="text" name="name" /></td>
					</tr>
					<tr>
						<td>密码</td>
						<td><input type="password" name="pwd" /></td>
					</tr>
					<tr>
						<td>验证码</td>
						<td><input type="text" name="checkcode" /></td>
					</tr>
					<tr>
						<td>点击刷新</td>
						<td><input type="image" name="img-code" id="img-code"
							alt="看不清，点击换图" src="CheckCode"
							onclick="javascript:this.src=this.src+'?';return false;" /></td>
					</tr>
					<tr class="cols2">
						<td colspan="2"><input type="submit" value="登录" /> <input
							type="reset" value="重置" /></td>
					</tr>
					<tr class="cols2">
						<td colspan="2" class="info"><%=request.getAttribute("msg")==null?"":request.getAttribute("msg") %>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>