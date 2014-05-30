<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="com.microcard.bean.*"%>
<%@page import="com.microcard.exception.*"%>
<%@page import="com.microcard.dao.*"%>
<%@page import="com.microcard.dao.impl.*"%>
<%@page import="com.microcard.dao.hibernate.*"%>
<%@page import="java.util.*"%>
<%@page import="com.microcard.log.*"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" id="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>购买记录</title>
<script type="text/javascript" src="../resources/js/jquery.js" /></script>
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
	height: auto;
	min-height: 100%;
	width: 100%;
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
	margin-left: 14%;
}

.returnbtn {
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

.recordList {
	border-top: 1px solid #B5B5B5;
	width: 100%;
	float: left;
}

.record {
	width: 100%;
	float: left;
	border-bottom: 1px dashed #B5B5B5;
	height: 60px;
	background: WhiteSmoke;
}

#goodsName {
	float: left;
	width: 40%;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

#buyTime {
	width: 40%;
	float: right;
}

.click_more {
	width: 100%;
	height: 35px;
	line-height: 35px;
	background: #F3F3F3;
	border: solid 1px #EFEFEF;
	text-align: center;
}
</style>
</head>
<body>
	<%
		try {
			String userId = request.getParameter("userId");
			String shopId = request.getParameter("shopId");
			UserDAO userDao = DAOFactory.createUserDAO();
			ShopDAO shopDao = DAOFactory.createShopDAO();
			
			List<Record> recordlist = new ArrayList<Record>();
			User user = userDao.getUserByID(Long.parseLong(userId));
			Shop shop = shopDao.getShopByID(Long.parseLong(shopId));
			recordlist = userDao.getRecordsByUserShop(user, shop, 0, 50);
			request.setAttribute("records", recordlist);
		} catch (Exception e) {
			Logger.getOperLogger().error(e, "");
		} finally {
			HibernateUtil.instance().closeSession();
		}
	%>
	<!-- this is content -->
	<div class="content">
		<div class="search_form">
			<form action="#" method="get">
				<input type="button" value="返回" class="returnbtn"
				onclick="javascript:history.go(-1);">
				<input type="text" name="s" class="sinput" placeholder="输入 回车搜索">
				<input type="submit" value="搜索" class="sbtn">
			</form>
		</div>
		<div class="recordList">
			<c:forEach var="item" items="${records}">
				<div class="record">
					<c:choose>
						<c:when test="${item.bonusUsed}">
						<h5 style="font-size: 1.2em; color:#c0c0c0" id="recordName">&nbsp;返还${item.bonus}</h5>
						</c:when>
						<c:otherwise>
						<h5 style="font-size: 1.2em; color:red" id="recordName">&nbsp;返还${item.bonus}</h5>
						</c:otherwise>
					</c:choose>
					<span id="goodsName">&nbsp;消费金额&nbsp;&nbsp;${item.price}</span> 
					<span id="buyTime">&nbsp;&nbsp;${item.time}</span>			
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>