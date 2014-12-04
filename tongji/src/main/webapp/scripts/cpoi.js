function getHint(id) {
	var ids = ["790","789","787","784","783","781","780","778","777","776","775","774","773","772","770","769","768","767","766","765","0"];
	id = '' + id;
	switch (id) {
		case ids[0]:
			return "方便食品行业";
		case ids[1]:
			return "罐头食品行业";
		case ids[2]:
			return "酒类行业";
		case ids[3]:
			return "调味品行业";
		case ids[4]:
			return "饮料行业";
		case ids[5]:
			return "蛋制品行业";
		case ids[6]:
			return "乳制品行业";
		case ids[7]:
			return "茶叶行业";
		case ids[8]:
			return "糖果类行业";
		case ids[9]:
			return "肉类及肉制品行业";
		case ids[10]:
			return "果蔬类行业";
		case ids[11]:
			return "粮食及粮食制品类行业";
		case ids[12]:
			return "食用油行业";
		case ids[13]:
			return "水产品行业";
		case ids[14]:
			return "菌类行业";
		case ids[15]:
			return "豆制品行业";
		case ids[16]:
			return "休闲食品行业";
		case ids[17]:
			return "保健食品行业";
		case ids[18]:
			return "食品添加剂行业";
		case ids[19]:
			return "清真食品行业";
		default : 
			return "全行业";
	}
}

// 截取字符串
function interceptStr(title) {
	var l = 18;
	if (title.length > l) {
		title = title.substr(0, l) + "...";
	}
	return title;
}

// 获得真实的发布周期
function getRealEndRq(startRq, endRq, mCircle) {
	var interD = (Date.parse(endRq.replace(/-/g,'/')) - Date.parse(startRq.replace(/-/g,'/')) + (1000 * 60 * 60 * 24));
	var dateStr = new Date(Date.parse(endRq.replace(/-/g,'/'))
			- Math.floor(interD % (mCircle * (1000 * 60 * 60 * 24))));
	return dateStr.getFullYear() + "-" + (dateStr.getMonth() + 1) + "-"
			+ dateStr.getDate();
}

// 根据指定日期获得往前推算的监测周期
function getCircle(rq, mCircle) {
	var date = new Date(Date.parse(rq) - (mCircle - 1) * 1000 * 60 * 60 * 24);
	var dateStart = date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
			+ date.getDate();

	if (mCircle == 1) {
		return rq;
	}
	return dateStart + "到" + rq;
}

/**
 * Decimal adjustment of a number.
 * 
 * @param {String}
 *            type The type of adjustment.
 * @param {Number}
 *            value The number.
 * @param {Integer}
 *            exp The exponent (the 10 logarithm of the adjustment base).
 * @returns {Number} The adjusted value.
 */
function decimalAdjust(type, value, exp) {
	// If the exp is undefined or zero...
	if (typeof exp === 'undefined' || +exp === 0) {
		return Math[type](value);
	}
	value = +value;
	exp = +exp;
	// If the value is not a number or the exp is not an integer...
	if (isNaN(value) || !(typeof exp === 'number' && exp % 1 === 0)) {
		return NaN;
	}
	// Shift
	value = value.toString().split('e');
	value = Math[type](+(value[0] + 'e' + (value[1] ? (+value[1] - exp) : -exp)));
	// Shift back
	value = value.toString().split('e');
	return +(value[0] + 'e' + (value[1] ? (+value[1] + exp) : exp));
}

// Decimal round
if (!Math.round10) {
	Math.round10 = function(value, exp) {
		return decimalAdjust('round', value, exp);
	};
}
// Decimal floor
if (!Math.floor10) {
	Math.floor10 = function(value, exp) {
		return decimalAdjust('floor', value, exp);
	};
}
// Decimal ceil
if (!Math.ceil10) {
	Math.ceil10 = function(value, exp) {
		return decimalAdjust('ceil', value, exp);
	};
}

//微博是否认证对应的文本
function convertToStr(user_verified) {
	if (user_verified == 1) {
		return "认证";
	} else if (user_verified == 2) {
		return "未认证";
	} else {
		return "";
	}
}
//用户是否加V
function is_v(user_verified) {
	if (user_verified == 1) {
		return "<img id='ver' src='images/v.jpg' alt='加V认证'>";
	} else {
		return "";
	}
}

function validateTime() {
	var rq1 = new Date($("input#rq1").val());
	var rq2 = new Date($("input#rq2").val());
	var mCircle = $("select[name='monitorCircle']").val();
	var oneDay = 1000 * 60 * 60 * 24;
	var test = (rq2.getTime() - rq1.getTime()) / oneDay;

	if (rq1.getTime() > Date.now() || rq2.getTime() > Date.now()) {
		alert("所选日期超过当前时间！");
		return false;
	}

	if (test < mCircle) {
		alert("发布周期不能小于监测周期！");
		return false;
	}

	latelyClick.password = latelyClick.password.split(" ")[0];
	
	if (latelyClick.name == 1 && latelyClick.password == "cpoi") {
		cpoi0();
	} else if (latelyClick.name == 2 && latelyClick.password == "cpoiData") {
		$(".cpoiData").click();
	} else if (latelyClick.password == "userid") {
		cpoiFor20(latelyClick.name);
	} else {
		$("." + latelyClick.password).click();
	}
	return false;
}

function formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr) {
	var content = cp_hint + cp_name +':\n\n';
	for(var i=0; i<cp_categoriesArr.length; i++) {
		content += cp_categoriesArr[i];
		var space = '';
		for(var j=0; j<30 - cp_categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
			space += ' ';
		}
		content += (space + cp_dataArr[i] + '\n');
	}
    return content;
}

function site_clsEye(id,typeName,pie_type,pieValue) {
	var containerColumnChart = echarts.init(document.getElementById('containerColumn'));
	// 过渡---------------------
	containerColumnChart.showLoading({
	    text: '正在努力的读取数据中...',   //loading话术
	    effect : 'ring'
	});
			
	    	var option = {
				    title : {
				        text: getHint(id) + pie_type + '对比图',
				        subtext: '发布周期' + startRq + '到' + endRq,
				        x:'center'
				    },
				   	tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					 },
				    legend: {
				        x : 'center',
				        y : 'bottom',
				        data:[typeName]
				    },
				    toolbox: {
				        show : true,
				        orient:'vertical',
				        feature : {
				            dataView : {show: false, readOnly: true},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    series : [
					        {
					            name:typeName,
					            type:'pie',
					            startAngle : 45,
					            minAngle:'10',
					            radius : '55%',
					            selectedOffset:10,
					            selectedMode:'single',
					            center: ['50%', '50%'],
					            data:pieValue,
					            itemStyle: {
								    normal: {
								       label : {
					                        position : 'outer',
					                        formatter : function (a,b,c,d) {return b + ': ' + (d - 0).toFixed(2) + '%'}
					                    },
					                    labelLine : {
					                        show : true
					                    }
								    }
								}
					        }
					    	 ]
				};
				
			containerColumnChart.setOption(option,true);
			containerColumnChart.hideLoading();
}

function cpoi0() {
	var $cpoiTable,
		$show_1,
		$releaseRq;
	clickBefore($cpoiTable,$show_1,$releaseRq);
	$(".show_1:gt(0)").hide();
	$(".show_1:eq(7)").show();
	$(".show_1:eq(10)").show();
	
	latelyClick.name = 1;
	latelyClick.password = "cpoi";

	//基于准备好的dom，初始化echarts图表
	var containerChart = echarts.init(document.getElementById('container'));
	// 过渡---------------------
	containerChart.showLoading({
	    text: '正在努力的读取数据中...',    //loading话术
	    effect:'whirling'
	});
	
	$.getJSON('firstLevel', {dataType:'cpoi', userId:0, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {

				var htmlHint = getHint(0) + ">>" + "舆情指数";
				$("#hint_1").empty();

				var dataArr = $.parseJSON(responseText.data),
					categoriesArr = $.parseJSON(responseText.categoriesArr);
				for(var i=0; i<dataArr.length; i++) {
					dataArr[i] = Math.round10(dataArr[i],-2);
				}
					
				var option = {
					title : {
						text : getHint(0) + '舆情指数变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					dataZoom : {
						show : true
					},
					legend : {
						data : [],
						x:'center',
						y : 'bottom'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
						        	var content = '全行业舆情指数:\n\n';
									for(var i=0; i<categoriesArr.length; i++) {
										content += categoriesArr[i];
										var space = '';
										for(var j=0; j<30 - categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
											space += ' ';
										}
										content += (space + dataArr[i] + '\n');
									}
						            return content;
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name:'舆情指数',
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
								name : '舆情指数',
								type : 'line',
								data : dataArr,
								markPoint : {
									data : [ {
												type : 'min',
												name : '最小值'
											}]
								}
							}]
				};

				// 为echarts对象加载数据
				containerChart.setOption(option);
				containerChart.hideLoading();
				
			});
			
			//基于准备好的dom，初始化echarts图表
			var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
			// 过渡---------------------
			containerColumnAllChart.showLoading({
			    text: '正在努力的读取数据中...',    //loading话术
			    effect:'whirling'
			});
			
			$.getJSON('firstLevel', {dataType:'cpoi20', rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
				$("#hint_2").empty();
				
				var categoriesArr = $.parseJSON(responseText.categories),
					dataArr = $.parseJSON(responseText.data);
				for(var i=0; i<dataArr.length; i++) {
					dataArr[i] = Math.round10(dataArr[i],-2);
				}
						
				var option = {
					title : {
						text : '20个细分行业的从高到低排列的舆情指数',
						subtext : '发布周期' + startRq + "到" + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [],
						x:'left'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
						        title : '数据视图',
						        readOnly: true,
						        optionToContent : function() {
						        	var content = '20个细分行业的从高到低排列的舆情指数:\n\n';
									for(var i=0; i<categoriesArr.length; i++) {
										content += categoriesArr[i];
										var space = '';
										for(var j=0; j<30 - categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
											space += ' ';
										}
										content += (space + dataArr[i] + '\n');
									}
						            return content;
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
						type : 'category',
						axisLabel:{
									interval : 0,
									rotate : 45
								},
						data : categoriesArr
					}],
					yAxis : [{
								type : 'value',
								name:'舆情指数',
								max : Math.max.apply(Math, dataArr)
							}],
					series : [{
						name : '舆情指数',
						type : 'bar',
						data : dataArr
					}]
				};
				
				// 为echarts对象加载数据
				containerColumnAllChart.setOption(option);
				containerColumnAllChart.hideLoading();

			});
			
			//基于准备好的dom，初始化echarts图表
			var containerColumnChart = echarts.init(document.getElementById('containerColumn'));
			containerColumnChart.showLoading({
			    text: '正在努力的读取数据中...',  //loading话术
			    effect:'bar'
			});
			
			$.getJSON('firstLevel', {dataType:'cpoi20Rq', userId:0, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
				$("#hint_3").empty();
				
				var dataArr = $.parseJSON(responseText.rqAndData);
				var rqArr = [],seriesArr=[];
				
				var originObj = {
					            title : {
					                text:'',
					                subtext:'发布周期' + startRq + '到' + endRq,
					                x:'center'
					            },
					            tooltip : {'trigger':'axis'},
					            legend : {
					                x:'right',
					                data:[],
					                selected:{
					                    '舆情指数':true
					                }
					            },
					            toolbox : {
					                show:true, 
					                orient : 'vertical',
					                x: 'right', 
					                y: 'center',
					                feature:{
					                    magicType:{'show':true,'type':['line','bar']},
					                    restore:{'show':true},
					                    saveAsImage:{'show':true}
					                }
					            },
					            calculable : true,
					            grid : {y:80,y2:100},
					            xAxis : [{
					                type:'category',
					                axisLabel:{interval:2,rotate:0},
					                data:$.parseJSON(responseText.categories)
					            }],
					            yAxis : [
					                {
					                    type:'value',
					                    name:'舆情指数',
					                    max : 10
					                }
					            ],
					            series : [
					                {
					                    name:'舆情指数',
					                    type:'bar',
					                    data: []
					                }
					                
					            ]
					        }
				
				var maxVal = 10;//纵轴的最大值默认设为10
				for(var i=0; i<dataArr.length; i++) {
					var obj = $.parseJSON(dataArr[i])
					rqArr.push(obj.rq);
					
					var tempArr = $.parseJSON(obj.data);
					for(var j=0; j<tempArr.length; j++) {
						tempArr[j] = Math.round10(tempArr[j],-2);
						
						if(tempArr[j] > maxVal) {
							maxVal  = tempArr[j];//取得舆情指数的最大值
						}
					}
					
					var seriesObj = {};
					seriesObj.title = {'text': getCircle(obj.rq,mCircle) + '各个细分行业舆情指数'}
					seriesObj.series = [
					                {'data': tempArr}
					            ]
					seriesArr.push(seriesObj)
				}
				
				var firstObj = seriesArr.shift();//将数组的第一个元素取出来，做些处理，再放回去
				originObj.title.text = firstObj.title.text;
				originObj.series[0].data = firstObj.series[0].data;
				originObj.yAxis[0].max = maxVal;
				seriesArr.unshift(originObj);
				
				var option = {
					    timeline:{
					        data:rqArr,
					        label : {
					            formatter : function(s) {
					                return s.slice(0, 10);
					            }
					        },
					        autoPlay : true,
					        playInterval : 1000
					    },
					    options:seriesArr
					};
					                    
				
				// 为echarts对象加载数据
				containerColumnChart.setOption(option);
				containerColumnChart.hideLoading();
			});
}

function cpoiFor20(id) {
	var $cpoiTable,
		$show_1,
		$releaseRq;
	clickBefore($cpoiTable,$show_1,$releaseRq);
	latelyClick.name = id;
	latelyClick.password = "userid";

	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('container'));
	$.getJSON('firstLevel', {dataType:'cpoi', userId:id, rq1:startRq, rq2:endRq, monitorCircle:mCircle},function(responseText) {

				var htmlHint = getHint(id) + ">>" + "舆情指数";
				$("#hint_1").empty();

				var arr = $.parseJSON(responseText.data);
				var dataArr = [];
				var categoriesArr = [];
				for (var i = 0; i < arr.length; i++) {
					var tempArr = $.parseJSON(arr[i])
					categoriesArr[i] = tempArr[0];
					dataArr[i] = Math.round10(tempArr[1], -2);
				}

				var option = {
					title : {
						text : getHint(id) + '舆情指数变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					dataZoom : {
						show : true
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [],
						x:'left'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
						        title : '数据视图',
						        readOnly: true,
						        optionToContent : function() {
						        	var content = getHint(id) + '舆情指数:\n\n';
									for(var i=0; i<categoriesArr.length; i++) {
										content += categoriesArr[i];
										var space = '';
										for(var j=0; j<30 - categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
											space += ' ';
										}
										content += (space + dataArr[i] + '\n');
									}
						            return content;
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name:'舆情指数',
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
								name : '舆情指数',
								type : 'line',
								data : dataArr,
								markPoint : {
									data : [{
												type : 'min',
												name : '最小值'
											}]
								}
							}]
				};

				// 为echarts对象加载数据
				myChart.setOption(option);
			});

	$(".show_1:gt(0)").hide();
	
}
// ////////////////////////////////////////////////////////////
var cpoi = {
	startRq : '',
	endRq : '',
	rq : '',
	userId : '',
	domain_1 : '',
	site_name : '',
	titleCFilterType : '',
	titleCFilter : true,
	setTitleCFilter : function(titleCFilter) {
		this.titleCFilter = titleCFilter;
	},
	containerAppFilterType : '',
	containerAppFilter : true,
	setContainerAppFilter : function(containerAppFilter) {
		this.containerAppFilter = containerAppFilter;
	},
	getTitleC : function() {
		if(this.titleCFilterType === 'viewCount') {
			this.getViewCountTitle(this.userId, this.rq);
		}else if(this.titleCFilterType === 'reprintCount') {
			this.getReprintCountTitle(this.userId, this.rq);
		}else if(this.titleCFilterType === 'replyCount') {
			this.getRelpyCountTitle(this.userId, this.rq);
		}else if(this.titleCFilterType === 'domain_1Title') {
			this.getDomain_1Title(this.domain_1, this.site_name, this.rq, this.userId);
		}else if(this.titleCFilterType === 'domain_1TitleCountArea') {
			this.getDomain_1TitleArea(this.domain_1, this.site_name, this.startRq, this.endRq, this.userId);
		}
	},
	getContainerApp : function() {
		if(this.containerAppFilterType === 'viewCountArea') {
			this.getViewCountAreaTitle(this.userId, this.startRq, this.endRq);
		}else if(this.containerAppFilterType === 'reprintCountArea') {
			this.getReprintCountAreaTitle(this.userId, this.startRq, this.endRq);
		}else if(this.containerAppFilterType === 'replyCountArea') {
			this.getReplyCountAreaTitle(this.userId, this.startRq, this.endRq);
		}else if(this.containerAppFilterType === 'withoutDomain_1Title') {
			this.getWithoutDomain_1Title(this.userId, this.rq);
		}else if(this.containerAppFilterType === 'withoutDomain_1TitleArea') {
			this.getWithoutDomain_1TitleArea(this.userId, this.startRq, this.endRq);
		}
	},
	getViewCountTitle : function(id, rq) {
			this.rq = rq;//缓存日期和行业id
			this.userId = id;
			this.titleCFilterType = 'viewCount'
			
    		var totalCount = 0;
	        var jqxhr = $.ajax( {
	        	type : 'get',
	        	async : false,
	        	url : 'tableInfo',
	        	data: {
	        		dataType : 'totalCount',
	        		userId : id,
	        		rq : rq,
	        		isFilter : this.titleCFilter
    	        	},
    	        	dataType: 'json'
    	        } )
    	    	  .done(function(responseText) {
    	    	  	totalCount = responseText.totalCount;
    	    	  })
    	    	  
	        $("#paginationTitleC").paging(totalCount, {
	    		format: "[ < nnncnnn > ]",
	    		perpage: 10, 
	    	    lapping: 0, 
	    	    page: 1, 
	    		onSelect: function (page) {
	
	    			var jqxhr = $.ajax( {
	    	        	type : 'get',
	    	        	url : 'table',
	    	        	data: {
	    	        		dataType : 'viewCount',
	    	        		userId : id,
	    	        		rq : rq,
	    	        		monitorCircle:mCircle,
	    	        		pageNo : page,
	    	        		pageSize : 10,
	    	        		isFilter : cpoi.titleCFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
	    	    		  var tbody = "<table class='tftable'><tr><th colspan=\"5\">监测周期\("+getCircle(rq,mCircle)+"\)内按浏览量降序排列的文章列表</th></tr>";
			        	    tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>浏览量</th></tr>";
				        	$.each(responseText,function(n,value) {  
				        		value = $.parseJSON(value);
				           		 var trs = "";  
				                  trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td>" + value.view +"</td></tr>";  
				                  tbody += trs; 
				                  
				                });  
				        	
				        	$("#titleC").html(tbody + "</table>");
	    	    	  })
	    	    	  .fail(function() {
	    	    	    alert( "error" );
	    	    	  })
	    	    	  .always(function() {
	    	    	  });
	    		},
	    		onFormat: function(type) {

					switch (type) {
						case 'block':
							if (!this.active) 
								return '<span class="disabled">' + this.value + '</span>';
							else if (this.value != this.page)
								return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
							return '<span class="current">' + this.value + '</span>';
						case 'next':
							if (this.active)
								return '<a href="#' + this.value + '" class="next">></a>';
							return '<span class="disabled">></span>';
						case 'prev':
							if (this.active)
								return '<a href="#' + this.value + '" class="prev"><</a>';
							return '<span class="disabled"><</span>';
						case 'first':
							if (this.active)
								return '<a href="#' + this.value + '" class="first">首页</a>';
							return '<span class="disabled">首页</span>';
						case 'last':
							if (this.active)
								return '<a href="#' + this.value + '" class="last">尾页</a>';
							return '<span class="disabled">尾页</span>';
					}
	    		}
			});
    	},
    getViewCountAreaTitle : function(id, rq1,rq2) {
    	this.userId = id;
    	this.startRq = rq1;
    	this.endRq = rq2;
    	this.titleCFilterType = 'viewCountArea';
    	
	    	var totalCount = 0;
	    		
	        var jqxhr = $.ajax( {
	        	type : 'get',
	        	async : false,
	        	url : 'tableInfo',
	        	data: {
	        		dataType : 'totalCountArea',
	        		userId : id,
	        		rq1:rq1, 
	    	        rq2:rq2,
	    	        isFilter : this.containerAppFilter
    	        	},
    	        	dataType: 'json'
    	        } )
    	    	  .done(function(responseText) {
    	    	  	totalCount = responseText.totalCount;
    	    	  })
    	    	  
	        $("#paginationContainerApp").paging(totalCount, {
	    		format: "[ < nnncnnn > ]",
	    		perpage: 10, 
	    	    lapping: 0, 
	    	    page: 1, 
	    		onSelect: function (page) {
	
	    			var jqxhr = $.ajax( {
	    	        	type : 'get',
	    	        	url : 'table',
	    	        	data: {
	    	        		dataType : 'viewCountArea',
	    	        		userId : id,
	    	        		rq1:rq1, 
	    	        		rq2:rq2,
	    	        		pageNo : page,
	    	        		pageSize : 10,
	    	        		isFilter : cpoi.containerAppFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
			        	    var tbody = "<table class='tftable'><tr><th colspan=\"5\">发布周期\("+startRq+"到"+endRq+"\)内按浏览量降序排列的文章列表</th></tr>";
				        	    tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>浏览量</th></tr>";
				        	$.each(responseText,function(n,value) {  
				        		value = $.parseJSON(value);
				           		 var trs = "";  
				                  trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td>" + value.view +"</td></tr>";  
				                  tbody += trs; 
				                  
				                });  
				        	
				        	$("#containerApp").html(tbody + "</table>");
	    	    	  })
	    	    	  .fail(function() {
	    	    	    alert( "error" );
	    	    	  })
	    	    	  .always(function() {
	    	    	  });
	    		},
	    		onFormat: function (type) {
	    	       switch (type) {
						case 'block':
							if (!this.active) 
								return '<span class="disabled">' + this.value + '</span>';
							else if (this.value != this.page)
								return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
							return '<span class="current">' + this.value + '</span>';
						case 'next':
							if (this.active)
								return '<a href="#' + this.value + '" class="next">></a>';
							return '<span class="disabled">></span>';
						case 'prev':
							if (this.active)
								return '<a href="#' + this.value + '" class="prev"><</a>';
							return '<span class="disabled"><</span>';
						case 'first':
							if (this.active)
								return '<a href="#' + this.value + '" class="first">首页</a>';
							return '<span class="disabled">首页</span>';
						case 'last':
							if (this.active)
								return '<a href="#' + this.value + '" class="last">尾页</a>';
							return '<span class="disabled">尾页</span>';
					}
	    	    }
	    	});
	    },	
	getReprintCountTitle : function(id, rq) {
			this.rq = rq;//缓存日期和行业id
			this.userId = id;
			this.titleCFilterType = 'reprintCount';
		
    		var totalCount = 0;
	        var jqxhr = $.ajax( {
	        	type : 'get',
	        	async : false,
	        	url : 'tableInfo',
	        	data: {
	        		dataType : 'totalCount',
	        		userId : id,
	        		rq : rq,
	        		isFilter : this.titleCFilter
    	        	},
    	        	dataType: 'json'
    	        } )
    	    	  .done(function(responseText) {
    	    	  	totalCount = responseText.totalCount;
    	    	  })
    	    	  
	        $("#paginationTitleC").paging(totalCount, {
	    		format: "[ < nnncnnn > ]",
	    		perpage: 10, 
	    	    lapping: 0, 
	    	    page: 1, 
	    		onSelect: function (page) {
	
	    			var jqxhr = $.ajax( {
	    	        	type : 'get',
	    	        	url : 'table',
	    	        	data: {
	    	        		dataType : 'reprintCount',
	    	        		userId : id,
	    	        		rq : rq,
	    	        		monitorCircle:mCircle,
	    	        		pageNo : page,
	    	        		pageSize : 10,
	    	        		isFilter : cpoi.titleCFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
	    	    		  var tbody = "<table class='tftable'><tr><th colspan=\"5\">监测周期\("+getCircle(rq,mCircle)+"\)内转载量降序排列的文章列表</th></tr>";
			        	    tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>转载量</th></tr>";
				        	$.each(responseText,function(n,value) {  
				        		value = $.parseJSON(value);
				           		 var trs = "";  
				                  trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td>" + value.similar_count +"</td></tr>";  
				                  tbody += trs; 
				                  
				                });  
			        	
			        	$("#titleC").html(tbody + "</table>");
	    	    	  })
	    	    	  .fail(function() {
	    	    	    alert( "error" );
	    	    	  })
	    	    	  .always(function() {
	    	    	  });
	    		},
	    		onFormat: function(type) {

					switch (type) {
						case 'block':
							if (!this.active) 
								return '<span class="disabled">' + this.value + '</span>';
							else if (this.value != this.page)
								return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
							return '<span class="current">' + this.value + '</span>';
						case 'next':
							if (this.active)
								return '<a href="#' + this.value + '" class="next">></a>';
							return '<span class="disabled">></span>';
						case 'prev':
							if (this.active)
								return '<a href="#' + this.value + '" class="prev"><</a>';
							return '<span class="disabled"><</span>';
						case 'first':
							if (this.active)
								return '<a href="#' + this.value + '" class="first">首页</a>';
							return '<span class="disabled">首页</span>';
						case 'last':
							if (this.active)
								return '<a href="#' + this.value + '" class="last">尾页</a>';
							return '<span class="disabled">尾页</span>';
					}
	    		}
			});
    	},
    getReprintCountAreaTitle : function(id, rq1, rq2) {
    	this.userId = id;
    	this.startRq = rq1;
    	this.endRq = rq2;
    	this.containerAppFilterType = 'reprintCountArea';
    	
	    	var totalCount = 0;
	    		
	        var jqxhr = $.ajax( {
	        	type : 'get',
	        	async : false,
	        	url : 'tableInfo',
	        	data: {
	        		dataType : 'totalCountArea',
	        		userId : id,
	        		rq1:rq1, 
	    	        rq2:rq2,
	    	        isFilter : this.containerAppFilter
    	        	},
    	        	dataType: 'json'
    	        } )
    	    	  .done(function(responseText) {
    	    	  	totalCount = responseText.totalCount;
    	    	  })
    	    	  
	        $("#paginationContainerApp").paging(totalCount, {
	    		format: "[ < nnncnnn > ]",
	    		perpage: 10, 
	    	    lapping: 0, 
	    	    page: 1, 
	    		onSelect: function (page) {
	
	    			var jqxhr = $.ajax( {
	    	        	type : 'get',
	    	        	url : 'table',
	    	        	data: {
	    	        		dataType : 'reprintCountArea',
	    	        		userId : id,
	    	        		rq1:rq1, 
	    	        		rq2:rq2,
	    	        		pageNo : page,
	    	        		pageSize : 10,
	    	        		isFilter : cpoi.containerAppFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
			        	    var tbody = "<table class='tftable'><tr><th colspan=\"5\">发布周期\("+startRq+"到"+endRq+"\)内转载量降序排列的文章列表</th></tr>";
					         	tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>转载量</th></tr>";
					         	$.each(responseText,function(n,value) {  
					         		value = $.parseJSON(value);
					            		 var trs = "";  
					                   trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td>" + value.similar_count +"</td></tr>";  
					                   tbody += trs; 
					                   
					                 });
					    	
					         	
					        $("#containerApp").html(tbody + "</table>");
	    	    	  })
	    	    	  .fail(function() {
	    	    	    alert( "error" );
	    	    	  })
	    	    	  .always(function() {
	    	    	  });
	    		},
	    		onFormat: function (type) {
	    	       switch (type) {
						case 'block':
							if (!this.active) 
								return '<span class="disabled">' + this.value + '</span>';
							else if (this.value != this.page)
								return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
							return '<span class="current">' + this.value + '</span>';
						case 'next':
							if (this.active)
								return '<a href="#' + this.value + '" class="next">></a>';
							return '<span class="disabled">></span>';
						case 'prev':
							if (this.active)
								return '<a href="#' + this.value + '" class="prev"><</a>';
							return '<span class="disabled"><</span>';
						case 'first':
							if (this.active)
								return '<a href="#' + this.value + '" class="first">首页</a>';
							return '<span class="disabled">首页</span>';
						case 'last':
							if (this.active)
								return '<a href="#' + this.value + '" class="last">尾页</a>';
							return '<span class="disabled">尾页</span>';
					}
	    	    }
	    	});
	    },	
	getRelpyCountTitle : function(id, rq) {
			this.rq = rq;//缓存日期和行业id
			this.userId = id;
			this.titleCFilterType = 'replyCount';
		
    		var totalCount = 0;
	        var jqxhr = $.ajax( {
	        	type : 'get',
	        	async : false,
	        	url : 'tableInfo',
	        	data: {
	        		dataType : 'totalCount',
	        		userId : id,
	        		rq : rq,
	        		isFilter : this.titleCFilter
    	        	},
    	        	dataType: 'json'
    	        } )
    	    	  .done(function(responseText) {
    	    	  	totalCount = responseText.totalCount;
    	    	  })
    	    	  
	        $("#paginationTitleC").paging(totalCount, {
	    		format: "[ < nnncnnn > ]",
	    		perpage: 10, 
	    	    lapping: 0, 
	    	    page: 1, 
	    		onSelect: function (page) {
	
	    			var jqxhr = $.ajax( {
	    	        	type : 'get',
	    	        	url : 'table',
	    	        	data: {
	    	        		dataType : 'replyCount',
	    	        		userId : id,
	    	        		rq : rq,
	    	        		monitorCircle:mCircle,
	    	        		pageNo : page,
	    	        		pageSize : 10,
	    	        		isFilter : cpoi.titleCFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
	    	    		  var tbody = "<table class='tftable'><tr><th colspan=\"5\">监测周期\("+getCircle(rq,mCircle)+"\)内评论量降序排列的文章列表</th></tr>";
				        	    tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>评论量</th></tr>";
				        	$.each(responseText,function(n,value) {  
				        		value = $.parseJSON(value);
				           		 var trs = "";  
				                  trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td>" + value.reply +"</td></tr>";  
				                  tbody += trs; 
				                  
				                });  
				        	
				        	$("#titleC").html(tbody + "</table>");
	    	    	  })
	    	    	  .fail(function() {
	    	    	    alert( "error" );
	    	    	  })
	    	    	  .always(function() {
	    	    	  });
	    		},
	    		onFormat: function(type) {

					switch (type) {
						case 'block':
							if (!this.active) 
								return '<span class="disabled">' + this.value + '</span>';
							else if (this.value != this.page)
								return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
							return '<span class="current">' + this.value + '</span>';
						case 'next':
							if (this.active)
								return '<a href="#' + this.value + '" class="next">></a>';
							return '<span class="disabled">></span>';
						case 'prev':
							if (this.active)
								return '<a href="#' + this.value + '" class="prev"><</a>';
							return '<span class="disabled"><</span>';
						case 'first':
							if (this.active)
								return '<a href="#' + this.value + '" class="first">首页</a>';
							return '<span class="disabled">首页</span>';
						case 'last':
							if (this.active)
								return '<a href="#' + this.value + '" class="last">尾页</a>';
							return '<span class="disabled">尾页</span>';
					}
	    		}
			});
    	},
    getReplyCountAreaTitle : function(id, rq1, rq2) {
    	this.userId = id;
    	this.startRq = rq1;
    	this.endRq = rq2;
    	this.containerAppFilterType = 'replyCountArea';
    	
	    	var totalCount = 0;
	    		
	        var jqxhr = $.ajax( {
	        	type : 'get',
	        	async : false,
	        	url : 'tableInfo',
	        	data: {
	        		dataType : 'totalCountArea',
	        		userId : id,
	        		rq1:rq1, 
	    	        rq2:rq2,
	    	        isFilter : this.containerAppFilter
    	        	},
    	        	dataType: 'json'
    	        } )
    	    	  .done(function(responseText) {
    	    	  	totalCount = responseText.totalCount;
    	    	  })
    	    	  
	        $("#paginationContainerApp").paging(totalCount, {
	    		format: "[ < nnncnnn > ]",
	    		perpage: 10, 
	    	    lapping: 0, 
	    	    page: 1, 
	    		onSelect: function (page) {
	
	    			var jqxhr = $.ajax( {
	    	        	type : 'get',
	    	        	url : 'table',
	    	        	data: {
	    	        		dataType : 'replyCountArea',
	    	        		userId : id,
	    	        		rq1:rq1, 
	    	        		rq2:rq2,
	    	        		pageNo : page,
	    	        		pageSize : 10,
	    	        		isFilter : cpoi.containerAppFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
			        	    var tbody = "<table class='tftable'><tr><th colspan=\"5\">发布周期\("+startRq+"到"+endRq+"\)内评论量降序排列的文章列表</th></tr>";
				        	tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>评论量</th></tr>";
				        	$.each(responseText,function(n,value) {  
				        		value = $.parseJSON(value);
				           		 var trs = "";  
				                  trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" +interceptStr(value.title)+"</a></td> <td>" + value.reply +"</td></tr>";  
				                  tbody += trs; 
				                  
				                });  
				        	
				        	$("#containerApp").html(tbody + "</table>");
	    	    	  })
	    	    	  .fail(function() {
	    	    	    alert( "error" );
	    	    	  })
	    	    	  .always(function() {
	    	    	  });
	    		},
	    		onFormat: function (type) {
	    	       switch (type) {
						case 'block':
							if (!this.active) 
								return '<span class="disabled">' + this.value + '</span>';
							else if (this.value != this.page)
								return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
							return '<span class="current">' + this.value + '</span>';
						case 'next':
							if (this.active)
								return '<a href="#' + this.value + '" class="next">></a>';
							return '<span class="disabled">></span>';
						case 'prev':
							if (this.active)
								return '<a href="#' + this.value + '" class="prev"><</a>';
							return '<span class="disabled"><</span>';
						case 'first':
							if (this.active)
								return '<a href="#' + this.value + '" class="first">首页</a>';
							return '<span class="disabled">首页</span>';
						case 'last':
							if (this.active)
								return '<a href="#' + this.value + '" class="last">尾页</a>';
							return '<span class="disabled">尾页</span>';
					}
	    	    }
	    	});
	    },
		getDomain_1Title : function(domain_1,site_name,rq,id) {
			this.rq = rq;//缓存日期和行业id
			this.userId = id;
			this.titleCFilterType = 'domain_1Title'
			this.domain_1 = domain_1;
			this.site_name = site_name;
			
    		var totalCount = 0;
	        var jqxhr = $.ajax( {
	        	type : 'get',
	        	async : false,
	        	url : 'tableInfo',
	        	data: {
	        		dataType : 'domain_1TitleCount',
	        		userId : id,
	        		domain_1 : domain_1,
	        		rq : rq,
	        		isFilter : this.titleCFilter
    	        	},
    	        	dataType: 'json'
    	        } )
    	    	  .done(function(responseText) {
    	    	  	totalCount = responseText.totalCount;
    	    	  })
    	    	  
	        $("#paginationTitleC").paging(totalCount, {
	    		format: "[ < nnncnnn > ]",
	    		perpage: 10, 
	    	    lapping: 0, 
	    	    page: 1, 
	    		onSelect: function (page) {
	
	    			var jqxhr = $.ajax( {
	    	        	type : 'get',
	    	        	url : 'table',
	    	        	data: {
	    	        		dataType : 'title',
	    	        		domain_1:domain_1,
	    	        		userId : id,
	    	        		rq : rq,
	    	        		monitorCircle:mCircle,
	    	        		pageNo : page,
	    	        		pageSize : 10,
	    	        		isFilter : cpoi.titleCFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
	    	    		  var tbody = "<table class='tftable'><tr><th colspan='5'> <em>" +site_name +"</em>文章列表</th></tr><tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>相关性</th></tr>";
							if(domain_1 === 'qq.com') {//针对腾讯特殊处理
								$.each(responseText,function(n,value) {  
					    		value = $.parseJSON(value);
					       		 var trs = "";  
					              trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +(value.site_cls===7 ? '微信' : value.site_name) +"</td><td>" +value.pubdate+"</td> <td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>"+interceptStr(value.title) +"</a></td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";  
					              tbody += trs;         
					            });  
							}else {
								$.each(responseText,function(n,value) {  
					    		value = $.parseJSON(value);
					       		 var trs = "";  
					              trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td> <td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>"+interceptStr(value.title) +"</a></td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";  
					              tbody += trs;         
					            });  
							}
					    	
					    	$("#titleC").html(tbody + "</table>");
	    	    	  })
	    	    	  .fail(function() {
	    	    	    alert( "error" );
	    	    	  })
	    	    	  .always(function() {
	    	    	  });
	    		},
	    		onFormat: function(type) {

					switch (type) {
						case 'block':
							if (!this.active) 
								return '<span class="disabled">' + this.value + '</span>';
							else if (this.value != this.page)
								return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
							return '<span class="current">' + this.value + '</span>';
						case 'next':
							if (this.active)
								return '<a href="#' + this.value + '" class="next">></a>';
							return '<span class="disabled">></span>';
						case 'prev':
							if (this.active)
								return '<a href="#' + this.value + '" class="prev"><</a>';
							return '<span class="disabled"><</span>';
						case 'first':
							if (this.active)
								return '<a href="#' + this.value + '" class="first">首页</a>';
							return '<span class="disabled">首页</span>';
						case 'last':
							if (this.active)
								return '<a href="#' + this.value + '" class="last">尾页</a>';
							return '<span class="disabled">尾页</span>';
					}
	    		}
			});
    	},
    	getDomain_1TitleArea : function(domain_1,site_name,rq1, rq2, id) {
	    	this.userId = id;
	    	this.startRq = rq1;
	    	this.endRq = rq2;
	    	this.domain_1 = domain_1;
	    	this.site_name = site_name;
	    	this.titleCFilterType = 'domain_1TitleCountArea';
	    	
		    	var totalCount = 0;
		    		
		        var jqxhr = $.ajax( {
		        	type : 'get',
		        	async : false,
		        	url : 'tableInfo',
		        	data: {
		        		dataType : 'domain_1TitleCountArea',
		        		userId : id,
		        		domain_1 : domain_1,
		        		rq1:rq1, 
		    	        rq2:rq2,
		    	        isFilter : this.titleCFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
	    	    	  	totalCount = responseText.totalCount;
	    	    	  })
	    	    	  
		        $("#paginationTitleC").paging(totalCount, {
		    		format: "[ < nnncnnn > ]",
		    		perpage: 10, 
		    	    lapping: 0, 
		    	    page: 1, 
		    		onSelect: function (page) {
		
		    			var jqxhr = $.ajax( {
		    	        	type : 'get',
		    	        	url : 'table',
		    	        	data: {
		    	        		dataType : 'titleArea',
		    	        		userId : id,
		    	        		domain_1 : domain_1,
		    	        		rq1:rq1, 
		    	        		rq2:rq2,
		    	        		pageNo : page,
		    	        		pageSize : 10,
		    	        		isFilter : cpoi.titleCFilter
		    	        	},
		    	        	dataType: 'json'
		    	        } )
		    	    	  .done(function(responseText) {
				        	    var tbody = "<table class='tftable'><tr><th colspan='5'> <em>" +site_name +"</em>文章列表</th></tr><tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>相关性</th></tr>";
						    	$.each(responseText,function(n,value) {  
						    		value = $.parseJSON(value);
						       		 var trs = "";  
						              trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +(value.site_cls===7 ? '微信' : value.site_name)+"</td><td>" +value.pubdate+"</td> <td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>"+interceptStr(value.title) +"</td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";   
						              tbody += trs;         
						            });  
						    	
						    	$("#titleC").html(tbody + "</table>");
		    	    	  })
		    	    	  .fail(function() {
		    	    	    alert( "error" );
		    	    	  })
		    	    	  .always(function() {
		    	    	  });
		    		},
		    		onFormat: function (type) {
		    	       switch (type) {
							case 'block':
								if (!this.active) 
									return '<span class="disabled">' + this.value + '</span>';
								else if (this.value != this.page)
									return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
								return '<span class="current">' + this.value + '</span>';
							case 'next':
								if (this.active)
									return '<a href="#' + this.value + '" class="next">></a>';
								return '<span class="disabled">></span>';
							case 'prev':
								if (this.active)
									return '<a href="#' + this.value + '" class="prev"><</a>';
								return '<span class="disabled"><</span>';
							case 'first':
								if (this.active)
									return '<a href="#' + this.value + '" class="first">首页</a>';
								return '<span class="disabled">首页</span>';
							case 'last':
								if (this.active)
									return '<a href="#' + this.value + '" class="last">尾页</a>';
								return '<span class="disabled">尾页</span>';
						}
		    	    }
		    	});
		    },
			getWithoutDomain_1Title : function(id, rq) {
				this.rq = rq;//缓存日期和行业id
				this.userId = id;
				this.containerAppFilterType = 'withoutDomain_1Title'
				
	    		var totalCount = 0;
		        var jqxhr = $.ajax( {
		        	type : 'get',
		        	async : false,
		        	url : 'tableInfo',
		        	data: {
		        		dataType : 'totalCount',
		        		userId : id,
		        		rq : rq,
		        		isFilter : this.containerAppFilter
	    	        	},
	    	        	dataType: 'json'
	    	        } )
	    	    	  .done(function(responseText) {
	    	    	  	totalCount = responseText.totalCount;
	    	    	  })
	    	    	  
		        $("#paginationContainerApp").paging(totalCount, {
		    		format: "[ < nnncnnn > ]",
		    		perpage: 10, 
		    	    lapping: 0, 
		    	    page: 1, 
		    		onSelect: function (page) {
		
		    			var jqxhr = $.ajax( {
		    	        	type : 'get',
		    	        	url : 'table',
		    	        	data: {
		    	        		dataType : 'titleWithoutDomain',
		    	        		userId : id,
		    	        		rq : rq,
		    	        		monitorCircle:mCircle,
		    	        		pageNo : page,
		    	        		pageSize : 10,
		    	        		isFilter : cpoi.containerAppFilter
		    	        	},
		    	        	dataType: 'json'
		    	        } )
		    	    	  .done(function(responseText) {
		    	    		  var tbody = "<table class='tftable'><tr><th colspan='5'>监测周期内(" + getCircle(rq,mCircle) + ")所有网站文章列表</th></tr>"+"<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>相关性</th></tr>";
						    	$.each(responseText,function(n,value) {  
						    		value = $.parseJSON(value);
						       		 var trs = "";  
						              trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td> <td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>"+interceptStr(value.title) +"</td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";   
						              tbody += trs;         
						            });  
						    	$("#containerApp").html(tbody + "</table>");
		    	    	  })
		    	    	  .fail(function() {
		    	    	    alert( "error" );
		    	    	  })
		    	    	  .always(function() {
		    	    	  });
		    		},
		    		onFormat: function(type) {
	
						switch (type) {
							case 'block':
								if (!this.active) 
									return '<span class="disabled">' + this.value + '</span>';
								else if (this.value != this.page)
									return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
								return '<span class="current">' + this.value + '</span>';
							case 'next':
								if (this.active)
									return '<a href="#' + this.value + '" class="next">></a>';
								return '<span class="disabled">></span>';
							case 'prev':
								if (this.active)
									return '<a href="#' + this.value + '" class="prev"><</a>';
								return '<span class="disabled"><</span>';
							case 'first':
								if (this.active)
									return '<a href="#' + this.value + '" class="first">首页</a>';
								return '<span class="disabled">首页</span>';
							case 'last':
								if (this.active)
									return '<a href="#' + this.value + '" class="last">尾页</a>';
								return '<span class="disabled">尾页</span>';
						}
		    		}
				});
	    	},
	    	getWithoutDomain_1TitleArea : function(id, rq1, rq2) {
		    	this.userId = id;
		    	this.startRq = rq1;
		    	this.endRq = rq2;
		    	this.containerAppFilterType = 'withoutDomain_1TitleArea';
		    	
			    	var totalCount = 0;
			    		
			        var jqxhr = $.ajax( {
			        	type : 'get',
			        	async : false,
			        	url : 'tableInfo',
			        	data: {
			        		dataType : 'totalCountArea',
			        		userId : id,
			        		rq1:rq1, 
			    	        rq2:rq2,
			    	        isFilter : this.containerAppFilter
		    	        	},
		    	        	dataType: 'json'
		    	        } )
		    	    	  .done(function(responseText) {
		    	    	  	totalCount = responseText.totalCount;
		    	    	  })
		    	    	  
			        $("#paginationContainerApp").paging(totalCount, {
			    		format: "[ < nnncnnn > ]",
			    		perpage: 10, 
			    	    lapping: 0, 
			    	    page: 1, 
			    		onSelect: function (page) {
			
			    			var jqxhr = $.ajax( {
			    	        	type : 'get',
			    	        	url : 'table',
			    	        	data: {
			    	        		dataType : 'titleAreaWithoutDomain',
			    	        		userId : id,
			    	        		rq1:rq1, 
			    	        		rq2:rq2,
			    	        		pageNo : page,
			    	        		pageSize : 10,
			    	        		isFilter : cpoi.containerAppFilter
			    	        	},
			    	        	dataType: 'json'
			    	        } )
			    	    	  .done(function(responseText) {
				    	    	  	var tbody = "<table class='tftable'><tr><th colspan='5'>发布周期内（" + rq1 +"到"+ rq2 + "）所有网站文章列表</th></tr>"+"<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>相关性</th></tr>";
							    	$.each(responseText,function(n,value) {  
							    		value = $.parseJSON(value);
							       		 var trs = "";  
							              trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td> <td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>"+interceptStr(value.title) +"</td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";   
							              tbody += trs;         
							            });  
							    	$("#containerApp").html(tbody + "</table>");
			    	    	  })
			    	    	  .fail(function() {
			    	    	    alert( "error" );
			    	    	  })
			    	    	  .always(function() {
			    	    	  });
			    		},
			    		onFormat: function (type) {
			    	       switch (type) {
								case 'block':
									if (!this.active) 
										return '<span class="disabled">' + this.value + '</span>';
									else if (this.value != this.page)
										return '<em><a href="#' + this.value + '">' + this.value + '</a></em>';
									return '<span class="current">' + this.value + '</span>';
								case 'next':
									if (this.active)
										return '<a href="#' + this.value + '" class="next">></a>';
									return '<span class="disabled">></span>';
								case 'prev':
									if (this.active)
										return '<a href="#' + this.value + '" class="prev"><</a>';
									return '<span class="disabled"><</span>';
								case 'first':
									if (this.active)
										return '<a href="#' + this.value + '" class="first">首页</a>';
									return '<span class="disabled">首页</span>';
								case 'last':
									if (this.active)
										return '<a href="#' + this.value + '" class="last">尾页</a>';
									return '<span class="disabled">尾页</span>';
							}
			    	    }
			    	});
			    }	
};

function goToHoutai(user_id,url,wid,uuid) {
	var Hurl = "http://www.kolong.com.cn/api/news_cache/{user_id}/{wid}__{uuid}";
	Hurl = Hurl.replace('{user_id}',user_id).replace('{wid}',wid).replace('{uuid}',uuid);
	return Hurl;
}

function getWeiBoBy(wid,uid,sour) {
	var url;
	if(sour === 'sina') {
		url = 'http://api.weibo.com/2/statuses/go?uid={uid}&id={wid}';
	}else if(sour === 'tencent') {
		url = 'http://t.qq.com/p/t/{wid}';
	}else if(sour === 'wangyi') {
		url = 'http://t.163.com/{uid}/status/{wid}';
	}else if(sour === 'sohu') {
		url = 'http://t.sohu.com/preExpr/m/{wid}';
	}
	url = url.replace('{sour}',sour).replace('{uid}',uid).replace('{wid}',wid);
	return url;
}

function clearTitle() {
	$("#titleC").empty();
	$("#paginationTitleC").empty();
	$("#containerApp").empty();
	$("#paginationContainerApp").empty();
	$("#containerOrg").empty();
	$("#containerTitle").empty();
	$("#containerSides").empty();
	$("#containerMins").empty();
}

function getType(id) {
	if(id == 1) {
		return "论坛"
	}else if(id == 2) {
		return "新闻";
	}else if(id == 3) {
		return "博客";
	}else if(id == 4) {
		return "社交媒体";
	}else if(id == 5) {
		return "视频";
	}else if(id == 6) {
		return "平面媒体";
	}else if(id == 7) {
		return "微信";
	}else if(id == 8) {
		return "微博";
	}
}

function getMinCountForOne(name,id) {
	
	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('containerColumnAll'));
	
	$.getJSON('table',{dataType:'minCountForOne', userId:id, name:encodeURIComponent(name), rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
    	var htmlHint = getHint(id) + name +"敏感词变化趋势";
		$("#hint_2").empty();
    	
		var categoriesValue = [],dataValue = [];
			$.each(responseText,function(n,value) {  
				value = $.parseJSON(value);
				categoriesValue[n] = value.rq;
				dataValue[n] = value.count
				
				//默认
				if(n == responseText.length-1) {
					getMinTitleForRq(name,id,value.rq);
				}
		    });  
			
					var option = {
						title : {
							text : getHint(id) + name + '数量变化趋势',
							subtext : '发布周期' + startRq + '到' + endRq,
							x:'center'
						},
						tooltip : {
							trigger : 'item',
							formatter: "{b} <br/>{a} : {c}"
						},
						legend : {
							data : [],
							x:'left'
						},
						toolbox : {
							show : true,
							feature : {
								dataView : {
									show : false,
									readOnly : true,
									optionToContent : function() {
										var cp_name = option.series[0].name,
											cp_categoriesArr = option.xAxis[0].data,
											cp_dataArr = option.series[0].data,
											cp_hint = getHint(id);
							        	
							            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
							        }
								},
								magicType : {
									show : true,
									type : ['line', 'bar']
								},
								restore : {
									show : true
								},
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : true,
						xAxis : [{
									type : 'category',
									boundaryGap : false,
									data : categoriesValue
								}],
						yAxis : [{
									type : 'value',
									name : name + '次数',
									axisLabel : {
										formatter : '{value}'
									}
								}],
						series : [{
								name : name,
								type : 'line',
								data : dataValue,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							}]
					};
	
					function eConsole(param) {
					    var mes = '';
					    if (typeof param.seriesIndex != 'undefined') {
					        mes += '  seriesIndex : ' + param.seriesIndex;
					        mes += '  dataIndex : ' + param.dataIndex;
					    }
					    if (param.type == 'click') {
					    	var rq = param.name;
					    	getMinTitleForRq(name,id,rq);
					    }//end if
					  
					}//end Console
					myChart.on(echarts.config.EVENT.CLICK, eConsole);
					myChart.setOption(option); 
			
	});
}
function getMinTitle(name,id) {
	
	$.getJSON('table',{dataType:'sidesTitle', userId:id, name:encodeURIComponent(name), rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		var tbody = "<table class='tftable'><tr><th colspan=\"5\">发布周期\("+startRq+"到"+endRq+"\)内出现<em>"+name+"</em>敏感词文章top10</th></tr>";
     	tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>相关性</th></tr>";
     	$.each(responseText,function(n,value) {  
     		value = $.parseJSON(value);
        		 var trs = "";  
               trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";  
               tbody += trs; 
               
             });
	
     	$("#containerMins").html(tbody + "</table>");
	});
}
function getMinTitleForRq(name,id,rq) {
	
	$.getJSON('table',{dataType:'sidesTitleForRq', userId:id, name:encodeURIComponent(name), rq:rq}, function(responseText) {
		var tbody = "<table class='tftable'><tr><th colspan=\"5\">\监测周期\("+getCircle(rq,mCircle)+"\)内出现<em>"+name+"</em>敏感词文章top10</th></tr>";
     	tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>相关性</th></tr>";
     	$.each(responseText,function(n,value) {  
     		value = $.parseJSON(value);
        		 var trs = "";  
               trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";  
               tbody += trs; 
               
             });
	
     	$("#containerSides").html(tbody + "</table>");
     	
	});
}
function getSidesCountForOne(name,id) {
	
	// 基于准备好的dom，初始化echarts图表
	var myChart = echarts.init(document.getElementById('containerColumnAll'));
	$.getJSON('table',{dataType:'sidesCountForOne', userId:id, name:encodeURIComponent(name), rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
    	var htmlHint = getHint(id) + name +"负面词变化趋势";
		$("#hint_2").empty();
    	
		var categoriesValue = [],dataValue = [];
			$.each(responseText,function(n,value) {  
				value = $.parseJSON(value);
				categoriesValue[n] = value.rq;
				dataValue[n] = value.count
				//默认
				if(n == responseText.length-1) {
					getSidesTitleForRq(name,id,value.rq);
				}
		    });  
			
		var option = {
			title : {
				text : getHint(id) + name + '数量变化趋势',
				subtext : '发布周期' + startRq + '到' + endRq,
				x:'center'
			},
			tooltip : {
				trigger : 'item',
				formatter: "{b} <br/>{a} : {c}"
			},
			legend : {
				data : [],
				x:'left'
			},
			toolbox : {
				show : true,
				feature : {
					dataView : {
						show : false,
						readOnly : true,
						optionToContent : function() {
							var cp_name = option.series[0].name,
								cp_categoriesArr = option.xAxis[0].data,
								cp_dataArr = option.series[0].data,
								cp_hint = getHint(id);
				        	
				            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
				        }
					},
					magicType : {
						show : true,
						type : ['line', 'bar']
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [{
						type : 'category',
						boundaryGap : false,
						data : categoriesValue
					}],
			yAxis : [{
						type : 'value',
						name : name + '次数',
						axisLabel : {
							formatter : '{value}'
						}
					}],
			series : [{
					name : name,
					type : 'line',
					data : dataValue,
					markPoint : {
						data : [{
									type : 'max',
									name : '最大值'
								}, {
									type : 'min',
									name : '最小值'
								}]
					}
				}]
		};

		function eConsole(param) {
		    var mes = '';
		    if (typeof param.seriesIndex != 'undefined') {
		        mes += '  seriesIndex : ' + param.seriesIndex;
		        mes += '  dataIndex : ' + param.dataIndex;
		    }
		    if (param.type == 'click') {
		    	var rq = param.name;
		    	getSidesTitleForRq(name,id,rq);
		    }//end if
		  
		}//end Console
		myChart.on(echarts.config.EVENT.CLICK, eConsole);
		myChart.setOption(option);
		    
	});
}

function getSidesTitle(name,id) {
	
	$.getJSON('table',{dataType:'sidesTitle', userId:id, name:encodeURIComponent(name), rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		var tbody = "<table class='tftable'><tr><th colspan=\"5\">发布周期\("+startRq+"到"+endRq+"\)内出现<em>"+name+"</em>负面词文章top10</th></tr>";
     	tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>相关性</th></tr>";
     	$.each(responseText,function(n,value) {  
     		value = $.parseJSON(value);
        		 var trs = "";  
               trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";  
               tbody += trs; 
               
             });
	
     	$("#containerMins").html(tbody + "</table>");
	});
}
function getSidesTitleForRq(name,id,rq) {
	
	$.getJSON('table',{dataType:'sidesTitleForRq', userId:id, name:encodeURIComponent(name), rq:rq}, function(responseText) {
		var tbody = "<table class='tftable'><tr><th colspan=\"5\">\监测周期\("+getCircle(rq,mCircle)+"\)内出现<em>"+name+"</em>负面词文章top10</th></tr>";
     	tbody = tbody + "<tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>相关性</th></tr>";
     	$.each(responseText,function(n,value) {  
     		value = $.parseJSON(value);
        		 var trs = "";  
               trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a  href='" +goToHoutai(value.user_id,value.url,value.id,value.uuid)+ "' target='_blank' title="+ getHint(value.user_id) +">" + interceptStr(value.title) +"</a></td> <td><a target='_blank' href='tableInfo?dataType=simiTitle&userId="+ value.user_id +"&simId="+ value.id +"'>" + value.similar_count +"</a></td></tr>";  
               tbody += trs; 
               
             });
	
     	
     	$("#containerSides").html(tbody + "</table>");
	});
}

function userCountDefault(id, rq) {
	
		// 基于准备好的dom，初始化echarts图表
		var pointChart = echarts.init(document.getElementById('point'));
		pointChart.showLoading({
		    text: '正在努力的读取数据中...',  //loading话术
		    effect:'dynamicLine'
		});
		$.getJSON('table',{dataType:'userCount', userId:id, rq:rq}, function(responseText) {
	                            	
	            var categoriesValue = [],dataValue = [];
	            var domain_1s = [];
				$.each(responseText,function(n,value) {  
					value = $.parseJSON(value);
					categoriesValue[n] = value.site_name;
					dataValue[n] = value.userCount;
					
					domain_1s.push({site_name:value.site_name,domain_1 : value.domain_1});
					
					//默认显示排名第一的网站文章列表
					if(n===0) {
	              		cpoi.getDomain_1Title(value.domain_1,value.site_name,rq,id);
	              	}
			    }); 
			    
				var option = {
					title : {
						text : getHint(id) + '发文量top10的网站',
						subtext : '监测周期' + getCircle(rq,mCircle),
						x:'center'
					},
					tooltip : {
						trigger : 'item'
					},
					legend : {
						data : [],
						x:'center',
						y:'bottom'
					},
					toolbox : {
						show : true,
						orient : 'verticle',
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(id);
						        	
						            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : true,
								axisLabel : {
									rotate : -45
								},
								data : categoriesValue
							}],
					yAxis : [{
								type : 'value',
								name : '发文量'
							}],
					series : [{
								name : '发文量',
								type : 'bar',
								barWidth : 20,
								data : dataValue
							}]
				};
				
			function eConsole(param) {
				    var mes = '';
				    if (typeof param.seriesIndex != 'undefined') {
				        mes += '  seriesIndex : ' + param.seriesIndex;
				        mes += '  dataIndex : ' + param.dataIndex;
				    }
				    if (param.type == 'click') {
				    	var site_name = param.name;
				    	
				    	var domain_1;
				    	for(var i=0; i<domain_1s.length; i++) {
				    		if (site_name == domain_1s[i].site_name) {
				    			domain_1 = domain_1s[i].domain_1;
				    		}
				    	}
				    	
				    	cpoi.setTitleCFilter(true);
				    	$("#selectTitleC").val("过滤");
	                    cpoi.getDomain_1Title(domain_1,site_name,rq,id);
				    }//end if
				  
				}//end Console
				pointChart.on(echarts.config.EVENT.CLICK, eConsole);
				pointChart.setOption(option);
				pointChart.hideLoading();
					
					
		    	
		 });
		 
			
		 // 基于准备好的dom，初始化echarts图表
		var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
		// 过渡---------------------
		containerColumnAllChart.showLoading({
		    text: '正在努力的读取数据中...'   //loading话术
		});
		
		$.getJSON('thirdLevel',{dataType:'userCountPieRq', userId:id, rq:rq}, function(responseText) {
		    	var htmlHint = getHint(id) +">>"+ "曝光程度" + ">>" + "监测周期内文章来源类型饼图"
				$("#hint_2").empty();
		    	
		    	var categoriesValue = [],dataOfficialValue = [],dataNonOfficialValue = [],dataCountryValue = [],dataProvinceValue = [],dataCityValue = [],dataXianjiValue = [],dataTraditionValue = [],dataGeneralValue = [],dataSocialValue = [];
		    	var officialT = 0,nonOfficialT = 0,countryT = 0,provinceT = 0,cityT = 0,xianjiT = 0,traditionT = 0,generalT = 0,socialT = 0;
				$.each(responseText,function(n,value) {  
					value = $.parseJSON(value);
					categoriesValue[n] = value.rq;
					dataOfficialValue[n] = value.official;
					dataNonOfficialValue[n] = value.non_official;
					dataCountryValue[n] = value.country;
					dataProvinceValue[n] = value.province;
					dataCityValue[n] = value.city;
					dataXianjiValue[n] = value.xianji;
					dataTraditionValue[n] = value.tradition;
					dataGeneralValue[n] = value.general;
					dataSocialValue[n] = value.social;
					
					officialT += value.official;
					nonOfficialT += value.non_official;
					countryT += value.country;
					provinceT += value.province;
					cityT += value.city;
					xianjiT += value.xianji;
					traditionT += value.tradition;
					generalT += value.general;
					socialT += value.social;
			    });
				
				var pieOfficialValue = [{
					name: "官方网站",
					value : officialT
				},{
					name: "非官方网站",
					value : nonOfficialT
				}], pieCountryValue = [{
					name: "全国性网站",
					value : countryT
				},{
					name: "省级网站",
					value : provinceT
				},{
					name: "市级网站",
					value : cityT
				},{
					name: "县级网站",
					value : xianjiT
				}], pieTraditionValue = [{
					name: "传统性网站",
					value : traditionT
				},{
					name: "一般性网站",
					value : generalT
				},{
					name: "社会化媒体",
					value : socialT
				}];
				
		    	var option2 = {
		    			title : {
							text : getHint(id) + '文章来源类型圆环图',
							subtext : '监测周期' + getCircle(rq,mCircle),
							x:'left'
						},
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    legend: {
					        orient : 'vertical',
					        x : 'left',
					        y : 80,
					        data:['官方网站','非官方网站','传统性网站','一般性网站','社会化媒体','全国性网站','省级网站','市级网站','县级网站']
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            dataView : {show: false, readOnly: true},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : false,
					    series : [
					        {
					            name:'网站发文数',
					            type:'pie',
					            selectedMode: 'single',
					            radius : [0, 40],
					            startAngle : 45,
					            minAngle : 20,
					            itemStyle : {
					                normal : {
					                    label : {
					                        position : 'inner'
					                    },
					                    labelLine : {
					                        show : false
					                    }
					                }
					            },
					            data:pieOfficialValue
					        },
					        {
					            name:'类型网站发文数',
					            type:'pie',
					            radius : [60, 100],
					            minAngle : 20,
					            startAngle : 45,
					            itemStyle : {
					                normal : {
					                    label : {
					                        position : 'inner'
					                    },
					                    labelLine : {
					                        show : false
					                    }
					                }
					            },
					            data:pieTraditionValue
					        },
					        {
					            name:'区域网站发文数',
					            type:'pie',
					            radius : [130, 170],
					            minAngle : 20,
					            startAngle : 45,
					            data:pieCountryValue
					        }
					    ]
					};
					containerColumnAllChart.setOption(option2,true);
					containerColumnAllChart.hideLoading();
				
		    });	
}
function site_cls(id, rq) {

		 // 基于准备好的dom，初始化echarts图表
		var containerTableChart = echarts.init(document.getElementById('containerTable'));
		// 过渡---------------------
		containerTableChart.showLoading({
		    text: '正在努力的读取数据中...'   //loading话术
		});
		
		$.getJSON('table',{dataType:'site_cls', userId:id, rq:rq}, function(responseText) {
		    	var htmlHint = getHint(id);
				$("#hint_3").empty();
				
				var pieValue = [];
				$.each(responseText,function(n,value) {  
			    	value = $.parseJSON(value);
			    
		    		if(value.count !== 0) {
						pieValue.push({
								name: getType(value.id),
								value : value.count
						});
		    		}
		         });  
				
		    	var option = {
					    title : {
					        text: getHint(id) + '媒体类型对比图',
					        subtext: '监测周期' + getCircle(rq,mCircle),
					        x:'center'
					    },
					    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						 },
					    legend: {
					        x : 'center',
					        y : 'bottom',
					        data:['媒体类型']
					    },
					    toolbox: {
					        show : true,
					        orient:'vertical',
					        feature : {
					            dataView : {show: false, readOnly: true},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    series : [
						        {
						            name:'媒体类型',
						            type:'pie',
						            minAngle:'10',
						            radius : '55%',
						            selectedOffset:10,
						            selectedMode:'single',
						            center: ['50%', '50%'],
						            data:pieValue,
						            itemStyle: {
									    normal: {
									       label : {
						                        position : 'outer',
						                        formatter : function (a,b,c,d) {return b + ': ' + (d - 0).toFixed(2) + '%'}
						                    },
						                    labelLine : {
						                        show : true
						                    }
									    }
									}
						        }
						    	 ]
					   
					};
				containerTableChart.setOption(option,true);
				containerTableChart.hideLoading();
				
		    });	
}
function site_clsArea(id, rq1, rq2) {

		 // 基于准备好的dom，初始化echarts图表
		var containerTableChart = echarts.init(document.getElementById('containerTable'));
		// 过渡---------------------
		containerTableChart.showLoading({
		    text: '正在努力的读取数据中...'   //loading话术
		});
		
		$.getJSON('table',{dataType:'site_clsArea', userId:id, rq1:rq1, rq2:rq2}, function(responseText) {
		    	var htmlHint = getHint(id);
				$("#hint_3").empty();
				
				var pieValue = [];
				$.each(responseText,function(n,value) {  
			    	value = $.parseJSON(value);
		    		if(value.count != 0) {
						pieValue.push({
								name: getType(value.id),
								value : value.count
						});
		    		}
		         });  
				
		    	var option = {
					    title : {
					        text: getHint(id) + '媒体类型对比图',
					        subtext: '发布周期' + startRq + '到' + endRq,
					        x:'center'
					    },
					    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						 },
					    legend: {
					        x : 'center',
					        y : 'bottom',
					        data:['媒体类型']
					    },
					    toolbox: {
					        show : true,
					        orient:'vertical',
					        feature : {
					            dataView : {show: false, readOnly: true},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    series : [
						        {
						            name:'媒体类型',
						            type:'pie',
						            minAngle:'15',
						            radius : '55%',
						            selectedOffset:10,
						            selectedMode:'single',
						            center: ['50%', '50%'],
						            data:pieValue,
						            itemStyle: {
									    normal: {
									       label : {
						                        position : 'outer',
						                        formatter : function (a,b,c,d) {return b+ ': ' + (d - 0).toFixed(2) + '%'}
						                    },
						                    labelLine : {
						                        show : true
						                    }
									    }
									}
						        }
						    	 ]
					};
				containerTableChart.setOption(option,true);
				containerTableChart.hideLoading();
				
		    });	
}

function site_yxlDefault(id,pr,startRq,endRq,mCircle) {
	
	// 基于准备好的dom，初始化echarts图表
	var pointChart = echarts.init(document.getElementById('point'));
	pointChart.showLoading({
	    text: '正在努力的读取数据中...',  //loading话术
	    effect:'dynamicLine'
	});
		
	$.getJSON('table',{dataType:'sitesTop10',pr:pr, userId:id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		                            	
		var categoriesValue = [],dataValue = [];
        var domain_1s = [];
		$.each(responseText,function(n,value) {  
			value = $.parseJSON(value);
			categoriesValue[n] = value.site_name;
			dataValue[n] = value.userCount;
			
			domain_1s.push({site_name:value.site_name,domain_1 : value.domain_1});
			
			//默认显示排名第一的网站文章列表
			if(n===0) {
				cpoi.getDomain_1TitleArea(value.domain_1,value.site_name,startRq,endRq,id);
          	}
	    }); 
	    //翻转过来
	    categoriesValue.reverse();
	    dataValue.reverse();
	    
		var option = {
			title : {
				text : getHint(id) + "pr值为("+pr + ")的发文量top10的网站",
				textStyle:{
					fontSize: 13, 
					fontWeight: 'bolder', 
					color: '#333'
				},
				subtext : '发布周期'+startRq+"到"+endRq,
				x:'center'
			},
			tooltip : {
				trigger : 'item'
			},
			legend : {
				data : [],
				x:'center',
				y:'bottom'
			},
			toolbox : {
				show : true,
				orient : 'verticle',
				feature : {
					dataView : {
						show : false,
						readOnly : true,
						optionToContent : function() {
							var cp_name = option.series[0].name,
								cp_categoriesArr = option.yAxis[0].data,
								cp_dataArr = option.series[0].data,
								cp_hint = getHint(id);
				        	
				            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
				        }
					},
					magicType : {
						show : true,
						type : ['line', 'bar']
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			yAxis : [{
						type : 'category',
						boundaryGap : true,
						data : categoriesValue
					}],
			xAxis : [{
						type : 'value',
						name : '发文量'
					}],
			series : [{
						name : '发文量',
						type : 'bar',
						barWidth : 20,
						data : dataValue
					}]
		};
		
	function eConsole(param) {
		    var mes = '';
		    if (typeof param.seriesIndex != 'undefined') {
		        mes += '  seriesIndex : ' + param.seriesIndex;
		        mes += '  dataIndex : ' + param.dataIndex;
		    }
		    if (param.type == 'click') {
		    	var site_name = param.name;
		    	
		    	var domain_1;
		    	for(var i=0; i<domain_1s.length; i++) {
		    		if (site_name == domain_1s[i].site_name) {
		    			domain_1 = domain_1s[i].domain_1;
		    		}
		    	}
		    	cpoi.setTitleCFilter(true);
				$("#selectTitleC").val("过滤");
                cpoi.getDomain_1TitleArea(domain_1,site_name,startRq,endRq,id);
		    }//end if
		  
		}//end Console
		pointChart.on(echarts.config.EVENT.CLICK, eConsole);
		pointChart.setOption(option);
		pointChart.hideLoading();
    });
}

function clickBefore($cpoiTable,$show_1,$releaseRq) {
	$cpoiTable = $cpoiTable || $("#cpoiTable");
	$cpoiTable.remove();
	$show_1 = $show_1 || $(".show_1");
	$show_1.show();
	$releaseRq = $releaseRq || $("#releaseRq")
	$releaseRq.show();
	$("#selectTitleC").show();
	$("#selectContainerApp").show();
	
	startRq = $inputRq1.val() || $("input#rq1").val();
	endRq = $inputRq2.val() || $("input#rq2").val();
	mCircle = $selectM.val() || $("select[name='monitorCircle']").val();
	// 获得真实的发布周期
	endRq = getRealEndRq(startRq, endRq, mCircle);
	
	cpoi.setTitleCFilter(true);
	cpoi.setContainerAppFilter(true);
	$( "#selectTitleC" ).val( "过滤" );
	$( "#selectContainerApp" ).val( "过滤" );
}

$(function() {
	
		var $cpoiTable,
			$show_1,
			$releaseRq;
			
		$(".exposIndex").on("click",function(e) {
			clickBefore($cpoiTable,$show_1,$releaseRq);
			$(".show_1:gt(0)").hide();
			clearTitle();
			
			var a = e.currentTarget;
			latelyClick.name = a.id;
			latelyClick.password = $(this).attr("class");
			
			$.getJSON('firstLevel',{dataType:'exposIndex', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
				var htmlHint = getHint(a.id) + ">>" + "曝光程度";
				$("#hint_1").empty();

				var arr = $.parseJSON(responseText.data);
				var categoriesArr = $.parseJSON(responseText.categories);
				for (var i = 0; i < arr.length; i++) {
					arr[i] = Math.round10(arr[i], -2);
				}

				// 基于准备好的dom，初始化echarts图表
				var myChart = echarts.init(document.getElementById('container'));
				var option = {
					title : {
						text : getHint(a.id) + '曝光程度指数变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [],
						x:'left'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	var content = cp_hint + cp_name +':\n\n';
									for(var i=0; i<cp_categoriesArr.length; i++) {
										content += cp_categoriesArr[i];
										var space = '';
										for(var j=0; j<30 - cp_categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
											space += ' ';
										}
										content += (space + cp_dataArr[i] + '\n');
									}
						            return content;
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name:'曝光程度指数',
								max : Math.max.apply(Math, arr)+100,
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
								name : '曝光程度指数',
								type : 'line',
								data : arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							}]
				};

				// 为echarts对象加载数据
				myChart.setOption(option);
				
		    });
		
	});
	
		$(".participationIndex").on("click",function(e) {
			clickBefore($cpoiTable,$show_1,$releaseRq);
			$(".show_1:gt(0)").hide();
			clearTitle();
			
			var a = e.currentTarget;
			latelyClick.name = a.id;
			latelyClick.password = $(this).attr("class");
			
			$.getJSON('firstLevel',{dataType:'participationIndex', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
				var htmlHint = getHint(a.id) + ">>"+ "参与程度";
				$("#hint_1").empty();
				
				var arr = $.parseJSON(responseText.data);
				var categoriesArr = $.parseJSON(responseText.categories);
				for (var i = 0; i < arr.length; i++) {
					arr[i] = Math.round10(arr[i], -2);
				}

				// 基于准备好的dom，初始化echarts图表
				var myChart = echarts.init(document.getElementById('container'));
				var option = {
					title : {
						text : getHint(a.id) + '参与程度指数变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [],
						x:'left'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	var content = cp_hint + cp_name +':\n\n';
									for(var i=0; i<cp_categoriesArr.length; i++) {
										content += cp_categoriesArr[i];
										var space = '';
										for(var j=0; j<30 - cp_categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
											space += ' ';
										}
										content += (space + cp_dataArr[i] + '\n');
									}
						            return content;
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name:'参与程度指数',
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
								name : '参与程度指数',
								type : 'line',
								data : arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							}]
				};

				// 为echarts对象加载数据
				myChart.setOption(option);
		    });
			
	});
	$(".publicOpinionIndex").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		$.getJSON('firstLevel',{dataType:'publicOpinionIndex', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
			var htmlHint = getHint(a.id) + ">>"+ "舆情观点";
			$("#hint_1").empty();
			
			var arr = $.parseJSON(responseText.data);
			var categoriesArr = $.parseJSON(responseText.categories);
			for (var i = 0; i < arr.length; i++) {
				arr[i] = Math.round10(arr[i], -2);
			}

			// 基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById('container'));
			var option = {
				title : {
					text : getHint(a.id) + '舆情观点指数变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [],
					x:'left'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	var content = cp_hint + cp_name +':\n\n';
									for(var i=0; i<cp_categoriesArr.length; i++) {
										content += cp_categoriesArr[i];
										var space = '';
										for(var j=0; j<30 - cp_categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
											space += ' ';
										}
										content += (space + cp_dataArr[i] + '\n');
									}
						            return content;
						        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name:'舆情观点指数',
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
							name : '舆情观点指数',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
	    });
		
	});
	$(".siteFeatureIndex").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		clearTitle();	
		
			var a = e.currentTarget;
			latelyClick.name = a.id;
			latelyClick.password = $(this).attr("class");
			
			$.getJSON('firstLevel',{dataType:'siteFeatureIndex', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
				var htmlHint = getHint(a.id) + ">>"+ "网站特点";
				$("#hint_1").empty();
				
				var arr = $.parseJSON(responseText.data);
				var categoriesArr = $.parseJSON(responseText.categories);
				for (var i = 0; i < arr.length; i++) {
					arr[i] = Math.round10(arr[i], -2);
				}

				// 基于准备好的dom，初始化echarts图表
				var myChart = echarts.init(document.getElementById('container'));
				var option = {
					title : {
						text : getHint(a.id) + '网站特点指数变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : [],
						x:'left'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	var content = cp_hint + cp_name +':\n\n';
									for(var i=0; i<cp_categoriesArr.length; i++) {
										content += cp_categoriesArr[i];
										var space = '';
										for(var j=0; j<30 - cp_categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
											space += ' ';
										}
										content += (space + cp_dataArr[i] + '\n');
									}
						            return content;
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name : '网站特点指数',
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
								name : '网站特点指数',
								type : 'line',
								data : arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							}]
				};

				// 为echarts对象加载数据
				myChart.setOption(option);
		    });
			
	});
	
	$(".transIndex").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		$.getJSON('firstLevel',{dataType:'transIndex', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
			var htmlHint = getHint(a.id) + ">>"+ "传播进度";
			$("#hint_1").empty();
			
			var arr = $.parseJSON(responseText.data);
			var categoriesArr = $.parseJSON(responseText.categories);
			for (var i = 0; i < arr.length; i++) {
				arr[i] = Math.round10(arr[i], -2);
			}

			// 基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById('container'));
			var option = {
				title : {
					text : getHint(a.id) + '传播进度指数变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [],
					x:'left'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	var content = cp_hint + cp_name +':\n\n';
									for(var i=0; i<cp_categoriesArr.length; i++) {
										content += cp_categoriesArr[i];
										var space = '';
										for(var j=0; j<30 - cp_categoriesArr[i].length*2; j++) {//一个汉字占两个空格所以要乘以2
											space += ' ';
										}
										content += (space + cp_dataArr[i] + '\n');
									}
						            return content;
						        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '传播进度指数'
						}],
				series : [{
							name : '传播进度指数',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
	    });
		
	});
	
	$(".userCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(3)").hide();
		$(".show_1:eq(7)").show();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var containerChart = echarts.init(document.getElementById('container'));
		containerChart.showLoading({
		    text: '正在努力的读取数据中...'    //loading话术
		});
	    $.getJSON('thirdLevel',{dataType:'userCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) +">>"+ "曝光程度" + ">>" + ""
			$("#hint_1").empty();
			
			var arr = $.parseJSON(responseText.data),
				wb_arr = $.parseJSON(responseText.wb_data),
				lt_arr = $.parseJSON(responseText.lt_data),
				categoriesArr = $.parseJSON(responseText.categories);
				
			var _arr = [];
			for(var i=0; i<arr.length; i++) {
				_arr[i] = arr[i] - wb_arr[i];
			}
				
			var option = {
				title : {
					text : getHint(a.id) + '新增发文量变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : ['总新增发文量','新增微博发文量','新增新闻发文量','新增论坛发文量'],
					x:'center',
					y:'bottom'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	
						            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
						        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '发文量',
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
							name : '总新增发文量',
							type : 'line',
							data : arr,
							markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
						},
						{
							name : '新增微博发文量',
							type : 'line',
							data : wb_arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						},
						{
							name : '新增新闻发文量',
							type : 'line',
							data : _arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						},
						{
							name : '新增论坛发文量',
							type : 'line',
							data : lt_arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
			};
			
			function eConsole(param) {
			    var mes = '';
			    if (typeof param.seriesIndex != 'undefined') {
			        mes += '  seriesIndex : ' + param.seriesIndex;
			        mes += '  dataIndex : ' + param.dataIndex;
			    }
			    if (param.type == 'click') {
			    	var rq = param.name;
			                             
                    userCountDefault(a.id, rq);
                    cpoi.getWithoutDomain_1Title(a.id, rq)
			    }
			  
			}
			containerChart.on(echarts.config.EVENT.CLICK, eConsole);

			// 为echarts对象加载数据
			containerChart.setOption(option);
			containerChart.hideLoading();
	    });
	    
	    //默认显示最近一个监测周期的数据
	    userCountDefault(a.id, endRq);
	    cpoi.getWithoutDomain_1Title(a.id, endRq)
	});
	
	$(".userCountArea").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(3)").hide();
		$(".show_1:eq(7)").show();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('thirdLevel',{dataType:'userCountArea', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "曝光程度" + ">>" + "累积发文量"
			$("#hint_1").empty();
	    	
			var arr = $.parseJSON(responseText.data),
				wb_arr = $.parseJSON(responseText.wb_data),
				lt_arr = $.parseJSON(responseText.lt_data),
				categoriesArr = $.parseJSON(responseText.categories);

			var _arr = [];
			for(var i=0; i<arr.length; i++) {
				_arr[i] = arr[i] - wb_arr[i];
			}
				
			var option = {
				title : {
					text : getHint(a.id) + '累积发文量变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : ['总累积发文量','累积微博发文量','累积新闻发文量','累积论坛发文量'],
					x:'center',
					y:'bottom'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	
						            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
						        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '发文量',
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
							name : '总累积发文量',
							type : 'line',
							data : arr,
							smooth:true,
							itemStyle: {normal: {areaStyle: {type: 'default'}}}
						},
						{
							name : '累积微博发文量',
							type : 'line',
							data : wb_arr,
							smooth:true,
							itemStyle: {normal: {areaStyle: {type: 'default'}}}
						},
						{
							name : '累积新闻发文量',
							type : 'line',
							data : _arr,
							smooth:true,
							itemStyle: {normal: {areaStyle: {type: 'default'}}}
						},
						{
							name : '累积论坛发文量',
							type : 'line',
							data : lt_arr,
							smooth:true,
							itemStyle: {normal: {areaStyle: {type: 'default'}}}
						}]
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
	    });
	    
	    // 基于准备好的dom，初始化echarts图表
		var pointChart = echarts.init(document.getElementById('point'));
		pointChart.showLoading({
		    text: '正在努力的读取数据中...',  //loading话术
		    effect:'dynamicLine'
		});
	    $.getJSON('table',{dataType:'userCountTop10', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
        	
        	var categoriesValue = [],dataValue = [];
            var domain_1s = [];
			$.each(responseText,function(n,value) {  
				value = $.parseJSON(value);
				categoriesValue[n] = value.site_name;
				dataValue[n] = value.userCount;
				
				domain_1s.push({site_name:value.site_name,domain_1 : value.domain_1});
				
				//默认显示排名第一的网站文章列表
				if(n===0) {
              		cpoi.getDomain_1TitleArea(value.domain_1,value.site_name,startRq,endRq,a.id)
              	}
		    }); 
		    
			var option = {
				title : {
					text : getHint(a.id) + '累积发文量top10的网站',
					subtext : '发布周期'+startRq+"到"+endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'item'
				},
				legend : {
					data : [],
					x:'center',
					y:'bottom'
				},
				toolbox : {
					show : true,
					orient : 'verticle',
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	
						            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
						        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : true,
							axisLabel : {
									rotate : -45
								},
							data : categoriesValue
						}],
				yAxis : [{
							type : 'value',
							name : '发文量'
						}],
				series : [{
							name : '发文量',
							type : 'bar',
							barWidth : 20,
							data : dataValue
						}]
			};
			
		function eConsole(param) {
			    var mes = '';
			    if (typeof param.seriesIndex != 'undefined') {
			        mes += '  seriesIndex : ' + param.seriesIndex;
			        mes += '  dataIndex : ' + param.dataIndex;
			    }
			    if (param.type == 'click') {
			    	var site_name = param.name;
			    	
			    	var domain_1;
			    	for(var i=0; i<domain_1s.length; i++) {
			    		if (site_name == domain_1s[i].site_name) {
			    			domain_1 = domain_1s[i].domain_1;
			    		}
			    	}
			    	
			    	cpoi.setTitleCFilter(true);
				    $("#selectTitleC").val("过滤");
                    cpoi.getDomain_1TitleArea(domain_1,site_name,startRq,endRq,a.id)
			    }//end if
			  
			}//end Console
			pointChart.on(echarts.config.EVENT.CLICK, eConsole);
			pointChart.setOption(option);
			pointChart.hideLoading();
					
        });
        
        cpoi.getWithoutDomain_1TitleArea(a.id,startRq,endRq)
		
		// 基于准备好的dom，初始化echarts图表
		var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
		// 过渡---------------------
		containerColumnAllChart.showLoading({
		    text: '正在努力的读取数据中...'    //loading话术
		});
		
	    $.getJSON('thirdLevel',{dataType:'userCountPie', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) +">>"+ "曝光程度" + ">>" + "发布周期内文章来源类型饼图"
			$("#hint_3").empty();
	    	
	    	var categoriesValue = [],dataOfficialValue = [],dataNonOfficialValue = [],dataCountryValue = [],dataProvinceValue = [],dataCityValue = [],dataXianjiValue = [],dataTraditionValue = [],dataGeneralValue = [],dataSocialValue = [];
	    	var officialT = 0,nonOfficialT = 0,countryT = 0,provinceT = 0,cityT = 0,xianjiT = 0,traditionT = 0,generalT = 0,socialT = 0;
			$.each(responseText,function(n,value) {  
				value = $.parseJSON(value);
				categoriesValue[n] = value.rq;
				dataOfficialValue[n] = value.official;
				dataNonOfficialValue[n] = value.non_official;
				dataCountryValue[n] = value.country;
				dataProvinceValue[n] = value.province;
				dataCityValue[n] = value.city;
				dataXianjiValue[n] = value.xianji;
				dataTraditionValue[n] = value.tradition;
				dataGeneralValue[n] = value.general;
				dataSocialValue[n] = value.social;
				
				officialT += value.official;
				nonOfficialT += value.non_official;
				countryT += value.country;
				provinceT += value.province;
				cityT += value.city;
				xianjiT += value.xianji;
				traditionT += value.tradition;
				generalT += value.general;
				socialT += value.social;
		    });
			
			var pieOfficialValue = [{
				name: "官方网站",
				value : officialT
			},{
				name: "非官方网站",
				value : nonOfficialT,
				selected:true
			}], pieCountryValue = [{
				name: "全国性网站",
				value : countryT
			},{
				name: "省级网站",
				value : provinceT
			},{
				name: "市级网站",
				value : cityT
			},{
				name: "县级网站",
				value : xianjiT
			}], pieTraditionValue = [{
				name: "传统性网站",
				value : traditionT
			},{
				name: "一般性网站",
				value : generalT
			},{
				name: "社会化媒体",
				value : socialT
			}];
			
	    	var option = {
	    			title : {
						text : getHint(a.id) + '累积文章来源类型圆环图',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'left'
					},
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        orient : 'vertical',
				        x : 'left',
				        y : 80,
				        data:['官方网站','非官方网站','传统性网站','一般性网站','社会化媒体','全国性网站','省级网站','市级网站','县级网站']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            dataView : {show: false, readOnly: true},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : false,
				    series : [
				        {
				            name:'网站发文数',
				            type:'pie',
				            selectedMode: 'single',
				            radius : [0, 40],
				            minAngle : 20,
				            startAngle : 45,
				            itemStyle : {
				                normal : {
				                    label : {
				                        position : 'inner'
				                    },
				                    labelLine : {
				                        show : false
				                    }
				                }
				            },
				            data:pieOfficialValue
				        },
				        {
				            name:'类型网站发文数',
				            type:'pie',
				            radius : [60, 100],
				            minAngle : 20,
				            startAngle : 45,
				            itemStyle : {
				                normal : {
				                    label : {
				                        position : 'inner'
				                    },
				                    labelLine : {
				                        show : false
				                    }
				                }
				            },
				            data:pieTraditionValue
				        },
				        {
				            name:'区域网站发文数',
				            type:'pie',
				            radius : [120, 160],
				            minAngle : 20,
				            startAngle : 45,
				            data:pieCountryValue
				        }
				    ]
				};
				containerColumnAllChart.setOption(option);
                containerColumnAllChart.hideLoading();
                
	    });
        
	});
	
	$(".viewCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:eq(1)").hide();
		$(".show_1:gt(2)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById('container'));
		    $.getJSON('thirdLevel',{dataType:'viewCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		    	var htmlHint = getHint(a.id) + ">>"+ "参与程度" + ">>" + "新增浏览量"
				$("#hint_1").empty();
		    	
		    		var arr = $.parseJSON(responseText.data),
						wb_arr = $.parseJSON(responseText.wb_data),
						lt_arr = $.parseJSON(responseText.lt_data),
						categoriesArr = $.parseJSON(responseText.categories);
	
					var _arr = [];
					for(var i=0; i<arr.length; i++) {
						_arr[i] = arr[i] - wb_arr[i];
					}
			
					var option = {
						title : {
							text : getHint(a.id) + '新增浏览量变化趋势',
							subtext : '发布周期' + startRq + '到' + endRq,
							x:'center'
						},
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : ['新增新闻浏览量','新增论坛浏览量'],
							x:'center',
							y:'bottom'
						},
						toolbox : {
							show : true,
							feature : {
								dataView : {
									show : false,
									readOnly : true,
									optionToContent : function() {
										var cp_name = option.series[0].name,
											cp_categoriesArr = option.xAxis[0].data,
											cp_dataArr = option.series[0].data,
											cp_hint = getHint(a.id);
							        	
							            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
							        }
								},
								magicType : {
									show : true,
									type : ['line', 'bar']
								},
								restore : {
									show : true
								},
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : true,
						xAxis : [{
									type : 'category',
									boundaryGap : false,
									data : categoriesArr
								}],
						yAxis : [{
									type : 'value',
									name : '浏览量',
									max : Math.max.apply(Math, arr) + 100,
									axisLabel : {
										formatter : '{value}'
									}
								}],
						series : [
							{
								name : '新增新闻浏览量',
								type : 'line',
								data : _arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							},{
								name : '新增论坛浏览量',
								type : 'line',
								data : lt_arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							}]
					};
					
					function eConsole(param) {
					    var mes = '';
					    if (typeof param.seriesIndex != 'undefined') {
					        mes += '  seriesIndex : ' + param.seriesIndex;
					        mes += '  dataIndex : ' + param.dataIndex;
					    }
					    if (param.type == 'click') {
					    	var rq = param.name;
					        cpoi.getViewCountTitle(a.id, rq);                     
					    }
					  
					}
				myChart.on(echarts.config.EVENT.CLICK, eConsole);
	
				// 为echarts对象加载数据
				myChart.setOption(option);
				
				//默认调用最近一个监测周期内的数据
				cpoi.getViewCountTitle(a.id, categoriesArr[categoriesArr.length-1]);
		    });
		    
	});
	
	$(".viewCountArea").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
	    $(".show_1:eq(3)").show();
		$(".show_1:eq(7)").show();
		$(".show_1:eq(10)").show();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
			// 基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById('container'));
		    $.getJSON('thirdLevel',{dataType:'viewCountArea', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		    	var htmlHint = getHint(a.id) + ">>"+ "参与程度" + ">>" + "累积浏览量"
				$("#hint_1").empty();
		    	
				var arr = $.parseJSON(responseText.data),
					wb_arr = $.parseJSON(responseText.wb_data),
					lt_arr = $.parseJSON(responseText.lt_data),
					categoriesArr = $.parseJSON(responseText.categories);
	
					var _arr = [];
					for(var i=0; i<arr.length; i++) {
						_arr[i] = arr[i] - wb_arr[i];
					}
					
					var option = {
						title : {
							text : getHint(a.id) + '累积浏览量变化趋势',
							subtext : '发布周期' + startRq + '到' + endRq,
							x:'center'
						},
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : ['累积新闻浏览量','累积论坛浏览量'],
							x:'center',
							y:'bottom'
						},
						toolbox : {
							show : true,
							feature : {
								dataView : {
									show : false,
									readOnly : true,
									optionToContent : function() {
										var cp_name = option.series[0].name,
											cp_categoriesArr = option.xAxis[0].data,
											cp_dataArr = option.series[0].data,
											cp_hint = getHint(a.id);
							        	
							            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
							        }
								},
								magicType : {
									show : true,
									type : ['line', 'bar']
								},
								restore : {
									show : true
								},
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : true,
						xAxis : [{
									type : 'category',
									boundaryGap : false,
									data : categoriesArr
								}],
						yAxis : [{
									type : 'value',
									name : '浏览量',
									max : Math.max.apply(Math, arr) + 100,
									axisLabel : {
										formatter : '{value}'
									}
								}],
						series : [
								{
									name : '累积新闻浏览量',
									type : 'line',
									data : _arr,
									smooth:true,
									itemStyle: {normal: {areaStyle: {type: 'default'}}}
								},{
									name : '累积论坛浏览量',
									type : 'line',
									data : lt_arr,
									smooth:true,
									itemStyle: {normal: {areaStyle: {type: 'default'}}}
								}]
					};
	
					// 为echarts对象加载数据
					myChart.setOption(option);
		    });
		    
		    cpoi.getViewCountAreaTitle(a.id, startRq, endRq, true);
		    
	        var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
			// 过渡---------------------
			containerColumnAllChart.showLoading({
			    text: '正在努力的读取数据中...',   //loading话术
			    effect : 'ring'
			});
			
		    $.getJSON('table',{dataType:'viewOfSite_cls', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
				var pieValue = [],
					pieData = {
						tradition : [{
							name : '平面媒体',
							value : 0
						}],
						general : [{
							name : '新闻',
							value : 0
						}],
						social : [{
							name : '论坛',
							value : 0
						},{
							name : '博客',
							value : 0
						},{
							name : '社交媒体',
							value : 0
						},{
							name : '微信',
							value : 0
						},{
							name : '微博',
							value : 0
						}],
						getTradition : function() {
					         //传统pie
					         var tradition = this.tradition,
					          	 tra_value = 0;
					         for(var i=0; i<tradition.length; i++) {
					         	tra_value += tradition[i].value;
					         }
					         return tra_value;
						},
						getGeneral : function() {
					         //一般pie
					         var general = this.general,
					          	 gen_value = 0;
					         for(var i=0; i<general.length; i++) {
					         	gen_value += general[i].value;
					         }
					         return gen_value;
						},
						getSocial : function() {
					         //社会化pie
					         var social = this.social,
					          	 soc_value = 0;
					         for(var i=0; i<social.length; i++) {
					         	soc_value += social[i].value;
					         }
					         return soc_value;
						}
					};
				
				$.each(responseText,function(n,value) {  
			    	value = $.parseJSON(value);
			    	if(value.id === 1) {
			    		pieData.social[0].value = value.count;
			    	}else if(value.id === 2) {
			    		pieData.general[0].value = value.count;
			    	}else if(value.id === 3) {
			    		pieData.social[1].value = value.count;
			    	}else if(value.id === 4) {
			    		pieData.social[2].value = value.count;
			    	//此处忽略视频
			    	}else if(value.id === 6) {
			    		pieData.tradition[0].value = value.count;
			    	}else if(value.id === 7) {
			    		pieData.social[3].value = value.count;
			    	}else if(value.id === 8) {
			    		pieData.social[4].value = value.count;
			    	}
					
		         });  
		         
		         pieValue.push({
							name: '传统媒体',
							value : pieData.getTradition()
					});
					
		         pieValue.push({
							name: '一般媒体',
							value : pieData.getGeneral()
					});
				
		         pieValue.push({
							name: '社会化媒体',
							value : pieData.getSocial()
					});
				
		    	var option = {
					    title : {
					        text: getHint(a.id) + '媒体类型对比图',
					        subtext: '发布周期' + startRq + '到' + endRq,
					        x:'center'
					    },
					   	tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						 },
					    legend: {
					        x : 'center',
					        y : 'bottom',
					        data:['浏览量']
					    },
					    toolbox: {
					        show : true,
					        orient:'vertical',
					        feature : {
					            dataView : {show: false, readOnly: true},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    series : [
						        {
						            name:'浏览量',
						            type:'pie',
						            startAngle : 45,
						            minAngle:'10',
						            radius : '55%',
						            selectedOffset:10,
						            selectedMode:'single',
						            center: ['50%', '50%'],
						            data:pieValue,
						            itemStyle: {
									    normal: {
									       label : {
						                        position : 'outer',
						                        formatter : function (a,b,c,d) {return b + ': ' + (d - 0).toFixed(2) + '%'}
						                    },
						                    labelLine : {
						                        show : true
						                    }
									    }
									}
						        }
						    	 ]
					};
					
				function eConsole(param) {
				    var mes = '';
				    if (typeof param.seriesIndex != 'undefined') {
				        mes += '  seriesIndex : ' + param.seriesIndex;
				        mes += '  dataIndex : ' + param.dataIndex;
				    }
				    if (param.type == 'click') {
				    	var pie_type = param.name;
				    	if(pie_type === '社会化媒体') {
				    		site_clsEye(a.id,'浏览量',pie_type,pieData.social)
				    	}else if(pie_type === '一般媒体') {
				    		site_clsEye(a.id,'浏览量',pie_type,pieData.general)
				    	}else if(pie_type === '传统媒体') {
				    		site_clsEye(a.id,'浏览量',pie_type,pieData.tradition)
				    	}
				    }
				  
			}
			
			//默认显示社会化的
			site_clsEye(a.id,'浏览量','社会化媒体',pieData.social);
			containerColumnAllChart.on(echarts.config.EVENT.CLICK, eConsole);
			containerColumnAllChart.setOption(option,true);
			containerColumnAllChart.hideLoading();
	        });
		    
	});
	
	$(".reprintCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:eq(1)").hide();
		$(".show_1:gt(2)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
			// 基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById('container'));
		    $.getJSON('thirdLevel',{dataType:'reprintCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		    	var htmlHint = getHint(a.id) + ">>"+ "参与程度" + ">>" + "新增转载量"
				$("#hint_1").empty();
		    	
				var arr = $.parseJSON(responseText.data),
					wb_arr = $.parseJSON(responseText.wb_data),
					lt_arr = $.parseJSON(responseText.lt_data),
					categoriesArr = $.parseJSON(responseText.categories);
	
					var _arr = [];
					for(var i=0; i<arr.length; i++) {
						_arr[i] = arr[i] - wb_arr[i];
					}
					
					var option = {
						title : {
							text : getHint(a.id) + '新增转载量变化趋势',
							subtext : '发布周期' + startRq + '到' + endRq,
							x:'center'
						},
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : ['总新增转载量','新增微博转载量','新增新闻转载量','新增论坛转载量'],
							x:'center',
							y:'bottom'
						},
						toolbox : {
							show : true,
							feature : {
								dataView : {
									show : false,
									readOnly : true,
									optionToContent : function() {
										var cp_name = option.series[0].name,
											cp_categoriesArr = option.xAxis[0].data,
											cp_dataArr = option.series[0].data,
											cp_hint = getHint(a.id);
							        	
							            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
							        }
								},
								magicType : {
									show : true,
									type : ['line', 'bar']
								},
								restore : {
									show : true
								},
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : true,
						xAxis : [{
									type : 'category',
									boundaryGap : false,
									data : categoriesArr
								}],
						yAxis : [{
									type : 'value',
									name : '转载量',
									max : Math.max.apply(Math, arr) + 100,
									axisLabel : {
										formatter : '{value}'
									}
								}],
						series : [{
								name : '总新增转载量',
								type : 'line',
								data : arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							},
							{
								name : '新增微博转载量',
								type : 'line',
								data : wb_arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							},
							{
								name : '新增新闻转载量',
								type : 'line',
								data : _arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							},
							{
								name : '新增论坛转载量',
								type : 'line',
								data : lt_arr,
								markPoint : {
									data : [{
												type : 'max',
												name : '最大值'
											}, {
												type : 'min',
												name : '最小值'
											}]
								}
							}]
					};
	
					function eConsole(param) {
					    var mes = '';
					    if (typeof param.seriesIndex != 'undefined') {
					        mes += '  seriesIndex : ' + param.seriesIndex;
					        mes += '  dataIndex : ' + param.dataIndex;
					    }
					    if (param.type == 'click') {
					    	var rq = param.name;
					        cpoi.getReprintCountTitle(a.id, rq);                   
					    }
					  
					}
					myChart.on(echarts.config.EVENT.CLICK, eConsole);
					// 为echarts对象加载数据
					myChart.setOption(option);
					
					//默认调用
					cpoi.getReprintCountTitle(a.id, categoriesArr[categoriesArr.length-1]);
		    });
		    
	});
	
	$(".reprintCountArea").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
	    $(".show_1:eq(3)").show();
	    $(".show_1:eq(4)").show();
	    $(".show_1:eq(7)").show();
	    $(".show_1:eq(10)").show();
		clearTitle();
	    
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('thirdLevel',{dataType:'reprintCountArea', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "参与程度" + ">>" + "累积转载量"
			$("#hint_1").empty();
	    	
			var arr = $.parseJSON(responseText.data),
				wb_arr = $.parseJSON(responseText.wb_data),
				lt_arr = $.parseJSON(responseText.lt_data),
				categoriesArr = $.parseJSON(responseText.categories);

				var _arr = [];
				for(var i=0; i<arr.length; i++) {
					_arr[i] = arr[i] - wb_arr[i];
				}
				
				var option = {
					title : {
						text : getHint(a.id) + '累积转载量变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : ['总累积转载量','累积微博转载量','累积新闻转载量','累积论坛转载量'],
						x:'center',
						y:'bottom'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
										var cp_name = option.series[0].name,
											cp_categoriesArr = option.xAxis[0].data,
											cp_dataArr = option.series[0].data,
											cp_hint = getHint(a.id);
							        	
							            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
							        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name : '转载量',
								max : Math.max.apply(Math, arr) + 100,
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
								name : '总累积转载量',
								type : 'line',
								data : arr,
								smooth:true,
								itemStyle: {normal: {areaStyle: {type: 'default'}}}
							},
							{
								name : '累积微博转载量',
								type : 'line',
								data : wb_arr,
								smooth:true,
								itemStyle: {normal: {areaStyle: {type: 'default'}}}
							},
							{
								name : '累积新闻转载量',
								type : 'line',
								data : _arr,
								smooth:true,
								itemStyle: {normal: {areaStyle: {type: 'default'}}}
							},
							{
								name : '累积论坛转载量',
								type : 'line',
								data : lt_arr,
								smooth:true,
								itemStyle: {normal: {areaStyle: {type: 'default'}}}
							}]
				};

				// 为echarts对象加载数据
				myChart.setOption(option);
	    });
	    
	    cpoi.getReprintCountAreaTitle(a.id, startRq, endRq);
	    
	    $.getJSON('table',{dataType:'rtCountAreaTop10', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var tbody = "<table class='tftable'><tr><th colspan=\"4\">发布周期内（"+startRq+"到"+endRq+"）累积转载量top10的微博</th></tr><tr><th>行业名称</th><th>微博作者</th><th>微博原文</th><th>转载量</th></tr>";
	    	$.each(responseText,function(n,value) {  
	    		value = $.parseJSON(value);
	       		 var trs = "";  
	       		trs += "<tr><td>" + getHint(value.userId) +"</td><td>" + value.user+ is_v(value.user_verified) +"</td><td><a  title='微博作者："+ value.user +"' target='_blank' title="+ getHint(value.user_id) +" href='"+ getWeiBoBy(value.wid,value.uid,value.sour) +"'>" +interceptStr(value.text) +"</a></td> <td>" + value.rt +"</td> </tr>"; 
	              tbody += trs;         
	            });
	    	
	    	$("#containerOrg").html(tbody + "</table>");
	    });
	    
	    
	     var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
		// 过渡---------------------
		containerColumnAllChart.showLoading({
		    text: '正在努力的读取数据中...',   //loading话术
		    effect : 'ring'
		});
		
	    $.getJSON('table',{dataType:'reprintOfSite_cls', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
			var pieValue = [],
				pieData = {
					tradition : [{
						name : '平面媒体',
						value : 0
					}],
					general : [{
						name : '新闻',
						value : 0
					}],
					social : [{
						name : '论坛',
						value : 0
					},{
						name : '博客',
						value : 0
					},{
						name : '社交媒体',
						value : 0
					},{
						name : '微信',
						value : 0
					},{
						name : '微博',
						value : 0
					}],
					getTradition : function() {
				         //传统pie
				         var tradition = this.tradition,
				          	 tra_value = 0;
				         for(var i=0; i<tradition.length; i++) {
				         	tra_value += tradition[i].value;
				         }
				         return tra_value;
					},
					getGeneral : function() {
				         //一般pie
				         var general = this.general,
				          	 gen_value = 0;
				         for(var i=0; i<general.length; i++) {
				         	gen_value += general[i].value;
				         }
				         return gen_value;
					},
					getSocial : function() {
				         //社会化pie
				         var social = this.social,
				          	 soc_value = 0;
				         for(var i=0; i<social.length; i++) {
				         	soc_value += social[i].value;
				         }
				         return soc_value;
					}
				};
			
			$.each(responseText,function(n,value) {  
		    	value = $.parseJSON(value);
		    	if(value.id === 1) {
		    		pieData.social[0].value = value.count;
		    	}else if(value.id === 2) {
		    		pieData.general[0].value = value.count;
		    	}else if(value.id === 3) {
		    		pieData.social[1].value = value.count;
		    	}else if(value.id === 4) {
		    		pieData.social[2].value = value.count;
		    	//此处忽略视频
		    	}else if(value.id === 6) {
		    		pieData.tradition[0].value = value.count;
		    	}else if(value.id === 7) {
		    		pieData.social[3].value = value.count;
		    	}else if(value.id === 8) {
		    		pieData.social[4].value = value.count;
		    	}
				
	         });  
	         
	         pieValue.push({
						name: '传统媒体',
						value : pieData.getTradition()
				});
				
	         pieValue.push({
						name: '一般媒体',
						value : pieData.getGeneral()
				});
			
	         pieValue.push({
						name: '社会化媒体',
						value : pieData.getSocial()
				});
			
	    	var option = {
				    title : {
				        text: getHint(a.id) + '媒体类型对比图',
				        subtext: '发布周期' + startRq + '到' + endRq,
				        x:'center'
				    },
				   	tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					 },
				    legend: {
				        x : 'center',
				        y : 'bottom',
				        data:['转载量']
				    },
				    toolbox: {
				        show : true,
				        orient:'vertical',
				        feature : {
				            dataView : {show: false, readOnly: true},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    series : [
					        {
					            name:'转载量',
					            type:'pie',
					            minAngle:'10',
					            startAngle : 45,
					            radius : '55%',
					            selectedOffset:10,
					            selectedMode:'single',
					            center: ['50%', '50%'],
					            data:pieValue,
					            itemStyle: {
								    normal: {
								       label : {
					                        position : 'outer',
					                        formatter : function (a,b,c,d) {return b + ': ' + (d - 0).toFixed(2) + '%'}
					                    },
					                    labelLine : {
					                        show : true
					                    }
								    }
								}
					        }
					    	 ]
				};
				
				function eConsole(param) {
				    var mes = '';
				    if (typeof param.seriesIndex != 'undefined') {
				        mes += '  seriesIndex : ' + param.seriesIndex;
				        mes += '  dataIndex : ' + param.dataIndex;
				    }
				    if (param.type == 'click') {
				    	var pie_type = param.name;
				    	if(pie_type === '社会化媒体') {
				    		site_clsEye(a.id,'转载量',pie_type,pieData.social)
				    	}else if(pie_type === '一般媒体') {
				    		site_clsEye(a.id,'转载量',pie_type,pieData.general)
				    	}else if(pie_type === '传统媒体') {
				    		site_clsEye(a.id,'转载量',pie_type,pieData.tradition)
				    	}
				    }
				  
			}
			
			//默认显示社会化的
			site_clsEye(a.id,'转载量','社会化媒体',pieData.social);
			
			containerColumnAllChart.on(echarts.config.EVENT.CLICK, eConsole);
			containerColumnAllChart.setOption(option,true);
			containerColumnAllChart.hideLoading();
        });
	    
	});
	
	$(".replyCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:eq(1)").hide();
		$(".show_1:gt(2)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('thirdLevel',{dataType:'replyCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "参与程度" + ">>" + "新增评论量";
			$("#hint_1").empty();
	    	
			var arr = $.parseJSON(responseText.data),
				wb_arr = $.parseJSON(responseText.wb_data),
				lt_arr = $.parseJSON(responseText.lt_data),
				categoriesArr = $.parseJSON(responseText.categories);

			var _arr = [];
			for(var i=0; i<arr.length; i++) {
				_arr[i] = arr[i] - wb_arr[i];
			}
			
			var option = {
				title : {
					text : getHint(a.id) + '新增评论量变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : ['总新增评论量','新增微博评论量','新增新闻评论量','新增论坛评论量'],
					x:'center',
					y:'bottom'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.xAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '评论量',
							max : Math.max.apply(Math, arr) + 100,
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
						name : '总新增评论量',
						type : 'line',
						data : arr,
						markPoint : {
							data : [{
										type : 'max',
										name : '最大值'
									}, {
										type : 'min',
										name : '最小值'
									}]
						}
					},
					{
						name : '新增微博评论量',
						type : 'line',
						data : wb_arr,
						markPoint : {
							data : [{
										type : 'max',
										name : '最大值'
									}, {
										type : 'min',
										name : '最小值'
									}]
						}
					},
					{
						name : '新增新闻评论量',
						type : 'line',
						data : _arr,
						markPoint : {
							data : [{
										type : 'max',
										name : '最大值'
									}, {
										type : 'min',
										name : '最小值'
									}]
						}
					},
					{
						name : '新增论坛评论量',
						type : 'line',
						data : lt_arr,
						markPoint : {
							data : [{
										type : 'max',
										name : '最大值'
									}, {
										type : 'min',
										name : '最小值'
									}]
						}
					}]
			};

			function eConsole(param) {
					    var mes = '';
					    if (typeof param.seriesIndex != 'undefined') {
					        mes += '  seriesIndex : ' + param.seriesIndex;
					        mes += '  dataIndex : ' + param.dataIndex;
					    }
					    if (param.type == 'click') {
					    	var rq = param.name;
					        cpoi.getRelpyCountTitle(a.id, rq);
					    }
					  
					}
			myChart.on(echarts.config.EVENT.CLICK, eConsole);
			// 为echarts对象加载数据
			myChart.setOption(option);
			
			cpoi.getRelpyCountTitle(a.id, categoriesArr[categoriesArr.length-1]);
	    });
	    
	});
	
	$(".replyCountArea").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
	    $(".show_1:eq(3)").show();
	    $(".show_1:eq(4)").show();
	    $(".show_1:eq(7)").show();
	    $(".show_1:eq(10)").show();
	    $(".show_1:eq(12)").show();
	    clearTitle();
	    
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('thirdLevel',{dataType:'replyCountArea', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "参与程度" + ">>" + "累积评论量"
			$("#hint_1").empty();
	    	
	    	var arr = $.parseJSON(responseText.data),
				wb_arr = $.parseJSON(responseText.wb_data),
				lt_arr = $.parseJSON(responseText.lt_data),
				categoriesArr = $.parseJSON(responseText.categories);

				var _arr = [];
				for(var i=0; i<arr.length; i++) {
					_arr[i] = arr[i] - wb_arr[i];
				}
				var option = {
					title : {
						text : getHint(a.id) + '累积评论量变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : ['总累积评论量','累积微博评论量','累积新闻评论量','累积论坛评论量'],
						x:'center',
						y:'bottom'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	
						            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name : '评论量',
								max : Math.max.apply(Math, arr) + 100,
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
								name : '总累积评论量',
								type : 'line',
								data : arr,
								smooth:true,
								itemStyle: {normal: {areaStyle: {type: 'default'}}}
							},
							{
								name : '累积微博评论量',
								type : 'line',
								data : wb_arr,
								smooth:true,
								itemStyle: {normal: {areaStyle: {type: 'default'}}}
							},
							{
								name : '累积新闻评论量',
								type : 'line',
								data : _arr,
								smooth:true,
								itemStyle: {normal: {areaStyle: {type: 'default'}}}
							},
							{
								name : '累积论坛评论量',
								type : 'line',
								data : lt_arr,
								smooth:true,
								itemStyle: {normal: {areaStyle: {type: 'default'}}}
							}]
				};

				// 为echarts对象加载数据
				myChart.setOption(option);
	    });
	    
	    cpoi.getReplyCountAreaTitle(a.id, startRq, endRq);
	    
	    $.getJSON('table',{dataType:'commCountTop10', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var tbody = "<table class='tftable'><tr><th colspan=\"4\">发布周期内（"+startRq+"到"+endRq+"）累积评论量top10的微博</th></tr><tr><th>行业名称</th><th>微博作者</th><th>微博原文</th><th>评论量</th></tr>";
	    	$.each(responseText,function(n,value) {  
	    		value = $.parseJSON(value);
	       		 var trs = "";  
	       		trs += "<tr><td>" + getHint(value.userId) +"</td><td>" + value.user+ is_v(value.user_verified) +"</td><td><a  title='微博作者："+ value.user +"' target='_blank' href='"+ getWeiBoBy(value.wid,value.uid,value.sour) +"'>" +interceptStr(value.text) +"</a></td><td>" + value.comm +"</td> </tr>"; 
	              tbody += trs;         
	            });  
	    	
	    	$("#containerOrg").html(tbody + "</table>");
	    });
	    
    	var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
		// 过渡---------------------
		containerColumnAllChart.showLoading({
		    text: '正在努力的读取数据中...',   //loading话术
		    effect : 'ring'
		});
		
	    $.getJSON('table',{dataType:'replyOfSite_cls', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	
			var pieValue = [],
				pieData = {
					tradition : [{
						name : '平面媒体',
						value : 0
					}],
					general : [{
						name : '新闻',
						value : 0
					}],
					social : [{
						name : '论坛',
						value : 0
					},{
						name : '博客',
						value : 0
					},{
						name : '社交媒体',
						value : 0
					},{
						name : '微信',
						value : 0
					},{
						name : '微博',
						value : 0
					}],
					getTradition : function() {
				         //传统pie
				         var tradition = this.tradition,
				          	 tra_value = 0;
				         for(var i=0; i<tradition.length; i++) {
				         	tra_value += tradition[i].value;
				         }
				         return tra_value;
					},
					getGeneral : function() {
				         //一般pie
				         var general = this.general,
				          	 gen_value = 0;
				         for(var i=0; i<general.length; i++) {
				         	gen_value += general[i].value;
				         }
				         return gen_value;
					},
					getSocial : function() {
				         //社会化pie
				         var social = this.social,
				          	 soc_value = 0;
				         for(var i=0; i<social.length; i++) {
				         	soc_value += social[i].value;
				         }
				         return soc_value;
					}
				};
			
			$.each(responseText,function(n,value) {  
		    	value = $.parseJSON(value);
		    	if(value.id === 1) {
		    		pieData.social[0].value = value.count;
		    	}else if(value.id === 2) {
		    		pieData.general[0].value = value.count;
		    	}else if(value.id === 3) {
		    		pieData.social[1].value = value.count;
		    	}else if(value.id === 4) {
		    		pieData.social[2].value = value.count;
		    	//此处忽略视频
		    	}else if(value.id === 6) {
		    		pieData.tradition[0].value = value.count;
		    	}else if(value.id === 7) {
		    		pieData.social[3].value = value.count;
		    	}else if(value.id === 8) {
		    		pieData.social[4].value = value.count;
		    	}
				
	         });  
	         
	         pieValue.push({
						name: '传统媒体',
						value : pieData.getTradition()
				});
				
	         pieValue.push({
						name: '一般媒体',
						value : pieData.getGeneral()
				});
			
	         pieValue.push({
						name: '社会化媒体',
						value : pieData.getSocial()
				});
			
	    	var option = {
				    title : {
				        text: getHint(a.id) + '媒体类型对比图',
				        subtext: '发布周期' + startRq + '到' + endRq,
				         x:'center'
				    },
				    tooltip : {
				        trigger: 'item',
						formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    legend: {
				        x : 'center',
				        y : 'bottom',
				        data:['评论量']
				    },
				    toolbox: {
				        show : true,
				        orient:'vertical',
				        feature : {
				            dataView : {show: false, readOnly: true},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    series : [
						        {
						            name:'评论量',
						            type:'pie',
						            startAngle : 45,
						            minAngle:'10',
						            radius : '55%',
						            selectedOffset:10,
						            selectedMode:'single',
						            center: ['50%', '50%'],
						            data:pieValue,
						            itemStyle: {
									    normal: {
									       label : {
						                        position : 'outer',
						                        formatter : function (a,b,c,d) {return b + ': ' + (d - 0).toFixed(2) + '%'}
						                    },
						                    labelLine : {
						                        show : true
						                    }
									    }
									}
						        }
						    	 ]
				};
				function eConsole(param) {
				    var mes = '';
				    if (typeof param.seriesIndex != 'undefined') {
				        mes += '  seriesIndex : ' + param.seriesIndex;
				        mes += '  dataIndex : ' + param.dataIndex;
				    }
				    if (param.type == 'click') {
				    	var pie_type = param.name;
				    	if(pie_type === '社会化媒体') {
				    		site_clsEye(a.id,'评论量',pie_type,pieData.social)
				    	}else if(pie_type === '一般媒体') {
				    		site_clsEye(a.id,'评论量',pie_type,pieData.general)
				    	}else if(pie_type === '传统媒体') {
				    		site_clsEye(a.id,'评论量',pie_type,pieData.tradition)
				    	}
				    }
				  
			}
			//默认显示社会化的
			site_clsEye(a.id,'评论量','社会化媒体',pieData.social);
			
			containerColumnAllChart.on(echarts.config.EVENT.CLICK, eConsole);
			containerColumnAllChart.setOption(option,true);
			containerColumnAllChart.hideLoading();
        });
	    
	     // 基于准备好的dom，初始化echarts图表
		var containerTu2Chart = echarts.init(document.getElementById('containerTu2'));
		// 过渡---------------------
		containerTu2Chart.showLoading({
		    text: '正在努力的读取数据中...'    //loading话术
		});
		
	    $.getJSON('table',{dataType:'verifiedCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) +"加V和未加V比例图";
			$("#hint_2").empty();
	    	
			var categoriesValue = [],dataValue = [],pieValue = [];
			$.each(responseText,function(n,value) {  
				value = $.parseJSON(value);
				categoriesValue[n] = convertToStr(value.user_verified);
				dataValue[n] = value.v_count;
				
				var pieData = {
						name: "",
						value : 0
				};
				pieData.name = convertToStr(value.user_verified);
				pieData.value = value.v_count;
				pieValue[n] = pieData;
		    });  
			
				var option = {
						    title : {
						        text: getHint(a.id)+'微博作者认证数对比图',
						        subtext: '发布周期' + startRq + '到' + endRq,
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        x : 'center',
						        y : 'bottom',
						        data:['认证','未认证']
						    },
						    toolbox: {
						        show : true,
						        orient:'verticle',
						        feature : {
						            dataView : {show: false, readOnly: true},
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    calculable : true,
						    series : [
						        {
						            name:'发文微博作者总数',
						            type:'pie',
						            radius : '55%',
						            center: ['50%', '50%'],
						            data:pieValue
						        }
						    ]
						};
				// 为echarts对象加载数据
				containerTu2Chart.setOption(option);
				containerTu2Chart.hideLoading();
			});
	    
	});
	
	$(".minCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(3)").hide();
    	$(".show_1:eq(1)").hide();
    	$(".show_1:eq(5)").show();
    	$(".show_1:eq(7)").show();
    	$(".show_1:eq(8)").show();
    	$(".show_1:eq(9)").show();
		clearTitle();
		
		$("#selectTitleC").hide();
		$("#selectContainerApp").hide();
	    
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('thirdLevel',{dataType:'minCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "舆情观点" + ">>" + "敏感词"
			$("#hint_1").empty();
	    	
			var arr = $.parseJSON(responseText.data),
				wb_arr = $.parseJSON(responseText.wb_data),
				lt_arr = $.parseJSON(responseText.lt_data),
				categoriesArr = $.parseJSON(responseText.categories);

			var _arr = [];
			for(var i=0; i<arr.length; i++) {
				_arr[i] = arr[i] - wb_arr[i];
			}
			var option = {
				title : {
					text : getHint(a.id) + '敏感词数量变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : ['总敏感词数量','微博敏感词数量','新闻敏感词数量','论坛敏感词数量'],
					x:'center',
					y:'bottom'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.xAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '敏感词数量',
							max : Math.max.apply(Math, arr) + 500,
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
						name : '总敏感词数量',
						type : 'line',
						data : arr,
						markPoint : {
							data : [{
										type : 'max',
										name : '最大值'
									}, {
										type : 'min',
										name : '最小值'
									}]
						}
					},
					{
						name : '微博敏感词数量',
						type : 'line',
						data : wb_arr,
						markPoint : {
							data : [{
										type : 'max',
										name : '最大值'
									}, {
										type : 'min',
										name : '最小值'
									}]
						}
					},
					{
						name : '新闻敏感词数量',
						type : 'line',
						data : _arr,
						markPoint : {
							data : [{
										type : 'max',
										name : '最大值'
									}, {
										type : 'min',
										name : '最小值'
									}]
						}
					},
					{
						name : '论坛敏感词数量',
						type : 'line',
						data : lt_arr,
						markPoint : {
							data : [{
										type : 'max',
										name : '最大值'
									}, {
										type : 'min',
										name : '最小值'
									}]
						}
					}]
			};
			
			function eConsole(param) {
			    var mes = '';
			    if (typeof param.seriesIndex != 'undefined') {
			        mes += '  seriesIndex : ' + param.seriesIndex;
			        mes += '  dataIndex : ' + param.dataIndex;
			    }
			    if (param.type == 'click') {
			    	var rq = param.name;
			    	
                    getMinContTop5Rq(a.id, rq);
			    }
			  
			}
			myChart.on(echarts.config.EVENT.CLICK, eConsole);

			// 为echarts对象加载数据
			myChart.setOption(option);
			
			//默认值
			getMinContTop5Rq(a.id, categoriesArr[categoriesArr.length - 1]);
	    });
	    
	    function getMinContTop5Rq(id, rq) {
		    	$.getJSON('table',{dataType:'minContTop5Rq' ,userId:id, rq:rq}, function(responseText) {
			    	var tbody = "<table class='tftable'><tr><th colspan=\"5\">监测周期内（"+getCircle(rq,mCircle)+"）内出现敏感词最多的文章top10</th></tr><tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>敏感词数量</th></tr>";
			    	$.each(responseText,function(n,value) {  
			    		value = $.parseJSON(value);
			       		 var trs = "";  
			              trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.rq+"</td><td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>" +interceptStr(value.name)+"</a></td> <td>" + value.count +"</td></tr>";  
			              tbody += trs;         
			            });  
			    	
			    	$("#titleC").html(tbody + "</table>");
			    });
		    }
	    
	    // 基于准备好的dom，初始化echarts图表
		var containerTableChart = echarts.init(document.getElementById('containerTable'));
		containerTableChart.showLoading({
		    text: '正在努力的读取数据中...',  //loading话术
		    effect:'dynamicLine'
		});
	    $.getJSON('table',{dataType:'minCountTop5' ,userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	
	    	var categoriesValue = [],dataValue = [];
			$.each(responseText,function(n,value) {  
				value = $.parseJSON(value);
				categoriesValue[n] = value.name;
				dataValue[n] = value.count;
				
				//默认
				if(n===0) {
              		getMinCountForOne(value.name, a.id);
              		getMinTitle(value.name,a.id);
              	}
		    }); 
		    //翻转过来
		    categoriesValue.reverse();
		    dataValue.reverse();
		    
			var option = {
				title : {
					text : getHint(a.id) + '敏感词出现频率top10',
					subtext : '发布周期'+startRq+"到"+endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'item'
				},
				legend : {
					data : [],
					x:'center',
					y:'bottom'
				},
				toolbox : {
					show : true,
					orient : 'verticle',
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.xAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : true,
							data : categoriesValue,
							axisLabel:{
									interval:0,
									rotate : -45
								}
						}],
				yAxis : [{
							type : 'value',
							name : '次数'
						}],
				series : [{
							name : '出现次数',
							type : 'bar',
							barWidth : 20,
							data : dataValue
						}]
			};
			
		function eConsole(param) {
			    var mes = '';
			    if (typeof param.seriesIndex != 'undefined') {
			        mes += '  seriesIndex : ' + param.seriesIndex;
			        mes += '  dataIndex : ' + param.dataIndex;
			    }
			    if (param.type == 'click') {
			    	var name = param.name;
			    	
                   getMinCountForOne(name, a.id);
                   getMinTitle(name,a.id);
			    }//end if
			  
			}//end Console
			containerTableChart.on(echarts.config.EVENT.CLICK, eConsole);
			containerTableChart.setOption(option);
			containerTableChart.hideLoading();
	    });
	    
	    $.getJSON('table',{dataType:'minContTop5' ,userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var tbody = "<table class='tftable'><tr><th colspan=\"5\">发布周期内（"+startRq+"到"+endRq+"）出现敏感词最多的文章top10</th></tr><tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题</th><th>敏感词数量</th></tr>";
	    	$.each(responseText,function(n,value) {  
	    		value = $.parseJSON(value);
	       		 var trs = "";  
	              trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.rq+"</td><td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>" +interceptStr(value.name)+"</a></td> <td>" + value.count +"</td></tr>";  
	              tbody += trs;         
	            });  
	    	
	    	$("#containerApp").html(tbody + "</table>");
	    });
	    
	});
	
	$(".sidesCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(3)").hide();
    	$(".show_1:eq(5)").show();
    	$(".show_1:eq(7)").show();
    	$(".show_1:eq(8)").show();
    	$(".show_1:eq(9)").show();
    	$(".show_1:eq(10)").show();
		clearTitle();
    	
		$("#selectTitleC").hide();
		$("#selectContainerApp").hide();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var pointChart = echarts.init(document.getElementById('point'));
	    $.getJSON('thirdLevel',{dataType:'side_wrong_count', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "舆情观点" + ">>" + "负面词";
			$("#hint_1").empty();
	    	
	    	var arr = $.parseJSON(responseText.data),
				wb_arr = $.parseJSON(responseText.wb_data),
				lt_arr = $.parseJSON(responseText.lt_data),
				categoriesArr = $.parseJSON(responseText.categories);

				var _arr = [];
				for(var i=0; i<arr.length; i++) {
					_arr[i] = arr[i] - wb_arr[i];
				}
				var option = {
					title : {
						text : getHint(a.id) + '负面词数量变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : ['总负面词数量','微博负面词数量','新闻负面词数量','论坛负面词数量'],
						x:'center',
						y:'bottom'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	
						            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name : '负面词数量',
								max : Math.max.apply(Math, arr) + 100,
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
							name : '总负面词数量',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						},
						{
							name : '微博负面词数量',
							type : 'line',
							data : wb_arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						},
						{
							name : '新闻负面词数量',
							type : 'line',
							data : _arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						},
						{
							name : '论坛负面词数量',
							type : 'line',
							data : lt_arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
				};

				function eConsole(param) {
				    var mes = '';
				    if (typeof param.seriesIndex != 'undefined') {
				        mes += '  seriesIndex : ' + param.seriesIndex;
				        mes += '  dataIndex : ' + param.dataIndex;
				    }
				    if (param.type == 'click') {
				    	var rq = param.name;
				                             
	                    getSidesContTop5Rq(a.id, rq);
				    }
				  
				}
				pointChart.on(echarts.config.EVENT.CLICK, eConsole);
				// 为echarts对象加载数据
				pointChart.setOption(option);
		        
				
				//默认调用最后一个监测周期的数据
				getSidesContTop5Rq(a.id,categoriesArr[categoriesArr.length-1])
		    });
		    
		    function getSidesContTop5Rq(id, rq) {
		    	$.getJSON('table',{dataType:'sidesContTop5Rq', userId:id, rq:rq}, function(responseText) {
					var tbody = "<table class='tftable'><tr><th colspan=\"5\">监测周期\("+getCircle(rq,mCircle)+"\)内出现负面词最多的文章top10</th></tr><tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题  </th><th>&nbsp; 负面词数量</th></tr>";
					$.each(responseText,function(n,value) {  
							value = $.parseJSON(value);
				   		 	var trs = "";  
				          	trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>" +interceptStr(value.title)+"</a></td> <td>" + value.side_wrong_count +"</td> </tr>";  
				          	tbody += trs; 
				        });  
					
					$("#titleC").html(tbody + "</table>");
				});
		    }
		
		// 基于准备好的dom，初始化echarts图表
		var containerChart = echarts.init(document.getElementById('container'));
	    $.getJSON('thirdLevel',{dataType:'sidesCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "舆情观点" + ">>" + "负面词";
			$("#hint_1").empty();
	    	
	    	var arr = $.parseJSON(responseText.data),
				wb_arr = $.parseJSON(responseText.wb_data),
				lt_arr = $.parseJSON(responseText.lt_data),
				categoriesArr = $.parseJSON(responseText.categories);

				var _arr = [];
				for(var i=0; i<arr.length; i++) {
					_arr[i] = arr[i] - wb_arr[i];
				}
				var option = {
					title : {
						text : getHint(a.id) + '负面观点量变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'axis'
					},
					legend : {
						data : ['总负面观点量','微博负面观点量','新闻负面观点量','论坛负面观点量'],
						x:'center',
						y:'bottom'
					},
					toolbox : {
						show : true,
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	
						            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : false,
								data : categoriesArr
							}],
					yAxis : [{
								type : 'value',
								name : '观点量',
								max : Math.max.apply(Math, arr) + 100,
								axisLabel : {
									formatter : '{value}'
								}
							}],
					series : [{
							name : '总负面观点量',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						},
						{
							name : '微博负面观点量',
							type : 'line',
							data : wb_arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						},
						{
							name : '新闻负面观点量',
							type : 'line',
							data : _arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						},
						{
							name : '论坛负面观点量',
							type : 'line',
							data : lt_arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
				};

				// 为echarts对象加载数据
				containerChart.setOption(option);
		        
		    });
		    
		    // 基于准备好的dom，初始化echarts图表
			var containerTableChart = echarts.init(document.getElementById('containerTable'));
			containerTableChart.showLoading({
			    text: '正在努力的读取数据中...',  //loading话术
			    effect:'dynamicLine'
			});
			
		    $.getJSON('table',{dataType:'sidesCountTop5', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		    	
		    	var categoriesValue = [],dataValue = [];
				$.each(responseText,function(n,value) {  
					value = $.parseJSON(value);
					categoriesValue[n] = value.name;
					dataValue[n] = value.count;
					
					//默认
					if(n===0) {
	              		getSidesCountForOne(value.name,a.id);
	              		getSidesTitle(value.name,a.id);
	              	}
			    }); 
			    //翻转过来
			    categoriesValue.reverse();
			    dataValue.reverse();
			    
				var option = {
					title : {
						text : getHint(a.id) + '负面词出现频率top10',
						subtext : '发布周期'+startRq+"到"+endRq,
						x:'center'
					},
					tooltip : {
						trigger : 'item'
					},
					legend : {
						data : [],
						x:'center',
						y:'bottom'
					},
					toolbox : {
						show : true,
						orient : 'verticle',
						feature : {
							dataView : {
								show : false,
								readOnly : true,
								optionToContent : function() {
									var cp_name = option.series[0].name,
										cp_categoriesArr = option.xAxis[0].data,
										cp_dataArr = option.series[0].data,
										cp_hint = getHint(a.id);
						        	
						            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
						        }
							},
							magicType : {
								show : true,
								type : ['line', 'bar']
							},
							restore : {
								show : true
							},
							saveAsImage : {
								show : true
							}
						}
					},
					calculable : true,
					xAxis : [{
								type : 'category',
								boundaryGap : true,
								data : categoriesValue
							}],
					yAxis : [{
								type : 'value',
								name : '次数'
							}],
					series : [{
								name : '出现次数',
								type : 'bar',
								barWidth : 20,
								data : dataValue
							}]
				};
				
			function eConsole(param) {
				    var mes = '';
				    if (typeof param.seriesIndex != 'undefined') {
				        mes += '  seriesIndex : ' + param.seriesIndex;
				        mes += '  dataIndex : ' + param.dataIndex;
				    }
				    if (param.type == 'click') {
				    	var name = param.name;
				    
				    	//负面词变化趋势
	                   getSidesCountForOne(name,a.id);
	                   //某一负面词发布周期内对应的文章列表
	                   getSidesTitle(name,a.id);
				    }//end if
				  
				}//end Console
				containerTableChart.on(echarts.config.EVENT.CLICK, eConsole);
				containerTableChart.setOption(option);
				containerTableChart.hideLoading();
			});
		    
		    $.getJSON('table',{dataType:'sidesContTop5', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
				var tbody = "<table class='tftable'><tr><th colspan=\"5\">发布周期\("+startRq+"到"+endRq+"\)内出现负面词最多的文章top10</th></tr><tr><th>行业名称</th><th>来源网站</th><th>发布时间</th><th>文章标题  </th><th>&nbsp; 负面词数量</th></tr>";
				$.each(responseText,function(n,value) {  
					value = $.parseJSON(value);
			   		 var trs = "";  
			          trs += "<tr><td>" +getHint(value.user_id)+"</td><td>" +value.site_name+"</td><td>" +value.pubdate+"</td><td><a target='_blank' title="+ getHint(value.user_id) +"  href='"+ goToHoutai(value.user_id,value.url,value.id,value.uuid) +"'>" +interceptStr(value.title)+"</a></td> <td>" + value.side_wrong_count +"</td> </tr>";  
			          tbody += trs; 
			        });  
				
			    $("#containerApp").html(tbody + "</table>");
			});
		    
			 // 基于准备好的dom，初始化echarts图表
			var containerColumnChart = echarts.init(document.getElementById('containerColumn'));
			// 过渡---------------------
			containerColumnChart.showLoading({
			    text: '正在努力的读取数据中...',    //loading话术
			    effect : 'ring'
			});
			
		    $.getJSON('thirdLevel',{dataType:'sidesCountColumn', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		    	var htmlHint = getHint(a.id) + ""
				$("#hint_3").empty();
		    	
		    	var categoriesValue = [],dataValue = [],pieValue = [];
				$.each(responseText,function(n,value) {  
					value = $.parseJSON(value);
					categoriesValue[n] = value.name;
					dataValue[n] = value.count;
					
					var pieData = {
							name: value.name,
							value : value.count
					};
					pieValue[n] = pieData;
			    });  
				
			    var option = {
						    title : {
						        text: getHint(a.id)+'正负面观点对比图',
						        subtext: '发布周期' + startRq + '到' + endRq,
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        x : 'center',
						        y : 'bottom',
						        data:['正面','负面','中立']
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            dataView : {show: false, readOnly: true},
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    calculable : true,
						    series : [
						        {
						            name:'观点量',
						            type:'pie',
						            radius : '55%',
						            minAngle:25,
						            center: ['50%', '60%'],
						            data:pieValue,
						            itemStyle: {
									    normal: {
									       label : {
						                        position : 'inner',
						                        formatter : function (a,b,c,d) {return b + ":" + (d - 0).toFixed(2) + '%'}
						                    },
						                    labelLine : {
						                        show : false
						                    }
									    }
									}
						        }
						    ]
						};
	
					// 为echarts对象加载数据
					containerColumnChart.setOption(option);
					containerColumnChart.hideLoading();
					 
		    });
		    
	});
	
	$(".officialCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
	 	$(".show_1:gt(0)").hide();
	    $(".show_1:eq(7)").show();
	    clearTitle();
	    
			var a = e.currentTarget;
			latelyClick.name = a.id;
			latelyClick.password = $(this).attr("class");
			
			// 基于准备好的dom，初始化echarts图表
			var containerChart = echarts.init(document.getElementById('container'));
		    $.getJSON('thirdLevel',{dataType:'officialCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		    	var htmlHint = getHint(a.id) + ">>"+ "网站特点" + ">>" + "网站权威性"
				$("#hint_1").empty();
		    	var htmlHint2 = getHint(a.id) + "网站权威性饼图"
				$("#hint_2").empty();
		    	
		    	var categoriesValue = [],dataOfficialValue = [],dataNonOfficialValue = [],pieValue = [];
		    	var officialT = 0,nonOfficialT = 0;
		    	var timeCategoriesValue = [],timeData = [];
				$.each(responseText,function(n,value) {  
					value = $.parseJSON(value);
					categoriesValue[n] = value.rq;
					dataOfficialValue[n] = value.official;
					dataNonOfficialValue[n] = value.non_official;
					
					officialT += value.official;
					nonOfficialT += value.non_official;
					
					//百搭时间轴数据
					timeCategoriesValue[n] = { name:value.rq, symbol:'emptyStar6', symbolSize:8 };
					timeData[n] = {
									title : {'text': '监测周期'+getCircle(value.rq,mCircle)+'网站权威性占比变化'},
						            series : [
						                {
						                    name:'网站权威性',
						                    type:'pie',
						                    data:[
						                        {value: value.official,  name:'官方网站'},
						                        {value: value.non_official,  name:'非官方网站'}
						                    ]
						                }
						            ]
						        }
			    });
				
				pieValue = [{
					name: "官方网站",
					value : officialT
				},{
					name: "非官方网站",
					value : nonOfficialT
				}];
				
				
					var option = {
						title : {
							text : getHint(a.id) + '官方与非官方网站数量变化趋势',
							subtext : '发布周期' + startRq + '到' + endRq,
							x:'center'
						},
						tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['官方网站','非官方网站'],
					        orient:'horizontal',
					        x : 'center',
					        y : 'bottom'
					    },
					    toolbox: {
					        show : true,
					        orient:'vertical',
					        feature : {
					            dataView : {show: false, readOnly: true},
					            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            boundaryGap : false,
					            data : categoriesValue
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value',
					            name : '网站数量'
					        }
					    ],
					    series : [
					        {
					            name:'官方网站',
					            type:'line',
					            tiled: '总量',
					            data:dataOfficialValue
					        },
					        {
					            name:'非官方网站',
					            type:'line',
					            tiled: '总量',
					            data:dataNonOfficialValue
					        },
					        {
				            name:'发布周期内',
				            type:'pie',
				            tooltip : {
				                trigger: 'item',
				                formatter: '{a} <br/>{b} : {c} ({d}%)'
				            },
				            center: ['80%', '30%'],
				            radius : [0, 50],
				            minAngle:20,
				            itemStyle :　{
				                normal : {
				                    labelLine : {
				                        length : 20
				                    }
				                }
				            },
				            data:pieValue
				        }
					    ]
					};
	
					// 为echarts对象加载数据
					containerChart.setOption(option,true);
					 
					
					 // 基于准备好的dom，初始化echarts图表
					var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
					// 过渡---------------------
					containerColumnAllChart.showLoading({
					    text: '正在努力的读取数据中...'    //loading话术
					});
				    
					var firstObj = timeData.shift();
					var origionObj = {
						            title : {
						                text: firstObj.title.text,
						                subtext: '发布周期' + startRq + '到' + endRq,
						                x:'center'
						            },
						            tooltip : {
						                trigger: 'item',
						                formatter: "{a} <br/>{b} : {c} ({d}%)"
						            },
						            legend: {
						            	orient:'vertical',
						                data:[],
						                x:'left'
						            },
						            toolbox: {
						                show : true,
						                feature : {
						                    dataView : {show: false, readOnly: true},
						                    restore : {show: true},
						                    saveAsImage : {show: true}
						                }
						            },
						            series : [
						                {
						                    name:'网站权威性',
						                    type:'pie',
						                    center: ['50%', '45%'],
						                    radius: '50%',
						                    minAngle:20,
						                    startAngle : 45,
						                    data:firstObj.series[0].data
						                }
						            ]
						        }
					
						timeData.unshift(origionObj);
						        
						var option2 = {
						    timeline : {
						        data : timeCategoriesValue,
						        label : {
						            formatter : function(s) {
						                return s.slice(0, 10);
						            }
						        },
						        autoPlay : true,
        						playInterval : 1000
						    },
						    options : timeData
						};
                    
			
						// 为echarts对象加载数据
						containerColumnAllChart.setOption(option2);
						containerColumnAllChart.hideLoading();
							 
		    });
		    
	});
	
	$(".countryCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		$(".show_1:eq(7)").show();
	    $(".show_1:eq(10)").show();
		clearTitle();
	    
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		var containerChart = echarts.init(document.getElementById('container'));
	    $.getJSON('thirdLevel',{dataType:'countryCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "网站特点" + ">>" + "区域全面性";
			$("#hint_1").empty();
	        var htmlHint2 = getHint(a.id) + "区域全面性饼图"
			$("#hint_2").empty();
	        
	        var categoriesValue = [],dataCountryValue = [],dataProvinceValue = [],dataCityValue = [],dataXianjiValue = [],pieValue = [];
	    	var countryT = 0,provinceT = 0,cityT = 0,xianjiT = 0;
	    	var timeCategoriesValue = [],timeData = [];
			$.each(responseText,function(n,value) {  
				value = $.parseJSON(value);
				categoriesValue[n] = value.rq;
				dataCountryValue[n] = value.country;
				dataProvinceValue[n] = value.province;
				dataCityValue[n] = value.city;
				dataXianjiValue[n] = value.xianji;
				
				countryT += value.country;
				provinceT += value.province;
				cityT += value.city;
				xianjiT += value.xianji;
				
				//百搭时间轴数据
				timeCategoriesValue[n] = { name:value.rq, symbol:'emptyStar6', symbolSize:8 };
				timeData[n] = {
								title : {'text': '监测周期'+getCircle(value.rq,mCircle)+'区域全面性占比变化'},
					            series : [
					                {
					                    name:'区域全面性',
					                    type:'pie',
					                    data:[
					                        {value: value.country,  name:'全国性网站'},
					                        {value: value.province,  name:'省级网站'},
					                        {value: value.city,  name:'市级网站'},
					                        {value: value.xianji,  name:'县级网站'}
					                    ]
					                }
					            ]
					        }
		    });
			
			pieValue = [{
				name: "全国性网站",
				value : countryT
			},{
				name: "省级网站",
				value : provinceT
			},{
				name: "市级网站",
				value : cityT
			},{
				name: "县级网站",
				value : xianjiT
			}];
			
			var option = {
				title : {
					text : getHint(a.id) + '区域网站数量变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['全国性网站','省级网站','市级网站','县级网站'],
			        orient:'horizontal',
			        x : 'center',
			        y : 'bottom'
			    },
			    toolbox: {
			        show : true,
			        orient:'vertical',
			        feature : {
			            dataView : {show: false, readOnly: true},
			            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : categoriesValue
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            name : '网站数量'
			        }
			    ],
			    series : [
			        {
			            name:'全国性网站',
			            type:'line',
			            tiled: '总量',
			            data:dataCountryValue
			        },
			        {
			            name:'省级网站',
			            type:'line',
			            tiled: '总量',
			            data:dataProvinceValue
			        },
			        {
			            name:'市级网站',
			            type:'line',
			            tiled: '总量',
			            data:dataCityValue
			        },
			        {
			            name:'县级网站',
			            type:'line',
			            tiled: '总量',
			            data:dataXianjiValue
			        },
			        {
		            name:'发布周期',
		            type:'pie',
		            tooltip : {
		                trigger: 'item',
		                formatter: '{a} <br/>{b} : {c} ({d}%)'
		            },
		            center: ['80%', '30%'],
		            radius : [0, 50],
		            minAngle:20,
		            itemStyle :　{
		                normal : {
		                    labelLine : {
		                        length : 20
		                    }
		                }
		            },
		            data:pieValue
		        }
			    ]
			};
	
			// 为echarts对象加载数据
			containerChart.setOption(option,true);
			 
			
			 // 基于准备好的dom，初始化echarts图表
			var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
			// 过渡---------------------
			containerColumnAllChart.showLoading({
			    text: '正在努力的读取数据中...'    //loading话术
			});
		    
			var firstObj = timeData.shift();
			var origionObj = {
				            title : {
				                text: firstObj.title.text,
				                subtext: '发布周期' + startRq + '到' + endRq,
				                x:'center'
				            },
				            tooltip : {
				                trigger: 'item',
				                formatter: "{a} <br/>{b} : {c} ({d}%)"
				            },
				            legend: {
				            	orient:'vertical',
				                data:[],
				                x:'left'
				            },
				            toolbox: {
				                show : true,
				                feature : {
				                    dataView : {show: false, readOnly: true},
				                    restore : {show: true},
				                    saveAsImage : {show: true}
				                }
				            },
				            series : [
				                {
				                    name:'区域全面性',
				                    type:'pie',
				                    center: ['50%', '45%'],
				                    radius: '50%',
				                    startAngle : 45,
				                    minAngle:20,
				                    data:firstObj.series[0].data
				                }
				            ]
				        }
			
				timeData.unshift(origionObj);
				        
				var option2 = {
				    timeline : {
				        data : timeCategoriesValue,
				        label : {
				            formatter : function(s) {
				                return s.slice(0, 10);
				            }
				        },
				        autoPlay : true,
						playInterval : 1000
				    },
				    options : timeData
				};
	        
	
				// 为echarts对象加载数据
				containerColumnAllChart.setOption(option2);
				containerColumnAllChart.hideLoading();
				
	    });		
					 
		    var containerColumnChart = echarts.init(document.getElementById('containerColumn')); 
	         containerColumnChart.showLoading({
				    text: '正在努力的读取数据中...',  //loading话术
				    effect:'dynamicLine'
				});
		    $.getJSON('table',{dataType:'countryCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		    	var dataArr = [];
		    	var maxVal = 0;
		    	$.each(responseText,function(i,value) {
		    		value = $.parseJSON(value);
		    		var obj = {
		    				
		    		};
		    		obj.name = value.name.replace("市","").replace("省","").replace("回族自治区","").replace("自治区","").replace("特别行政区","");
		    		obj.value = value.count;
		    		dataArr[i] = obj;
		    		if(obj.value > maxVal) {
		    			maxVal = obj.value;
		    		}
		    	});
		    	
		         var option = {
		         	    title : {
		         	        text: getHint(a.id) + '参与报到食品安全的网站所在省份图',
		         	        subtext: '发布周期'+startRq + '到' + endRq,
		         	        x:'center'
		         	    },
		         	    tooltip : {
		         	        trigger: 'item'
		         	    },
		         	    legend: {
		         	        orient: 'vertical',
		         	        x:'left',
		         	        data:[]
		         	    },
		         	    dataRange: {
		         	        min: 0,
		         	        max: maxVal,
		         	        x: 'left',
		         	        y: 'bottom',
		         	        text:['高','低'],           // 文本，默认为数值文本
		         	        color: ['orangered','yellow','lightskyblue'],
		         	        calculable : true
		         	    },
		         	    toolbox: {
		         	        show : true,
		         	        orient : 'vertical',
		         	        x: 'right',
		         	        y: 'center',
		         	        feature : {
		         	            dataView : {
		         	            	show: false, 
		         	            	readOnly: true,
		         	            	optionToContent : function() {
										var cp_name = option.series[0].name,
											cp_dataArr = option.series[0].data,
											cp_hint = getHint(a.id);
							        	
							            var content = cp_hint + cp_name +':\n\n';
										for(var i=0; i<cp_dataArr.length; i++) {
											content += cp_dataArr[i].name;
											var space = '';
											for(var j=0; j<30 - cp_dataArr[i].name.length*2; j++) {//一个汉字占两个空格所以要乘以2
												space += ' ';
											}
											content += (space + cp_dataArr[i].value + '\n');
										}
									    return content;
							        }
		         	            },
		         	            restore : {show: true},
		         	            saveAsImage : {show: true}
		         	        }
		         	    },
		         	    roamController: {
					        show: true,
					        x: 'right',
					        mapTypeControl: {
					            'china': true
					        }
					    },
		         	    series : [
		         	        {
		         	            name: '网站数量',
		         	            type: 'map',
		         	            mapType: 'china',
		         	            roam: false,
		         	            showLegendSymbol:false,
		         	            itemStyle:{
		         	                normal:{label:{show:true}},
		         	                emphasis:{label:{show:true}}
		         	            },
		         	            data:dataArr
		         	        }
		         	    ]
		         	};
		         	                    
	
		         // 为echarts对象加载数据 
		         containerColumnChart.setOption(option);
		         containerColumnChart.hideLoading();
		    });
		    
	});
	
	$(".traditionCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		$(".show_1:eq(7)").show();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
			var containerChart = echarts.init(document.getElementById('container'));
		    $.getJSON('thirdLevel',{dataType:'traditionCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
		    	var htmlHint = getHint(a.id) + ">>"+ "网站特点" + ">>" + "类型全面性";
				$("#hint_1").empty();
				
				var categoriesValue = [],dataTraditionValue = [],dataGeneralValue = [],dataSocialValue = [],pieValue = [];
		    	var traditionT = 0,generalT = 0,socialT = 0;
		    	var timeCategoriesValue = [],timeData = [];
				$.each(responseText,function(n,value) {  
					value = $.parseJSON(value);
					categoriesValue[n] = value.rq;
					dataTraditionValue[n] = value.tradition;
					dataGeneralValue[n] = value.general;
					dataSocialValue[n] = value.social;
					
					traditionT += value.tradition;
					generalT += value.general;
					socialT += value.social;
					
					//百搭时间轴数据
					timeCategoriesValue[n] = { name:value.rq, symbol:'emptyStar6', symbolSize:8 };
					timeData[n] = {
									title : {'text': '监测周期'+getCircle(value.rq,mCircle)+'类型全面性占比变化'},
						            series : [
						                {
						                    name:'类型全面性',
						                    type:'pie',
						                    data:[
						                        {value: value.tradition,  name:'传统性网站'},
						                        {value: value.general,  name:'一般性网站'},
						                        {value: value.social,  name:'社会化媒体数量'}
						                    ]
						                }
						            ]
						        }
			    });
				
				pieValue = [{
					name: "传统性网站",
					value : traditionT
				},{
					name: "一般性网站",
					value : generalT
				},{
					name: "社会化媒体数量",
					value : socialT
				}];
					
				var option = {
					title : {
						text : getHint(a.id) + '类型数量变化趋势',
						subtext : '发布周期' + startRq + '到' + endRq,
						x:'center'
					},
					tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['传统性网站','一般性网站','社会化媒体数量'],
				        x:'center',
				        y:'bottom'
				    },
				    toolbox: {
				        show : true,
				        orient:'vertical',
				        feature : {
				            dataView : {show: false, readOnly: true},
				            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : categoriesValue
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            name : '网站数量'
				        }
				    ],
				    series : [
				        {
				            name:'传统性网站',
				            type:'line',
				            tiled: '总量',
				            data:dataTraditionValue
				        },
				        {
				            name:'一般性网站',
				            type:'line',
				            tiled: '总量',
				            data:dataGeneralValue
				        },
				        {
				            name:'社会化媒体数量',
				            type:'line',
				            tiled: '总量',
				            data:dataSocialValue
				        },
				        {
			            name:'发布周期内',
			            type:'pie',
			            tooltip : {
			                trigger: 'item',
			                formatter: '{a} <br/>{b} : {c} ({d}%)'
			            },
			            center: ['80%', '30%'],
			            radius : [0, 50],
			            minAngle:20,
			            itemStyle :　{
			                normal : {
			                    labelLine : {
			                        length : 20
			                    }
			                }
			            },
			            data:pieValue
			        }
				    ]
				};
		
				// 为echarts对象加载数据
				containerChart.setOption(option,true);
				 
				
				 // 基于准备好的dom，初始化echarts图表
				var containerColumnAllChart = echarts.init(document.getElementById('containerColumnAll'));
				// 过渡---------------------
				containerColumnAllChart.showLoading({
				    text: '正在努力的读取数据中...'    //loading话术
				});
			    
				var firstObj = timeData.shift();
				var origionObj = {
					            title : {
					                text: firstObj.title.text,
					                subtext: '发布周期' + startRq + '到' + endRq,
					                x:'center'
					            },
					            tooltip : {
					                trigger: 'item',
					                formatter: "{a} <br/>{b} : {c} ({d}%)"
					            },
					            legend: {
					            	orient:'vertical',
					                data:[],
					                x:'left'
					            },
					            toolbox: {
					                show : true,
					                feature : {
					                    dataView : {show: false, readOnly: true},
					                    restore : {show: true},
					                    saveAsImage : {show: true}
					                }
					            },
					            series : [
					                {
					                    name:'类型全面性',
					                    type:'pie',
					                    center: ['50%', '45%'],
					                    radius: '50%',
					                    minAngle:20,
					                    data:firstObj.series[0].data
					                }
					            ]
					        }
				
					timeData.unshift(origionObj);
					        
					var option2 = {
					    timeline : {
					        data : timeCategoriesValue,
					        label : {
					            formatter : function(s) {
					                return s.slice(0, 10);
					            }
					        },
					        autoPlay : true,
							playInterval : 1000
					    },
					    options : timeData
					};
		        
		
					// 为echarts对象加载数据
					containerColumnAllChart.setOption(option2);
					containerColumnAllChart.hideLoading();	
					
		        
		    });
		    
	});
	
	$(".site_yxl").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(2)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
		myChart.showLoading({
		    text: '正在努力的读取数据中...',  //loading话术
		    effect:'dynamicLine'
		});
		
		$.getJSON('table',{dataType:'site_yxl', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
			var htmlHint = getHint(a.id) + ">>"+ "网站特点" + ">>" + "网站影响力";
			$("#hint_1").empty();
			
			var categoriesValue = [],dataValue = [];
			$.each(responseText,function(n,value) {  
				value = $.parseJSON(value);
				categoriesValue[n] = value.grade;
				dataValue[n] = value.sCount
		    });  
			
			var option = {
				title : {
					text : getHint(a.id) + 'pr值对应的网站数量变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'item',
					formatter: "{b} <br/>{a} : {c}"
				},
				legend : {
					data : [],
					x:'left'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.yAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				yAxis : [{
							type : 'category',
							boundaryGap : true,
							data : categoriesValue
						}],
				xAxis : [{
							type : 'value',
							name : '网站数量'
						}],
				series : [{
							name : '网站数量',
							type : 'bar',
							barWidth : 20,
							data : dataValue
						}]
			};
			
		function eConsole(param) {
			    var mes = '';
			    if (typeof param.seriesIndex != 'undefined') {
			        mes += '  seriesIndex : ' + param.seriesIndex;
			        mes += '  dataIndex : ' + param.dataIndex;
			    }
			    if (param.type == 'click') {
			    	var pr = param.name;
                    
			    	site_yxlDefault(a.id,pr,startRq,endRq,mCircle);
			    }//end if
			  
			}//end Console
			myChart.on(echarts.config.EVENT.CLICK, eConsole);
			myChart.setOption(option);
			myChart.hideLoading();
			
		});
		
		//默认显示pr==8-8.99的网站列表
		site_yxlDefault(a.id,'8-8.99',startRq,endRq,mCircle);
		
	});
	
	$(".sitesCount").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('thirdLevel',{dataType:'sitesCount', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "网站特点" + ">>" + "网站数量";
			$("#hint_1").empty();
	    	
			var arr = $.parseJSON(responseText.data);
			var categoriesArr = $.parseJSON(responseText.categories);

			var option = {
				title : {
					text : getHint(a.id) + '网站数量变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'item',
					formatter: "{b} <br/>{a} : {c}"
				},
				legend : {
					data : [],
					x:'left'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.xAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '网站数量',
							max : Math.max.apply(Math, arr) + 100,
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
							name : '网站数量',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
			};
			
			myChart.setOption(option);
	    });
		    
	});
	
	$(".exposIndexRate").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('firstLevel',{dataType:'exposIndexRate', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "传播进度" + ">>" + responseText.name;
			$("#hint_1").empty().html("计算公式：曝光程度变化率 =（曝光程度指数n-曝光程度指数n-1）/ 曝光程度指数n-1");
	    	
			var arr = $.parseJSON(responseText.data);
			var categoriesArr = $.parseJSON(responseText.categories);
			for (var i = 0; i < arr.length; i++) {
				arr[i] = Math.round10(arr[i], -2);
			}

			var option = {
				title : {
					text : getHint(a.id) + '曝光程度变化率变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [],
					x:'left'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.xAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '曝光程度变化率',
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
							name : '曝光程度变化率',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
	    });
	    
	});
	
	$(".participationIndexRate").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('firstLevel',{dataType:'participationIndexRate', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "传播进度" + ">>" + responseText.name;
			$("#hint_1").empty().html("计算公式：参与程度变化率 =（参与程度n-参与程度n-1）/ 参与程度n-1");
	    	
			var arr = $.parseJSON(responseText.data);
			var categoriesArr = $.parseJSON(responseText.categories);
			for (var i = 0; i < arr.length; i++) {
				arr[i] = Math.round10(arr[i], -2);
			}

			var option = {
				title : {
					text : getHint(a.id) + '参与程度变化率变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [],
					x:'left'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.xAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '参与程度变化率',
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
							name : '参与程度变化率',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
	    });
	    
	});
	
	$(".publicOpinionIndexRate").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('firstLevel',{dataType:'publicOpinionIndexRate', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "传播进度" + ">>" + responseText.name;
			$("#hint_1").empty().html("计算公式：舆情观点变化率 =（舆情观点指数n-舆情观点指数n-1）/ 舆情观点指数n-1");
	    	
			var arr = $.parseJSON(responseText.data);
			var categoriesArr = $.parseJSON(responseText.categories);
			for (var i = 0; i < arr.length; i++) {
				arr[i] = Math.round10(arr[i], -2);
			}

			var option = {
				title : {
					text : getHint(a.id) + '舆情观点变化率变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [],
					x:'left'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.xAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '舆情观点变化率',
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
							name : '舆情观点变化率',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
	    });
	    
	});
	
	$(".siteFeatureIndexRate").on("click",function(e) {
		clickBefore($cpoiTable,$show_1,$releaseRq);
		$(".show_1:gt(0)").hide();
		clearTitle();
		
		var a = e.currentTarget;
		latelyClick.name = a.id;
		latelyClick.password = $(this).attr("class");
		
		// 基于准备好的dom，初始化echarts图表
		var myChart = echarts.init(document.getElementById('container'));
	    $.getJSON('firstLevel',{dataType:'siteFeatureIndexRate', userId:a.id, rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
	    	var htmlHint = getHint(a.id) + ">>"+ "传播进度" + ">>" + responseText.name;
			$("#hint_1").empty().html("计算公式：网站特点变化率=（网站特点指数n-网站特点指数n-1）/ 网站特点指数n-1");
	    	
			var arr = $.parseJSON(responseText.data);
			var categoriesArr = $.parseJSON(responseText.categories);
			for (var i = 0; i < arr.length; i++) {
				arr[i] = Math.round10(arr[i], -2);
			}

			var option = {
				title : {
					text : getHint(a.id) + '网站特点变化率变化趋势',
					subtext : '发布周期' + startRq + '到' + endRq,
					x:'center'
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [],
					x:'left'
				},
				toolbox : {
					show : true,
					feature : {
						dataView : {
							show : false,
							readOnly : true,
							optionToContent : function() {
								var cp_name = option.series[0].name,
									cp_categoriesArr = option.xAxis[0].data,
									cp_dataArr = option.series[0].data,
									cp_hint = getHint(a.id);
					        	
					            return formatContent(cp_hint,cp_name,cp_categoriesArr,cp_dataArr);
					        }
						},
						magicType : {
							show : true,
							type : ['line', 'bar']
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				xAxis : [{
							type : 'category',
							boundaryGap : false,
							data : categoriesArr
						}],
				yAxis : [{
							type : 'value',
							name : '网站特点变化率',
							axisLabel : {
								formatter : '{value}'
							}
						}],
				series : [{
							name : '网站特点变化率',
							type : 'line',
							data : arr,
							markPoint : {
								data : [{
											type : 'max',
											name : '最大值'
										}, {
											type : 'min',
											name : '最小值'
										}]
							}
						}]
			};

			// 为echarts对象加载数据
			myChart.setOption(option);
	        
	    });
		    
	});
	
	$(".cpoiData").on("click", function() {
		$(".show_1").hide();
		$("#releaseRq").show();
		latelyClick.name = 2;
		latelyClick.password = $(this).attr("class");
		
		startRq = $("input#rq1").val();
		endRq = $("input#rq2").val();
		mCircle = $("select[name='monitorCircle']").val();
		
		$.getJSON('firstLevel',{dataType:'cpoiData', rq1:startRq, rq2:endRq, monitorCircle:mCircle}, function(responseText) {
			var jsonAll = responseText;
			var str = "<table id='cpoiTable' class='tftable'> <thead><tr>";
				str +="<th>周期</th><th>方便食品行业</th><th>罐头食品行业</th><th>酒类行业</th><th>调味品行业</th><th>饮料行业 </th><th>蛋制品行业</th><th>乳制品行业</th><th>茶叶行业</th><th>糖果类行业</th><th>肉类及肉制品行业</th><th>果蔬类行业</th><th>粮食及粮食制品类</th><th>食用油行业</th><th>水产品行业</th><th>菌类行业</th>" + 
				"<th>豆制品行业</th><th>休闲食品行业</th><th>保健食品行业</th><th>食品添加剂行业</th><th>清真食品行业</th></tr></thead>"
			
			var jsonRq = $.parseJSON(jsonAll[0]);	
			var json1500 = $.parseJSON(jsonAll[1]);	
			var json1501 = $.parseJSON(jsonAll[2]);	
			var json1502 = $.parseJSON(jsonAll[3]);	
			var json1503 = $.parseJSON(jsonAll[4]);	
			var json1504 = $.parseJSON(jsonAll[5]);	
			var json1505 = $.parseJSON(jsonAll[6]);	
			var json1506 = $.parseJSON(jsonAll[7]);	
			var json1507 = $.parseJSON(jsonAll[8]);	
			var json1508 = $.parseJSON(jsonAll[9]);	
			var json1509 = $.parseJSON(jsonAll[10]);	
			var json1510 = $.parseJSON(jsonAll[11]);	
			var json1511 = $.parseJSON(jsonAll[12]);	
			var json1512 = $.parseJSON(jsonAll[13]);	
			var json1513 = $.parseJSON(jsonAll[14]);	
			var json1514 = $.parseJSON(jsonAll[15]);	
			var json1515 = $.parseJSON(jsonAll[16]);	
			var json1516 = $.parseJSON(jsonAll[17]);	
			var json1517 = $.parseJSON(jsonAll[18]);	
			var json1518 = $.parseJSON(jsonAll[19]);	
			var json1519 = $.parseJSON(jsonAll[20]);	
				
			for(var i=0; i<jsonRq.length; i++) {
				str += "<tr><td>" + jsonRq[i] + "</td><td>" + json1500[i] + "</td><td>" + json1501[i] + "</td><td>" + json1502[i] + "</td><td>" + json1503[i] + "</td><td>" + json1504[i] + "</td><td>" + json1505[i] + "</td><td>" + json1506[i] +
				"</td><td>" + json1507[i] + "</td><td>" + json1508[i] + "</td><td>" + json1509[i] + "</td><td>" + json1510[i] + "</td><td>" + json1511[i] + "</td><td>" + json1512[i] + "</td><td>" + json1513[i] + "</td><td>" + json1514[i] + "</td><td>" + json1515[i] + 
				"</td><td>" + json1516[i] + "</td><td>" + json1517[i] + "</td><td>" + json1518[i] + "</td><td>" + json1519[i] + "</td></tr>"
			}
			str += "</table>"
			$("#cpoiTable").remove();
			$(".show").append(str);
		});
		
	});
	
	$(".aboutCpoi").on("click", function() {
		$cpoiTable = $cpoiTable || $("#cpoiTable");
		$cpoiTable.remove();
		$show_1 = $show_1 || $(".show_1");
		$show_1.show();
		$releaseRq = $releaseRq || $("#releaseRq")
		$releaseRq.hide();
		$("#hint_1").empty();
		
		var str= "待添加中";
		$("#containerSides").html(str);
		$(".show_1").hide();
		$(".show_1:eq(5)").show();
		
	});
	
	$( "#selectTitleC" ).on("change", function() {
		var value = $( "#selectTitleC" ).val();
		if(value==='0') {
			cpoi.setTitleCFilter(false);
			cpoi.getTitleC();
		}else {
			cpoi.setTitleCFilter(true);
			cpoi.getTitleC();
		}
	});
	$("#selectContainerApp").on("change", function() {
		var value = $("#selectContainerApp").val();
		if(value==='0') {
			cpoi.setContainerAppFilter(false);
			cpoi.getContainerApp();
		}else {
			cpoi.setContainerAppFilter(true);
			cpoi.getContainerApp();
		}
	});
	
});//$(function(){}) the end
