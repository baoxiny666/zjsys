var _ERPURL="http://10.1.0.22";
var _currentUserId="";
var _currentwlCode="";
var companyType = "";
//父节点id
var pId=0;
//父节点名称
var pName="";

var currentCode = "";

$(function() {
	initTree();
	
	$("#objStartTime_search").datebox({
		onChange: function(date){
             validateDateTime('objStartTime_search','objEndTime_search','objStartTime_search');
             func_search();
         }
	})
	
	
	$("#objEndTime_search").datebox({
		onChange: function(date){
             validateDateTime('objStartTime_search','objEndTime_search','objEndTime_search');
             func_search();
        }
	})
	$("#objStartTime_search").datebox('setValue',func_IecurrentData());
	$("#objEndTime_search").datebox('setValue',func_IecurrentData());
	
	
	reWindowSize();
	reWindowSize_tree();
})


/**
 * 初始化功能管理结构树
 */
function initTree(){
	var setting = {
			edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false,
				drag:{
					 isMove:true ,
					 isMove:true
				}
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick : reSetContent
			}
		};
	
		var zNodes =new Array();
		$.ajax({
			url : getContextPath() + "/zjsys_testDataSrc/zhilrbYuanlAction_zhilrbInitMenu.action",
			type : "post",
			dataType : "json",
			data:{"belongcompany":companyfactorynew},
			async : false,
			success : function(DATA, request, settings) {
				if(DATA.STATUS=='SUCCESS'){
					zNodes=DATA.RETURN_DATA;
					$.each(zNodes, function(index) { 
						if(zNodes[index].icon!=''){
						  zNodes[index].icon=getContextPath() +'/img/icon/'+zNodes[index].icon; 
						}
					});
					
					
					var menuzhiltree =  $.fn.zTree.init($("#menuTree"), setting, zNodes);
					
					var menuzhilnode = menuzhiltree.getNodes()[0].children[0].children[0];
					menuzhiltree.selectNode(menuzhilnode);
					setting.callback.onClick(null, menuzhiltree.setting.treeId, menuzhilnode);
					
					
					
					
					
					
				}
			},
			error : function(event, request, settings) {
				$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
			}
		});
		
}




/**
 * 单击事件
 * @param event
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
function reSetContent(event, treeId, treeNode, clickFlag) {	
	pId=treeNode.id;
	pName=treeNode.name;
	if(treeNode.isParent == true){
		console.log("此节点为长辈目录，不能点击");
	}else{
		currentCode = pName;

		
		func_creatTable();  
		
		
	}
	
	
}






 
function doSearch(value){
	var treeId="menuTree";
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var selectnodes = treeObj.getSelectedNodes();
	for ( var i = 0; i < selectnodes.length; i++) {
		treeObj.cancelSelectedNode(selectnodes[i]);
		
	}
	if (value != "") {
		var nodes = treeObj.getNodesByParamFuzzy("name", value, null);
		for ( var i = 0; i < nodes.length; i++) {
			treeObj.selectNode(nodes[i], true);
		}
	}
}




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
	var _wlCode= currentCode;
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
			            			"editor": {
			            				 "type":"textbox",
			                             "options": {
			                                 "validType": "isNumber"
			                             }
			                         }
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
			wlCode:_currentwlCode,
			companyType:companyType,
			/*dataStatus:$("#dataStatus_search").combobox('getValue'),*/
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			objEndTime: $("#objEndTime_search").datebox('getValue'),
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
	

	$('#datagrid').datagrid('load',{
		wlCode:_currentwlCode,
		companyType:companyType,
		/*dataStatus:$("#dataStatus_search").combobox('getValue'),*/
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		objEndTime: $("#objEndTime_search").datebox('getValue'),
		sampleNum:$("#sampleNum_search").textbox('getValue')
	}); 
	
  

	
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

	

	
	
	
	 var form=$("<form>"); //定义一个form表单,通过form表单来发送请求
	    form.attr("style","display:none");  //设置表单状态为不显示
	    form.attr("method","post");//method属性设置请求类型为post
	    form.attr("action",getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_downloadexcell.action");//action属性设置请求路径，请求类型是post时,路径后面跟参数的方式不可用，可以通过表单中的input来传递参数
	    $("body").append(form);//将表单放置在web中
	    var input1=$("<input>"); //在表单中添加input标签来传递参数，如有多个参数可添加多个input标签
	    input1.attr("type","hidden");//设置为隐藏域
	    input1.attr("name","currentwlCodeString");//设置参数名称
	    input1.attr("value",_currentwlCode);//设置参数值
	
	    
	    var input2=$("<input>"); //
	    input2.attr("type","hidden");//设置为隐藏域
	    input2.attr("name","wlCodeString");//设置参数名称
	    input2.attr("value",_currentwlCode);//设置参数值
	    
	    var input3=$("<input>"); //
	    input3.attr("type","hidden");//设置为隐藏域
	    input3.attr("name","companyTypeString");//设置参数名称
	    input3.attr("value",companyType);//设置参数值
	    
	    var input4=$("<input>"); //
	    input4.attr("type","hidden");//设置为隐藏域
	    input4.attr("name","objStartTimeString");//设置参数名称
	    input4.attr("value",$("#objStartTime_search").datebox('getValue'));//设置参数值
	    
	    var input5=$("<input>"); //
	    input5.attr("type","hidden");//设置为隐藏域
	    input5.attr("name","objEndTimeString");//设置参数名称
	    input5.attr("value",$("#objEndTime_search").datebox('getValue'));//设置参数值
	    
	    var input6=$("<input>"); //
	    input6.attr("type","hidden");//设置为隐藏域
	    input6.attr("name","sampleNumString");//设置参数名称
	    input6.attr("value",$("#sampleNum_search").textbox('getValue'));//设置参数值
	    
	    form.append(input1).append(input2).append(input3).append(input4).append(input5).append(input6);//添加到表单中
	    form.submit();//表单提交
	

}

/*开始时间要小于结束时间*/

function validateDateTime(beginTimeId,endTimeId,whichTimeId){
	
	var myDate = new Date();
	var nowdate = myDate.getFullYear()+"-"+
				 ((myDate.getMonth() + 1)<10?"0"+(myDate.getMonth() + 1):(myDate.getMonth() + 1))+"-"+
				 (myDate.getDate()<10?"0"+myDate.getDate():myDate.getDate());
				 
	var startdate =  myDate.getFullYear()+"-"+
					 ((myDate.getMonth() + 1)<10?"0"+(myDate.getMonth() + 1):(myDate.getMonth() + 1))+"-"+
					 (myDate.getDate()<10?"0"+myDate.getDate():myDate.getDate());
	
    var v1=$('#'+beginTimeId).datebox("getValue").replace(/-/g,'/');
    console.log(v1);
    var date1 = new Date(v1);
    var date1Srting = date1.getTime();
    var v2=$('#'+endTimeId).datebox("getValue").replace(/-/g,'/');
    console.log(v2);
    var date2 = new Date(v2);
    var date2Srting = date2.getTime();
    
    if(v1==''||v2==''){
        return true;
    }    
    
    
    if(date1Srting<date2Srting){
        console.log(date1+"<"+date2);
      
        return true;
    }else if(date1Srting == date2Srting){
    	  console.log(date1+"<"+date2);
          
          return true;
    }else{
    	if(whichTimeId == "objStartTime_search"){
    			
        	 $('#'+whichTimeId).datebox("setValue",startdate);
        	 $.messager.alert('提示','开始时间要小于或等于结束时间！');
        }else{
        	$('#'+whichTimeId).datebox("setValue",nowdate);
        	 $.messager.alert('提示','结束时间要大于或等于开始时间！');
        }
    }
   
    
    return false;       
}

function DateDiff(date11,date22){
	return Math.floor((date22 - date11) / 1000 / 60 / 60 / 24);
}
/**
 * 页面缩放监听事件
 */
$(window).resize(function() {
	reWindowSize();
	reWindowSize_tree();
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


/**
 * 页面缩放自适应大小
 */
function reWindowSize_tree() {
	var _parentId = $(window.parent.document).find("#tabContent");
	var _height = $(_parentId).height() - 48;
	$('#treeDiv').layout({    
		height : _height
	});  
}