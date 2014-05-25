<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@page import="com.microcard.bean.*"%>
<%@page import="com.microcard.dao.DAOFactory"%>
<%@page import="java.util.*"%>
<%@page import="com.microcard.dao.hibernate.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的商品</title>
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
	width: 50%;
	height: 21px;
	line-height: 21px;
	padding: 4px 7px;
	color: b3b3b3;
	border: 1px solid #999;
	border-radius: 2px;
	background-color: #fbfbfb;
	margin-left: 12%;
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

.commodityList {
	border-top: 1px solid #B5B5B5;
	width: 100%;
	float: left;
}

.commodity {
	width: 100%;
	float: left;
	border-bottom: 1px dashed #B5B5B5;
}

#commodityName {
	padding-top: 1%;
	font-size: 1.5em;
	color: #6E6E6E;
	padding-left: 1%;
}

#goodsPrice {
	padding: 1% 0 1% 1%;
	float: left;
	font-size: 1.5em;
	color: red;
	text-shadow: -2px -2px 0 #E6E6FA;
}

.delbtn {
	float: right;
	margin-right: 3%;
	border-radius: 2px;
	padding: 5px;
	margin-top: -3%;

}

.click_more {
	width: 100%;
	height: 60px;
	line-height: 60px;
	border: solid 1px #EFEFEF;
	text-align: center;
}

.commodityInfo {
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
	left: 5%;
	right: 5%;
	bottom: 20%;
	width: 90%;
	height: 60%;
	z-index: 6;
	background: #E9E9E9;
	box-shadow: #666 0 0 5px;
	border-radius: 4px;
}

.dialog_bar {
	border-radius: 4px 4px 0 0;
	height: 40px;
	width: 100%;
	text-align: center;
	background: url("../resources/images/header.jpg");
}

.dialog_title {
	height: 40px;
	line-height: 40px;
	color: white;
	font-size: 1.2em;
}

.dialog_content ul li {
	margin: 5% 0 5% 5%;
}

.dialog_content ul li input {
	margin-left: 4%;
}

</style>
<script type="text/javascript">
	$(function() {

		/** 点击商品修改商品信息 **/
		$(".commodity").click(function() {
			$("#dialog_commodityName").val(
					$(this).find("h5").html());
			$("#dialog_commodityPrice").val(
					$(this).find("span").html().split("￥")[1]);
			$("#commodityid").val($(this).find("#commodityid").val());
			$(".commodityInfo").fadeIn("normal");
			

		});

		/** 获取商品图片的url值 **/
		$("#dialog_commodityImgUrl").change(function() {
			$("#imgUrl").attr("src", $(this).val());

		});

		/** 关闭dialog **/
		$(".dialog_cancle").click(function() {
			$(".commodityInfo").fadeOut("normal");
		});

		/** 添加商品 **/
		$(".addbtn").click(function(event) {
			$("#dialog_commodityName").val("");
			$("#dialog_commodityPrice").val("");
			$("#imgUrl").attr("src", "");
			$(".commodityInfo").fadeIn("normal");
			$("#commodityid").val("");

		});

		/** 删除商品 **/
		$(".delbtn").click(function(event) {
			if (document.all) {
				event.cancelBubble = true
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
					        type: "post",
					        dataType: "json", traditional:true,
					        data: { name: del.parent(".commodity").find("h5").html(),
					        	    price:  del.parent(".commodity").find("span").html().split("￥")[1],
					        	    id: del.parent(".commodity").find("#commodityid").val(),
					        	    openid: $("#openid").val(),
					        	    oper: 'delete'
					              },
					        url: 'http://localhost:8080/microcard/commodityServlet.do',
					        error: function( jqXHR, textStatus, errorThrown){		        
					       	 $.Zebra_Dialog(jqXHR.responseText ,
			        				 {'type': 'error',
			        			     'title': '错误'});        	
					        },		          
					       success: function(data){
					    	   $.Zebra_Dialog(data.result ,
				        				 {'type': 'information',
				        			     'title': '成功'}); 
					    	   del.parent(".commodity").remove();
					        } 
					       
					    });
				
					} else {
						return;
					}
				}
			});
		});
		
		$(".dialog_save").click(function(event){
			$(".commodityInfo").fadeOut("normal");
			$.ajax({ 
		        type: "post",
		        dataType: "json", traditional:true,
		        data: { name: $('#dialog_commodityName').val(),
		        	    price:  $('#dialog_commodityPrice').val(),
		        	    id: $('#commodityid').val(),
		        	    openid: $("#openid").val(),
		        	    oper: 'updateorsave'
		              },
		        url: "../commodityServlet.do",
		        error: function( jqXHR, textStatus, errorThrown){		        
		       	 $.Zebra_Dialog(jqXHR.responseText ,
        				 {'type': 'error',
        			     'title': '错误'});        	
		        },		          
		       success: function(data){	    
		    	   $.Zebra_Dialog(data.result ,
	        				 {'type': 'information',
	        			     'title': '成功',
	        			     'buttons' : [ 'Yes' ],
	        			     'onClose': function(caption) {
	        			    	 window.location.reload();
	        			     }
		    	   });
		        } 
		       
		    });
		});

	});
</script>
</head>
<body>
	<%
		try{
			HibernateUtil.instance().beginTransaction();
			String opendId = request.getParameter("OPENID");
			List<Commodity> commodities = DAOFactory.createShopDAO().getCommodity(opendId, 0, -1);
			request.setAttribute("commodities", commodities);
			HibernateUtil.instance().commitTransaction();
		} catch(Exception e){
			HibernateUtil.instance().rollbackTransaction();
		} finally{
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
		<div class="commodityList">
  			<c:forEach var="item" items="${commodities}">
			   <div class="commodity">
				<h5 id="commodityName">${item.name}</h5>
				<span id="goodsPrice">￥${item.price}</span> 
				<input type="button" value="删除"  class="delbtn">
				<input type="hidden" id="commodityid" value="${item.id}">
			</div>
			</c:forEach>
		</div>
		<div class="click_more" style="clear: both;">点击查看更多</div>
		<div class="commodityInfo">
			<div class="dialog-overlay"></div>
			<!-- 对话框 -->
			<div class="dialog">
					<div class="dialog_bar">
						<span class="dialog_title">标题</span>
					</div>
					<div class="dialog_content">
						<ul style="list-style: none; display: inline;">
							<li>商品名称:<input type="text" name="dialog_commodityName"
								id="dialog_commodityName" style="width: 60%"></li>
							<li>商品价格:<input type="number" name="dialog_commodityPrice"
								id="dialog_commodityPrice" style="width: 30%"></li>						
						</ul>
						<div style="width: 100%; text-align: center;">
							<input class="dialog_cancle" type="button" value="取消"
								style="width: 30%; height: 30px; border-radius: 2px; border: 1px solid #B5B5B5">
							<input class="dialog_save" type="button" value="保存"
								style="width: 30%; height: 30px; border-radius: 2px; border: 1px solid #B5B5B5">
							<input type="hidden" id="commodityid">
							<input type="hidden" id="openid" value="${param.OPENID}">
						</div>
				  </div>
			</div>
		</div>
	</div>
</body>
</html>