<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@page import="com.microcard.bean.*"%>
<%@page import="com.microcard.dao.DAOFactory"%>
<%@page import="java.util.*"%>
<%@page import="com.microcard.dao.hibernate.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的营销</title>
<link href="../resources/styles/bootstrap.min.css" rel="stylesheet" />
<link href="../resources/styles/zebra_dialog.css" rel="stylesheet" />
<script type="text/javascript" src="../resources/js/jquery.js" /></script>
<script type="text/javascript" src="../resources/js/zebra_dialog.js" /></script>
<link href="../resources/styles/bootstrap.min.css" rel="stylesheet" />
<style type="text/css">
* {
	margin: 0;
	outline: medium none;
	padding: 0;
}

body {
	width: auto;
}

.content {
	position: absolute;
	width: 100%;
	height: auto;
	min-height: 100%;
	height: auto;
}

.search_form {
	width: 100%;
	height: 40px;
	padding-top: 3%;
	background: url("../resources/images/header.jpg");
	box-shadow: #666 0 0 5px;
}

.sinput {
	width: 50%;
	height: 21px;
	line-height: 21px;
	padding: 4px 7px;
	color: b3b3b3;
	border: 1px solid #999;
	border-radius: 2px;
	background-color: #fbfbfb;
	margin-left: 12%;
}

.sbtn {
	width: 10%;
	height: 30px;
	margin-left: 2%;
	padding: 0 12px;
	border-radius: 2px;
	border: 1px solid #99CC33;
	background-color: #99CC33;
	font-size: 1em;
	color: #f3f7fc;
	position: absolute;
}

.addbtn {
	width: 30px;
	height: 31px;
	line-height: 31px;
	float: right;
	margin-right: 3%;
}

.salesList {
	border-top: 1px solid #B5B5B5;
	width: 100%;
	float: left;
}

.sales {
	width: 100%;
	float: left;
	border-bottom: 1px dashed #B5B5B5;
	background: WhiteSmoke;
	padding-left: 1%;
}

#salesName {
	font-size: 1.5em;
	color: red;
}

#salesType {
	color: green;
	font-family: "Microsoft YaHei", Helvetica, Arial, sans-serif;
	font-size: 1.2em;
}

.click_more {
	width: 100%;
	height: 30px;
	line-height: 30px;
	background: #F3F3F3;
	border: solid 1px #EFEFEF;
	text-align: center;
}

.salesInfo {
	display: none;
}
</style>
<script type="text/javascript">
	$(function() {
		$(".sales").click(function() {
			var saleId = $(this).find("input").val();
			location.href="salesDetail.jsp?saleId=" + saleId;
			
		});
		
		$(".addbtn").click(function() {
			location.href="salesDetail.jsp";
		});

	});
</script>
</head>
<body>
	<%
		try {
			HibernateUtil.instance().beginTransaction();
			String openId = request.getParameter("OPENID");
			Shop shop = DAOFactory.createShopDAO().getShopByOpenID("001");
			Set<Sales> sales = shop.getSales();
			if (sales.isEmpty() || sales.size() == 0) {
				String noSales = "Sorry！暂无促销活动。";
				request.setAttribute("noSales", noSales);
			} else {
				request.setAttribute("sales", sales.iterator());
			}
			request.setAttribute("openId", openId);
			HibernateUtil.instance().commitTransaction();
		} catch (Exception e) {
			HibernateUtil.instance().rollbackTransaction();
		} finally {
			HibernateUtil.instance().closeSession();
		}
	%>
	<!-- this is content -->
	<div class="content">
		<div class="search_form">
			<form action="#" method="get">
				<input type="text" name="s" class="sinput" placeholder="输入 回车搜索">
				<input type="submit" value="搜索" class="sbtn"> <img
					src="../resources/images/addbtn.png" class="addbtn">
			</form>
		</div>
		<div class="salesList">
			<span>${noSales}</span>
			<c:forEach items="${sales}" var="sale">
				<div class="sales">
					<input type="hidden" value="${sale.id}">
					<h5 id="salesName">${sale.name}</h5>
					<span id="salesType">满￥${sale.price}返￥${sale.bonus}</span>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>