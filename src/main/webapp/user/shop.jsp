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
<title>我的商铺</title>
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

.shopList {
	border-top: 1px solid #B5B5B5;
	width: 100%;
	float: left;
}

.shop {
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
<script type="text/javascript">
	$(function() {
		$(".shop").click(
				function() {
					var shopId = $(this).find("input").val();
					location.href = "shopDetail.jsp?userId=" + $("#userId").val()
							+ "&shopId=" + shopId;
				});
	});
</script>
</head>
<body>
	<%
		try {
			String openId = request.getParameter("OPENID");
			UserDAO userdao = DAOFactory.createUserDAO();
			User user = userdao.getUserByOpenID(openId);
			List<Shop> shoplist = new ArrayList<Shop>();
			if (user != null) {
				shoplist = userdao.getShopsByUser(user, 0, 50);
			}
			List<Record> records = new ArrayList<Record>();
			for (int i = 0; i < shoplist.size(); i++) {
				List<Record> rs = userdao.getRecordsByUserShop(user,
						shoplist.get(i), 0, 1);
				if (rs.size() == 1) {
					Record r = rs.get(0);
					records.add(r);
				}
			}
			request.setAttribute("shops", shoplist);
			request.setAttribute("records", records);
			request.setAttribute("userid", user.getId());

		} catch (Exception e) {
			Logger.getOperLogger().error(e, "");
		} 
	%>
	<!-- this is content -->
	<div class="content">
		<input type="hidden" id="userId" value=${userid} />
		<div class="search_form">
			<form action="#" method="get">
				<input type="text" name="s" class="sinput" placeholder="输入 回车搜索">
				<input type="submit" value="搜索" class="sbtn">
			</form>
		</div>
		<div class="shopList">
			<c:forEach var="item" items="${shops}" varStatus="status">
				<div class="shop">
					<input type="hidden" value="${item.id}">
					<h5 style="font-size: 1.2em" id="memberName">&nbsp;${item.name} </h5>
					<span id="goodsName">&nbsp;${records[status.index].bonus}</span> <span
						id="buyTime">&nbsp;${records[status.index].time}</span>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>