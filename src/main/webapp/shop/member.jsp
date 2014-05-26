<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@page import="com.microcard.bean.*"%>
<%@page import="com.microcard.client.*"%>
<%@page import="com.microcard.exception.*"%>
<%@page import="com.microcard.msg.*"%>
<%@page import="com.microcard.dao.*"%>
<%@page import="com.microcard.dao.impl.*"%>
<%@page import="com.microcard.dao.hibernate.*"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" id="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的会员</title>
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
	margin-left:2%;
	padding: 0 12px;
	border-radius: 2px;
	border: 1px solid #99CC33;
	background-color: #99CC33;
	font-size: 1em;
	color: #f3f7fc;
	position: absolute;
}

.memberList {
	border-top: 1px solid #B5B5B5;
	width: 100%;
	float: left;
}

.member {
	width: 100%;
	float: left;
	border-bottom: 1px dashed #B5B5B5;
	height: 60px;
	background: WhiteSmoke;
}

.member img {
	float: left;
	width: 50px;
	height: 50px;
	box-shadow: #666 0 0 5px;
	margin: 2% 2% 0 2%;
	border-radius: 2px;
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
/* 	$(function() {
		for (var i = 1; i <= 40; i++) {
			$(".memberList")
					.append(
							"<div class='member'> "
									+ "<img src='../resources/images/memberFace/" + i + ".jpg'>"
									+ "<div class='memberAbout'>"
									+ "<h5 style='font-size: 1.2em' id='memberName'>赵六</h5>"
									+ "<div><span id='goodsName'>巴贝拉牛排</span>"
									+ "<span id='buyTime'>2014-12-23 下午</span></div></div></div>")

		}
	}) */
</script>
</head>
<body>
	<!-- this is content -->
	<div class="content">
		<div class="search_form">
			<form action="#" method="get">
				<input type="text" name="s" class="sinput" placeholder="输入 回车搜索"> 
				<input type="submit" value="搜索"
					class="sbtn">
			</form>
		</div>

		<div class="memberList">
		<%
		    String openId = request.getParameter("OPENID");
		    ShopDAO shopDao = DAOFactory.createShopDAO();
		    Shop shop = shopDao.getShopByOpenID(openId);
		    List<User> list = new ArrayList<User>();
		    if(shop != null)
		      list = shopDao.getUsersByShop(shop, 0, -1);
		    
			for(User user : list) {
		%>
				<div class="member">
				<img src="<%=user.getHeadImgUrl()%>">
				<div class="memberAbout">
					<h5 style="font-size: 1.2em" id="memberName">&nbsp;<%=user.getNickName()%></h5>
					<div>
						<span id="goodsName">&nbsp;<%=user.getCity()%></span> <span
							id="buyTime"><%=user.getSubscribeTime().toLocaleString()%></span>
					</div>
				</div>
			</div>	
		<%
			}
		%>
	</div>
</body>
</html>