<%@page import="com.microcard.dao.hibernate.HibernateUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@page import="com.microcard.bean.*"%>
<%@page import="com.microcard.dao.DAOFactory"%>
<%@page import="java.util.*"%>
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
	padding: 3% 0 3% 8%;
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

#tipImg {
	width: 30px;
	margin-left: 4%;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#userId").change(
				function() {
					$.ajax({
						url : "../recordServlet",
						type : "post",
						dataType : "json",
						data : {
							userId : $('#userId').val(),
							openId : $('#openId').val()
						},
						success : function(data) {
							$("#userName").html(data.userName);
							$("#tipImg").attr("src",
									"../resources/images/right_alt.png");
						},
						error : function() {
							$.Zebra_Dialog('该会员ID不存在，请重新输入！', {
								'type' : 'error',
								'title' : '错误'
							});
							$("#tipImg").attr("src",
									"../resources/images/wrong_alt.png");
						}
					});
					$("#userName").html("");
					$("#recordPrice").val("");
					$("#recordBonus").val("");
					$("input:[type='radio']").each(function() {
						if ($(this).attr("checked", true)) {
							$(this).attr("checked", false);
						}
					});
				});

		$("input:[type='radio']")
				.each(
						function() {
							$(this)
									.click(
											function() {
												if ($("#recordPrice").val() == "") {
													$
															.Zebra_Dialog(
																	'请先输入支付金额',
																	{
																		'type' : 'warning',
																		'title' : '提示'
																	});
													$(this).attr("checked",
															false);
												} else {
													var recordPrice = $(
															"#recordPrice")
															.val();
													var salePrice = $(this)
															.siblings(
																	"#salePrice")
															.html();
													var saleBonus = $(this)
															.siblings(
																	"#saleBonus")
															.html();
													if (eval(recordPrice) < eval(salePrice)) {
														$("#recordBonus").val(
																"");
														$("#recordBonus").attr(
																"placeholder",
																"暂无返利！");
													} else {
														var recordBonus = parseInt(eval(recordPrice)
																/ eval(salePrice));
														$("#recordBonus")
																.val(
																		eval(recordBonus)
																				* eval(saleBonus));
													}
												}

											});

						});

	});
</script>
</head>
<body>
	<%
		try {
			HibernateUtil.instance().beginTransaction();
			String openId = request.getParameter("OPENID");
			Shop shop = DAOFactory.createShopDAO().getShopByOpenID(openId);
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
	<div class="header"></div>
	<!-- this is content -->
	<div class="content">
		<input type="hidden" id="openId" value="${openId}">
		<table class="record">
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td>会员ID：</td>
				<td><input type="number" id="userId"><img id="tipImg"></td>
			</tr>
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td>会员姓名：</td>
				<td id="userName"></td>
			</tr>
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td>支付金额：</td>
				<td><input type="number" id="recordPrice"
					style="margin-top: 2%"></td>
			</tr>
			<tr>
				<td>促销活动：</td>
				<td>${noSales}</td>
			</tr>
			<c:forEach items="${sales}" var="sale">
				<tr>
					<td></td>
					<td><input type="radio" name="group">买满<span
						id="salePrice">${sale.price}</span>返现<span id="saleBonus">${sale.bonus}</span></td>
				</tr>
			</c:forEach>
			<tr style="border-bottom: 1px solid #E1E1E1">
				<td>返还金额：</td>
				<td><input type="number" id="recordBonus"
					style="margin-top: 2%"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><input
					class="okBtn" type="button" value="确认"
					onclick="javascript:history.go(-1);"></td>
			</tr>
		</table>
	</div>
</body>
</html>