<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@page import="com.microcard.bean.User"%>
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
<title>记一笔</title>
<link href="../resources/styles/bootstrap.min.css" rel="stylesheet" />
<link href="../resources/styles/zebra_dialog.css" rel="stylesheet" />
<script type="text/javascript" src="../resources/js/jquery.js" /></script>
<script type="text/javascript" src="../resources/js/zebra_dialog.js" /></script>
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
	height: auto;
	font-size: 1.2em;
	color: #6E6E6E;
}

.header {
	width: 100%;
	height: 30px;
	background: url("../resources/images/header.jpg");
	box-shadow: #666 0 0 5px;
}

.record {
	position: absolute;
	width: 100%;
	border-collapse: collapse;
	top: 8%;
}

table tr td {
	padding: 2% 0 2% 8%;
}

table tr td:FIRST-CHILD {
	width: 35%;
}

table tr td input {
	width: 35%;
}

.okBtn {
	padding: 6px 15px;
	border-radius: 4px;
	background: #9ACD32;
	border: 0;
	color: white;
}

#tipImg{
	width: 30px;
	margin-left: 4%;
}
</style>
<script type="text/javascript">
	$(function() {
		  $("#userId").blur(function () {

			$.ajax({
				url : "../recordServlet",
				type : "post",
				dataType : "json",
				data : {
					userId : $('#userId').val()
				},
				success : function(data) {
					$("#userName").html(data.userName);
					$("#tipImg").attr("src", "../resources/images/right_alt.png");
				},
				error : function(userName) {
					$.Zebra_Dialog('该会员ID不存在，请重新输入！', {
						    'type':     'error',
						    'title':    '错误'
						});
					$("#tipImg").attr("src", "../resources/images/wrong_alt.png");
				}
			});

		});
	});
</script>
</head>
<body>
	<div class="header"></div>
	<!-- this is content -->
	<div class="content">
		<table class="record">
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td>会员ID：</td>
				<td><input type="number" id="userId"><img id="tipImg" ></td>
			</tr>
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td>会员姓名：</td>
				<td id="userName"></td>
			</tr>
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td>支付金额：</td>
				<td><input type="number" style="margin-top: 2%" value="400.00"></td>
			</tr>
			<tr>
				<td>购买商品：</td>
				<td><input type="checkbox">牙膏</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox">牙刷</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox">洗发水</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox">沐浴露</td>
			</tr>
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td></td>
				<td><input type="checkbox">毛巾</td>
			</tr>
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td>返还金额：</td>
				<td>¥200.00</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input
					class="okBtn" type="button" value="确认"></td>
			</tr>
		</table>
	</div>
</body>
</html>