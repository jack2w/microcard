<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
	font-size: 1.2em;
	height: 80px;
	border-bottom: 1px solid #E1E1E1;
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

</style>
<script type="text/javascript">

	function changeClick() {
		/** 点击修改跳转到编辑界面  **/
		if ($(".header-modify").is(":visible")) {
			$(".header-modify").hide();
			$(".header-submit").show();
			$("td span").hide();
			$("td input,td textarea").show();
		} else {
			/** 点击完成跳转到显示界面  **/
			$(".header-submit").hide();
			$(".header-modify").show();
			$("td input,td textarea").hide();
			$("td span").show();

			/** 提交数据到shopServlet 
			$("form").submit();**/
			$.ajax({
				url : "../shopservlet",
				type : "post",
				dataType : "json",
				data : {
					shopName : $('#shopName').val(),
					shopPhone : $('#shopPhone').val(),
					shopAddress : $('#shopAddress').val(),
					shopMemo : $('#shopMemo').val(),
					shopOpenId : $('#shopOpenId').val(),
				},
				success : function(data) {
					var dataList =  new Array();
					dataList[0] = data.shopName;
					dataList[1] = data.shopPhone;
					dataList[2] = data.shopAddress;
					dataList[3] = data.shopMemo;
					$("td span").each(function(i) {
						$(this).html(dataList[i]);
					});
				},
				error : function(userName) {
					$.Zebra_Dialog('该商铺不存在，请重新输入', {
						    'type':     'error',
						    'title':    '错误'
						});
				}
			});

		}
	}

	$(function() {

			/** OpenId为空时提示错误  **/
			if ($("#shopOpenId").val() == "") {
				$.Zebra_Dialog('数据错误，请重新获取！', {
					'type' : 'error',
					'title' : '错误'
				});
			/** shop为空时提示错误  **/
			} else if ($("#shop").val() == "") {
				$.Zebra_Dialog('无此商铺数据！', {
					'type' : 'error',
					'title' : '错误'
				});
			};
		
		/** 商品名称为空时直接跳转到编辑界面  **/
		if ($("td span:first-child").html() == "") {
			$(".header-modify").hide();
			$(".header-submit").show();
			$("td span").hide();
			$("td input,td textarea").show();
		}
	})
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
					<td><span>${shop.name}</span><input value="${shop.name}"
						style="display: none;" type="text" name="shopName" id="shopName"></td>
				</tr>
				<tr>
					<td>商铺电话:</td>
					<td><span>${shop.phone}</span><input value="${shop.phone}"
						style="display: none;" type="tel" name="shopPhone" id="shopPhone"></td>
				</tr>
				<tr>
					<td>商铺地址:</td>
					<td><span>${shop.address}</span><input value="${shop.address}"
						style="display: none;" type="text" name="shopAddress"
						id="shopAddress"></td>
				</tr>
				<tr>
					<td>商铺简介:</td>
					<td><span>${shop.memo}</span> <textarea
							style="display: none; height: 80px" name="shopMemo" id="shopMemo">${shop.memo}</textarea></td>
				</tr>
			</table>
			<input type="hidden" name="shopOpenId" id="shopOpenId"
				value="${opendId}"> <input type="hidden" name="shop"
				id="shop" value="${shop}">
		</div>
	
</body>
</html>