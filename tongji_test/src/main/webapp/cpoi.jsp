<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String rq1 = request.getParameter("rq1");
if (rq1 == null) {
	rq1 = "2014-7-1";
}
String rq2 = request.getParameter("rq2");
if (rq2 == null) {
	rq2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date(new Date().getTime() - 1000*60*60*24));
	rq2 = "2014-7-15";
}
String monitorCircle = request.getParameter("monitorCircle");
if(monitorCircle == null) {
	monitorCircle = "1";
}
%>
<!DOCTYPE html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
  <head>
	<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="description" content="北京智慧联合，恐龙智库，食品安全，舆情指数" />
	<meta name="keywords" content="北京智慧联合，恐龙智库，食品安全，舆情指数，食品，指数，舆情"/>
	<meta name="author" content="dufugang, 867658127@qq.com" />
	<meta name="robots" content="index,follow" />
	<meta name ="viewport" content ="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
	<meta name="msapplication-TileColor" content="#000"/> <!-- Windows 8 磁贴颜色 -->
	<meta name="msapplication-TileImage" content="images/icon.png"/> <!-- Windows 8 磁贴图标 -->
	<title>恐龙智库</title>
	<link rel="stylesheet" type="text/css" href="css/theme.css"/>
	<link rel="stylesheet" type="text/css" href="css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="css/cpoi.css"/>
  </head>
  
  <body>
  <script src="scripts/jquery-1.7.2.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="scripts/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="scripts/cookies.js"></script>
  <script src="scripts/echarts-plain-original-map.js"></script>
  <script type="text/javascript" src="scripts/jquery.paging.js"></script>
  <script type="text/javascript" src="scripts/cpoi.js"></script>
  <script type="text/javascript">
	$(function(){
		
		//二级
		$(".nav_left_2").hover(function(){
			$(".show").css("position","relative");
			$(this).children(".nav_left_3").slideDown(150);
			$(this).children("a").css({background:"#26A8E2"});
		},function(){
			$(this).children(".nav_left_3").slideUp(150);	
			$(this).children("a").css({background:"none"});
			$(".show").css("position","static");
		});
		//************************导航按钮的相关事件*******************************************************/
		
		//细分行业 下的 所有行业 json数据格式
		var data = {
				"_01":["方便食品行业","1500"],
				"_02":["罐头食品行业","1501"],
				"_03":["酒类行业","1502"],
				"_04":["调味品行业","1503"],
				
				"_05":["保健食品行业","1517"],
				"_06":["茶叶行业","1507"],
				"_07":["糖果类行业","1508"],
				"_08":["清真食品行业","1519"],
				
				"_09":["果蔬类行业","1510"],
				"_10":["乳制品行业","1506"],
				"_11":["食用油行业","1512"],
				"_12":["肉类及肉制品行业","1509"],
				
				"_13":["粮食及粮食制品类","1511"],
				"_14":["水产品行业","1513"],
				"_15":["豆制品行业","1515"],
				"_16":["菌类行业","1514"],
				
				"_17":["休闲食品行业","1516"],
				"_18":["饮料行业","1504"],
				"_19":["食品添加剂行业","1518"],
				"_20":["蛋制品行业","1505"]
				};
		
		
		//显示&隐藏 左部 分类的下级菜单
		$(".right_2 li").click(function(){
			$(".right_2 dl").css("display","none");
			$(this).next().css("display","block");
			
			$("#type").hide();
			$(".show").show();
			//设置 当前位置的信息
			setCurrPost(this);
		});
		//左部分类的下级菜单的 选中项设置（以及 单击事件）
		$(".right_2 dl dt a").click(function(){
			$(".right_2 dl dt a").removeClass("right_type_bgc");
			$(this).addClass("right_type_bgc");
			$("#type").hide();
			$(".show").show();
			
			//设置 当前位置的信息
			setCurrPost(this);
			
			//调用 当前项 显示数据
			callData(this);
			
		});
		//上部导航条中  设置当前选中项
		$(".nav_left li").click(function(){
			$(".nav_left li").removeClass("nav_left_bgc");
			$(this).addClass("nav_left_bgc");
			$("#type").hide();
			$(".show").show();
		});
		//全行业舆情指数 按钮的单击事件
		$(".nav_left li:eq(0)").click(function(){
			$(".right .right_1").empty().append("全行业舆情指数");
			
			//设置分类的 各项 id 值
			setTypeId(0);
			
			//初始化选中项 以及 当前的位置信息
			$("#news_1").empty().append("全行业舆情指数");
			
			//调用  默认显示的数据
			//callData($(".right_2 dl dt a:eq(0)"));
			cpoi0();
			
		});
		//细分行业舆情指数 按钮的单击事件
		$(".nav_left li:eq(1)").click(function(){
			$(".show").hide();
			$("#type").show();
		});
		
		//细分行业下的以及分类图片 事件
		$("#type img").click(function(){
			var id = $(this).attr("id");
			$(".right .right_1").empty().append(data["_"+id][0]);
			
			//设置分类的 各项 id 值
			setTypeId(data["_"+id][1]);
			
			//初始化选中项 以及 当前的位置信息
			init();
			
			//可以设置  默认 调用的数据
			//callData($(".right_2 dl dt a:eq(0)"));
			cpoiFor20(data["_"+id][1]);
			$("#news_1").empty().append("细分行业舆情指数 > "+data["_"+id][0]);
			
		});
		
		//进入首页时，初始化进入页面 默认 打开及选中项的设置
		init();
		//进入首页时，设置 默认 调用的数据
		//callData($(".right_2 dl dt a:eq(0)"));
		
		//cpoi();
		$(".show").hide();
		$("#type").show();

	//$("select[name='monitorCircle'] option[value='"+mCircle+"']").attr("selected", "selected");
		
	});
	//设置分类的id方法
	function setTypeId(id){
		$(".right_2 a").attr("id",id);
	}
	//设置 当前位置信息
	function setCurrPost(obj){
		var a_text = '<a href="javascript:void(0)">'+$(obj).text()+'</a>';
		var li_text = '<a href="javascript:void(0)">'+$(obj).parents("dl").prev().children().text()+'</a>';
		var type = '<a href="javascript:void(0)">'+($(".right_2 dl a:eq(0)").attr("id") == '0'?"全行业舆情指数":"细分行业舆情指数")+'</a>';
		var hy = '<a href="javascript:void(0)">'+$(".right .right_1").text()+'</a>';
		var direct = '&nbsp;&gt;&nbsp;';
		var text = type + ($(type).text() == "细分行业舆情指数" ? direct + hy : "") + direct + li_text + direct + a_text;
		$("#news_1").empty().append(text);
	}
	
	//默认显示（曝光程度--新增发文），以及 当前位置的信息
	function init(){
		$(".right_2 dl").hide();
		//$(".right_2 dl:eq(0)").show();
		//$(".right_2 dl dt a").removeClass("right_type_bgc");
		//$(".right_2 dl dt a:eq(0)").addClass("right_type_bgc");
		$("#type").hide();
		$(".show").show();
		//setCurrPost($(".right_2 dl dt a:eq(0)"));
	}
	
	//默认&当前项  调用的数据的函数
	function callData(ob){
		var obj = $(ob);
		var id = obj.attr("id");
		var clazz = obj.attr("class").split(" ")[0];
		//$("#container").empty().append("id=="+id+",class=="+clazz).append("<br>").append($("#news_1").text());
	}
	
	function AddFavorite(sURL, sTitle){
	    try{
	        window.external.addFavorite(sURL, sTitle);
	    }catch (e){
	        try{
	            window.sidebar.addPanel(sTitle, sURL, "");
	        }catch (e){
	            alert("加入收藏失败，请使用Ctrl+D进行添加");
	        }
	    }
	}
	</script>
	<!-- 头部分 -->
	<div class="header_1">
		<div class="box">
	    <a href="http://www.kolong.cn/index.php" class="logo"><img src="images/logo.png" /></a>
	    <div class="header_1_1">
	    	<form action="http://www.kolong.cn/search.php" name="form" method="get">
	    	<input type="text" name="key" placeholder="请输入关键字" />
	      <input type="submit" value="搜索" />
	      </form>
	    </div>
	    <div class="header_1_2"><a href="http://www.kolong.cn/">中文版</a>&nbsp;|&nbsp;<a href="http://www.kolong.cn/en/">English</a></div>
	    <div class="header_1_3">
	      <a href="http://www.kolong.cn/index.php"><img src="images/img_1_3.jpg" /></a>
	      <a href="javascript:AddFavorite(window.location,document.title)"><img src="images/img_1_5.jpg" /></a>
	      <a href="http://www.kolong.cn/about.php?type=-0-4-21-"><img src="images/img_1_7.jpg" /></a>
	    </div>
	  </div>
	</div>
	
	<!-- 位置部分 -->
	<div class="box news_1"><img src="images/list_3.jpg" />当前的位置：<font id="news_1"><a href="#">首页</a>&nbsp;&gt;&nbsp;<a href="#">指数数据</a>&nbsp;&gt;&nbsp;<a href="#">食品安全指数</a></font></div>
	
	<!-- 中间部分 -->
	<div class="box center">
	  <div class="left">
	  	<div class="nav_left">
	    	<ul class="nav_left_1">
	      	<li class="nav_left_2"><a href="javascript:void(0)">全行业舆情指数</a>
	        </li>
	      	<li class="nav_left_2"><a href="javascript:void(0)">细分行业舆情指数</a>
	        </li>
	        <li class="nav_left_2 nav_left_2_1"><a href="javascript:void(0)">指数数据</a>
	        	<dl class="nav_left_3">
	          	<dt><a href="javascript:void(0)" class="cpoiData">舆情指数</a>
	            </dt>
	          	<dt><a href="http://houtai.kolong.com.cn/" target="_blank">原始数据</a></dt>
	          </dl>
	        </li>
	      	<li class="nav_left_2"><a href="javascript:void(0)" class="aboutCpoi">关于指数</a></li>
	      </ul>
	    </div>
	    
	    <!-- 中间  分类部分 -->
	    <div id="type" style="padding:0; line-height:22px;z-index:-101; max-width: 780px;display: none;">
	    	<table width="780" height="487" border="0" align="center" cellpadding="0" cellspacing="0" id="__01">
				<tr>
					<td colspan="13">&nbsp;			</td>
				</tr>
				<tr>
					<td rowspan="5">&nbsp;			</td>
					<td colspan="3">
						<img id="01" src="images/zs_03.png" width="244" height="80" alt="方便食品行业">			</td>
					<td rowspan="2">&nbsp;			</td>
					<td colspan="3">
						<img id="02" src="images/zs_05.png" width="245" height="80" alt="罐头食品行业">			</td>
					<td rowspan="3">&nbsp;			</td>
					<td>
						<img id="03" src="images/zs_07.png" width="116" height="80" alt="酒类行业">			</td>
					<td>&nbsp;			</td>
					<td>
						<img id="04" src="images/zs_09.png" width="116" height="80" alt="调味品行业">			</td>
					<td rowspan="5">&nbsp;			</td>
				</tr>
				<tr>
					<td colspan="3">
						<img id="05" src="images/zs_15.png" width="244" height="80" alt="保健食品行业">			</td>
					<td>
						<img id="06" src="images/zs_16.png" width="117" height="80" alt="茶叶行业">			</td>
					<td rowspan="4">&nbsp;			</td>
					<td>
						<img id="07" src="images/zs_18.png" width="118" height="80" alt="糖果类行业">			</td>
					<td colspan="3"><div align="center"><img id="08" src="images/zs_19.png" width="244" height="80" alt="清真食品行业"> </div></td>
				</tr>
				<tr>
					<td>
						<img id="09" src="images/zs_24.png" width="117" height="78" alt="果蔬类行业">			</td>
					<td>&nbsp;			</td>
					<td colspan="3">
						<img id="10" src="images/zs_26.png" width="247" height="78" alt="乳制品行业">			</td>
					<td>
						<img id="11" src="images/zs_27.png" width="118" height="78" alt="食用油行业">			</td>
					<td colspan="3">
						<div align="center"><img id="12" src="images/zs_28.png" width="244" height="78" alt="肉类及肉制品行业">			</div></td>
				</tr>
				<tr>
					<td colspan="3">
						<img id="13" src="images/zs_33.png" width="244" height="87" alt="粮食及粮食制品类">			</td>
					<td rowspan="2">&nbsp;			</td>
					<td>
						<img id="14" src="images/zs_35.png" width="117" height="87" alt="水产品行业">			</td>
					<td colspan="3">
						<img id="15" src="images/zs_36.png" width="245" height="87" alt="豆制品行业">			</td>
					<td rowspan="2">&nbsp;			</td>
					<td>
						<div align="center"><img id="16" src="images/zs_38.png" width="116" height="87" alt="菌类行业">			</div></td>
				</tr>
				<tr>
					<td colspan="3">
						<img id="17" src="images/zs_43.png" width="244" height="88" alt="休闲食品行业">			</td>
					<td>
						<img id="18" src="images/zs_44.png" width="117" height="88" alt="饮料行业">			</td>
					<td colspan="3">
						<img id="19" src="images/zs_45.png" width="245" height="88" alt="食品添加剂行业">			</td>
					<td>
						<div align="center"><img id="20" src="images/zs_46.png" width="116" height="88" alt="蛋制品行业">			</div></td>
				</tr>
				<!-- 空白行，用于定位整个的样式及宽度位置的 -->
				<tr style="">
					<td>
						<img src="images/k_b.gif" width="11" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="117" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="10" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="117" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="13" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="117" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="10" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="118" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="11" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="116" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="12" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="116" height="0" alt=""></td>
					<td>
						<img src="images/k_b.gif" width="12" height="0" alt=""></td>
				</tr>
				 
			</table>
	    </div>
	    
	    <!-- 中间  其他显示部分 -->
	    <div class="show" style="position:static;z-index:-101; max-width: 780px">
	    	<form id="releaseRq" action="" method="get">
			发布周期：<input type="text"  name="rq1" value="<%=rq1%>" id="rq1" onClick="WdatePicker()" style="font-family:宋体; font-size: 12px; font-weight: lighter;" />
			 	- <input type="text" name="rq2" value="<%=rq2%>" id="rq2" onClick="WdatePicker()" style="font-family:宋体; font-size: 12px; font-weight: lighter;"/>
			监测周期：
			<select name="monitorCircle">
				<option value="1" selected="selected">一天</option>
				<option value="7" >一周</option>
				<option value="15">半个月</option>
				<option value="30">一个月</option>
			
			</select> 	<input id="tongji" type="submit" onclick="return validateTime();" class="button button-rounded button-flat-primary button-tiny" value="确定" />
			</form>
			<br/>
	    	<div class="show_1">
	    		<div id="container"></div>
	    		<div id="hint_1"></div><!-- 一个图 -->
	    	</div>
	     	<div class="show_1">
				<div id="point"></div><!-- 一个图 -->
	    	</div>
	     	<div class="show_1"><!-- 一表 -->
	    		<div id="titleC"></div>
	    		<div id="paginationTitleC" class="paginationTitleC"></div>
	    		<div><select id="selectTitleC">
	    			<option value="1" >过滤</option>
	    			<option value="0" selected="selected">未过滤</option>
	    		</select></div>
	    	</div>
	    	<div class="show_1">
	    		<div id="containerApp"></div><!-- 文章列表 -->
	    		<div id="paginationContainerApp" class="paginationContainerApp"></div>
	    		<div><select id="selectContainerApp">
	    			<option value="1" selected="selected">过滤</option>
	    			<option value="0">未过滤</option>
	    		</select></div>
	    	</div>
	    	<div class="show_1">
	    		<div id="containerOrg"></div><!-- 文章列表 -->
	    	</div>
	    	<div class="show_1">
	    		<div id="containerTable"></div><!-- 一图 -->
	    	</div>
	    	<div class="show_1">
	    		<div id="containerTitle"></div><!--一表 -->
	    	</div>
	    	<div class="show_1">
	    		<div id="hint_5"></div><!-- 一个图 -->
	    		<div id="containerColumnAll"></div>
	    	</div>
	    	<div class="show_1">
	    		<div id="containerSides"></div><!-- 文章列表 -->
	    	</div>
	    	<div class="show_1">
	    		<div id="containerMins"></div><!-- 文章列表 -->
	    	</div>
	    	<div class="show_1">
	    		<div id="hint_7"></div><!-- 一个图 -->
	    		<div id="containerColumn"></div>
	    	</div>
	    	<div class="show_1">
	    		<div id="containerTu1"></div><!-- 图 -->
	    	</div>
	    	<div class="show_1">
	    		<div id="containerTu2"></div><!-- 图 -->
	    	</div>
	    </div>
	  </div>
	  <div class="right" style="">
	    <h1 class="right_1">全行业舆情指数</h1>
	    <ul class="right_2" style="border:1px solid #ddd;padding:0px 4px 4px 4px;">
	      <li><a href="javascript:void(0)" class="exposIndex" id="0">曝光程度</a></li>
	      	<dl class="right_type">
          	<dt><a href="javascript:void(0)" class="userCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;新增发文量</a></dt>
          	<dt><a href="javascript:void(0)" class="userCountArea" id="0">&nbsp;&nbsp;&nbsp;&nbsp;累积发文量</a></dt>
          	</dl>
	      <li><a href="javascript:void(0)" class="participationIndex" id="0">参与程度</a></li>
	      	<dl class="right_type">
           	<dt><a href="javascript:void(0)" class="viewCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;新增浏览量</a></dt>
           	<dt><a href="javascript:void(0)" class="viewCountArea" id="0">&nbsp;&nbsp;&nbsp;&nbsp;累积浏览量</a></dt>
           	<dt><a href="javascript:void(0)" class="reprintCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;新增转载量</a></dt>
           	<dt><a href="javascript:void(0)" class="reprintCountArea" id="0">&nbsp;&nbsp;&nbsp;&nbsp;累积转载量</a></dt>
           	<dt><a href="javascript:void(0)" class="replyCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;新增评论量</a></dt>
           	<dt><a href="javascript:void(0)" class="replyCountArea" id="0">&nbsp;&nbsp;&nbsp;&nbsp;累积评论量</a></dt>
           	</dl>
	      <li><a href="javascript:void(0)" class="publicOpinionIndex" id="0">舆情观点</a></li>
	      	<dl class="right_type">
           	<dt><a href="javascript:void(0)" class="minCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;敏感词</a></dt>
           	<dt><a href="javascript:void(0)" class="sidesCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;负面词</a></dt>
           	</dl>
	      <li><a href="javascript:void(0)" class="siteFeatureIndex" id="0">网站特点</a></li>
	      	<dl class="right_type">
           	<dt><a href="javascript:void(0)" class="officialCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;网站权威性</a></dt>
           	<dt><a href="javascript:void(0)" class="countryCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;区域全面性</a></dt>
           	<dt><a href="javascript:void(0)" class="traditionCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;类型全面性</a></dt>
           	<dt><a href="javascript:void(0)" class="site_yxl" id="0">&nbsp;&nbsp;&nbsp;&nbsp;网站影响力</a></dt>
           	<dt><a href="javascript:void(0)" class="sitesCount" id="0">&nbsp;&nbsp;&nbsp;&nbsp;网站数量</a></dt>
           	</dl>
	      <li><a href="javascript:void(0)" class="transIndex" id="0">传播进度</a></li>
	      	<dl class="right_type">
           	<dt><a href="javascript:void(0)" class="exposIndexRate" id="0">&nbsp;&nbsp;&nbsp;&nbsp;曝光程度变化率</a></dt>
           	<dt><a href="javascript:void(0)" class="participationIndexRate" id="0">&nbsp;&nbsp;&nbsp;&nbsp;参与程度变化率</a></dt>
           	<dt><a href="javascript:void(0)" class="publicOpinionIndexRate" id="0">&nbsp;&nbsp;&nbsp;&nbsp;舆情观点变化率</a></dt>
           	<dt><a href="javascript:void(0)" class="siteFeatureIndexRate" id="0">&nbsp;&nbsp;&nbsp;&nbsp;网站特点变化率</a></dt>
           	</dl>
	    </ul>
	    
	    <!-- 
	    <div class="right_3">
	      <h1 class="right_4">联系我们</h1>
	      <div class="right_5">
	        <h1>使用以下方式将便于您了解更多</h1>
	        <p><img src="images/list_14.jpg" />E-mail给我们</p>
	        <p><img src="images/list_17.jpg" />或即刻致电</p>
	        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;400-5698-6264</p>
	        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;010-5694-3569</p>
	      </div>
	    </div>
	    <h1 class="right_6">最新动态</h1>
	    <ul class="right_7">
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	      <li><a href="#"><img src="images/list_21.jpg" />标题标题标题标题标题标题标题标题</a></li>
	    </ul>
	    
	     -->
	  </div>
	  <div class="clear" ></div>
	</div>
	
	<!-- 底部部分 -->
	<div class="box bottom">
		<img src="images/ico_3.jpg" class="bottom_3" height="39" />
	  <div class="bottom_1">
	  	<p><a href="http://www.kolong.cn/about.php?type=-0-4-20-">关于我们</a> | <a href="http://www.kolong.cn/about.php?type=-0-4-21-">联系我们</a> | <a href="http://www.kolong.cn/about.php?type=-0-4-22-">战略合作</a> | <a href="http://www.kolong.cn/about.php?type=-0-4-23-">网站地图</a> | <a href="http://www.kolong.cn/about.php?type=-0-4-24-">友情链接</a> | <a href="http://www.kolong.cn/about.php?type=-0-4-25-">人才招聘</a> | <a href="http://www.kolong.cn/message.php">留言板</a></p>
	    <p>版权所有：恐龙智库 Kolong 京ICP备09082752号-2</p>
	  </div>
	  <img src="images/img_1_27.jpg" class="bottom_2" />
	</div>
<script>
//全局变量
var $inputRq1 = $("input#rq1"),
	$inputRq2 = $("input#rq2"),
	$selectM = $("select[name='monitorCircle']"),
	startRq = $inputRq1.val(),
	endRq = $inputRq2.val(),
	mCircle = $selectM.val(),
	latelyClick = {
	name:1,
	password:"cpoi"
	};
</script>
  </body>
</html>