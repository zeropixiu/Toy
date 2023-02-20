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
<title>客户查询</title>
<script type="text/javascript">
	function del_confirm(){
		if(confirm("确定要删除吗？")){
				return true;
			}else{
				return false;
			}
	}
</script>
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
						<td>客户姓名</td>
						<td><input type="text" name="name" disabled="disabled" value="${ customer.name}"/></td>
					</tr>
					<tr>
						<td >联系电话</td>
						<td><input type="text" name="tel" disabled="disabled" value="${ customer.tel}"/></td>
					</tr>
					<tr>
						<td >家庭地址</td>
						<td><input type="text" name="address" disabled="disabled" value="${ customer.address}"/></td>
					</tr>
					<tr class="cols2">
						<td colspan="2" class="info">
							<a href="${pageContext.request.contextPath }/PetServlet?m=toAdd&cid=${customer.id}&cname=${customer.name}">添加新玩具</a>
						</td>
					</tr>
					<tr class="cols2">
						<td colspan="2" class="info"><%=request.getAttribute("msg")==null?"":request.getAttribute("msg") %>
						</td>
					</tr>
				</table>
				<hr>
				
			<!-- 以下为玩具信息 -->
			<table class="wide">
				<thead>
					<tr>
						<td colspan="2">玩具信息</td>
						<td>操作</td>
					</tr>
				</thead>
				<!-- 遍历客户的pets,显示用户的所有玩具信息 -->
				<c:forEach items="${customer.pets }" var="pet">
					<tr>
						<td><img alt="此次应有图片" src="${pageContext.request.contextPath }/${pet.photo }" height="64" width="64"></td>
						<td>名称:${pet.name }, 生产日期:${pet.birthdate }</td>
						<td>
							<a href="${pageContext.request.contextPath }/PetServlet?m=delete&petId=${pet.id }&cid=${pet.ownerId}" onclick="return del_confirm()">删除</a>
							<a href="${pageContext.request.contextPath }/VisitServlet?m=showHistory&petId=${pet.id }">浏览修理情况</a>
							<a href="${pageContext.request.contextPath }/VisitServlet?m=toAdd&petName=${pet.name}&petId=${pet.id}&cid=${pet.ownerId}">添加修理情况</a>
						</td>
					</tr>
				</c:forEach>
				<tr class="cols2">
					<td colspan="2"><input type="button" value="返回"
						onclick="history.back(-1);" /></td>
				</tr>
			</table>
			
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>