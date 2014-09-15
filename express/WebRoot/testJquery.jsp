<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>test jquery</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<style type="text/css">
	div.panel {
		display:none;
	}
	</style>

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
  </head>
  
  <body>
    <hr color="red" width="50%" align="center" size="10">
    <script type="text/javascript">
    $(document).ready(function() {
    	$("button").click(function() {
    		$(this).hide();
    		$(".panel").slideToggle("slow");
    	});
    });
    </script>
    <button type="button" >click me1</button>
    <button type="button" >click me2</button>
    <button type="button" >click me3</button>
    <button type="button" >click me4</button>
    <div id="div1" style="width: 100;height: 100; background-color: red;"></div>
    <div id="div2" style="width: 100;height: 100; background-color: green;"></div>
    <div class="panel" id="div3" style="width: 100;height: 100; background-color: blue;"></div>
  </body>
</html>
