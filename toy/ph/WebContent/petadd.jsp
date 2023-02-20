<%@page import="java.util.List"%>
<%@page import="ph.entity.Vet"%>
<%@page import="ph.entity.Speciality"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"
			+request.getServerName()+":"+request.getServerPort()
			+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8" http-equiv="Content-Type"
	content="text/html:charset=UTF-8">
<base href="<%=basePath%>">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/CSS/styles.css" />
<title>客户管理</title>
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
			<form action="${pageContext.request.contextPath }/PetServlet?m=add" method="post" enctype="multipart/form-data">
				<table>
					<tr>
						<td>主人姓名</td>
						<td>
							<input type="text" name="cname" disabled="disabled" value="<%=request.getParameter("cname") %>" />
							<input type="hidden" name="cid" value="<%=request.getParameter("cid") %>" />
							<!-- hidden:用隐藏传主人id到PetServlet,添加玩具时需要的是主人的id值-->
						
						</td>

					</tr>
					<tr>
						<td>玩具姓名</td>
						<td><input type="text" name="name"/></td>
					</tr>
					<tr>
						<td>生产日期</td>
						<td><input type="text" name="birthdate"/></td>
					</tr>
					<tr>
						<td>玩具照片</td>
						<td><input type="file" name="photo"/></td>
						<!--type="file"用于文件上传  -->
					</tr>
					
					<tr class="cols2">
						<td colspan="2"><input type="submit" value="添加" /> <input
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