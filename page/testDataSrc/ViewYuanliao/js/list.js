$(function() {
	reWindowSize();
	initDataGrid();
})
/**
 * 表头信息
 */
var head = [[{"field":"ck","checkbox":true},{"field":"recordTime","title":"recordTime","width":"140","align":"center","sortable":false,"formatter":"formateTime"},{"field":"dataStatus","title":"dataStatus","width":"140","align":"center","sortable":false},{"field":"recordUserName","title":"操作人","width":100,"align":"center","sortable":false},{"field":"handInput_sampleNum","title":"handInput_sampleNum","width":"140","align":"center","sortable":false},{"field":"handInput_dataTime","title":"handInput_dataTime","width":"140","align":"center","sortable":false},{"field":"handInput_burnLoss","title":"handInput_burnLoss","width":"140","align":"center","sortable":false},{"field":"handInput_h2o","title":"handInput_h2o","width":"140","align":"center","sortable":false},{"field":"handInput_h2o1","title":"handInput_h2o1","width":"140","align":"center","sortable":false},{"field":"handInput_h2o2","title":"handInput_h2o2","width":"140","align":"center","sortable":false},{"field":"handInput_fu200mm","title":"handInput_fu200mm","width":"140","align":"center","sortable":false},{"field":"handInput_feo","title":"handInput_feo","width":"140","align":"center","sortable":false},{"field":"handInput_m6_3","title":"handInput_m6_3","width":"140","align":"center","sortable":false},{"field":"handInput_m0_5","title":"handInput_m0_5","width":"140","align":"center","sortable":false},{"field":"handInput_fu200mu","title":"handInput_fu200mu","width":"140","align":"center","sortable":false},{"field":"handInput_reactivity","title":"handInput_reactivity","width":"140","align":"center","sortable":false},{"field":"handInput_gjcs_rate","title":"handInput_gjcs_rate","width":"140","align":"center","sortable":false},{"field":"handInput_mm0_5","title":"handInput_mm0_5","width":"140","align":"center","sortable":false},{"field":"handInput_ralone","title":"handInput_ralone","width":"140","align":"center","sortable":false},{"field":"handInput_mfe","title":"handInput_mfe","width":"140","align":"center","sortable":false},{"field":"handInput_dy_80mm","title":"handInput_dy_80mm","width":"140","align":"center","sortable":false},{"field":"handInput_xy25mm","title":"handInput_xy25mm","width":"140","align":"center","sortable":false},{"field":"handInput_ca_oh2","title":"handInput_ca_oh2","width":"140","align":"center","sortable":false},{"field":"handInput_fu10mm","title":"handInput_fu10mm","width":"140","align":"center","sortable":false},{"field":"handInput_nahco3","title":"handInput_nahco3","width":"140","align":"center","sortable":false},{"field":"handInput_ph","title":"handInput_ph","width":"140","align":"center","sortable":false},{"field":"handInput_sic","title":"handInput_sic","width":"140","align":"center","sortable":false},{"field":"handInput_ylc","title":"handInput_ylc","width":"140","align":"center","sortable":false},{"field":"handInput_pzrong","title":"handInput_pzrong","width":"140","align":"center","sortable":false},{"field":"handInput_jzjia","title":"handInput_jzjia","width":"140","align":"center","sortable":false},{"field":"handInput_xs_rate","title":"handInput_xs_rate","width":"140","align":"center","sortable":false},{"field":"handInput_xl_size","title":"handInput_xl_size","width":"140","align":"center","sortable":false},{"field":"handInput_mtshi","title":"handInput_mtshi","width":"140","align":"center","sortable":false},{"field":"handInput_palone","title":"handInput_palone","width":"140","align":"center","sortable":false},{"field":"handInput_salone","title":"handInput_salone","width":"140","align":"center","sortable":false},{"field":"handInput_calone","title":"handInput_calone","width":"140","align":"center","sortable":false},{"field":"handInput_si","title":"handInput_si","width":"140","align":"center","sortable":false},{"field":"handInput_mn","title":"handInput_mn","width":"140","align":"center","sortable":false},{"field":"handInput_al","title":"handInput_al","width":"140","align":"center","sortable":false},{"field":"handInput_ca","title":"handInput_ca","width":"140","align":"center","sortable":false},{"field":"handInput_ba","title":"handInput_ba","width":"140","align":"center","sortable":false},{"field":"tanliu_C","title":"tanliu_C","width":"140","align":"center","sortable":false},{"field":"tanliu_S","title":"tanliu_S","width":"140","align":"center","sortable":false},{"field":"yingguang_test_TFe","title":"yingguang_test_TFe","width":"140","align":"center","sortable":false},{"field":"yingguang_test_SiO2","title":"yingguang_test_SiO2","width":"140","align":"center","sortable":false},{"field":"yingguang_test_CaO","title":"yingguang_test_CaO","width":"140","align":"center","sortable":false},{"field":"yingguang_test_MgO","title":"yingguang_test_MgO","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Al2O3","title":"yingguang_test_Al2O3","width":"140","align":"center","sortable":false},{"field":"yingguang_test_MnO","title":"yingguang_test_MnO","width":"140","align":"center","sortable":false},{"field":"yingguang_test_TiO2","title":"yingguang_test_TiO2","width":"140","align":"center","sortable":false},{"field":"yingguang_test_V2O5","title":"yingguang_test_V2O5","width":"140","align":"center","sortable":false},{"field":"yingguang_test_P","title":"yingguang_test_P","width":"140","align":"center","sortable":false},{"field":"yingguang_test_S","title":"yingguang_test_S","width":"140","align":"center","sortable":false},{"field":"yingguang_test_K2O","title":"yingguang_test_K2O","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Na2O","title":"yingguang_test_Na2O","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Co2O3","title":"yingguang_test_Co2O3","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Pb","title":"yingguang_test_Pb","width":"140","align":"center","sortable":false},{"field":"yingguang_test_As","title":"yingguang_test_As","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Zn","title":"yingguang_test_Zn","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Cu","title":"yingguang_test_Cu","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Ni","title":"yingguang_test_Ni","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Cr2O3","title":"yingguang_test_Cr2O3","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Au2O","title":"yingguang_test_Au2O","width":"140","align":"center","sortable":false},{"field":"yingguang_test_R2","title":"yingguang_test_R2","width":"140","align":"center","sortable":false},{"field":"yingguang_test_Fe2O3","title":"yingguang_test_Fe2O3","width":"140","align":"center","sortable":false}]];
/**
 * -列表初始化
 */
function initDataGrid() {
	$('#datagrid').datagrid({
		toolbar : '#tb',
		url : getContextPath() + "/zjsys_testDataSrc/viewYuanliaoAction_list.action",
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		queryParams : {  //参数传递
			id : $("#id").val()
		},
		columns : head

	});
}

/**
 * 检索
 * @param gridId
 */
function func_search(){
	$('#datagrid').datagrid('load',{
		id : $("#id").val()
	}); 
}
/**
 * 增加-信息
 */
function func_add() {
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '添加 &nbsp;<font color="red">-</font> ',
         width : 400,
         height : 300,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/testDataSrc/ViewYuanliao/manager.html',
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/zjsys_testDataSrc/viewYuanliaoAction_add.action",
    					onSubmit:function(){
    						var isValid = $(this).form('enableValidation').form('validate');
    						if (!isValid){
    							$.messager.progress('close');
    						}
    						return isValid;	
    					},
    					success: function(data){
    						var resultData = jQuery.parseJSON(data); 
    						$.messager.progress('close');
    						$("#modelWindow").dialog('destroy');
    						if(resultData.STATUS=="SUCCESS") {
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
         } ],
     });
}


/**
 * 编辑-信息
 */
function func_edit(){
	var row = $('#datagrid').datagrid('getSelected');
	if(!row){
		$.messager.alert('信息','请先选择要更新的记录。','info'); 
		return;
	}
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '编辑 &nbsp;<font color="red">-</font> ',
         width : 400,
         height : 300,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/testDataSrc/ViewYuanliao/manager.html',
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         
         onLoad:function(){
        	 $('#modelForm').form('load',row);
          },
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/zjsys_testDataSrc/viewYuanliaoAction_edit.action",
    					onSubmit:function(){
    						var isValid = $(this).form('enableValidation').form('validate');
    						if (!isValid){
    							$.messager.progress('close');
    						}
    						return isValid;	
    					},
    					success: function(data){
    						var resultData = jQuery.parseJSON(data); 
    						$.messager.progress('close');
    						$("#modelWindow").dialog('destroy');
    						if(resultData.STATUS=="SUCCESS") {
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
				url : getContextPath() + "/zjsys_testDataSrc/viewYuanliaoAction_del.action",
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
