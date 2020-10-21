var maxMinData={}
$(function() {
	reWindowSize();
	$("#objStartTime_search").datebox('setValue',func_currentData())
	maxMinData=func_sampleMaxMin();//获取碳硫仪阈值
	initDataGrid();
	initFlyGrid();
})
var GydeviceNum="'ZJ-ZX-041','ZJ-ZX-038'";
/**
 * 表头信息
 */
var head = [[{"field":"ck","checkbox":true},            
             {"field":"sampleNum","title":"样品编号","width":"120","align":"center","sortable":true}, 
             {"field":"test_sybh","title":"试验编号","width":"120","align":"center","sortable":true}, 
             {"field":"test_syrq","title":"分析日期","width":"120","align":"center","sortable":true}, 
            /* {"field":"test_sybh","title":"试验序号","width":"120","align":"center","sortable":false},*/
             {"field":"testType","title":"类型","width":"80","align":"center","sortable":false},                                                        
             {"field":"test_mad","title":"Mad","width":"100","align":"center","sortable":false,
        		"styler":function(value,row,index){
        			if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
        				 return 'background-color:#9BED7E;color:red;';
        			}
        			if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
        				 return 'background-color:#ffee00;color:red;';
        			}
        		}
             },
             {"field":"test_vad","title":"Vad","width":"100","align":"center","sortable":false,
        		"styler":function(value,row,index){
        			if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
        				 return 'background-color:#9BED7E;color:red;';
        			}
        			if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
        				 return 'background-color:#ffee00;color:red;';
        			}
        		}
             },
             {"field":"test_aad","title":"Aad","width":"100","align":"center","sortable":false,
        		"styler":function(value,row,index){
        			if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
        				 return 'background-color:#9BED7E;color:red;';
        			}
        			if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
        				 return 'background-color:#ffee00;color:red;';
        			}
        		}	 
             },
            {"field":"calcula_ad","title":"Ad","width":"110","align":"center","sortable":false,
	    		"styler":function(value,row,index){
	    			if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
	    				 return 'background-color:#9BED7E;color:red;';
	    			}
	    			if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
	    				 return 'background-color:#ffee00;color:red;';
	    			}
	    		}
            },
            /* {"field":"calcula_fcad","title":"固定碳（计算）","width":"144","align":"center","sortable":false},
             {"field":"calcula_vdaf","title":"挥发分vd vdaf（计算）","width":"150","align":"center","sortable":false},
             {"field":"calcula_jt_vdaf","title":"焦炭挥发分（计算）","width":"150","align":"center","sortable":false},
             {"field":"calcula_mt_vdaf","title":"煤炭挥发分（计算）","width":"150","align":"center","sortable":false},*/
             {"field":"recordTime","title":"采集时间","width":"140","align":"center","sortable":false,"formatter":formateDate}/*,*/
            /* {"field":"recordUserName","title":"操作人","width":100,"align":"center","sortable":false}*/
            ]];
/**
 * -列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/zjsys_testDataSrc/zjGongyefenxiSrcAction_list.action",
		collapsible : false,
		pagination : false,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		groupField:'sampleNum',
		view:groupview,
		groupFormatter:function(value,rows){
		return '<span style="color:blue;">样品编号：'+rows[0].sampleNum+'</span>';
		},
		queryParams : {  //参数传递			
			dataStatus:$("#dataStatus_search").combobox('getValue'),
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			deviceNum:GydeviceNum,			
			testType:$("#testType_search").combobox("getValue"),
			sampleNum:$("#sampleNum_search").textbox("getValue")
		},
		columns : head,
		onLoadSuccess:function(data){
			 $('#datagrid').datagrid("scrollTo",data.rows.length-1);
		}

	});
}

/**
 * 检索
 * @param gridId
 */
function func_search(){
	$('#datagrid').datagrid('load',{  //参数传递			
		dataStatus:$("#dataStatus_search").combobox('getValue'),
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		deviceNum:GydeviceNum,			
		testType:$("#testType_search").combobox("getValue"),
		sampleNum:$("#sampleNum_search").textbox("getValue")
	}); 
	
	func_search_fly();
}


/**
 * 手动刷新数据
 */
function func_upload(deviceNum){	 
	 
	 $('<div></div>').dialog({
	        id : 'modelWindow',
	        title : '手动刷新 ',
	        width : 400,
	        height : 200,
	        closed : false,
	        cache : false,
	        href : getContextPath() + '/page/testDataSrc/ZjGongyefenxiSrc038_041/refresh.html',
	        modal : true,
	        onClose : function() {
	            $(this).dialog('destroy');
	        },
	        onLoad:function(){
	       	 $("#deviceNum_edit").textbox('setValue',deviceNum);
	       	 $("#objStartMonth_edit").val(func_currentMonth());
	        },
	        buttons : [ {
	            text : '保存',
	            iconCls : 'icon-ok',
	            handler : function() {
	           	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
	   				$('#modelForm').form('submit', {
	   					url : getContextPath() + "/zjsys_testDataSrc/zjGongyefenxiSrcAction_uploadGngyefenxiData.action",	   					
	   					onSubmit:function(param){
	   						var isValid = $(this).form('enableValidation').form('validate');
	   						if (!isValid){
	   							$.messager.progress('close');
	   						}
	   						return isValid;	
	   					},
	   					success: function(data){
	   						var resultData = jQuery.parseJSON(data); 
	   						$.messager.progress('close');
	   						if(resultData.STATUS=="SUCCESS") {
	   							$("#modelWindow").dialog('destroy');
	   							func_msg_info(resultData.RETURN_DATA);
	   							func_search();
	   						}else if(resultData.STATUS=="ALERT"){
	   							func_msg_error(resultData.RETURN_DATA);
	   						}else{
	   							func_msg_error(resultData.RETURN_DATA);
	   						}
	   					}
	   				});
	            }
	        }, {
	            text : '取消',
	            iconCls : 'icon-cancel',
	            handler : function() {
	                $("#modelWindow").dialog('destroy');
	            }
	        } ]
	    });
}

/**
 * 提交-信息
 */
function func_fly(){
	
	var dataStatus=$("#dataStatus_search").combobox('getValue');
	//判断是否已提交或素具已作废
	if(dataStatus=="已提交"||dataStatus=="作废数据"){
		alert("已提交数据，不能重复提交！");return false;
	}
	var allType="";
    var rowId="";
    var sampleno="";
    //挥发分数量，灰分数量
	var hfArry=[];
	var hfaArry=[];
	//var sampleType="";
	var rows = $('#datagrid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){		
		rowId=rowId+rows[i].id+',';
		allType+=rows[i].testType;
		if(sampleno==""){
			sampleno=rows[i].sampleNum;
		}else{
			if(sampleno!=rows[i].sampleNum){
				alert("请选择同一个样品下的数据发送！");
				return false;}
		}	
		if(rows[i].testType.indexOf("灰分")!=-1){
			hfArry.push(rows[i]);
		}else if(rows[i].testType.indexOf("挥发分")!=-1){
			hfaArry.push(rows[i]);
		}	
		//sampleType=rows[i].sampleNum;
  }
	if((allType.indexOf("灰分")==-1)||(allType.indexOf("挥发分")==-1)){
		alert("请选择同一样品下的“灰分”，“挥发分进行发送”"); return false;
	}	
	if(rowId==""){
		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要发送的数据</font>','warning');
		return;}
	
  //ad 灰分 ，水分Mad，Aad
	var ad=0;var Mad=0;var Aad=0;
	for(var i=0;i<hfArry.length;i++){
		ad=accAdd(ad,hfArry[i].calcula_ad);
		Mad=accAdd(Mad,hfArry[i].test_mad);
		Aad=accAdd(Aad,hfArry[i].test_aad);
	}
	ad=accDiv(ad,hfArry.length);
	Mad=accDiv(Mad,hfArry.length);
	Aad=accDiv(Aad,hfArry.length);	
  //挥发分	vad 
	var Vad=0;
   for(var j=0;j<hfaArry.length;j++){
		Vad=accAdd(Vad,hfaArry[j].test_vad);
	}
	    Vad=accDiv(Vad,hfaArry.length);
	var fcad=(100-Mad-Aad-Vad); var vdvdaf=0;	
	 if(sampleno.indexOf("JTLT")!=-1){
		 vdvdaf=(accDiv(Vad,(100-Mad-Aad))*100);
		 }else{
		 vdvdaf=(accDiv(Vad,(100-Mad))*100); 
		 }
	 
	 $('<div></div>').dialog({
	        id : 'modelWindow',
	        title : '提交工业分析仪数据？ ',
	        width : 450,
	        height : 280,
	        closed : false,
	        cache : false,
	        href : getContextPath() + '/page/testDataSrc/ZjGongyefenxiSrc038_041/manager.html',
	        modal : true,
	        onClose : function() {
	            $(this).dialog('destroy');
	        },
	        onLoad:function(){
               $("#recordIdS").val(rowId);
	           $("#sampleNum").text(sampleno);
	           $("#Mad").text(Mad.toFixed(2));
	           $("#Aad").text(Aad.toFixed(2));
	           $("#Ad").text(ad.toFixed(2));
	           $("#vdaf").text(vdvdaf.toFixed(2));	
	           $("#FCad").text(fcad.toFixed(2));	
	        },
	        buttons : [ {
	            text : '提交',
	            iconCls : 'icon-ok',
	            handler : function() {
	           	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
	   				$('#modelForm').form('submit', {
	   					url : getContextPath() + "/zjsys_testDataSrc/zjGongyefenxiSrcAction_fly.action",	   					
	   					onSubmit:function(param){
	   						var isValid = $(this).form('enableValidation').form('validate');
	   						if (!isValid){
	   							$.messager.progress('close');
	   						}
	   						return isValid;	
	   					},
	   					success: function(data){
	   						var resultData = jQuery.parseJSON(data); 
	   						$.messager.progress('close');
	   						if(resultData.STATUS=="SUCCESS") {
	   							$("#modelWindow").dialog('destroy');
	   							func_msg_info(resultData.RETURN_DATA);
	   							func_search();	   						
	   						}else{
	   							func_msg_error(resultData.RETURN_DATA);
	   						}
	   					}
	   				});
	            }
	        }, {
	            text : '取消',
	            iconCls : 'icon-cancel',
	            handler : function() {
	                $("#modelWindow").dialog('destroy');
	            }
	        } ]
	    });
	 
	 
	/*$.messager.confirm('确定发送:'+sampleno+'?', 'Mad:'+Mad.toFixed(6)+',Ad:'+ad.toFixed(6)+'</br>FCad:'+fcad+',VdVdaf'+vdvdaf, function(r){
		if (r){
			  $.messager.progress({title:'请等待',msg:'处理数据中...'});
			$.ajax( {
				url : getContextPath() + "/zjsys_testDataSrc/zjGongyefenxiSrcAction_fly.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					$.messager.progress('close');
					if (DATA.STATUS == 'SUCCESS') {
						func_msg_info(DATA.RETURN_DATA);
						func_search();
					} else {
						func_msg_error(DATA.RETURN_DATA);
					}
				},
				error : function(event, request, settings) {
					func_msg_error("网络异常!");
				}
			});
		}
	});*/
	
	
	
	
	
}

/**
 * 删除-信息
 */
function func_del(){
    var rowId="";
	var rows = $('#datagrid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		rowId=rowId+rows[i].id+',';
	}
	if(rowId==""){
		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要删除的数据</font>','warning');
		return;}
	$.messager.confirm('删除', '确定删除数据么?', function(r){
		if (r){
			  $.messager.progress({title:'请等待',msg:'删除数据中...'});
			$.ajax( {
				//url : getContextPath() + "/zjsys_testDataSrc/yinguangSrcAction_del.action",
				url: getContextPath() + "/zjsys_testDataSrc/zjGongyefenxiSrcAction_del.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					$.messager.progress('close');
					if (DATA.STATUS == 'SUCCESS') {
						func_msg_info(DATA.RETURN_DATA);
						func_search();
					} else {
						func_msg_error(DATA.RETURN_DATA);
					}
				},
				error : function(event, request, settings) {
					func_msg_error("网络异常!");
				}
			});
		}
	});
}

/**
 * 恢复-信息
 */
function func_recovery(){
    var rowId="";
	var rows = $('#datagrid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		rowId=rowId+rows[i].id+',';
	}
	if(rowId==""){
		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要恢复的数据</font>','warning');
		return;}
	$.messager.confirm('恢复', '确定恢复数据么?', function(r){
		if (r){
			  $.messager.progress({title:'请等待',msg:'恢复数据中...'});
			$.ajax( {
				url: getContextPath() + "/zjsys_testDataSrc/zjGongyefenxiSrcAction_recovery.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					$.messager.progress('close');
					if (DATA.STATUS == 'SUCCESS') {
						func_msg_info(DATA.RETURN_DATA);
						func_search();
					} else {
						func_msg_error(DATA.RETURN_DATA);
					}
				},
				error : function(event, request, settings) {
					func_msg_error("网络异常!");
				}
			});
		}
	});
}

/**
 * 页面缩放监听事件
 */
$(window).resize(function() {
	reWindowSize();
}).resize();
/**
 * 页面缩放自适应大小
 */
function reWindowSize() {
	var _parentId = $(window.parent.document).find("#tabContent");
	var _height = $(_parentId).height() - 48;
	$('#divConId').css("height",_height);
	$('#datagrid').css("height","97%");
}
