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
<title>修理师傅管理</title>
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

			<table>
				<tr>
					<td>修理师傅姓名</td>
					<td>专业特长</td>
				</tr>
				<%
						List<Vet> vets=(List<Vet>)request.getAttribute("vets");
						for(Vet vet:vets){
					%>
				<tr class="result">
					<td><%=vet.getName() %></td>
					<td>
						<%
								for(Speciality spec:vet.getSpecs()){
									out.print(spec.getName()+"&nbsp");
								}
							%>
					</td>
				</tr>
				<%
						}
					%>


				<tr class="cols2">
					<td colspan="2"><input type="button" value="返回"
						onclick="history.back(-1);" /></td>
				</tr>
				<tr class="cols2">
					<td colspan="2" class="info"><%=request.getAttribute("msg")==null?"":request.getAttribute("msg") %>
					</td>
				</tr>
			</table>

		</div>
		<div id="footer"></div>
	</div>
</body>
</html>