<%@page import="com.microcard.bean.Commodity"%>
<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page isELIgnored="false"%>
<%@page import="com.microcard.bean.*"%>
<%@page import="com.microcard.dao.DAOFactory"%>
<%@page import="java.util.*"%>
<%@page import="com.microcard.dao.hibernate.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jstl/function"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" id="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销信息</title>
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

.header {
	width: 100%;
	height: 50px;
	background: url("../resources/images/header.jpg");
	box-shadow: #666 0 0 5px;
}

.returnbtn,.comfirmbtn {
	padding: 6px 15px;
	border-radius: 4px;
	background: #9ACD32;
	border: 0;
	color: white;
	margin-top: 10px;
}

.returnbtn {
	float: left;
	margin-left: 2%;
}

.comfirmbtn {
	float: right;
	margin-right: 2%;
}

.content {
	position: absolute;
	width: 100%;
	height: auto;
	top: 15%;
	font-size: 1.2em;
	color: #6E6E6E;
}

#salesPrice,#salesBonus {
	width: 20%;
}

.goods {
	position: absolute;
	width: 100%;
	top: 8%;
	border-bottom: 1px solid #E1E1E1;
}

table tr td {
	padding: 4% 0 4% 8%;
}

table tr td:FIRST-CHILD {
	width: 35%;
}

table tr td input {
	width: 65%;
}
</style>
</head>
<body>
	<%
		try {
			HibernateUtil.instance().beginTransaction();
			String saleId = request.getParameter("saleId");
			String shopId = request.getParameter("shopId");
			Shop shop = DAOFactory.createShopDAO().getShopByID(
					Long.valueOf(shopId));
			// 商铺的商品
			Set<Commodity> shopCommodities = shop.getCommodities();
			// saleId不为空则为修改促销信息
			if (saleId != null) {
				Sales sales = DAOFactory.createSalesDAO().getSalesByID(
						Long.valueOf(saleId));
				// 促销的商品
				Set<Commodity> salesCommodities = sales.getCommodities();
				Iterator<Commodity> salesCommodity = salesCommodities
						.iterator();
				// 获取已选中的促销商品
				Set<Commodity> checkedCommodities = new HashSet<Commodity>();
				while (salesCommodity.hasNext()) {
					Commodity checkedCommodity = salesCommodity.next();
					if (shopCommodities.contains(checkedCommodity)) {
						// 添加商品到被选择的促销商品中
						checkedCommodities.add(checkedCommodity);
					}
				}
				request.setAttribute("checkedCommodities",
						checkedCommodities);
				request.setAttribute("sales", sales);
			}

			if (shopCommodities.isEmpty() || shopCommodities.size() == 0) {
				String noCommodities = "暂无促销 商品。";
				request.setAttribute("noCommodities", noCommodities);
			} else {
				request.setAttribute("shopCommodities",
						shopCommodities.iterator());
			}
			HibernateUtil.instance().commitTransaction();
		} catch (Exception e) {
			HibernateUtil.instance().rollbackTransaction();
		} finally {
			HibernateUtil.instance().closeSession();
		}
	%>
	<form action="../salesDetailServlet" method="post">
		<div class="header">
			<input type="button" class="returnbtn"
				onclick="javascript:history.go(-1);" value="返回"> <input
				type="submit" class="comfirmbtn" value="确认">
		</div>
		<div class="content">
			<table class="goods">
				<tr style="border-bottom: 1px solid #E1E1E1">
					<td>促销名称：</td>
					<td><input type="text" name="salesName" id="salesName"
						value="${sales.name}"></td>
				</tr>

				<tr style="border-bottom: 1px solid #E1E1E1">
					<td colspan="2">买满<input type="number" name="salesPrice"
						id="salesPrice" value="${sales.price}">返现<input
						type="number" name="salesBonus" id="salesBonus"
						value="${sales.bonus}"></td>
				</tr>
				<tr style="border-bottom: 1px solid #E1E1E1">
					<td>促销商品：</td>
					<td>${noCommodities}</td>
				</tr>
				<c:forEach items="${shopCommodities}" var="commodity">
					<c:choose>
						<c:when test="${fn:contains(checkedCommodities,commodity)}">
							<tr>
								<td></td>
								<td><input type="checkbox" checked="checked" name="checkbox"
									value="${commodity.id}">${commodity.name}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td></td>
								<td><input type="checkbox" name="checkbox"
									value="${commodity.id}">${commodity.name}</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</table>
		</div>
		<input type="hidden" name="salesId" id="salesId" value="${sales.id}">
	</form>
</body>
</html>