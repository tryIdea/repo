<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String userId = null;
String simId = "1";
String totalCount = "0";
	userId = request.getParameter("userId");
	simId = request.getParameter("simId");
	totalCount = request.getParameter("totalCount");

%>

<!DOCTYPE html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>相关报表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/cpoi.css"/>
  </head>
  
  <body>
  	<div class="spagination">
  	<div id='simiTitle'></div>
    <div id="pagination" class="pagination"></div>
    </div>
    <script src="scripts/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="scripts/cpoi.min.js"></script>
    <script type="text/javascript" src="scripts/jquery.paging.min.js"></script>
    <script>
    	$("#pagination").paging(<%=totalCount%>, {
    		format: '[< ncnnn >]',
    		perpage: 10, 
    	    lapping: 0, 
    	    page: 1, 
    		onSelect: function (page) {

    			var jqxhr = $.ajax( {
    	        	type : 'get',
    	        	url : 'table',
    	        	data: {
    	        		dataType : 'simiTitle',
    	        		userId : <%=userId%>,
    	        		sim_id : <%=simId%>,
    	        		pageNo : page,
    	        		pageSize : 10
    	        	},
    	        	dataType: 'json'
    	        } )
    	    	  .done(function(responseText) {
    	    		  var tbody = "<table class='tftable'><tr><th colspan='4'> 文章列表</th></tr><tr><th>行业名称</th><th>来源网站</th><th>文章标题</th><th>发布时间</th></tr>";
    	    	    	$.each(responseText,function(n,value) {  
    	    	    		value = $.parseJSON(value);
    	    	       		 var trs = "";  
    	    	              trs += "<tr><td>" +getHint(value.user_id)+"</td><td><a target='_blank' href='"+ value.site_url +"'>" +value.site_name+"</a></td> <td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>"+interceptStr(value.title) +"</a></td><td>" +value.pubdate+"</td></tr>";  
    	    	              tbody += trs;         
    	    	            });  
    	    	    	$("#simiTitle").html(tbody + "</table>");
    	    	  })
    	    	  .fail(function() {
    	    	    alert( "error" );
    	    	  })
    	    	  .always(function() {
    	    	  });
    		},
    		onFormat: function (type) {
    	        switch (type) {
    	        case 'block': // n and c
    	            return '<a href="#">' + this.value + '</a>';
    	        case 'next': // >
    	            return '<a href="#">&gt;</a>';
    	        case 'prev': // <
    	            return '<a href="#">&lt;</a>';
    	        case 'first': // [
    	            return '<a href="#">首页</a>';
    	        case 'last': // ]
    	            return '<a href="#">尾页</a>';
    	        }
    	    }
    	});
    </script>
  </body>
</html>
