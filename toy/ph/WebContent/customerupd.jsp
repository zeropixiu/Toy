<%@page import="java.util.List"%>
<%@page import="ph.entity.User"%>
<%@page import="ph.entity.Speciality"%>
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
<title>用户查询结果</title>
</head>
<body>
	<div id="container">
		<div id="header">
			<a id="quit" href="${pageContext.request.contextPath }/QuitServlet">退出</a>
			<h1>玩具修理厂</h1>
			<ul id="menu">
				<li><a
					href="${pageContext.request.contextPath }/vetsearch.jsp">修理师傅管理</a>
				<li>
				<li><a href="${pageContext.request.contextPath }/customersearch.jsp">客户管理</a>
				<li>
					<!--创建后更新此处-->
			</ul>
		</div>
		<div id="content">
			<form action="${pageContext.request.contextPath }/CustomerServlet?m=update" method="post">
				<table>
					<tr>
						<td>客户姓名</td>
						<td>
							<input type="text" name="name" value="${customer.name }" disabled="disabled"/>
							<input type="hidden" name="id" value="${customer.id }"/>
						
						</td>
					</tr>
					<tr>
						<td>联系电话</td>
						<td><input type="text" name="tel" value="${customer.tel }"/></td>
					</tr>
					<tr>
						<td>家庭地址</td>
						<td><input type="text" name="address" value="${customer.address }"/></td>
					</tr>
					<tr class="cols2">
						<td colspan="2">
						<input type="submit" value="更新" />
						<input type="reset" value="重置"/>
						</td>
					</tr>
					<tr class="cols2">
						<td colspan="2"><input type="button" value="返回"
							onclick="history.back(-1);" /></td>
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