var maxMinData={}
var _ERPURL="http://10.1.0.22";
$(function() {
	reWindowSize();
	$("#objStartTime_search").datebox('setValue',func_currentData()),
	maxMinData=func_sampleMaxMin();//获取碳硫仪阈值
	initDataGrid();
	initFlyGrid();
	var erpData=func_erpUrl();
	if(erpData!=null&&erpData.remoteUrlPath!=undefined&&erpData.remoteUrlPath!=null){
		_ERPURL=erpData.remoteUrlPath
	}
})
var GDdeviceNum="ZJ-LX-033";
/**
 * 表头信息
 */
var head = [ [ {
	"field" : "ck",
	"checkbox" : true
},
{
	"field" : "filename",
	"title" : "文件名",
	"width" : "140",
	"align" : "center",
	"sortable" : false
}, {
	"field" : "dataTime",
	"title" : "分析日期",
	"width" : "120",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "classGroup",
	"title" : "班组",
	"width" : "120",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "lunum",
	"title" : "炉号",
	"width" : "100",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "steeltype",
	"title" : "钢种",
	"width" : "100",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "steelGuige",
	"title" : "规格",
	"width" : "120",
	"align" : "center",
	"sortable" : true
}, {
	"field" : "branchFactory",
	"title" : "生产线",
	"width" : "80",
	"align" : "center",
	"sortable" : true
},
{
	"field" : "yieldDown_streng",
	"title" : "下屈服强度",
	"width" : "120",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.steeltype+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.steeltype+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "kangla_streng",
	"title" : "抗拉强度",
	"width" : "120",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.steeltype+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.steeltype+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "duanhouLong_rate",
	"title" : "断后伸长率",
	"width" : "120",
	"align" : "center",
	"sortable" : false,
	"styler":function(value,row,index){
		if(!checFieldkMin(maxMinData,row.steeltype+"_"+this.title,value)){
			 return 'background-color:#9BED7E;color:red;';
		}
		if(!checFieldkMax(maxMinData,row.steeltype+"_"+this.title,value)){
			 return 'background-color:#ffee00;color:red;';
		}
	}
}, {
	"field" : "testTime",
	"title" : "试验时间",
	"width" : "160",
	"align" : "center",
	"sortable" : true
} ] ];
/**
 * -列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/zjsys_testDataSrc/zjDaiganglixueSrcAction_list.action",
		collapsible : false,
		pagination : false,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		sortName:"testTime",
		sortOrder:"asc",
		queryParams:{  //参数传递
			deviceNum:GDdeviceNum,
			filename:$("#fileName_search").textbox("getValue"),
			objStartTime:$("#objStartTime_search").datebox("getValue"),
			dataStatus:$("#dataStatus_search").combobox('getValue'),
			classGroup:$("#classGroup_search").textbox("getValue"),
			lunum:$("#lunum_search").textbox("getValue"),
			steeltype:$("#steeltype_search").textbox("getValue"),
			steelGuige:$("#steelGuige_search").textbox("getValue"),
			branchFactory:$("#branchFactory_search").textbox("getValue")
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
		deviceNum:GDdeviceNum,
		filename:$("#fileName_search").textbox("getValue"),
		objStartTime:$("#objStartTime_search").datebox("getValue"),
		dataStatus:$("#dataStatus_search").combobox('getValue'),
		classGroup:$("#classGroup_search").textbox("getValue"),
		lunum:$("#lunum_search").textbox("getValue"),
		steeltype:$("#steeltype_search").textbox("getValue"),
		steelGuige:$("#steelGuige_search").textbox("getValue"),
		branchFactory:$("#branchFactory_search").textbox("getValue")
	}); 
	func_search_fly();
}

/**
 * 手动刷新
 */
function func_upload(){
	 $('<div></div>').dialog({
       id : 'modelWindow',
       title : '手动刷新 ',
       width : 400,
       height : 200,
       closed : false,
       cache : false,
       href : getContextPath() + '/page/testDataSrc/ZjDaiganglixueSrc033/refresh.html',
       modal : true,
       onClose : function() {
           $(this).dialog('destroy');
       },
       onLoad:function(){
      	 $("#deviceNum_edit").textbox('setValue',GDdeviceNum);
      	 $("#objStartMonth_edit").datebox("setValue",func_currentData());
       },
       buttons : [ {
           text : '保存',
           iconCls : 'icon-ok',
           handler : function() {
          	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
  				$('#modelForm').form('submit', {
  					url: getContextPath() + "/zjsys_testDataSrc/zjDaiganglixueSrcAction_refreshData.action",
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
/*function func_fly(){
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
				url: getContextPath() + "/zjsys_testDataSrc/zjDaiganglixueSrcAction_fly.action",
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
*/
/**
 * 提交-信息
 */
function func_fly(){
	 var dataStatus=$("#dataStatus_search").combobox('getValue');
		//判断是否已提交或素具已作废
		if(dataStatus=="已发送"||dataStatus=="作废数据"){
			alert("此数据状态为已发送或作废不能发送！");
			return false;
		}
	var rowId="";   
	var rows = $('#datagrid').datagrid('getSelections');
	for(var i=0; i<rows.length; i++){
		rowId=rowId+rows[i].id+',';	
		if((rows[i].steeltype).indexOf("195")!=-1){
		    rows[i].d='d=0'; 
		}
		if((rows[i].steeltype).indexOf("235")!=-1){
		    rows[i].d='d=a';
		}
		if((rows[i].steeltype).indexOf("275")!=-1){
		    rows[i].d='D=1.5a';
		}
		if((rows[i].steeltype).indexOf("345")!=-1){
		    rows[i].d='D=2a';
		}			
	}
	if(rowId==""){
		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;">请选择要发送的数据</font>','warning');
		return;}
	$.messager.confirm('发送', '确定发送数据么?', function(r){
		if (r){
			  $.messager.progress({title:'请等待',msg:'发送数据中...'});			 
				$.ajax({
					url:_ERPURL+"/api/wulishiyan.php",
					type : "post",
					dataType : "text",
					data :"flyData="+JSON.stringify(rows),
					contentType: "application/x-www-form-urlencoded",
					async : false,
					success : function(DATA, request, settings) {
						if(DATA=="true"){
							saveReturnData(rowId,DATA);
							func_msg_info("发送成功！");
						}else{
							func_msg_info("发送失败！");
						}
					   $.messager.progress('close'); 
					},
					error : function(event, request, settings) {
						func_msg_error("网络异常!");
						$.messager.progress('close'); 
					}
				});
		}
	});
			
}


function saveReturnData(rowId,returnMess){		
	$.ajax( {
		url: getContextPath() + "/zjsys_testDataSrc/zjDaiganglixueSrcAction_fly.action",
		type : "post",
		dataType : "json",
		data : "recordIdS=" + rowId,
		async : false,
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
				url : getContextPath() + "/zjsys_testDataSrc/zjDaiganglixueSrcAction_del.action",
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
				url : getContextPath() + "/zjsys_testDataSrc/zjDaiganglixueSrcAction_recovery.action",
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
