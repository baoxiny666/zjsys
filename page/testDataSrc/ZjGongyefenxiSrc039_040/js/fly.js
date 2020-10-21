
/**
 * 表头信息
 */
var headFly = [[
             {"field":"ck","checkbox":true},   
             {"field":"test_syrq","title":"分析日期","width":"140","align":"center","sortable":false},       
             {"field":"sampleNum","title":"样品编号","width":"140","align":"center","sortable":false},
             {"field":"test_Mad","title":"Mad","width":"140","align":"center","sortable":false},
             {"field":"calcula_Ad","title":"Ad","width":"140","align":"center","sortable":false},
             {"field":"calcula_FCad","title":"FCad","width":"140","align":"center","sortable":false},
             {"field":"calcula_VdVdaf","title":"VdVdaf","width":"160","align":"center","sortable":false},
             {"field":"input_Mt","title":"Mt","width":"140","align":"center","sortable":false},
             {"field":"input_M25","title":"M25","width":"140","align":"center","sortable":false}, 
             {"field":"input_M10","title":"M10","width":"140","align":"center","sortable":false}, 
             {"field":"recordTime","title":"采集时间","width":"140","align":"center","sortable":false,"formatter":formateDate},
             {"field":"recordUserName","title":"操作人","width":"100","align":"center","sortable":false}]];
/**
 * -列表初始化
 */
function initFlyGrid() {
	$('#flygrid').datagrid({
		toolbar : '#tbfly',
		url : getContextPath() + "/zjsys_testDataSrc/zjGongyefenxiFlyAction_list.action",	
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true, 
		selectOnCheck : true,
		ctrlSelect : true,
		sortOrder:'desc',
		sortName:'test_syrq',	
		queryParams : {  //参数传递
			objStartTime: $("#objStartTime_search").datebox('getValue'),
			deviceNum :GydeviceNum,
			dataStatus:'已提交'
		},
		columns : headFly

	});
}

function func_search_fly(){
	$('#flygrid').datagrid('load',{
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		deviceNum : GydeviceNum,
		dataStatus:'已提交'
	}); 
}

function func_putData(){
	
	var row = $('#flygrid').datagrid('getSelected');
	if(!row){
		$.messager.alert('信息','请先选择要更新的记录。','info'); 
		return;
	}
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title :'录入&nbsp;<font color="red">化验数据</font> ',
         width :400,
         height:320,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/testDataSrc/ZjGongyefenxiSrc039_040/dataPage.html',
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },       
         onLoad:function(){
        	 $('#modelForm').form('load',row);
        	 $("#sampleNum_edit").text(row.sampleNum);
        	 $("#test_Mad_edit").text(row.test_Mad);
        	 $("#calcula_Ad_edit").text(row.calcula_Ad);
        	 $("#calcula_FCad_edit").text(row.calcula_FCad);
        	 $("#calcula_VdVdaf_edit").text(row.calcula_VdVdaf);
          },
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/zjsys_testDataSrc/zjGongyefenxiFlyAction_edit.action",
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
    							func_search_fly();
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

function accAdd(arg1,arg2){ 
	var r1,r2,m; 
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
	m=Math.pow(10,Math.max(r1,r2)) 
	return (arg1*m+arg2*m)/m 
	} 
function accDiv(arg1,arg2){ 
	var t1=0,t2=0,r1,r2; 
	try{t1=arg1.toString().split(".")[1].length}catch(e){} 
	try{t2=arg2.toString().split(".")[1].length}catch(e){} 
	with(Math){ 
	r1=Number(arg1.toString().replace(".","")) 
	r2=Number(arg2.toString().replace(".","")) 
	return (r1/r2)*pow(10,t2-t1); 
	} 
	} 
	function accMul(arg1,arg2) 
	{ 
	var m=0,s1=arg1.toString(),s2=arg2.toString(); 
	try{m+=s1.split(".")[1].length}catch(e){} 
	try{m+=s2.split(".")[1].length}catch(e){} 
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m) 
	}
 