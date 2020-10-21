$(function() {
	reWindowSize();
	initDataGrid();
})
/**
 * 表头信息
 */
var head = [ [ {
	"field" : "ck",
	"checkbox" : true
}, {
	"field" : "loginName",
	"title" : "登录账户",
	"width" : 100,
	"align" : "center",
	"sortable" : true
}, {
	"field" : "userName",
	"title" : "真实姓名",
	"width" : 100,
	"align" : "center",
	"sortable" : true
}, {
	"field" : "userSex",
	"title" : "性别",
	"width" : 100,
	"align" : "center",
	"sortable" : false
},{
	"field" : "parentDepName",
	"title" : "部门名称",
	"width" : 200,
	"align" : "center",
	"sortable" : true,
	"formatter" :function(value,row,index){
		if(row.depName.indexOf('组')>=0){
			return row.parentDepName;
		}else{
			return row.depName;
		}
	}
	
}, {
	"field" : "depName",
	"title" : "试验组",
	"width" : 200,
	"align" : "center",
	"sortable" : true,
	"formatter" :function(value,row,index){
		if(value.indexOf('组')>=0){
			return value;
		}
	}
},{
	"field" : "workName",
	"title" : "职务",
	"width" : 100,
	"align" : "center",
	"sortable" : false
}, {
	"field" : "userBrithday",
	"title" : "生日",
	"width" : 100,
	"align" : "center",
	"sortable" : false
},  {
	"field" : "uploadFlag",
	"title" : "是否上传电子签名",
	"width" : 100,
	"align" : "center",
	"sortable" : false,
	"formatter" :function(value,row,index){
		if(value>0){
			return "已上传";
		}else{
			return "未上传";
		}
	}
},  {
	"field" : "flag",
	"title" : "是否启用",
	"width" : 100,
	"align" : "center",
	"sortable" : false
}  ] ];
/**
 * 管理角色 start
 * @param index
 */
function func_Role(userId,userName){
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '管理 &nbsp;<font color="red">'+userName+' </font> &nbsp;角色',
         width : 450,
         height : 350,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/system/SystemUser/userRole.html',
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         onLoad:function(){
        	    $.ajax({
					url : getContextPath() + "/tried_system/systemUserAction_userGetRole.action",
					type : "post",
					dataType : "json",
				    async:false,  
					data :'id='+userId,
			        success: function(DATA, request, settings){
			        	if(DATA.STATUS='SUCCESS'){
			        		var leftData=DATA.RETURN_DATA.leftMultiple;
			        		var rightData=DATA.RETURN_DATA.rightMultiple; 
				          for(var leftkey in leftData){ 
				             $('#leftRole').append("<option value='"+leftkey+"'>"+leftData[leftkey]+"</option>");   
				          }
				          for(var rightkey in rightData){ 
				             $('#rightRole').append("<option value='"+rightkey+"'>"+rightData[rightkey]+"</option>");   
				          }
			        	}
						return true;
			        },
			        error:function (event, request, settings){
                     	 return true;
			        }
		    	});
          },
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	  $.messager.progress({title:'请等待',msg:'保存数据中...'});
            	 	var roleid="";
	     	        $('#rightRole option').each(function(i) {
	     	   		   roleid=roleid+$(this).val()+",";
	             	}); 
            	 $.ajax({
 					url : getContextPath() + "/tried_system/systemUserAction_userSetRole.action",
 					type : "post",
 					dataType : "json",
 				    async:true,  
 					data :'id='+userId+'&recordIdS='+roleid,
 			        success: function(DATA, request, settings){
 			        	$.messager.progress('close');
 			        	if(DATA.STATUS='SUCCESS'){
 			        		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: blue">保存成功</font>','info');
 			        	}
 			        	else{
 			        		$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: blue">保存失败</font>','info');
 			        	}
 						return true;
 			        },
 			        error:function (event, request, settings){
 			        	$.messager.progress('close');
                      	 return true;
 			        }
 		    	});
            	
             }
         }, {
             text : '取消',
             iconCls : 'icon-cancel',
             handler : function() {
                 $("#modelWindow").dialog('destroy');
             }
         } ],
     });
}

function singleToRight(){
    $('#leftRole option:selected').each(function(i) {
  		$('#rightRole').append("<option value='"+$(this).val()+"'>"+ $(this).text()+"</option>");  
 }); 
 $('#leftRole').find("option:selected").remove();
  }
  //向左添加一个元素
  function singleToleft(){
    $('#rightRole option:selected').each(function(i) {
  		$('#leftRole').append("<option value='"+$(this).val()+"'>"+ $(this).text()+"</option>");  
 }); 
 $('#rightRole').find("option:selected").remove();
   }
  //全部移向右
  function batchToright(){
	 $('#leftRole option').each(function(i) {
  		$('#rightRole').append("<option value='"+$(this).val()+"'>"+ $(this).text()+"</option>");  
	}); 
  	$('#leftRole').empty();
  }
   //全部移向左
  function batchToleft(){
    $('#rightRole option').each(function(i) {
  		$('#leftRole').append("<option value='"+$(this).val()+"'>"+ $(this).text()+"</option>");  
	}); 
    $('#rightRole').empty();
   }
  
/** 管理角色 end  */
  
  
/**
 * 用户信息列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/tried_system/systemUserAction_list.action",
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : {  //参数传递
			loginName : $("#loginName").textbox('getValue'),
			depName:$("#depName").textbox('getValue')
		},
		columns : head

	});
}


function func_search(){
	$('#datagrid').datagrid('load', {
		loginName : $("#loginName").textbox('getValue'),
		depName:$("#depName").textbox('getValue')
		});	
}

/**
 * 增加用户信息
 */
function func_add() {
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '添加 &nbsp;<font color="red">用户信息</font> ',
         width : 400,
         height : 300,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/system/SystemUser/manager.html',
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         onLoad:function(){
        	 $("#depId_edit").depComboTree();
        	 $("#depId_edit").combotree({
        			onClick: function(rec){
        				 $("#workId_edit").jobCombobox(rec.id);
        			}
        	 });
        	 
        	 $("#workId_edit").combobox({
      			onSelect: function(rec){
      				 $("#workJz_edit").workTreebox(rec.id);    				
    			}
      	 });       	           	        	 
        	 
         },
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/tried_system/systemUserAction_add.action",
    					onSubmit:function(param){
    						var recordIdS=$('#workJz_edit').combotree('getValues');
						    param.recordIdS=recordIdS;
    						var isValid = $(this).form('enableValidation').form('validate');
    						console.log("isValid"+isValid);
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
    							initDataGrid();
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
         } ],
     });
}
//可编译的上传
function func_file_list1(recordId) { 
 	 $.ajax({
			url : getContextPath()+'/system_file/fileManagerAction_list.action',
			type : "post",
			dataType : "json",
			data : "recordId=" + recordId,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS') {
					$.each( DATA.RETURN_DATA, function(index, value){
						$.each(value, function(k, v){
							 if(k=="pkColumn"){
								 var _r_fileName=value['filename'];
								 var _r_id=value['id'];
								 var _r_pkId=value['pkId'];
								 $("#system_user_sign_name_edit").val(_r_id);
								 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+'</br><img width="100"   src="'+getContextPath() + '/system_file/fileManagerAction_downFile.action?id=' + _r_id+'" > </img></td></tr>';
								
								 $("#"+v).append(_tr) ;
							 }
						 });
					});
				}  
			},
			error : function(event, request, settings) {
				func_msg_error("网络异常!");
			}
		});
} 

/**
 * 编辑用户信息
 */
function func_edit(){
	var row = $('#datagrid').datagrid('getSelected');
	if(!row){
		$.messager.alert('信息','请先选择要更新的记录。','info'); 
		return;
	}
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '编辑用户信息',
         width : 400,
         height : 350,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/system/SystemUser/manager.html',
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         
         onLoad:function(){
        	 $("#depId_edit").depComboTree();
        	 $("#depId_edit").combotree({
     			onClick: function(rec){
     				 $("#workId_edit").jobCombobox(rec.id);
     			}
        	 });
        	 debugger;
        	 $("#workId_edit").jobCombobox(row.depId);
        	 
        	 $("#workId_edit").combobox({
        			onSelect: function(rec){
        				 $("#workJz_edit").workTreebox(rec.id);    				
      			} 
         	 });
         	 $("#workJz_edit").workTreebox(row.workId);   
         	 $("#workJz_edit").combotree("setValues", getPartWork(row.id));
        	 debugger;
        	 $('#modelForm').form('load',row);
        	 func_file_list1(row.id);
          },
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/tried_system/systemUserAction_edit.action",
    					onSubmit:function(param){
    						var recordIdS=$('#workJz_edit').combotree('getValues');
    					
						    param.recordIdS=recordIdS;	
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
    							initDataGrid();
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

function getPartWork(userId){
	var userData=[];
	$.ajax({
		url : getContextPath() + "/tried_system/systemUserAction_partWork.action",
		type : "post",
		dataType : "json",
		data : "id=" + userId,
		async : false,
		success : function(DATA, request, settings) {		
				
				for(var i=0;i<DATA.length;i++){				
					userData.push(DATA[i].workId);
				}           				 					
		},
		error : function(event, request, settings) {
			 func_msg_error("网络异常!");
			 }
	});
	 
	return userData;
}


function getAllWorksAndUser(){
	 var works={};	 
		$.ajax({
			url : getContextPath() + "/tried_system/systemUserAction_partWork.action",
			type : "post",
			dataType : "json",
			async : false,
			success : function(DATA, request, settings) {					
				for(var i=0;i<DATA.length;i++){					
					 var userId=DATA[i].id;
					 if(works[userId]==undefined||works[userId]==""){
						 works[userId]=DATA[i].workName+",";
					 }else{
						 works[userId]=works[userId]+DATA[i].workName+",";						 
					 }
				 }									         				 					
			},
			error : function(event, request, settings) {
				 func_msg_error("网络异常!");
				 }
		});
		return works; 
}


/**
 * 删除用户信息
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
				url : getContextPath() + "/tried_system/systemUserAction_del.action",
				type : "post",
				dataType : "json",
				data : "recordIdS=" + rowId,
				async : true,
				success : function(DATA, request, settings) {
					 $.messager.progress('close');
					 if(DATA.STATUS=='SUCCESS'){
						 func_msg_info(DATA.RETURN_DATA);
							 initDataGrid();
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


function func_ret(){
	var row = $('#datagrid').datagrid('getSelected');
	if(!row){
		$.messager.alert('信息','请先选择要更新的记录。','info'); 
		return;
	}
	$.messager.confirm('重置密码', '确定重置密码数据么?', function(r){
		if (r){
			  $.messager.progress({title:'请等待',msg:'数据处理中...'});
			$.ajax( {
				url : getContextPath() + "/tried_system/systemUserAction_resetPass.action",
				type : "post",
				dataType : "json",
				data : "recordId=" + row.id,
				async : true,
				success : function(DATA, request, settings) {
					 $.messager.progress('close');
					 if(DATA.STATUS=='SUCCESS'){
						 func_msg_info(DATA.RETURN_DATA);
							 initDataGrid();
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
