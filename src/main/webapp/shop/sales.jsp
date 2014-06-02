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
	width: 60%;
	height: 21px;
	line-height: 21px;
	padding: 4px 7px;
	color: b3b3b3;
	border: 1px solid #999;
	border-radius: 2px;
	background-color: #fbfbfb;
	margin-left: 2%;
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
	border-bottom: 1px solid #E1E1E1;
	background: WhiteSmoke;
}

#salesName {
	padding-top: 1%;
	font-size: 1.5em;
	color: #6E6E6E;
	padding-left: 1%;
	/*text-shadow: -2px -2px 0 #E6E6FA;*/
}

#salesType {
	padding: 1% 0 1% 1%;
	color: green;
	font-family: "Microsoft YaHei", Helvetica, Arial, sans-serif;
	font-size: 1.2em;
	float: left;
}

.delbtn {
	width: 10%;
	margin-right: 2%;
	margin-top: -3%;
	padding: 5px 12px;
	border-radius: 2px;
	border: 1px solid #99CC33;
	background-color: #99CC33;
	font-size: 1em;
	color: #f3f7fc;
	float: right;
	vertical-align: middle;
	
	
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
		var shopId = $("#shopId").val();
		/** 修改营销 **/
		$(".sales").click(
				function() {
					var saleId = $(this).find("input").val();
					location.href = "salesDetail.jsp?shopId=" + shopId
							+ "&saleId=" + saleId;

				});

		/** 增加营销 **/
		$(".addbtn").click(function() {
			location.href = "salesDetail.jsp?shopId=" + shopId;
		});

		/** 删除营销 **/
		$(".delbtn").click(function(event) {
			if (document.all) {
				event.cancelBubble = true;
			} else {
				event.stopPropagation();
			}
			var del = $(this);
			$.Zebra_Dialog('<strong>是否删除？', {
				'type' : 'question',
				'title' : '提示',
				'buttons' : [ 'Yes', 'No' ],
				'onClose' : function(caption) {
					if (caption == 'Yes') {
						$.ajax({
							type : "post",
							dataType : "json",
							traditional : true,
							data : {
								shopId : $("#shopId").val(),
								saleId : del.siblings("#saleId").val()
							},
							url : "../salesServlet",
							error : function(jqXHR, textStatus, errorThrown) {
								$.Zebra_Dialog(jqXHR.responseText, {
									'type' : 'error',
									'title' : '错误'
								});
							},
							success : function(data) {
								$.Zebra_Dialog(data.result, {
									'type' : 'information',
									'title' : '成功'
								});
								window.location.reload();
							}

						});

					} else {
						return;
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
			String shopId = String.valueOf(shop.getId());
			List<Sales> sales = DAOFactory.createShopDAO().getSalesByShop(openId, 0, -1);
			if (sales.isEmpty() || sales.size() == 0) {
				String noSales = "无促销活动。";
				request.setAttribute("noSales", noSales);
			} else {
				request.setAttribute("sales", sales);
			}
			request.setAttribute("openId", openId);
			request.setAttribute("shopId", shopId);
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
		<input type="hidden" id="shopId" name="shopId" value="${shopId}">
		<div class="salesList">
			<c:forEach items="${sales}" var="sale">
				<div class="sales">
					<input type="hidden" id="saleId" value="${sale.id}">
					<h5 id="salesName">${sale.name}</h5>
					<span id="salesType">满￥${sale.price}元，返￥${sale.bonus}元</span> <input
						type="button" value="删除" class="delbtn">
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>