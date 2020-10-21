var maxMinData={}
$(function() {
	reWindowSize();
	$("#objStartTime_search").datebox('setValue',func_currentData())
	maxMinData=func_sampleMaxMin();//获取碳硫仪阈值
	initDataGrid();
	initFlyGrid();
})
 var GDDeviceNum='ZJ-ZX-031';
/**
 * 表头信息
 */
var head = [ [ {
	"field" : "ck",
	"checkbox" : true
}, {
	"field" : "dataTime",
	"title" : "分析日期",
	"width" : "120",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "sampleNum",
	"title" : "样品编号",
	"width" : "100",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "test_TFe",
	"title" : "TFe",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_SiO2",
	"title" : "SiO2",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_CaO",
	"title" : "CaO",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_MgO",
	"title" : "MgO",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Al2O3",
	"title" : "Al2O3",
	"width" : "70",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_MnO",
	"title" : "MnO",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_TiO2",
	"title" : "TiO2",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_V2O5",
	"title" : "V2o5",
	"width" : "70",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_P",
	"title" : "P",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_S",
	"title" : "S",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_K2O",
	"title" : "K2O",
	"width" : "60",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Na2O",
	"title" : "Na2O",
	"width" : "70",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Co2O3",
	"title" : "Co2O3",
	"width" : "70",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Pb",
	"title" : "Pb",
	"width" : "50",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_As",
	"title" : "As",
	"width" : "50",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Zn",
	"title" : "Zn",
	"width" : "50",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Cu",
	"title" : "Cu",
	"width" : "50",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Ni",
	"title" : "Ni",
	"width" : "50",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Cr2O3",
	"title" : "Cr2O3",
	"width" : "70",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Au2O",
	"title" : "Au2O",
	"width" : "70",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_R2",
	"title" : "R2",
	"width" : "50",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "test_Fe2O3",
	"title" : "Fe2O3",
	"width" : "80",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
} ] ];
/**
 * -列表初始化
 */
function initDataGrid() {
	debugger;
	
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/zjsys_testDataSrc/yinguangSrcAction_list.action",
		collapsible : false,
		pagination : false,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		sortOrder:'desc',
		sortName:'dataTime',
		queryParams : {  //参数传递
			deviceNum : GDDeviceNum,
			dataStatus:$("#dataStatus_search").combobox('getValue'),
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			sampleNum:$("#sampleNum_search").textbox('getValue')
		},
		columns : head,
		onLoadSuccess:function(data){
			 $('#datagrid').datagrid("selectRow",data.rows.length-1);
			 $('#datagrid').datagrid("scrollTo",data.rows.length-1);
		}

	});
}


/**
 * 检索
 * @param gridId
 */
function func_search(){
	$('#datagrid').datagrid('load',{
		deviceNum : GDDeviceNum,
		dataStatus:$("#dataStatus_search").combobox('getValue'),
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		sampleNum:$("#sampleNum_search").textbox('getValue')
	}); 
	func_search_fly();
}


 function func_upload(){	 
	 $('<div></div>').dialog({
	        id : 'modelWindow',
	        title : '手动刷新 ',
	        width : 400,
	        height : 200,
	        closed : false,
	        cache : false,
	        href : getContextPath() + '/page/testDataSrc/YinguangSrc031/refresh.html',
	        modal : true,
	        onClose : function() {
	            $(this).dialog('destroy');
	        },
	        onLoad:function(){
	       	 $("#deviceNum_edit").textbox('setValue',GDDeviceNum);
	       	 $("#objStartMonth_edit").datebox("setValue",func_currentDateTime());
	        },
	        buttons : [ {
	            text : '保存',
	            iconCls : 'icon-ok',
	            handler : function() {
	           	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
	   				$('#modelForm').form('submit', {
	   					url : getContextPath() + "/zjsys_testDataSrc/yinguangSrcAction_uploadYinguangData.action",				
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
 * 编辑-信息
 */
function func_fly(){
	var dataStatus=$("#dataStatus_search").combobox('getValue');
	//判断是否已提交或素具已作废
	if(dataStatus=="已提交"||dataStatus=="作废数据"){
		alert("已提交数据，不能重复提交！");return false;
	}
    var rowId="";
	var rows = $('#datagrid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		rowId=rowId+rows[i].id+',';
	}
	if(rowId==""){
		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要发送的数据</font>','warning');
		return;}
	$.messager.confirm('发送', '确定发送数据么?', function(r){
		if (r){
			  $.messager.progress({title:'请等待',msg:'删除数据中...'});
			$.ajax( {
				url : getContextPath() + "/zjsys_testDataSrc/yinguangSrcAction_fly.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					$.messager.progress('close');
					if (DATA.STATUS == 'SUCCESS') {
						func_msg_info(DATA.RETURN_DATA);
						func_search();func_search_fly();
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
				url : getContextPath() + "/zjsys_testDataSrc/yinguangSrcAction_del.action",
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
				url : getContextPath() + "/zjsys_testDataSrc/yinguangSrcAction_recovery.action",
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
