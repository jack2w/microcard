<%@page import="javax.swing.JOptionPane"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@page import="com.microcard.bean.Shop"%>
<%@page import="com.microcard.dao.DAOFactory"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的商铺</title>
<link href="../resources/styles/bootstrap.min.css" rel="stylesheet" />
<link href="../resources/styles/zebra_dialog.css" rel="stylesheet" />
<script type="text/javascript" src="../resources/js/jquery.js" /></script>
<script type="text/javascript" src="../resources/js/zebra_dialog.js" /></script>
<style type="text/css">
body {
	margin: 0 auto;
	min-width: 320px;
	max-width: 100%;
}

a:link,a:visited {
	font-style: normal;
}

img {
	border: 0 none;
	height: auto;
	max-width: 100%;
	vertical-align: middle;
}

.header {
	background: url("../resources/images/header.jpg");
	height: 60px;
	position: relative;
	width: 100%;
	box-shadow: #666 0 0 10px;
}

.header .title {
	line-height: 1.5em;
	text-align: center;
	font-size: 1.5em;
	color: #fff;
	vertical-align: middle;
	padding: 0.7em 0px;
	font-family: "微软雅黑";
	margin: 0px auto;
	overflow: hidden;
	height: 22px;
}

.header .change {
	color: #FFFFFF;
	display: block;
	right: 0px;
	top: 0px;
	padding: 13px 20px;
	position: absolute;
}

.header .header-icon {
	background-position: center center;
	background-repeat: no-repeat;
	background-size: 100% 100%;
	display: block;
	height: 22px;
	margin: 0 auto;
	width: 22px;
}

.header .header-icon-change {
	background-image: url("../resources/images/shop_modify.png");
}

.header-submit {
	display: none;
}

.content {
	position: relative;
	width: 100%;
}

table {
	width: 100%;
}

table tr {
	font-size: 1.5em;
	height: 80px;
}

td input,td textarea {
	margin-top: 4%
}

table tr td:FIRST-CHILD {
	width: 30%;
	text-align: center;
}

table tr td:LAST-CHILD {
	padding: 1% 2% 1% 2%;
	width: 70%;
}

。errorDialog {
	display: none;
}

.dialog-overlay {
	position: fixed;
	_position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 3;
	background-color: #101010;
	opacity: .4;
}

.dialog {
	position: fixed;
	top: 20%;
	left: 20%;
	right: 20%;
	bottom: 40%;
	width: 60%;
	height: 40%;
	z-index: 6;
	background: #E9E9E9;
	box-shadow: #666 0 0 5px;
	border-radius: 4px;
}
</style>
<script type="text/javascript">
	document.onreadystatechange = subSomething;
	function subSomething() {
		if (document.readyState == "complete") {
			if ($("#shopOpenId") == null || $("#shopOpenId").val() == "") {
				$.Zebra_Dialog('数据错误，请重新获取！', {
					'type' : 'error',
					'title' : '错误'
				});
			} else if ($("#shop") == null || $("#shop").val() == "") {
				$.Zebra_Dialog('数据库异常！', {
					'type' : 'error',
					'title' : '错误'
				});
			}
		}
	}

	function changeClick() {
		var data = new Array();
		if ($(".header-modify").is(":visible")) {
			$(".header-modify").hide();
			$(".header-submit").show();
			$("td span").hide();
			$("td input,td textarea").show();

			$("td span").each(function(i) {
				data[i] = $(this).html();
			});

			$("#shopName").val(data[0]);
			$("#shopPhone").val(data[1]);
			$("#shopAddress").val(data[2]);
			$("#shopMemo").val(data[3]);

		} else {
			$(".header-submit").hide();
			$(".header-modify").show();
			$("td input,td textarea").hide();
			$("td span").show();

			data[0] = $("#shopName").val();
			data[1] = $("#shopPhone").val();
			data[2] = $("#shopAddress").val();
			data[3] = $("#shopMemo").val();
			$("td span").each(function(i) {
				$(this).html(data[i]);
			});
			$("form").submit();
		}
	}
</script>
</head>
<body>
	<%
		String opendId = request.getParameter("OPENID");
		Shop shop = DAOFactory.createShopDAO().getShopByOpenID(opendId);
		request.setAttribute("opendId", opendId);
		request.setAttribute("shop", shop);
	%>
	<!-- this is header -->
	<form method="post" action="../shopservlet">
		<div class="header">
			<div class="title" id="titleString">我的商铺</div>
			<a onclick="changeClick()" class="change"> <span
				class="header-icon header-icon-change"></span> <span
				class="header-modify">修改</span> <span class="header-submit">完成</span>
			</a>
		</div>
		<!-- this is content -->
		<div class="content">
			<table rules="rows">
				<tr>
					<td>商铺名称:</td>
					<td><span>${shop.name}</span><input
						style="display: none;" type="text" name="shopName" id="shopName"></td>
				</tr>
				<tr>
					<td>商铺电话:</td>
					<td><span>${shop.phone}</span><input
						style="display: none;" type="tel" name="shopPhone" id="shopPhone"></td>
				</tr>
				<tr>
					<td>商铺地址:</td>
					<td><span>${shop.address}</span><input
						style="display: none;" type="text" name="shopAddress"
						id="shopAddress"></td>
				</tr>
				<tr>
					<td>商铺简介:</td>
					<td><span>${shop.memo}</span> <textarea
							style="display: none; height: 80px" name="shopMemo" id="shopMemo"></textarea></td>
				</tr>
			</table>
			<input type="hidden" name="shopOpenId" id="shopOpenId" value="${opendId}">
			<input type="hidden" name="shop" id="shop" value="${shop}">
		</div>
	</form>
</body>
</html>