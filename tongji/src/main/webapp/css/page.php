<?php /*?>
<?php 
	$maxnum = 15; //每页显示记录条数 
	
	$query1 = "SELECT COUNT(*) AS totalrows FROM web where bid=',0,$dl,$xl,'";
	$result1 = mysql_query($query1, $conn) or die(mysql_error()); 
	$rs1 = mysql_fetch_assoc($result1); 
	$totalRows1 = $rs1['totalrows']; //数据集数据总条数 
	$totalpages = ceil($totalRows1/$maxnum);//计算可分页总数，ceil()为上舍函数 
	
	if(!isset($_GET['page']) || !intval($_GET['page']) || $_GET['page'] > $totalpages) $page = 1; //对3种出错进行默认处理 
	//在url参数page不存在时，page不为10进制数时，page大于可分页数时，默认为1 
	else $page = $_GET['page']; 
	
	$startnum = ($page - 1)*$maxnum; //从数据集第$startnum条开始取，注意数据集是从0开始的 
	$query="SELECT * FROM web where bid=',0,$dl,$xl,'";
	$query.=" order by paixu asc,tuijian desc,shijian desc LIMIT $startnum,$maxnum";
	$result = mysql_query($query, $conn) or die(mysql_error()); 
	while($rs = mysql_fetch_assoc($result)){
?>
<li><span>
	<?=date('Y-m-d',strtotime($rs['shijian']))?>
	</span><a href="newshow.php?dl=<?=$dl?>&xl=<?=$xl?>&id=<?=$rs['id']?>">
	<?=$rs['title']?>
	</a></li>
<?php
}
?>
<?php */?>


<? if ($totalRows1==0){echo"<font color='#ff0000'>暂无信息...</font>";}else{?>
	<?php 
	//echo "共计<font color=\"#ff0000\">$totalRows1</font>条记录&nbsp;&nbsp;"; 
	//echo "<font color=\"#ff0000\">".$page."</font>"."/".$totalpages."页&nbsp;&nbsp;"; 
	
	//整理url
	//$t="news.php?dl=$dl&xl=$xl";
	$pageurl = $_SERVER["REQUEST_URI"];
	$parse_url = parse_url($pageurl);
	$url_query = $parse_url["query"]; 
	if ($url_query) {
	$url_query = ereg_replace("(^|&)page=$page", "", $url_query);
	$pageurl = str_replace($parse_url["query"], $url_query, $pageurl);
	if ($url_query)
	$pageurl .= "";
	else
	$pageurl .= "page";
	} else {
	$pageurl .= "?page";
	}
	//实现 << < 1 2 3 4 5> >> 分页链接 
	$pre = $page - 1;//上一页 
	$next = $page + 1;//下一页 
	$maxpages = 8;//处理分页时 << < 1 2 3 4 > >>显示4页 
	$pagepre = 1;//如果当前页面是4，还要显示前$pagepre页，如<< < 3 /4/ 5 6 > >> 把第3页显示出来 
	
	if($page != 1) { echo "<a href='".$pageurl."'>首页</a>"; 
	echo "<a href='".$pageurl."&page=".$pre."' class='pre'>上一页</a>";} 
	if($page == 1) { echo "<a href='javascript:void(0)'>首页</a>"; 
	echo "<a href='javascript:void(0)' class='pre'>上一页</a>";} 
	
	if($maxpages>=$totalpages) //如果总记录不足以显示4页 
	{$pgstart = 1;$pgend = $totalpages;}//就不所以的页面打印处理 
	elseif(($page-$pagepre-1+$maxpages)>$totalpages)//就好像总页数是6，当前是5，则要把之前的3 4 显示出来，而不仅仅是4 
	{$pgstart = $totalpages - $maxpages + 1;$pgend = $totalpages;} 
	else{ 
	$pgstart=(($page<=$pagepre)?1:($page-$pagepre));//当前页面是1时，只会是1 2 3 4 > >>而不会是 0 1 2 3 > >> 
	$pgend=(($pgstart==1)?$maxpages:($pgstart+$maxpages-1)); 
	} 
	
	for($pg=$pgstart;$pg<=$pgend;$pg++){ //跳转菜单 
	if($pg == $page) echo "<a href=".$pageurl."&page=".$pg." class='page_on'>".$pg."</a>"; 
	else echo "<a href=".$pageurl."&page=".$pg.">".$pg."</a>"; 
	} 
	if($page != $totalpages) 
	{echo "<a href='".$pageurl."&page=".$next."' class='next'>下一页</a>"; 
	echo "<a href='".$pageurl."&page=".$totalpages."'>尾页</a>";} 
	if($page == $totalpages) 
	{echo "<a href='javascript:void(0)' class='next'>下一页</a>"; 
	echo "<a href='javascript:void(0)'>尾页</a>";} 
	?>
			<select name="menu1" onChange="javascript:window.location=this.value;" class="page_select">
				<option value=""><?=$page."/".$totalpages?></option>
				<?php for($pg1=1;$pg1<=$totalpages;$pg1++) { 
	echo "<option value=\"".$pageurl."&page=".$pg1."\">".$pg1."</option>"; 
	}?>
	</select>
<?
}
?>
