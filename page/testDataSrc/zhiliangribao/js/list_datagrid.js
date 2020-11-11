
var _currentUserId="";
var _currentwlCode="";
var companyType = "";
var countload = 0; 
$(function() {
	countload_jishu();
	
	$("#objStartTime_search").datebox({
		onSelect: function(date){
             validateDateTime('objStartTime_search','objEndTime_search','objStartTime_search');
             func_search();
         }
	})
	
	
	$("#objEndTime_search").datebox({
		onSelect: function(date){
             validateDateTime('objStartTime_search','objEndTime_search','objEndTime_search');
             func_search();
        }
	})
	$("#objStartTime_search").datebox('setValue',func_IecurrentData());
	$("#objEndTime_search").datebox('setValue',func_IecurrentData());
	
	var erpData=func_erpUrl();
	if(erpData.remoteUrlPath!=undefined&&erpData.remoteUrlPath!=null){
		_ERPURL=erpData.remoteUrlPath
	}
	var _currentUser=func_currentUser()
	if(_currentUser.loginName!=undefined&&_currentUser.loginName!=null){
		_currentUserId=_currentUser.loginName;
	}
	
	
	reWindowSize();
	
})

function countload_jishu(){
	if(countload == 0){
		countload = 1;
	}else{
		func_creatTable();
	}
}




/**
 * -列表初始化
 */
 var _currentHead;
function func_creatTable(){
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
	                          "url": getContextPath()+"/page/testDataSrc/zhiliangribao/json/belongcompany.json",
	                          "required": true
	                      }
	                  }
                  },
	              {"field":"dataStatus","title":"状态","width":"100","align":"center","sortable":false}
	             ]];
	var _wlCode=parent.currentCode;
	_currentwlCode = _wlCode;
	$.ajax({
		url : getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_wlHead.action?wlCode="+encodeURIComponent(encodeURIComponent(_wlCode)),
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
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
      
     
	$('#datagrid').datagrid({
		url : getContextPath() + "/zjsys_testDataSrc/zjHandInputFlyAction_listFly.action",
		toolbar : '#tb',
		collapsible : false,
		pagination : true,//分页
		rownumbers : true, //行号
		striped : true,    //各行换色
		checkOnSelect : true,
		singleSelect:false,
		selectOnCheck : true,
		ctrlSelect : false,
		fitColumns:true,//宽度自适应
		queryParams : {  //参数传递
			wlCode:_currentwlCode,
			companyType:companyType,
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
		objStartTime: $("#objStartTime_search").datebox('getValue'),
		objEndTime: $("#objEndTime_search").datebox('getValue'),
		sampleNum:$("#sampleNum_search").textbox('getValue')
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
	    input2.attr("value",$("#wlCode_search").combobox('getValue'));//设置参数值
	    
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
}).resize();
/**
 * 页面缩放自适应大小
 */
function reWindowSize() {
	var _parentId = $(window.parent.document).find("#tabContent");
	var _height = $(_parentId).height() - 48;
	$('#treeDiv').layout({    
		height : _height
	});  
}
