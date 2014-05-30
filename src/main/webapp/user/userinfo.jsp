<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@page import="com.microcard.bean.User"%>
<%@page import="com.microcard.msg.*"%>
<%@page import="com.microcard.msg.processor.user.*"%>
<%@page import="com.microcard.dao.DAOFactory"%>
<%@page import="com.microcard.dao.hibernate.*"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员信息</title>
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
	height: 50px;
	border-bottom: 1px solid #E1E1E1;
}

td input,td textarea {
	margin-top: 4%
}

table tr td:FIRST-CHILD {
	width: 30%;
	text-align: left;
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

			/** 提交数据到shopServlet **/
			$.ajax({
				url : "../userServlet",
				type : "post",
				dataType : "json",traditional:true,
				data : {
					username : $('#username').val(),
					useraddress : $('#useraddress').val(),
					userphone : $('#userphone1').val(),
					userid : $("td div:first-child").html()
				},
				success : function(data) {
					var dataList =  new Array();
					dataList[0] = data.name;
					dataList[1] = data.address;
					dataList[2] = data.phone;
					$("td span").each(function(i) {
						$(this).html(dataList[i]);
					});
				},
				error : function(jqXHR) {
					$.Zebra_Dialog(jqXHR.responseText, {
						    'type':     'error',
						    'title':    '错误'
						});
				}
			});

		}
	}

	$(function() {

			/** OpenId为空时提示错误  **/
			if ($("#userOpenId").val() == "") {
				$.Zebra_Dialog('数据错误，请重新获取！', {
					'type' : 'error',
					'title' : '错误'
				});
			/** user为空时提示错误  **/
			} else if ($("#user").val() == "") {
				$.Zebra_Dialog('无此用户数据！', {
					'type' : 'error',
					'title' : '错误'
				});
			};
		
		/** 商品名称为空时直接跳转到编辑界面  **/
		if ($("td div:first-child").html() == "") {
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
	try{
		HibernateUtil.instance().beginTransaction();
		String openId = request.getParameter("OPENID");
		User user = DAOFactory.createUserDAO().getUserByOpenID(openId);
		if(user == null) {//如果用户为空，则手动添加到数据库
			ReceivedSubscribeMsg msg = new ReceivedSubscribeMsg();
			msg.setFromUserName(openId);
			UserSubscribeProcessor processor = new UserSubscribeProcessor();
			processor.proccess(msg);
			user = DAOFactory.createUserDAO().getUserByOpenID(openId);
		}
		request.setAttribute("user", user);
	}finally{
		HibernateUtil.instance().closeSession();
	}
	%>
	<!-- this is header -->
	
		<div class="header">
			<div class="title" id="titleString">我的信息</div>
			<a onclick="changeClick()" class="change"> <span
				class="header-icon header-icon-change"></span> <span
				class="header-modify">修改</span> <span class="header-submit">完成</span>
			</a>
		</div>
		<!-- this is content -->
		<div class="content">
			<table rules="rows">
				<tr>
					<td>&nbsp;会员ID:</td>
					<td><div>${user.id}</div></td>
				</tr>
				<tr>
					<td>&nbsp;会员昵称:</td>
					<td><div>${user.nickName}</div></td>
				</tr>
				<tr>
					<td>&nbsp;真实姓名:</td>
					<td><span>${user.name}</span><input value="${user.name}"
						style="display: none; height:30px" type="text" name="username"
						id="username"></td>
				</tr>
				<tr>
					<td>&nbsp;联系地址:</td>
					<td><span>${user.address}</span><input value="${user.address}"
						style="display: none; height:30px" type="text" name="useraddress"
						id="useraddress"></td>
				</tr>
				<tr>
					<td>&nbsp;联系方式:</td>
					<td><span>${user.phone1}</span><input value="${user.phone1}"
						style="display: none; height:30px" type="tel" name="userphone1"
						id="userphone1"></td>
				</tr>
				<tr>
					<td>&nbsp;国家:</td>
					<td><div>${user.country}</div></td>
				</tr>
				<tr>
					<td>&nbsp;城市:</td>
					<td><div>${user.city}</div></td>
				</tr>
				
			</table>
			<input type="hidden" name="userOpenId" id="userOpenId"
				value="${param.OPENID}"> <input type="hidden" name="user"
				id="user" value="${user}">
		</div>
	
</body>
</html>