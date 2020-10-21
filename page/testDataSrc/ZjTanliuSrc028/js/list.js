var maxMinData={}
$(function() {
	reWindowSize();
	$("#objStartTime_search").datebox('setValue',func_currentData()),
	maxMinData=func_sampleMaxMin();//获取碳硫仪阈值
	initDataGrid();
	initFlyGrid();
})
/**
 * 表头信息
 */
var head = [ [ {
	"field" : "ck",
	"checkbox" : true
},{
	"field" : "deviceNum",
	"title" : "设备编号",
	"width" : "100",
	"align" : "center",
	"sortable" : false
},  {
	"field" : "sampleNum",
	"title" : "样品编号",
	"width" : "120",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "resultc",
	"title" : "C",
	"width" : "150",
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
	"field" : "results",
	"title" : "S",
	"width" : "120",
	"align" : "center",
	"sortable" : false,
	"formatter" :formate3num,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.sample_code+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
},  {
	"field" : "dataStatus",
	"title" : "数据状态",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "time",
	"title" : "分析时间",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "recordTime",
	"title" : "采集时间",
	"width" : "140",
	"align" : "center",
	"sortable" : false,
	"formatter" : formateTime
} ] ];
/**
 * -列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/zjsys_testDataSrc/zjTanliuSrcAction_list.action",
		collapsible : false,
		pagination : false,// 分页
		rownumbers : true, // 行号
		striped : true, // 各行换色
		checkOnSelect : true,
		selectOnCheck : true,
		ctrlSelect : true,
		sortOrder : 'asc',
		sortName : 'time',
		queryParams : { // 参数传递
			dataStatus:$("#dataStatus_search").combobox('getValue'),
			sampleNum: $("#sampleNum_search").textbox('getValue'),
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			deviceNum : 'ZJ-ZX-028'
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
 * 
 * @param gridId
 */
function func_search() {
	$('#datagrid').datagrid('load', {
		dataStatus:$("#dataStatus_search").combobox('getValue'),
		sampleNum: $("#sampleNum_search").textbox('getValue'),
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		deviceNum : 'ZJ-ZX-028'
	});
	func_search_fly()
}


function func_refresh(deviceNum) {
	$('<div></div>').dialog({
        id : 'modelWindow',
        title : '手动刷新 ',
        width : 400,
        height : 300,
        closed : false,
        cache : false,
        href : getContextPath() + '/page/testDataSrc/ZjTanliuSrc028/refresh.html',
        modal : true,
        onClose : function() {
            $(this).dialog('destroy');
        },
        onLoad:function(){
       	 $("#deviceNum_edit").textbox('setValue',deviceNum);
       	$("#objStartTime_edit").datebox('setValue',func_currentData());
        },
        buttons : [ {
            text : '保存',
            iconCls : 'icon-ok',
            handler : function() {
           	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
   				$('#modelForm').form('submit', {
   					url: getContextPath() + "/zjsys_testDataSrc/zjTanliuSrcAction_refreshData.action",
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
				url : getContextPath() + "/zjsys_testDataSrc/zjTanliuSrcAction_del.action",
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
				url : getContextPath() + "/zjsys_testDataSrc/zjTanliuSrcAction_recovery.action",
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
 * 信息
 */
function func_fly() {
	var rowId = "";
	var rows = $('#datagrid').datagrid('getSelections');
	for ( var i = 0; i < rows.length; i++) {
		rowId = rowId + rows[i].id + ';';
	}
	if (rowId == "") {
		$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;">请选择要发送的数据</font>', 'warning');
		return;
	}
	$.messager.confirm('发送', '确定发送数据么?', function(r) {
		if (r) {
			$.messager.progress({
				title : '请等待',
				msg : '删除数据中...'
			});
			$.ajax({
				url : getContextPath() + "/zjsys_testDataSrc/zjTanliuFlyAction_submitData.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					$.messager.progress('close');
					if (DATA.STATUS == 'SUCCESS') {
						func_msg_info(DATA.RETURN_DATA);
						func_search();
						func_search_fly();
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
	$('#divConId').css("height", _height);
	$('#datagrid').css("height", "97%");
}
