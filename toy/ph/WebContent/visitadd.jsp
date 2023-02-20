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
<title>添加玩具修理情况</title>
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
			<form action="${pageContext.request.contextPath }/VisitServlet?m=add"
				method="post">
				<table>
					<tr>
						<td>玩具名称</td>
						<td>
							<input type="text" name="petName" disabled="disabled" value="${param.petName }" />
							<input type="hidden" name="petId" value="${param.petId }"/>
							<input type="hidden" name="cid" value="${param.cid }"/>
						</td>

					</tr>
					<tr>
						<td>修理师傅</td>
						<td><select name="vetId">
								<c:forEach items="${vets }" var="v">
									<option value="${v.id }">${v.name }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td>修理情况描述</td>
						<td><textarea row="4" name="description"></textarea></td>
					</tr>
					<tr>
						<td>修理方案</td>
						<td><textarea row="4" name="treatment"></textarea></td>
					</tr>
					<tr class="cols2">
						<td colspan="2"><input type="submit" value="添加" />
						 <input type="reset" value="重置" /></td>
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