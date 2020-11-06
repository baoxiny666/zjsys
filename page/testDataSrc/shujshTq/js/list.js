var _ERPURL="http://10.1.0.22";
var _currentUserId="";
var _currentwlCode="";
var companyType = "";
var xialakuangdata = "";
$(function() {
	reWindowSize();
	$("#wlCode_search").rlCombobox();
	

	
	
	
	
	
	 $(".easyui-datetimebox").datetimebox({
    	stopFirstChangeEvent: true,
	    onChange: function() {
	        var options = $(this).datetimebox('options');
	        if(options.stopFirstChangeEvent) {
	            options.stopFirstChangeEvent = false;
	            return;
	        }
	        func_search();
	    }

    
     });

	
	var myDate = new Date();
	var nowdate = myDate.getFullYear()+"-"+
				 ((myDate.getMonth() + 1)<10?"0"+(myDate.getMonth() + 1):(myDate.getMonth() + 1))+"-"+
				 (myDate.getDate()<10?"0"+myDate.getDate():myDate.getDate())+" "+
				 (myDate.getHours()<10?"0"+myDate.getHours():myDate.getHours())+":"+
				 (myDate.getMinutes()<10?"0"+myDate.getMinutes():myDate.getMinutes())+":"+
				 (myDate.getSeconds()<10?"0"+myDate.getSeconds():myDate.getSeconds());
				 
	var startdate =  myDate.getFullYear()+"-"+
					 ((myDate.getMonth() + 1)<10?"0"+(myDate.getMonth() + 1):(myDate.getMonth() + 1))+"-"+
					 (myDate.getDate()<10?"0"+myDate.getDate():myDate.getDate())+" 00:00:00";
	
	
	$("#objStartTime_search").datetimebox('setValue',startdate);
	$("#objEndTime_search").datetimebox('setValue',nowdate);
	
	
	var erpData=func_erpUrl();
	if(erpData.remoteUrlPath!=undefined&&erpData.remoteUrlPath!=null){
		_ERPURL=erpData.remoteUrlPath
	}
	var _currentUser=func_currentUser()
	if(_currentUser.loginName!=undefined&&_currentUser.loginName!=null){
		_currentUserId=_currentUser.loginName;
	}
	
})
/**
 * 表头信息
 */

/**
 * -列表初始化
 */
 var _currentHead;
function func_creatTable(){
	debugger;
	var frozenColumns=[[ {"field":"ck","checkbox":true},
	    	             {"field":"handInput_sampleNum","title":"样品编号","width":"200","align":"center","sortable":true,"editor":{ "type":"textbox","options":{required:true}}}
	    	            ]];
	var  head = [[
	              {"field":"handInput_dataTime","title":"分析日期","width":"180","align":"center","sortable":true,"formatter":formateRowBxyTime,"editor":{ "type":"datetimebox","options":{required:true}}},
	              {"field": "belongcompany", "title": "所属厂", "width": "100", "align": "center","sortable":true,
	            	 
	                  "editor": {
	                      "type": "combobox",
	                      "options": {
	                          "valueField": "id",
	                          "textField": "text",
	                          "url": getContextPath()+"/page/testDataSrc/shujshTq/json/belongcompany.json",
	                          "required": true
	                      }
	                  }
                  },
	              {"field":"dataStatus","title":"状态","width":"100","align":"center","sortable":false}
	             ]];
	
	
	
	
	
	
	
	
	var _wlCode= $("#wlCode_search").combobox('getValue');
	_currentwlCode = _wlCode;
	$.ajax({
		url : getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_wlHead.action?wlCode="+encodeURIComponent(encodeURIComponent(_wlCode)),
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			debugger;
			_currentHead=DATA;
				$.each(DATA,function(i,v){
						var _h={"field" : v.fieldName,
								"title" :"<font  style='font-weight: bold;color: #000'>"+v.keyName+"</font>",
		            			"width" : 80,
		            			"halign" : "center",
		            			"align" : "left",
		            			"sortable" : false
							}
						if(v.fieldName.indexOf("handInput_")!=-1 ){
								_h={"field" : v.fieldName,
									"title" :"<font  style='font-weight: bold;color: red'>"+v.keyName+"</font>",
			            			"width" : 80,
			            			"halign" : "center",
			            			"align" : "left",
			            			"sortable" : false,
			            			"editor":{ "type":"textbox"}
								}
							}
						if(v.fieldName.indexOf("tanliu_S")!=-1 ){//碳硫仪保留3位
							_h={"field" : v.fieldName,
									"title" :"<font  style='font-weight: bold;color: #000'>"+v.keyName+"</font>",
			            			"width" : 80,
			            			"halign" : "center",
			            			"align" : "left",
			            			"formatter" :formate3num,
			            			"sortable" : false
								}
						}
					head[0].push(_h);
				  
				});
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	
	  head[0].push({"field":"recordTime","title":"操作时间","width":"140","align":"center","sortable":false,"formatter":formateTime});
      head[0].push({"field":"recordUserName","title":"操作人","width":"100","align":"center","sortable":false});
      
      console.log(head);
      debugger;
      $('#datagrid').datagrid({
		url : getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_listFly.action",
		toolbar : '#tb',
		collapsible : false,
		pagination : false,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true,
		singleSelect:true,
		selectOnCheck : true,
		ctrlSelect : false,
		onClickRow: onClickCell,
		onEndEdit: onEndEdit,
		queryParams : {  //参数传递
			wlCode:$("#wlCode_search").combobox('getValue'),
			companyType:companyType,
			/*dataStatus:$("#dataStatus_search").combobox('getValue'),*/
			objStartTime: $("#objStartTime_search").datetimebox('getValue'),
			objEndTime: $("#objEndTime_search").datetimebox('getValue'),
			sampleNum:$("#sampleNum_search").textbox('getValue')
		},
		frozenColumns:frozenColumns,
		columns : head
	});
}
 

/**
 * 检索
 * @param gridId
 */
function func_search(){
	
	var combobolistvalue  = $('#wlCode_search').combobox('getValue');
	debugger;
	if (combobolistvalue.match(/^[ ]*$/)) {
		$.messager.alert('信息','物料名称下拉框必须选择一个。','info');
	}else{
		$('#datagrid').datagrid('load',{
			wlCode:$("#wlCode_search").combobox('getValue'),
			companyType:companyType,
			/*dataStatus:$("#dataStatus_search").combobox('getValue'),*/
			objStartTime: $("#objStartTime_search").datetimebox('getValue'),
			objEndTime: $("#objEndTime_search").datetimebox('getValue'),
			sampleNum:$("#sampleNum_search").textbox('getValue')
		}); 
	}
  

	
}
  







var editIndex = undefined;
function endEditing(){
	debugger;
	if (editIndex == undefined){return true}
	if ($('#datagrid').datagrid('validateRow', editIndex)){
		$('#datagrid').datagrid('endEdit', editIndex);
		
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

//if(editIndex!=undefined){
//	  func_saveRow(editIndex);
//	}
function onEndEdit(index, row){
	func_saveRow(index);
}

function onClickCell(index, field){
	var row = $('#datagrid').datagrid('getSelected');
	if(row.dataStatus!="已发送"){
		debugger;
		if (editIndex != index){
			if (endEditing()){
				$('#datagrid').datagrid('selectRow', index).datagrid('beginEdit', index);
				var ed = $('#datagrid').datagrid('getEditor', {index:index,field:field});
				if (ed){
					($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				}
				editIndex = index;
			} else {
				setTimeout(function(){$('#datagrid').datagrid('selectRow', editIndex);},0);
			}
		}
	}else{
		alert("已发送，无法编辑！");
	}
}


function bxyreload(){
	
	$('#datagrid').datagrid('reload');//刷新
}





function func_add(){
	 if (endEditing()){
		$('#datagrid').datagrid('appendRow',{});
		editIndex = $('#datagrid').datagrid('getRows').length-1;
		$('#datagrid').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
	 }
}
function func_del(){
	var row= $('#datagrid').datagrid('getSelected');
	if(!row){
		$.messager.alert('信息','请先选择要删除的记录。','info'); 
		return;
	}
	if(row.id==undefined||row.id==""){
		$('#datagrid').datagrid('deleteRow',editIndex);
	}else{ 
		$.messager.confirm('删除', '确定删除数据么?', function(r){
			if (r){
				  $.messager.progress({title:'请等待',msg:'删除数据中...'});
				$.ajax( {
					url : getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_del.action",
					type : "post",
					dataType : "json",
					data : "recordId=" + row.id,
					async : true,
					success : function(DATA, request, settings) {
						 $.messager.progress('close');
						 if(DATA.STATUS=='SUCCESS'){
							 	func_msg_info(DATA.RETURN_DATA); 
								func_search()
						 }else{
							 func_msg_info(DATA.RETURN_DATA); 
						 }
					},
					error : function(event, request, settings) {
						 func_msg_error("网络异常!");
						 }
				});
			}
		});
	}
		
	
}
function func_accept(){
	debugger;
	if (endEditing()){
		$('#datagrid').datagrid('acceptChanges');
	}
}
/**
 * 
 * @param index
 */
function func_saveRow(_index){
	debugger;
	var rows=$("#datagrid").datagrid("getRows");
	rows[_index]["currentwlCode"] = _currentwlCode;
	var _temData= JSON.stringify(rows[_index]);
	_temData=encodeURI(encodeURI(_temData));
	$.ajax( {
		url : getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_updateHand.action",
		type : "post",
		dataType : "json",
		data : "rowsData="+_temData,
		async : true,
		success : function(DATA, request, settings) {
			 $.messager.progress('close');
			 if(DATA.STATUS=='SUCCESS'){
				 func_msg_info(DATA.RETURN_DATA);
 		    //	 func_search();
			 }else{
				 func_msg_info(DATA.RETURN_DATA); 
			 }
		},
		error : function(event, request, settings) {
			 func_msg_error("网络异常!");
			 }
	});
}
$.fn.rlCombobox = function(wlType,defaultVal){
	this.combobox({    
 	    url: getContextPath() + "/zjsys_basics/dataWlInfoAction_comboboxWlNotDgAll.action?companyType="+encodeURIComponent(encodeURIComponent(companyType)),//?wlType=燃料",  
 	    panelWidth:200,
	    panelHeight:300,
 	    valueField:'wlCode',    
	    textField:'wlName',
	    required:true,
	    filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q)>= 0;
		},
	    onLoadSuccess:function(){
	    	debugger;
	 	  if(defaultVal!=undefined){
	 			
            
 	    		$(this).combobox('select',defaultVal);
 	    		$(this).combobox('setValue',defaultVal);
 	    		
 	    		
 	    	
 	    	
	 	  }else{
	 		  	xialakuangdata= $(this).combobox("getData");
 	    	    if (xialakuangdata.length > 0) {
 	    	    	
	                $(this).combobox('select', xialakuangdata[0].wlCode);
	                $(this).combobox('setValue',xialakuangdata[0].wlCode);
	                  
	                
	             
	            	
	            	
	            	
	              	
                }
	 	  }
	 	  
	 	  
	 	},
	 	onChange:function(newValue, oldValue){
	 		
	 		
	 	},
	 	onSelect:function(record){
	 		func_creatTable();
	 	}
		
 	});
}

function func_fly(){
	debugger;
	var row= $('#datagrid').datagrid('getSelected');
	if(!row){
		$.messager.alert('信息','请先选择要删除的记录。','info'); 
		return;
	}
	if(row.dataStatus=="已发送"){
		$.messager.alert('信息','数据已发送，请不要重复发送。','info'); 
		return;
	}
	var flyData={};
	flyData["sampleNum"]=row["handInput_sampleNum"];
	flyData["dataTime"]=row["handInput_dataTime"];
	flyData["sampleType"]=$("#wlCode_search").combobox("getText");;//名称
	flyData["recordTime"]=row["recordTime"];
	flyData["recordUserName"]=_currentUserId;
	var _sjData={};
	$.each(_currentHead,function(i,v){
		_sjData[v.keyName]=row[v.fieldName]
		 if(v.fieldName.indexOf("tanliu_S")!=-1 ){
			 _sjData[v.keyName]=formate3num(row[v.fieldName]);
		 }
		
	});
	flyData["data"]=_sjData;
	var _data=new Array();
	_data[0]=flyData;
	$.messager.confirm('发送', '确定发送数据么?', function(r){
		if (r){
			  $.messager.progress({title:'请等待',msg:'发送数据中...'});			 
				$.ajax({
					url:_ERPURL+"/api/hys.php",
					type : "post",
					dataType : "text",
					data :"flyData="+JSON.stringify(_data),
					contentType: "application/x-www-form-urlencoded",
					async : false,
					success : function(DATA, request, settings) {
						if(DATA=="true"){
							func_saveReturnData(row.id);
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



function func_saveReturnData(rowId){		
	$.ajax( {
		url: getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_fly.action",
		type : "post",
		dataType : "json",
		data : "recordId=" + rowId,
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

/*导出数据到excell*/
function func_daocexcell(){
	//var rows=$("#datagrid").datagrid("getRows");
	
	
	/*
	 * wlCode:$("#wlCode_search").combobox('getValue'),
			companyType:companyType,
			
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			objEndTime: $("#objEndTime_search").datebox('getValue'),
			sampleNum:$("#sampleNum_search").textbox('getValue')
	 */
	var rows  = {};
	rows["currentwlCode"] = _currentwlCode;
	rows["wlCode"] = $("#wlCode_search").combobox('getValue');
	rows["companyType"] = companyType;
	rows["objStartTime"] = $("#objStartTime_search").datetimebox('getValue');
	rows["objEndTime"] = $("#objEndTime_search").datetimebox('getValue');
	rows["sampleNum"] = $("#sampleNum_search").textbox('getValue');
	var _temData= JSON.stringify(rows);
	_temData=encodeURI(encodeURI(_temData));
	
	
	window.location.href = getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_downloadexcell.action?rowsData="+_temData;
	/*$.ajax( {
		url : getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_downloadexcell.action",
		type : "post",
		dataType : "json",
		data : "rowsData="+_temData,
		async : true,
		success : function(DATA, request, settings) {
			 $.messager.progress('close');
			 if(DATA.STATUS=='SUCCESS'){
				
			 }else{
				
			 }
		}
	});*/
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
