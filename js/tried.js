
var _persionTaskName="";
//对应页面详细
var _urlMap = {
		 
}
              
var _GLOUB_ITEM_MAP={};//检测id->检测项名称
//工作流任务状态全局变量
var _GLOUB_JbpmTaskStatus="待处理";

//switchButton触发事件，fn为触发后执行的函数
function func_taskStatusEvent(fn){
	$('#taskStatus_search').switchbutton({ 
	      onChange: function(checked){
	    	  if(checked){
	    		  _GLOUB_JbpmTaskStatus=$(this).switchbutton("options").onText;
	    	  }else{
	    		  _GLOUB_JbpmTaskStatus=$(this).switchbutton("options").offText;
	    	  }
	    	  try {
	              fn = eval(fn);
	          } catch(e) {
	              alert(fn+'方法不存在！');
	          }
	          if (typeof fn === 'function'){
	              fn.call(this); 
	          }  
	      } 
	 });
}
 

$.fn.wlCombobox = function(wlType,defaultVal){
	this.combobox({    
 	    url: getContextPath() + "/zjsys_basics/dataWlInfoAction_comboboxWlAll.action?wlType="+wlType,  
 	    panelWidth:200,
	    panelHeight:300,
 	    valueField:'wlCode',    
	    textField:'wlName',
	    filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q)>= 0;
		},
	   onLoadSuccess:function(){
	 	  if(defaultVal!=undefined){
	 	    		$(this).combobox('select',defaultVal);
	 	    		$(this).combobox('setValue',defaultVal);
	 	    }else{
	 	    	 var data= $(this).combobox("getData");
	 	    	  if (data.length > 0) {
	                  $(this).combobox('select', data[0].wlCode);
	                  $(this).combobox('setValue',data[0].wlCode);
	                }
	 	    }
	 	}
 	});
}
/**日期转换(HH:mm)**/
function formateHM(value,  row, index) {
    if (value == undefined) {
        return "";
    }else{
    	 var time = new Date(value.time); 
    	 var _hour = time.getHours()<10?("0"+time.getHours()):time.getHours();
    	 var _minutes = time.getMinutes()<10?("0"+time.getMinutes()):time.getMinutes();	
    	 return  _hour+":"+_minutes;
    }
}
/**
 * mask遮罩不显示百分比
 */
var MaskUtil = (function(){    
    var $mask,$maskMsg;    
    var defMsg = '正在处理，请稍待...';    
    function init(){    
        if(!$mask){    
            $mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo("body");    
        }    
        if(!$maskMsg){    
            $maskMsg = $("<div class=\"datagrid-mask-msg mymask\">"+defMsg+"</div>")    
                .appendTo("body").css({'font-size':'12px'});  
        }    
        $mask.css({width:"100%",height:$(document).height()});    
        $maskMsg.css({    
            left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2,  
        });     
    }    
    return {    
        mask:function(msg){    
            init();    
            $mask.show();    
            $maskMsg.html(msg||defMsg).show();    
        }    
        ,unmask:function(){    
            $mask.hide();    
            $maskMsg.hide();    
        }    
    }    
}());    
/**
 * 根据id删除datalist里所有数据
 * @param divId
 */
function delDataListAll(divId){
    var allRows = $('#'+divId).datalist('getRows');
    if (allRows.length > 0)
    {
        var allRowLength = allRows.length;
        for (var i = 0; i < allRowLength; i++)
        {
            var checkedRow = allRows[0];
            var checkedRowIndex = $('#userId_ul').datalist('getRowIndex', checkedRow);
            $('#'+divId).datalist('deleteRow', checkedRowIndex);
        }
    }
}  
/**
 * 数据刷新
 * @param gridId
 */
function func_refresh(gridId){
	$('#'+gridId).datagrid('reload');    
}
/**
 * 获取hex颜色值，目的：解决chrom,firefox 获取颜色值是rgb的问题
 */
$.fn.getHexBackgroundColor = function() { 
	var rgb = $(this).css('background-color'); 
	if(!$.support ){ 
	rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/); 
	function hex(x) { 
	return ("0" + parseInt(x).toString(16)).slice(-2); 
	} 
	rgb= "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]); 
	} 
	return rgb; 
	} 

/**
 * 获取登录用户信息
 * @returns
 */
function func_currentUser(){
	var userData;
	$.ajax({
		url : getContextPath() + "/systemLoginAction_getLoginUser.action",
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			userData=DATA
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	return userData
}

/**
 *获取erp地址
 * @returns
 */
function func_erpUrl(){
	var resultData;
	$.ajax({
		url : getContextPath() + "/zjsys_basics/dataCircleAction_findErp.action",
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			resultData=DATA
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	return resultData
}
/**
 * 获取当前日期
 * @returns {String}
 */
function func_currentData(){
	var myDate = new Date();
	var dt=myDate.getFullYear()+"-"+((myDate.getMonth() + 1)<10?"0"+(myDate.getMonth() + 1):(myDate.getMonth() + 1))+"-"+(myDate.getDate()<10?"0"+myDate.getDate():myDate.getDate())	 
    return dt; 
}
function func_currentMonth(){
	var myDate = new Date();
	var dt=myDate.getFullYear()+"-"+((myDate.getMonth() + 1)<10?"0"+(myDate.getMonth() + 1):(myDate.getMonth() + 1));
    return dt; 
}
function func_currentYear(){
	var myDate = new Date();
	var dt=myDate.getFullYear();
    return dt; 
}

/**
 * 日期加减
 * @param date
 * @param days
 * @returns {String}
 */
function func_addDate(date,days){ 
    var d=new Date(date); 
    d.setDate(d.getDate()+parseInt(days)); 
    var m=d.getMonth()+1; 
    return d.getFullYear()+'-'+(m<10?"0"+m:m)+'-'+d.getDate(); 
  } 
/**
 * 日期按月份加减
 * @param d "yyyy-mm-dd"
 * @param m 个数
 * @returns
 */
function addMoth(d,m){
	   var ds=d.split('-'),_d=ds[2]-0;
	   var nextM=new Date( ds[0],ds[1]-1+m+1, 0 );
	   var max=nextM.getDate();
	   d=new Date( ds[0],ds[1]-1+m,_d>max? max:_d );
	   return d.toLocaleDateString().match(/\d+/g).join('-');
}
/**
 * 日期按月份加减
 * @param d "yyyy-mm-dd"
 * @param m 个数
 * @returns
 */
function func_addMoth(d,m){
     var sDate = new Date(d);

     var sYear = sDate.getFullYear();
     var sMonth = sDate.getMonth() + 1;
     var sDay = sDate.getDate();

     var eYear = sYear;
     var eMonth = sMonth + parseInt(m);
     var eDay = sDay;
     while (eMonth > 12) {
         eYear++;
         eMonth -= 12;
     }
     return eYear+'-'+(eMonth<10?"0"+eMonth:eMonth)+'-'+sDate.getDate(); 
}
/**
 * 日期按年加减
 * @param d "yyyy-mm-dd"
 * @param m 个数
 * @returns
 */
function func_addYear(date,m){
	 var d=new Date(date); 
     var m=d.getMonth()+1; 
     return (d.getFullYear()+m)+'-'+(m<10?"0"+m:m)+'-'+d.getDate(); 
}
/**
 * 计算两个时间差
 * @param startTime xxxx-xx-xx的时间格式
 * @param endTime xxxx-xx-xx的时间格式
 * @param diffType second 秒 minute 分 hour 时 day 天
 * @returns
 */
function func_getDateDiff(startTime, endTime, diffType) {
    startTime = startTime.replace(/\-/g, "/");
    endTime = endTime.replace(/\-/g, "/");
    //将计算间隔类性字符转换为小写
    diffType = diffType.toLowerCase();
    var sTime =new Date(startTime); //开始时间
    var eTime =new Date(endTime); //结束时间
    //作为除数的数字
    var timeType =1;
    switch (diffType) {
        case"second":
            timeType =1000;
        break;
        case"minute":
            timeType =1000*60;
        break;
        case"hour":
            timeType =1000*3600;
        break;
        case"day":
            timeType =1000*3600*24;
        break;
        default:
        break;
    }
    return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(timeType));
}
/**
 * 获取当前时间
 * @returns
 */
function func_currentTime(){
	var currentTime=new Date();
 	return currentTime.toLocaleString();
}
/**
 * 获取当前时间
 * @returns
 */
function func_currentDateTime(){
	var time=new Date();
	return time.getFullYear()+"-"+(time.getMonth() + 1)+"-"+time.getDate()+" "+time.getHours()+":"+time.getMinutes()+":"+time.getSeconds();
}

/** 
 * 部门树信息deptId,treeId
 */
$.fn.depComboTree= function() { 
 	this.combotree({    
 	    url: getContextPath() + "/tried_system/systemDepartmentAction_findDepTree.action" /*,
		onLoadSuccess:function(node,data){
		 	  if(deptId!=undefined&&deptId!=null){
		 		  
		 		   for (var i=0;i<data.length ;i++ ){ 
	                	 if(data[i].id != deptId){
	                		 continue;
	                     	}
	                	 else{
	                		 $("#"+treeId).combotree('setValue',data[i].id);	                		
	                	 }
	                 }

		 		  
		 		  
		 		  
		 		  
		 	    		
		 	  }
		 	 
		 }*/
 	});
 	 
} 

/**
 * 获得部门下的部长、组长用户名
 */
$.fn.depManagerCombobox= function(recordId) { 
 	this.combobox({    
 	    url: getContextPath() + "/tried_system/systemDepartmentAction_findDepManagerPeople.action?recordId="+recordId,  
 	    valueField:'id',    
	    textField:'userName',
	    formatter : function(row) {
			return  row.userName+"("+row.workName+")";
		}
 	});
}


$.fn.systemReportModelCombobox= function(modelType) { 
 	this.combobox({    
 	    url: getContextPath() + "/tried_system/systemReportModelAction_findModelCombox.action?modelType="+modelType,  
 	    valueField:'id',    
	    textField:'reportName',
	    panelWidth:250, 
 	    panelHeight:300,
 	    filter: function(q, row){
			var opts = $(this).combobox('options');
			
			return row['reportName'].indexOf(q)>= 0||row['jcyiName'].indexOf(q)>= 0;
		},
 	    formatter : function(row) {
			return  row.reportName+" | "+row.jcyiName+" ";
		}
 	});
}
/**
 * 
 */
	$.fn.systemQtModelCombobox=function(){	 
	 	this.combobox({    
	 	    url: getContextPath() + "/tried_system/systemReportModelAction_findModelCombox.action",  
	 	    valueField:'id',    
		    textField:'reportName',
		    panelWidth:250, 
	 	    panelHeight:300,
	 	    filter: function(q, row){
				var opts = $(this).combobox('options');
				
				return row['reportName'].indexOf(q)>= 0||row['jcyiName'].indexOf(q)>= 0;
			},
	 	    formatter : function(row) {
				return  row.reportName+" | "+row.jcyiName+" ";
			}
	 	});
	}



/**
 * 大于当前时间
 */
$.fn.greaterCurrentDate= function() { 
	this.datebox().datebox('calendar').calendar({
		validator: function(date){
			var now = new Date();
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1<=date;
		}
	});
}
/**
 * 获得部门下面应有角色的用户名
 */
$.fn.depRoleUserCombobox = function(depId,roleName,defaultVal,delValue){
	this.combobox({
		url:getContextPath() + "/tried_system/systemRoleAction_findDepRoleUser.action?recordId="+depId+"&roleName="+roleName,
		valueField : 'id',
		textField : 'userName',
		onLoadSuccess:function(){
		 	  if(defaultVal!=undefined&&defaultVal!=null){
		 	    		$(this).combobox('select',defaultVal);
		 	    		$(this).combobox('setValue',defaultVal);
		 	  }
		 	 
		 }
	});
}

/**
 * 获得部门下的部长、组长用户名
 */
$.fn.roleUserCombobox= function(roleName) { 
 	this.combobox({    
 	    url: getContextPath() + "/tried_system/systemRoleAction_findRoleUser.action?recordId="+roleName,  
 	    valueField:'id',    
	    textField:'userName'
 	});
}
/**
 * 获得部门下所有员工
 */
$.fn.depUserCombobox= function(depId,ywbId) { 
	if(ywbId!=undefined&&depId!=ywbId){
		depId=depId+","+ywbId
	}
	this.combobox({    
		url: getContextPath() + "/tried_system/systemDepartmentAction_findJcPeople.action?recordId="+depId,  
		valueField:'id',    
		textField:'userName',
		onHidePanel : function() {  
		    var _options = $(this).combobox('options');  
		    var _data = $(this).combobox('getData');/* 下拉框所有选项 */  
		    var _value = $(this).combobox('getValue');/* 用户输入的值 */  
		    var _b = false;/* 标识是否在下拉列表中找到了用户输入的字符 */  
		    for (var i = 0; i < _data.length; i++) {  
		        if (_data[i][_options.valueField] == _value) {  
		            _b=true;  
		            break;  
		        }  
		    }  
		    if(!_b){  
		        $(this).combobox('setValue', '');  
		    }  
		}
	}
	);
}

/**
 * 获取所有人员信息，默认当前人员
 */
$.fn.allUserCombobox = function(defaultVal){
	this.combobox({
		url:getContextPath() + "/tried_system/systemUserAction_allUserlist.action",
		valueField : 'id',
		textField : 'userName',
		onLoadSuccess:function(){
		 	  if(defaultVal!=undefined&&defaultVal!=null){
		 	    		$(this).combobox('select',defaultVal);
		 	    	//	$(this).combobox('setValue',defaultVal);
		 	  }
		 	 
		 }
	});
}

/**
 * 根据检测机构获取检测组信息
 */
$.fn.childrenDepCombobox = function(recordId,defaultVal){
		if(recordId==undefined){
			recordId="0";
		}
		this.combotree({    
			  url: getContextPath() + "/tried_system/systemDepartmentAction_depChliTree.action?recordId="+recordId,
			  onLoadSuccess:function(){
			 	  if(defaultVal!=undefined){
			 	    		$(this).combotree('select',defaultVal);
			 	    		$(this).combotree('setValue',defaultVal);
			 	    }
			 	}
	 	});
	 
}
/**
 * 获得设备类型树下拉框
 */
$.fn.toolTypeComboboxTree= function() { 
	this.combotree({    
 	    url: getContextPath() + "/testsys_other/otherToolTypeAction_findTree.action",
 	    valueField:'text',    
	    textField:'text'
	   
 	});
}
/**
 * 获得设备类型树下拉框
 */
$.fn.sparePartTypeComboboxTree= function() { 
	this.combotree({    
 	    url: getContextPath() + "/testsys_other/otherSparePartTypeAction_findTree.action",
 	    valueField:'text',    
	    textField:'text'
 	});
}

 
 
/**
 *维修等级
 */
$.fn.faultGradeValCombobox= function() { 
	this.combobox({    
		url: getContextPath() + "/testsys_other/otherRepairAction_comboList.action",  
		valueField:'repairLevel',    
		textField:'repairLevel'
	});
}
 
/**
 * 仓库下拉框
 */
$.fn.warehouseCombobox = function(defaultVal){
	this.combobox({    
	    url: getContextPath() + "/scheduesys_other/otherWarehouseAction_comboList.action", 
	    valueField:'houseName',    
	    textField:'houseName' ,
	    onLoadSuccess:function(){
		 	  if(defaultVal!=undefined){
		 	    		$(this).combobox('select',defaultVal);
		 	    		$(this).combobox('setValue',defaultVal);
		 	    }
		 	}
	});
}

/**
 * 库位下拉框
 */
$.fn.warehousePostionCombobox = function(houseId,defaultVal){
	this.combobox({    
	    url: getContextPath() + "/scheduesys_other/otherWarehousePositionAction_comboList.action?recordId="+houseId,   
	    valueField:'positionName',    
	    textField:'positionName' ,
	    onLoadSuccess:function(){
		 	  if(defaultVal!=undefined){
	 	    		$(this).combobox('select',defaultVal);
	 	    		$(this).combobox('setValue',defaultVal);
	 	    }
	 	}
	});
}
/**
 * 库位下拉框
 */
$.fn.warePostionCombobox = function(defaultVal){
	this.combobox({    
	    url: getContextPath() + "/scheduesys_other/otherWarehousePositionAction_comboList.action?recordId=样品库",   
	    valueField:'id',    
	    textField:'positionName',
	    formatter: function(row){
			return row.wareHouseName+"－"+row.positionName;
		},
	    onLoadSuccess:function(){
		 	  if(defaultVal!=undefined){
	 	    		$(this).combobox('select',defaultVal);
	 	    		$(this).combobox('setValue',defaultVal);
	 	    }
	 	}   
	});
}
/**
 * 获得来往公司下拉框
 */
$.fn.manuFacturerCombobox= function() { 
 	this.combobox({    
 	    url: getContextPath() + "/scheduesys_customer/proxyCompanyAction_comboboxProxyAll.action",  
 	    panelWidth:200,
	    panelHeight:300,
 	    valueField:'companyName',    
	    textField:'companyName',
	    filter: function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q)>= 0;
		}
 	});
}
 
 

/**
 * 根据检测机构获取检测组信息2
 */
$.fn.jcGroupCombobox = function(recordId){
	this.combobox({    
	    url: getContextPath() + "/tried_system/systemDepartmentAction_findJcDepAndGroup.action?recordId="+recordId,   
	    valueField:'id',    
	    textField:'name'   
	});
}

 /**
  *职务列表
  */
$.fn.jobCombobox = function(depId){
	
	this.combobox({    
	    url: getContextPath() + "/tried_system/systemDepartmentAction_findAllJob.action?recordId="+depId,   
	    valueField:'id',    
	    textField:'name'   
	});
}

/**
 * 组织数1
 */
$.fn.workTreebox = function(workId){
	this.combotree({    
		  url: getContextPath() + "/tried_system/systemDepartmentAction_findPartWorkTree.action?id="+workId
 	});
}

/**
 * 组织数2
 */
$.fn.workTree = function(workId){
	this.tree({    
		  url: getContextPath() + "/tried_system/systemDepartmentAction_findPartWorkTree.action?id="+workId
		  
 	});
}


 
/**html传参**/ 
function getUrlParam(name) {   
			 var reg = new RegExp("(^|&)" + name+ "=([^&]*)(&|$)", "i");   
	  		 var r = window.location.search.substr(1).match(reg);  
	         if (r != null) return unescape(r[2]); return null;  
 }
 
 /** 获取全路径* */
function getContextPath() {
	var pathName = document.location.pathname;
	    var index = pathName.substr(1).indexOf("/");
	    var result = pathName.substr(0, index + 2);
	if(result.indexOf("/zjsys/")==-1){
		result="";
	}
	return result;
}
 
 /**&,+ 字符转义**/
 function vchar(str) {
 	str = str.replace(/\+/g, "%2B");
 	str = str.replace(/\&/g, "%26");
 	return str;
 }
 
 /**货币转换**/
 function formateRmb(value,  row, index) {
     if (value == undefined||value=="") {
         return formateMoney(0,2);
     }else{
    	 return formateMoney(value,2);
     }
 }
 
 /**货币转换
  * 没有值为空
  * **/
 function formateRmb2(value,  row, index) {
     if (value == undefined||value=="") {
         return "";
     }else{
    	 return formateMoney(value,2);
     }
 }
 /**
  * 合计 貨幣
  * 表id，行，不计入和列
  */
 function func_totalCols(gridId,row,colStr){
  var fields = $("#"+gridId).datagrid('getColumnFields');
   var sum=0;
   for(var j=0;j<fields.length;j++){
 	  if(row[fields[j]]!=undefined&&row[fields[j]]!=""&&(colStr.indexOf('<'+fields[j]+'>')==-1)){
 		  sum=sum+parseFloat(row[fields[j]]); 
 	  }	 
     }  
    row.total=formateRmb(sum);
 	return formateRmb(sum);		
 }
 /**
  * 合计整型
  * 表id，行，不计入和列
  */
 function func_totalNumCols(gridId,row,colStr){
	  var fields = $("#"+gridId).datagrid('getColumnFields');
	   var sum=0;
	   for(var j=0;j<fields.length;j++){
	 	  if(row[fields[j]]!=undefined&&row[fields[j]]!=""&&(colStr.indexOf('<'+fields[j]+'>')==-1)){
	 		  sum=sum+parseInt(row[fields[j]]); 
	 	  }	 
	     }  
	    row.total=sum;
	 	return sum;		
	 }
 /**
  * 数字转货币
  * @param s
  * @param n
  * @returns {String}
  */
 function formateMoney(s, n) {  
	 if(isNaN(s)){return s;}
    n = n > 0 && n <= 20 ? n : 2;   
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
    var l = s.split(".")[0].split("").reverse(),   
    r = s.split(".")[1];   
    t = "";   
    for(i = 0; i < l.length; i ++ )   
    {   
       t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
    }   
    return t.split("").reverse().join("") + "." + r;   
 } 

 /**时间转换**/
 function formateTime(value,  row, index) {
     if (value == undefined||value==""||value==null) {
         return "";
     }else{
     	 var time = new Date(value.time); 
     	// return  time.getFullYear()+"-"+(time.getMonth() + 1)+"-"+time.getDate()+" "+time.getHours()+":"+time.getMinutes()+":"+time.getSeconds();
     	 return  time.getFullYear()+"-"+((time.getMonth() + 1)<10?"0"+(time.getMonth() + 1):(time.getMonth() + 1))+"-"+(time.getDate()<10?"0"+time.getDate():time.getDate())+" "+(time.getHours()<10?"0"+time.getHours():time.getHours())+":"+(time.getMinutes()<10?"0"+time.getMinutes():time.getMinutes())+":"+(time.getSeconds()<10?"0"+time.getSeconds():time.getSeconds());         
         
     }
 }
 
 
 /**时间转换yyyy-MM-dd HH:mm:ss**/
 function formateRowTime(value,  row, index) {
     if (value == undefined||value==""||value==null) {
         return "";
     }else{
     	 var time = new Date(value.time); 
     	 return  time.getFullYear()+"-"+((time.getMonth() + 1)<10?"0"+(time.getMonth() + 1):(time.getMonth() + 1))+"-"+(time.getDate()<10?"0"+time.getDate():time.getDate())+" "+(time.getHours()<10?"0"+time.getHours():time.getHours())+":"+(time.getMinutes()<10?"0"+time.getMinutes():time.getMinutes())+":"+(time.getSeconds()<10?"0"+time.getSeconds():time.getSeconds());
         
     }
 }
 
 
 /**时间转换yyyy-MM-dd HH:mm:ss**/
 function formateRowBxyTime(value,  row, index) {
     if (value == undefined||value==""||value==null) {
         return "";
     }else{
     	 var time = new Date(value); 
     	 return  time.getFullYear()+"-"+((time.getMonth() + 1)<10?"0"+(time.getMonth() + 1):(time.getMonth() + 1))+"-"+(time.getDate()<10?"0"+time.getDate():time.getDate())+" "+(time.getHours()<10?"0"+time.getHours():time.getHours())+":"+(time.getMinutes()<10?"0"+time.getMinutes():time.getMinutes())+":"+(time.getSeconds()<10?"0"+time.getSeconds():time.getSeconds());
         
     }
 }
 
 /**时间转换yyyy-MM-dd**/
 function formateRowDate(value,  row, index) {
     if (value == undefined||value==""||value==null) {
         return "";
     }else{
     	 var time = new Date(value.time);      	
     	 return  time.getFullYear()+"-"+((time.getMonth() + 1)<10?"0"+(time.getMonth() + 1):(time.getMonth() + 1))+"-"+(time.getDate()<10?"0"+time.getDate():time.getDate());
         
     }
 }
 
 /**日期转换**/
 function formateDate(value,  row, index) {
     if (value == undefined) {
         return "";
     }else{
     	 var time = new Date(value.time); 
     	 return time.getFullYear()+"-"+((time.getMonth() + 1)<10?("0"+(time.getMonth() + 1)):(time.getMonth() + 1))+"-"+(time.getDate()<10?("0"+time.getDate()):time.getDate());
     }
 }
 
/**
 * 月-日 时：分
 * @param dateTime
 * @returns {String}
 */
 function formateMDHM(dateTime) {
     if (dateTime == undefined) {
         return "";
     }else{
     	 var time = new Date(dateTime); 
     	 return   (time.getMonth() + 1)+"-"+time.getDate()+" "+time.getHours()+":"+time.getMinutes();
     }
 }

/**
 * 重新登录按钮
 */
 function relogin(){
	 window.location.href =getContextPath()+"/index/login.html"; 
 }
 /**
  * 错误信息框
  * @param msg
  */
 function func_msg_error(msg){
		$.messager.alert('<font color="red">错误</font>',msg,'error');
 }
 /**
  * 警告信息款
  * @param msg
  */
 function func_msg_warn(msg){
		$.messager.alert('<font color="red">警告</font>',msg,'warning');
}
 /**
  * 一般信息框
  * @param msg
  */
 function func_msg_info(msg){
	 $.messager.show({
			title:'消息',
			msg:'<font style="font-weight: bold;font-size: 15;color: blue">'+msg+'</font>',
			showType:'fade',
			timeout:500,
			style:{
				right:'',
				bottom:''
			}
		});
	 }
 
 /**
  * 权限不足弹出框
  */
 function noParticle(){
	 $.messager.alert('My Title','Here is a error message!','error');
	 $.messager.alert('<font color="red">警告</font>','权限不足','error')
 }
 
 /**
  * 字符串随机数
  * @returns
  */
 function randomstr(){
	 var r=Math.random();
	 return  r.toString().replace("0.","");
 }
 /**
  * 生成UUID
  */
 function getUUID(){
	 return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g,function(c){
		var r = Math.random()*16|0,v = c =='x' ? r : (r&0x3|0x8);
		return v.toString(16);
	 });
 }
 

 
 /**
  * 根据字段查看文件列表
  * @param recordId 业务表id
  * @param fileColumn 对应字段名
  */
 function func_sampleFile_list(recordId,fileColumn) { 
	 $.ajax({
			url : getContextPath()+'/system_file/fileManagerAction_list.action',
			type : "post",
			dataType : "json",
			data : "recordId=" + recordId+"&pkColumn="+fileColumn,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS'&&DATA.RETURN_DATA!=null) {

					$.each( DATA.RETURN_DATA, function(index, value){
						$.each(value, function(k, v){
							
							 if(k=="pkColumn"){
								 if($("#"+v).length>0&&fileColumn.indexOf(v)!=-1){
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];
									 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+' <a href="#" onclick="func_file_id_down(\''+_r_id+'\')" >下载</a></td></tr>';
									console.log(v+":"+_tr);
									 $("#"+v).append(_tr) ;
								 }
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
  * 根据字段编辑文件列表
  * @param recordId 业务表id
  * @param fileColumn 对应字段名
  */
 function func_sampleFileEdit_list(recordId,fileColumn){
		
	 $.ajax({
			url : getContextPath()+'/system_file/fileManagerAction_list.action',
			type : "post",
			dataType : "json",
			data : "recordId=" + recordId+"&pkColumn="+fileColumn,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS') {
					$.each( DATA.RETURN_DATA, function(index, value){
						$.each(value, function(k, v){
							 if(k=="pkColumn"){
								 if($("#"+v).length>0&&fileColumn.indexOf(v)>=0){
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];
									 var _tr = '<tr id=\''+_r_id+'\' ><td style="border:0px"><img id=\''+_r_id+'_img\' onclick="func_del_file(this,\''+v+'\')" src="'+getContextPath()+'/img/icon/del.png"/></td>'
											   +'<td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+'&nbsp;&nbsp;<a href="#" onclick="func_file_id_down(\''+_r_id+'\')" >下载</a></td></tr>';
									 $("#"+v).append(_tr) ;
									 $("#"+_r_id+"_img").attr("src",getContextPath()+'/img/icon/del.png');
								 }
								 var _file_column_ids="";
				         		 var _fileCc=$("#"+v).children();
				         		 if(_fileCc.length>0){
				         			var _trLen= _fileCc.get(0).children.length
					         		for(var i=0;i<_trLen;i++){
					         			var _h=_fileCc.get(0).children[i];
					         			_file_column_ids=_file_column_ids+";"+ $(_h).attr("id");
					         		}
				         		}
				         		 $("#"+v+"_edit").val(_file_column_ids);
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
 * 文件上传
 * @param fileID 监听input type=file的id
 * @param fileObject 对应对象
 * @param fileColumn 对应字段名
 * @param pkId       临时id
 */
 function func_upfile(obj,fileColumn,pkId,flag){
	 debugger;
	 var  fileID=$(obj).attr("id");
	 if((obj.value.substring(obj.value.indexOf("."))).toLowerCase()=='.doc'){
		 func_msg_error("禁止 上传“DOC”文件，请新建DOCX格式文档！");
	 		return;
	 }
 	var pkVal="";
 	if($("#"+pkId).length>0){
 		if($("#"+pkId).val()!=""){
 			if(flag){
 			 pkVal=$("#"+pkId).val();
 			}
 		}
 	}else{
 		func_msg_error("文件ID属性框不存在");
 		return;
 	}
 	if($("#"+fileColumn).length<=0){
 		func_msg_error("文件显示区不存在");
 		return;
 	}
 	 $.messager.progress({ title : '请等待', msg : '上传中...'});
 	 var option="?pkColumn="+fileColumn+"&pkId="+pkVal+"&flag="+flag;
 	 $.ajaxFileUpload({
          url: getContextPath()+'/system_file/fileManagerAction_save.action'+option, //用于文件上传的服务器端请求地址
          secureuri: false,
          fileElementId: fileID, 
          dataType: 'json',  
          success: function (DATA, status) 
          {
         	 if (DATA.STATUS == 'SUCCESS') {
         		     var _r_Id=DATA.RETURN_DATA.fileId;
         		     var _r_pkId=DATA.RETURN_DATA.pkId;
	         		 var _r_fileName=DATA.RETURN_DATA.fileName;
 	         		
	         		 if(flag){
 	         			var _tr = '<tr id=\''+_r_Id+'\'><td style="border:0px;width:30px;"><img  id=\''+_r_Id+'_img\' onclick="func_del_file(this, \''+fileColumn+'\' )" src="'+getContextPath()+'/img/icon/del.png"/></td>'
 	         					 +'<td style="text-align:left; padding-left: 3px;border:0px " >'+_r_fileName
 	         					 +'&nbsp;&nbsp;<a href="#" onclick="func_file_down(\''+_r_pkId+'\',\''+fileColumn+'\',\''+_r_fileName+'\')" >下载</a></td></tr>';
	         			  	$("#"+fileColumn).append(_tr); 
	         			  	$("#"+_r_Id+"_img").attr("src",getContextPath()+'/img/icon/del.png');
 	         		}else{
 	         			$("#"+fileColumn).empty();
 	         			var _tr = '<tr  id=\''+_r_Id+'\'><td style="border:0px;width:30px;"><img  id=\''+_r_Id+'_img\' onclick="func_del_file(this, \''+fileColumn+'\' )" src="'+getContextPath()+'/img/icon/del.png"/></td><td style="text-align:left; padding-left: 3px;border:0px " >'+_r_fileName
 	         				+'&nbsp;&nbsp;<a href="#" onclick="func_file_down(\''+_r_pkId+'\',\''+fileColumn+'\',\''+_r_fileName+'\')" >下载</a></td></tr>';
 	         			 $("#"+fileColumn).append(_tr) ;
 	         		}	
	         		 var _file_column_ids="";
	         		 var _fileCc=$("#"+fileColumn).children();
	         		 if(_fileCc.length>0){
	         			var _trLen= _fileCc.get(0).children.length
		         		for(var i=0;i<_trLen;i++){
		         			var _h=_fileCc.get(0).children[i];
		         			_file_column_ids=_file_column_ids+";"+ $(_h).attr("id");
		         		}
	         		}
	         		 $("#"+fileColumn+"_edit").val(_file_column_ids);
	         		 
	         		$("#"+pkId).val(_r_pkId);
 				} else {
 					func_msg_info(DATA.RETURN_DATA);
 				}
         	 $.messager.progress('close');
          },
          error: function (data, status, e)//服务器响应失败处理函数
          {
               func_msg_error(e);
          }
      }
  );
 }
 
 /**
  * 文件上传
  * @param fileID 监听input type=file的id
  * @param fileId       原文件id
  */
  function func_repalce_report(obj,pkId,datagridId){
	var  fileID=$(obj).attr("id");
  	$.messager.progress({ title : '请等待', msg : '上传中...'});
  	 var option="?id="+pkId;
  	 $.ajaxFileUpload({
           url: getContextPath()+'/system_file/fileManagerAction_replaceFile.action'+option, //用于文件上传的服务器端请求地址
           secureuri: false,
           fileElementId: fileID, 
           dataType: 'json',  
           success: function (DATA){
          	 	if (DATA.STATUS == 'SUCCESS') {
          	 		func_msg_info(DATA.RETURN_DATA);
          	 		$('#'+datagridId).datagrid('reload');
  				} else {
  					func_msg_info(DATA.RETURN_DATA);
  				}
          	 $.messager.progress('close');
           },
           error: function (data, status, e)//服务器响应失败处理函数
           {
                func_msg_error(e);
           }
       }
   );
  }
 
 
 /**
  * 文件批量上传
  * @param fileID 监听input type=file的id
  * @param fileObject 对应对象
  * @param fileColumn 对应字段名
  * @param pkId       临时id
  */
  function func_batch_upfile(obj,fileColumn,pkId,flag){
 	var  fileID=$(obj).attr("id");
  	var pkVal="";
  	if($("#"+pkId).length>0){
  		if($("#"+pkId).val()!=""){
  			if(flag){
  			 pkVal=$("#"+pkId).val();
  			}
  		}
  	}else{
  		func_msg_error("文件ID属性框不存在");
  		return;
  	}
  	if($("#"+fileColumn).length<=0){
  		func_msg_error("文件名称属性框不存在");
  		return;
  	}
  	 $.messager.progress({ title : '请等待', msg : '上传中...'});
  	 var option="?pkColumn="+fileColumn+"&pkId="+pkVal+"&flag="+flag;
  	 $.ajaxFileUpload({
           url: getContextPath()+'/system_file/fileManagerAction_batchSave.action'+option, //用于文件上传的服务器端请求地址
           secureuri: false,
           fileElementId: fileID, 
           dataType: 'json',  
           success: function (DATA, status) 
           {
          	 if (DATA.STATUS == 'SUCCESS') {
          		 $.each(DATA.RETURN_DATA,function(i,item){
          			 var _r_Id=item.fileId;
          		     var _r_pkId=item.pkId;
 	         		 var _r_fileName=item.fileName;
  	         		
 	         		 if(flag){
  	         			var _tr = '<tr id=\''+_r_Id+'\' style="height:27px;"><td style="border:0px;width:30px;"><img  id=\''+_r_Id+'_img\' onclick="func_del_file(this, \''+fileColumn+'\' )" src="'+getContextPath()+'/img/icon/del.png"/></td>'
  	         					 +'<td style="text-align:left; padding-left: 3px;border:0px " >'+_r_fileName
  	         					 +'&nbsp;&nbsp;<a href="#" onclick="func_file_down(\''+_r_pkId+'\',\''+fileColumn+'\',\''+_r_fileName+'\')" >下载</a></td></tr>';
 	         			  	$("#"+fileColumn).append(_tr); 
 	         			  	$("#"+_r_Id+"_img").attr("src",getContextPath()+'/img/icon/del.png');
  	         		}else{
  	         			$("#"+fileColumn).empty();
  	         			var _tr = '<tr  id=\''+_r_Id+'\' style="height:27px;"><td style="text-align:left; padding-left: 3px;border:0px " >'+_r_fileName
  	         				+'&nbsp;&nbsp;<a href="#" onclick="func_file_down(\''+_r_pkId+'\',\''+fileColumn+'\',\''+_r_fileName+'\')" >下载</a></td></tr>';
  	         			 $("#"+fileColumn).append(_tr) ;
  	         		}	
 	         		 var _file_column_ids="";
 	         		 var _fileCc=$("#"+fileColumn).children();
 	         		 if(_fileCc.length>0){
 	         			var _trLen= _fileCc.get(0).children.length
 		         		for(var i=0;i<_trLen;i++){
 		         			var _h=_fileCc.get(0).children[i];
 		         			_file_column_ids=_file_column_ids+";"+ $(_h).attr("id");
 		         		}
 	         		}
 	         		 $("#"+fileColumn+"_edit").val(_file_column_ids);
 	         		 $("#"+pkId).val(_r_pkId);
          		 });
  				} else {
  					func_msg_info(DATA.RETURN_DATA);
  				}
          	 $.messager.progress('close');
           },
           error: function (data, status, e)//服务器响应失败处理函数
           {
                func_msg_error(e);
           }
       }
   );
  }
  
 
 function func_fileEdit_list(recordId) { 

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
								 if($("#"+v).length>0){
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];
									 var _tr = '<tr id=\''+_r_id+'\' ><td style="border:0px"><img onclick="func_del_file(this, \''+v+'\')" src="'+getContextPath()+'/img/icon/del.png"/></td>'
											   +'<td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+'&nbsp;&nbsp;<a href="#" onclick="func_file_id_down(\''+_r_id+'\')" >下载</a></td></tr>';
									 $("#"+v).append(_tr) ;
								 }
								 var _file_column_ids="";
				         		 var _fileCc=$("#"+v).children();
				         		 if(_fileCc.length>0){
				         			var _trLen= _fileCc.get(0).children.length
					         		for(var i=0;i<_trLen;i++){
					         			var _h=_fileCc.get(0).children[i];
					         			_file_column_ids+=";"+ $(_h).attr("id").replace(/[\r\n]/g,"");
					         		}
				         		}
				         		 
				         		 $("#"+v+"_edit").val(_file_column_ids);
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
  * @param recordId pkid
  * @param fileColumn 字段名称
  * @param wType 属性 1 只读，2编辑，3批注
  */
 function func_editTaskCard(fileName,recordId,fileColumn,wType){	 
	 var _retr;
	 $.ajax({
			url : getContextPath()+'/system_file/fileManagerAction_list.action',
			type : "post",
			dataType : "json",
			data : "recordId=" + recordId+"&pkColumn="+fileColumn,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS') {					 
					$.each( DATA.RETURN_DATA, function(index, value){
						$.each(value, function(k, v){
						if(k=="pkColumn"){
							if(wType==2){
							
								 var _r_fileName=value['filename'];
								 var _r_id=value['id'];
								 var _r_pkId=value['pkId'];
								 _retr=fileName+'&nbsp;<a href="#" onclick="func_openWord(\''+_r_id+'\','+wType+')" >编辑</a>';
						
							}else{								
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];									 
									 _retr = ' <a href="#" onclick="func_openWord(\''+_r_id+'\','+wType+')" >'+value['filename']+'</a>';
							}
						}
						});						
					});						 
				}  
			},
			error : function(event, request, settings) {
				func_msg_error("网络异常!");
			}
		}); 
	return _retr;
 }
 
 
 

 /**
  * 
  * @param recordId pkid
  * @param fileColumn 字段名称
  * @param wType 属性 1 只读，2编辑，3批注
  */
 function func_openWord_list(recordId,fileColumn,wType) { 
	 $.ajax({
			url : getContextPath()+'/system_file/fileManagerAction_list.action',
			type : "post",
			dataType : "json",
			data : "recordId=" + recordId+"&pkColumn="+fileColumn,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS') {
					 
					$.each( DATA.RETURN_DATA, function(index, value){
						$.each(value, function(k, v){
						if(k=="pkColumn"){
							if(wType==2){
							 if($("#"+v).length>0&&fileColumn.indexOf(v)>=0){
								 var _r_fileName=value['filename'];
								 var _r_id=value['id'];
								 var _r_pkId=value['pkId'];
								 var _tr = '<tr id=\''+_r_id+'\' ><td style="border:0px"><img id=\''+_r_id+'_img\' onclick="func_del_file(this,\''+v+'\')" src="'+getContextPath()+'/img/icon/del.png"/></td>'
										   +'<td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+'&nbsp;&nbsp;<a href="#" onclick="func_openWord(\''+_r_id+'\','+wType+')" >编辑</a></td></tr>';
								 $("#"+v).append(_tr) ;
								 $("#"+_r_id+"_img").attr("src",getContextPath()+'/img/icon/del.png');
							 }
							 var _file_column_ids="";
			         		 var _fileCc=$("#"+v).children();
			         		 if(_fileCc.length>0){
			         			var _trLen= _fileCc.get(0).children.length
				         		for(var i=0;i<_trLen;i++){
				         			var _h=_fileCc.get(0).children[i];
				         			_file_column_ids=_file_column_ids+";"+ $(_h).attr("id");
				         		}
			         		}
			         		 $("#"+v+"_edit").val(_file_column_ids);
							}else{
								 if($("#"+v).length>0&&fileColumn.indexOf(v)!=-1){
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];
									 
									 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px " > <a href="#" onclick="func_openWord(\''+_r_id+'\','+wType+')" >'+value['filename']+'</a></td></tr>';
									 $("#"+v).append(_tr) ;
								 }
								
							}
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
  *在线编辑word文档
  * type  3:批注;2:编辑；1：只读
  * @param id
  */
 function func_openWord(id,type){
	 if(type==undefined){
		 POBrowser.openWindowModeless(getContextPath() + '/system_file/wordFileHandleAction_readword.action?id=' + id, 'fullscreen=yes;frame=yes;resizable=yes;');
	 }else{
		 if(type==3){ 
			 POBrowser.openWindowModeless(getContextPath() + '/system_file/wordFileHandleAction_pizhuWord.action?id=' + id, 'fullscreen=yes;frame=yes;resizable=yes;');
		 }else if(type==2){
			 POBrowser.openWindowModeless(getContextPath() + '/system_file/wordFileHandleAction_editword.action?id=' + id, 'fullscreen=yes;frame=yes;resizable=yes;');
		 }else if(type==1){
			 POBrowser.openWindowModeless(getContextPath() + '/system_file/wordFileHandleAction_readword.action?id=' + id, 'fullscreen=yes;frame=yes;resizable=yes;');
		 }
	 }
}
 
 
 /**
   1：只读报告变更
  * @param id
  */
 function func_readChangeCard(id){
   POBrowser.openWindowModeless(getContextPath() + '/system_file/wordFileHandleAction_readReportChangeCard.action?id=' + id, 'fullscreen=yes;frame=yes;resizable=yes;');		
}
 /**
 1：只读
* @param id
*/
function func_readCard(id){
 POBrowser.openWindowModeless(getContextPath() + '/system_file/wordFileHandleAction_readwordCard.action?id=' + id, 'fullscreen=yes;frame=yes;resizable=yes;');		
}
 /**
  * 报告上传，上传的报告能在线编辑815
  */
 
 function func_uploadAndOpenWordList(obj,fileColumn,pkId,flag){
	    var wType=2;
		var  fileID=$(obj).attr("id");
		 if((obj.value.substring(obj.value.indexOf("."))).toLowerCase()=='.doc'){
			 func_msg_error("禁止 上传“DOC”文件，请新建DOCX格式文档！");
		 		return;
		 }
		 
	 	var pkVal="";
	 	if($("#"+pkId).length>0){
	 		if($("#"+pkId).val()!=""){
	 			if(flag){
	 			 pkVal=$("#"+pkId).val();
	 			}
	 		}
	 	}else{
	 		func_msg_error("文件ID属性框不存在");
	 		return;
	 	}
	 	if($("#"+fileColumn).length<=0){
	 		func_msg_error("文件显示区不存在");
	 		return;
	 	}
	 	 $.messager.progress({ title : '请等待', msg : '上传中...'});
	 	 var option="?pkColumn="+fileColumn+"&pkId="+pkVal+"&flag="+flag;
	 	 $.ajaxFileUpload({
	          url: getContextPath()+'/system_file/fileManagerAction_save.action'+option, //用于文件上传的服务器端请求地址
	          secureuri: false,
	          fileElementId: fileID, 
	          dataType: 'json',  
	          success: function (DATA, status) 
	          {
	         	 if (DATA.STATUS == 'SUCCESS') {
	         		     var _r_Id=DATA.RETURN_DATA.fileId;
	         		     var _r_pkId=DATA.RETURN_DATA.pkId;
		         		 var _r_fileName=DATA.RETURN_DATA.fileName;
	 	         		
		         		 if(flag){
	 	         			var _tr = '<tr id=\''+_r_Id+'\'><td style="border:0px;width:30px;"><img  id=\''+_r_Id+'_img\' onclick="func_del_file(this, \''+fileColumn+'\' )" src="'+getContextPath()+'/img/icon/del.png"/></td>'
	 	         					 +'<td style="text-align:left; padding-left: 3px;border:0px " >'+_r_fileName
	 	         					 +'&nbsp;<a href="#" onclick="func_openWord(\''+_r_Id+'\','+wType+')" >编辑</a>&nbsp;<a href="#" onclick="func_file_down(\''+_r_pkId+'\',\''+fileColumn+'\',\''+_r_fileName+'\')" >下载</a></td></tr>';
		         			  	$("#"+fileColumn).append(_tr); 
		         			  	$("#"+_r_Id+"_img").attr("src",getContextPath()+'/img/icon/del.png');
	 	         		}else{
	 	         			$("#"+fileColumn).empty();
	 	         			var _tr = '<tr  id=\''+_r_Id+'\'><td style="text-align:left; padding-left: 3px;border:0px " >'+_r_fileName
	 	         				+'&nbsp;&nbsp;<a href="#" onclick="func_file_down(\''+_r_pkId+'\',\''+fileColumn+'\',\''+_r_fileName+'\')" >下载</a></td></tr>';
	 	         			 $("#"+fileColumn).append(_tr) ;
	 	         		}	
		         		 var _file_column_ids="";
		         		 var _fileCc=$("#"+fileColumn).children();
		         		 if(_fileCc.length>0){
		         			var _trLen= _fileCc.get(0).children.length
			         		for(var i=0;i<_trLen;i++){
			         			var _h=_fileCc.get(0).children[i];
			         			_file_column_ids=_file_column_ids+";"+ $(_h).attr("id");
			         		}
		         		}
		         		 $("#"+fileColumn+"_edit").val(_file_column_ids);
		         		 
		         		$("#"+pkId).val(_r_pkId);
	 				} else {
	 					func_msg_info(DATA.RETURN_DATA);
	 				}
	         	 $.messager.progress('close');
	          },
	          error: function (data, status, e)//服务器响应失败处理函数
	          {
	               func_msg_error(e);
	          }
	      }
	  );
	 }
 
 
 
 /**
 * 检索文件列表 
 * @param pkObjName 对象名
 * @param recordId 主键
 * @param pkId 关联id
 * @param flag 是否多文件 ，
  */
  function func_file_list(recordId) { 
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
								 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+' <a href="#" onclick="func_file_id_down(\''+_r_id+'\')" >下载</a></td></tr>';
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
   * 检索文件列表 
   * @param pkObjName 对象名
   * @param recordId 主键
   * @param pkId 关联id
   * @param flag 是否多文件 ，
    */
    function func_file_downLoad_list(recordId,aId) { 
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
  								 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+' <a href="#" onclick="func_file_id_down(\''+_r_id+'\')" >下载</a></td></tr>';
  								 $("#"+aId).append(_tr) ;
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
   * 显示指定文件到指定位置
   * 目标
   * @param recordId
   */
  
  function func_appoint_file_list(recordId,appoint,pointId){ 
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
									 if(appoint==v){
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];
									 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+' <a href="#" onclick="func_file_id_down(\''+_r_id+'\')" >下载</a></td></tr>';
									 $("#"+pointId).append(_tr) ;
									 }
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
   * 通过主键下载文件
   * @param id
   */
  function func_file_id_down(id){
	  window.location.href=getContextPath() + '/system_file/fileManagerAction_downFile.action?id=' + id;
  }
  /**
   * 
   * @param pk通过关联id,字段名，文件名下载
   * @param columnName
   * @param fileName
   */
  function func_file_down(pkId,columnName,fileName){
	 var  option= "?pkId=" + pkId+"&pkColumn="+columnName+"&filename="+fileName;
	  window.location.href=getContextPath() + '/system_file/fileManagerAction_downFile.action'+option;
  }
  /**
   * 删除文件
   */
   function func_del_file(obj, fileColumn) {
	var _td = $(obj).parent();
	var _tr = $(_td).parent();
	$(_tr).remove();
	var _file_column_ids = "";
	var _fileCc = $("#" + fileColumn).children();
	if (_fileCc.length > 0) {
		var _trLen = _fileCc.get(0).children.length
		for ( var i = 0; i < _trLen; i++) {
			var _h = _fileCc.get(0).children[i];
			_file_column_ids = _file_column_ids + ";" + $(_h).attr("id");
		}
	}
	$("#" + fileColumn + "_edit").val(_file_column_ids);
} 
   
   
   /**
	 * 获取url地址
	 * 
	 * @param taskId
	 * @returns {String}
	 */
   function func_fromUrl(taskId){
	   var url="";
	   $.ajax( {
				url : getContextPath() + "/system_jbpm/viewJbpmTaskDbAction_findFormUrl.action",
				type : "post",
				data :"taskId="+taskId,
				dataType : "json",
				async : false,
				success : function(DATA, request, settings) {
					if(DATA.STATUS=="SUCCESS"){
						 url =  DATA.RETURN_DATA;
						
					}else{
						$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">流程获取失败!</font>','error');
					}
				},
				error : function(event, request, settings) {
					  $.messager.progress('close');
					  $.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>','error');
				}
			});
	   return url;
   }
   
   /**
	 * 获取url地址
	 * 
	 * @param taskId
	 * @returns {String}
	 */
  function func_getJbpmVersion(taskId){
	   var version=1;
	   $.ajax( {
				url : getContextPath() + "/tried_system/systemJbpmAction_getJbpmVersion.action",
				type : "post",
				data :"taskId="+taskId,
				dataType : "json",
				async : false,
				success : function(DATA, request, settings) {
					if(DATA.STATUS=="SUCCESS"){
						version =  DATA.RETURN_DATA;
					}else{
						$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">流程获取失败!</font>','error');
					}
				},
				error : function(event, request, settings) {
					  $.messager.progress('close');
					  $.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>','error');
				}
			});
	   return version;
  }
 //scrstr 源字符串 armstr 特殊字符
function getStrCount(scrstr,armstr){ 
	   var count=0;
	   while(scrstr.indexOf(armstr) >=1 ) {
		       scrstr = scrstr.replace(armstr,"");
		       count++;    
	           }
	    return count;
	   }


   /**
    * 流程图查看
    * @param processId
    */
   function func_flowImg(processId){
   	$('<div></div>').dialog({
   		id : 'modelWindow',
   		title : '<font color="red">查看流程明细</font> ',
   		width :1000,
   		height : 450,
   		closed : false,
   		cache : false,
   		href : getContextPath() + '/page/jbpm/flowImg.html',
   		modal : true,
   		onClose : function() {
   			$(this).dialog('destroy');
   		},
   		onLoad : function() {
   			$.ajax( {
   				url : getContextPath() + "/system_jbpm/viewJbpmStatusAction_flowImg.action",
   				type : "post",
   				data :"processId="+processId,
   				dataType : "json",
   				async : false,
   				success : function(DATA, request, settings) {
   					if(DATA.STATUS=="SUCCESS"){
   						var maptask = {};  						
   						var point =  DATA.coordinates;
   						if(point!=null){
   							for(var i=0;i<point.length;i++){
   								    var _index=999+i;
   									var _html = '<img  style="position:absolute;left:'+(point[i].x+8)+'px;top:'+(point[i].y+5)+'px;width:30px;height:9px;z-index:'+_index+'" src="'+getContextPath() +'/img/png/dabangif.gif"/>';
   								    $("#flow").append(_html);
   							}
   						}
   						var history =  DATA.historyUser;
   						if(history!=null){
   							for(var i=0;i<history.length;i++){
   								    var _index=999+i;
   								    var userName=history[i].userName;
   								    var depName=history[i].depName;
   								    var workName=history[i].workName;
   								    var position=history[i].position;
   								    
   								    if(!(position.x in maptask)){   								    	
								    	maptask[position.x]=0;
								    }  								 
   									var _html = '<span  style="color:blue;border:1px; background:#FFF;position:absolute;left:'+(position.width+position.x+parseInt(maptask[position.x]))+'px;top:'+( position.y+60)+'px; height:9px;z-index:'+_index+'" >'+userName+'</span>';
   									 _html += '<span  style="color:blue;border:1px; background:#FFF;position:absolute;left:'+(position.width+position.x+parseInt(maptask[position.x]))+'px;top:'+( position.y+40)+'px; height:9px;z-index:'+_index+'" >'+depName+'('+workName+')</span>';
   								    $("#flow").append(_html);
   								 if(position.x in maptask){
								     var v= maptask[position.x];
								     var num=getStrCount(userName,',');
								    	maptask[position.x]=v+(num+1)*38;
								    }
   								    
   							}
   						}
   						var currentUser =  DATA.currentUser;
   						_currentOpt=currentUser; 
   						if(currentUser!=null){   							
   							for(var i=0;i<currentUser.length;i++){
   								    var _index=1999+i;
   								    var userName=currentUser[i].userName;
   								    var position=currentUser[i].position;  
   								    var depName=currentUser[i].depName;
								    var workName=currentUser[i].workName;
								    var fullName = depName+'('+workName+')';
   								    var _wordNameWidth = 14;//用字符串长度不行，需要的是字符串所占的像素宽度，简单处理
   								    
   								  if(!(position.x in maptask)){   								    	
								    	maptask[position.x]=0;
								    }  
   									var _html = '<span  style="color:red;border:2px; background:transparent;position:absolute;left:'+(position.width+(i*_wordNameWidth*6)+position.x+parseInt(maptask[position.x])-38)+'px;top:'+( position.y+25)+'px; height:10px;z-index:'+_index+'" >'+userName+'</span>';
   									if(depName!=""){
   										_html += '<span  style="color:red;border:2px; background:transparent;position:absolute;left:'+(position.width+(i*_wordNameWidth*6)+position.x+parseInt(maptask[position.x])-38)+'px;top:'+( position.y+5)+'px; height:10px;z-index:'+_index+'" >'+depName+'('+workName+')</span>';
   									}
   								    $("#flow").append(_html);
   								   if(position.x in maptask){
								     var v= maptask[position.x];
								     var num=getStrCount(userName,',');
								    	maptask[position.x]=v+(num+1)*38;
								     } 								    
   							}
   						}
   						var logButton = '<button onclick="func_showTimeLine(\''+processId+'\')" style="border:1px;  position:absolute;left:15px;top:35px;  z-index:2999" >流程</button>';
						$("#flow").parent().append(logButton);
   						$("#flow").append($("<img/>").attr("src",getContextPath()+"/system_jbpm/viewJbpmStatusAction_ajaxShowImg.action?processId="+processId));
   					}else{
   						$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">流程获取失败!</font>','error');
   					}
   				},
   				error : function(event, request, settings) {
   					 $.messager.progress('close');
   					  $.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>','error');
   				}
   			});
   		}
   	});
   }
   /**
    * 根据流程实例id查找流程日志
    * @param processId
    */
   function func_showTimeLine(processId){
		   	$('<div></div>').dialog({
		   		id : 'modelWindow_timeLine',
		   		title : '<font color="red">查看流程</font> ',
		   		width : 650,
		   		height : 400,
		   		closed : false,
		   		cache : false,
		   		href : getContextPath() + '/page/jbpm/logs.html',
		   		modal : true,
		   		onClose : function() {
		   			$(this).dialog('destroy');
		   		},
		   		onLoad : function() {
		   			 $.post(getContextPath() + "/system_jbpm/jbpmTaskLogAction_list.action",{process_id : processId,page:1,rows:99},function(data){
		   				loadInfo(data);
		   			 },'json');
		   		}
		   	}); 
   }
   /**
    * 根据流程实例id查找流程日志
    * @param processId
    */
   function func_log(processId){
	   	$('<div></div>').dialog({
	   		id : 'modelWindow',
	   		title : '<font color="red">日志明细</font> ',
	   		width : 920,
	   		height : 350,
	   		closed : false,
	   		cache : false,
	   		href : getContextPath() + '/page/jbpm/logContent.html',
	   		modal : true,
	   		onClose : function() {
	   			$(this).dialog('destroy');
	   		},
	   		onLoad : function() {
	   			func_logList(processId);
	   			func_contentList(processId);//流程备注
	   		}
	   	});
	   }
   /**
    * 日志详细
    * @param processId
    */
   function func_log_detail(processId){
		var _hdiv = '<div style="margin-top:10px;margin-bottom:10px;margin-left:0px;text-align:left;font-size:12px;font-weight:bold;">处理人意见区    <a href="#" onclick="func_log_detail_info(\''+processId+'\')" style="color: red">查看日志</a></div>';
		$("#processLogDetail").append(_hdiv);
   }
   function func_log_detail_info(processId){
	   $("div").remove(".logDiv");
	  
	 	 $.messager.progress({title:'请等待',msg:'处理数据中...'});
	   $.ajax({
		   url: getContextPath() + "/system_jbpm/jbpmTaskLogAction_logList.action",
		   type : "post",
		   data : "process_id="+ processId,
		   dataType : "json",
		   async : true,
	   	   success : function(DATA, request, settings) {
	   			if(DATA.STATUS=="SUCCESS"){
	   				var logList = DATA.RETURN_DATA;
	   				$.each(logList,function(key,val){
	   					var _div ='<div class="logDiv"><div style="border:1px solid #C1C1C1 ;text-align: left;margin-left: 0px;height:30px;line-height:30px;background-color: #EBF3FF">'
	   						+'<span>&nbsp;&nbsp;<font color="#318ed9"  style="font-weight:bold;">'+val.taskName+'</font> -><font   style="font-weight: bold;">'+val.optStatus+'</font>&nbsp;&nbsp;</span>'
	   						+'</div></div><div class="logDiv" style="border:1px solid #C1C1C1 ; text-align: left;margin-bottom:5px;margin-top: 1px;background-color: #D8F5B9;padding-left: 10px">'
	   						+'<span style="height: 30px;line-height: 30px;">'
	   						+'处理人：'+val.recordUserName+'  <font color=\'blue\'>|</font> 处理时间：'+ val.changeTime
	   						+'<br>处理意见：'+val.optContent
	   						/*+'<br>附件： <table id="'+val.id+'jbpmTaskLog_otherFile" width="100%" cellpadding="0" cellspacing="0" border="1" style="border: 0;"></table></span>'*/
	   						+'<div><div>';
	   					$("#processLogDetail").append(_div);
	   				});
	   				func_log_fileList(processId);
	   				$.messager.progress('close');
	   			}else{
	   				$.messager.progress('close');
	   				$.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">流程获取失败!</font>','error');
	   			}
	   		
	   		},
	   		error : function(event, request, settings) {
	   			 $.messager.progress('close');
	   			  $.messager.alert('信息','<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>','error');
	   		}
	   });
   }
   
   
   
   
   /**
    * log日志file文件 根据流程Id查找file文件
    */
  function func_log_fileList(processId){
	   $.ajax({
			url :  getContextPath()+'/system_jbpm/jbpmTaskLogAction_logFileList.action',
			type : "post",
			dataType : "json",
			data : "process_id=" + processId,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS') {
					$.each( DATA.RETURN_DATA, function(index, value){
						$.each(value, function(k, v){
							 if(k=="pkColumn"){
								 var _r_fileName=value['filename'];
								 var _r_id=value['id'];
								 var _r_pkId=value['pkId'];
								 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px;color:red " >'+value['filename']+' <a href="#" onclick="func_file_id_down(\''+_r_id+'\')" >下载</a></td></tr>';
								 $("#"+_r_pkId+v).append(_tr) ;
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
    * 流程详细+流程日志
    * @param title
    * @param taskId
    * @param id
    */
  function func_detail_log(title,taskId,id,model_id,model_name,processId){
	  var formUrl = "";
		if(model_name=="InstrumentSqinfo"){//仪器仪表申请
			formUrl = "/page/processDetail/instrumentDetail.html";
	   	}else if(model_name=="BedstandSqinfo"){//试验台申请
	   		formUrl = "/page/processDetail/bedstandDetail.html";
	   	}else if(model_name=="ContractReview"){//评审记录表申请
	   		formUrl = "/page/processDetail/contractDetail.html";
	   	}else if(model_name="ExperimentTaskInfo"){//试验任务信息
	   		formUrl = "/page/processDetail/experimentDetail.html";
	   	}
	  $('<div></div>').dialog({
	   		id : 'modelWindow',
	   		title : '<font color="red">'+title+'</font> ',
	   		width : 1000,
	   		height : 450,
	   		closed : false,
	   		cache : false,
	   		href : getContextPath() + formUrl,
	   		modal : true,
	   		onClose : function() {
	   			$(this).dialog('destroy');
	   		},
	   		onLoad : function() {
	   			func_initManager(id,model_id,processId);
	   		}
	   	});
  }
  
  /**
   * 导出到excel文件
  * head  datagrid 的head[0]标题
  * rows datagrid  $('#datagrid').datagrid("getRows");
  * 
  **/
  function func_excelFile(datagrid,excelTitle,fileName,dataHead){
	   if(dataHead!=undefined){
		   head= dataHead;
	   }
	   var rowsData =$("#"+datagrid).datagrid("getRows");
  	 var rowsTitle={};
  	 if(head.length==1){
	   	 $.each(head[0],function(i,obj){	   		
	   		 if("ck"!=obj.field){
	   			obj.title=obj.title.replace("<br/>","");
	   		 rowsTitle[obj.field]=obj.title;
	   		 }
	   	 });
  	 }
  	if(head.length==2){
  	 $.each(head[0],function(i,obj){
  		 if("ck"!=obj.field){
  		 if(obj.colspan==undefined){
  			    obj.title=obj.title.replace("<br/>","");
  				 rowsTitle[obj.field]=obj.title;
  			 }
  		 }
  	 });
	 $.each(head[1],function(i,obj){
		 obj.title=obj.title.replace("<br/>","");
  		 rowsTitle[obj.field]=obj.title;
  	 });
  	}

  	var _temHead= JSON.stringify(rowsTitle);
  		_temHead=encodeURI(encodeURI(_temHead)); 
  	var _temData= JSON.stringify(rowsData);
  	_temData=encodeURI(encodeURI(_temData));
  
   	$.ajax({
  		url : getContextPath()+ "/system_file/fileManagerAction_exportExcel.action",
  		type : "post",
  		dataType : "json",
  		data : "rowsTitle="+_temHead+"&rowsData="+_temData+"&excelTitle="+excelTitle,
  		async : false,
  		success : function(DATA, request, settings) {
  			if (DATA.STATUS == 'SUCCESS') {
  				 window.location.href=getContextPath() + '/system_file/fileManagerAction_downTempFile.action?filename='+fileName+'&recordId=' + DATA.RETURN_DATA;
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
 * 生成页脚统计 统计数字
 * fieldName “合计” 放置位置
 * exceptField 不参与计算得列 多列用“;”分割
 */
function func_createFoot(table,fieldName,exceptField){
	if(exceptField==undefined){
		exceptField="";
	}

	var rows=table.datagrid("getRows");
	var foot={}
	for(var i in rows){
		var row=rows[i];
		for(var key in row){
			if(exceptField.indexOf(key+";")==-1){
				try{
					if(!isNaN(row[key])&&row[key]!=""){
						if(foot[key]){
							foot[key]+=parseFloat(row[key]);
						}else{
							foot[key]=parseFloat(row[key]);
						}
					}
				}catch(e){
					console.log(e)
				}
			}
		
		}
	}
	
	if(fieldName!=undefined){
		foot[fieldName]="合计";
	}
	table.datagrid("reloadFooter",[foot]); 
}

 
/**
 * 全屏切换
 */
function func_size_panel(obj) {
	var _src = $(obj).attr('src');
	if (_src.indexOf("to_full.png") >= 0) {
		$("#modelWindow").dialog({
			width:1000,
			height:600
		});
		$(obj).attr('src', './../img/png/to_raw.png');
	}
	else {
		$("#modelWindow").dialog({
			width:800,
			height:400
		});
		$(obj).attr('src', './../img/png/to_full.png');
	}
}

/**
 * 富文本编辑框初始化调用
 */
var _RichEditor ;//全局变量
function func_initRichText(richId,textAreaId){
    var E = window.wangEditor;
	  _RichEditor = new E('#'+richId);
	 _RichEditor.customConfig.uploadImgShowBase64 = true;
     if(textAreaId!=undefined&&textAreaId!=null){
    	 var $_textarea = $('#'+textAreaId);
    	 _RichEditor.customConfig.onchange = function (html) {
    	   	 $_textarea.val(html);
    	 }
    }
    _RichEditor.create();
    if(textAreaId!=undefined&&textAreaId!=null){
    	$_textarea.val(_RichEditor.txt.html())
    }   
}

/**
 * 填充富文本框内容
 * @param richId
 * @param content
 */
function func_richTextContent(richId,content){
	var E = window.wangEditor;
	  _RichEditor = new E('#'+richId);
	 _RichEditor.customConfig.uploadImgShowBase64 = true;
	 _RichEditor.create();
	 _RichEditor.txt.html(content);
	 _RichEditor.$textElem.attr('contenteditable', false);
}
/**
 * 获取富文本存储内容
 */
function func_relationContext(relationId,relationColumn){
	var relationData;
	$.ajax({
		url : getContextPath() + "/system_file/systemMemoryAction_findMemory.action",
		type : "post",
		dataType : "json",
		async : false,
	    data : "relationId="+ relationId+"&relationColumn="+relationColumn,
		success : function(DATA, request, settings) {
			if (DATA.STATUS == 'SUCCESS') {
				relationData= DATA.RETURN_DATA;
			}		
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	return relationData
}

/**
 * 获取文件个数
 * @returns
 */
function func_upFileNum(id){
	var _filelen=$("#"+id).find("tr").length;
	return _filelen;
}


/**
 * 货币小写转中文
 * @param n
 * @returns
 */
function smalltoBIG(n)     
{    
    var fraction = ['角', '分'];    
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];    
    var unit = [ ['元', '万', '亿'], ['', '拾', '佰', '仟']  ];    
    var head = n < 0? '欠': '';    
    n = Math.abs(n);    
  
    var s = '';    
  
    for (var i = 0; i < fraction.length; i++)     
    {    
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');    
    }    
    s = s || '整';    
    n = Math.floor(n);    
  
    for (var i = 0; i < unit[0].length && n > 0; i++)     
    {    
        var p = '';    
        for (var j = 0; j < unit[1].length && n > 0; j++)     
        {    
            p = digit[n % 10] + unit[1][j] + p;    
            n = Math.floor(n / 10);    
        }    
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零')  + unit[0][i] + s;    
    }    
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');    
}


/**
 * 保养班组
 */
$.fn.matainClassRecordCombobox=function(recordId){	
	this.combobox({    
		url: getContextPath() + "/tried_system/systemDepartmentAction_findChildrenDep.action?recordId="+recordId,    
		valueField:'id',    
		textField:'name'
	});	
}



$.fn.leaderCombobox=function(planId){
	this.combobox({    		
		url: getContextPath() + "/tried_system/systemUserAction_getPlanUserList.action?recordId="+planId,  
		valueField:'id',    
		textField:'userName'
	});
}

function getTaskDays(date1,date2){	
	var d1 = new Date(date1.replace(/-/g, "/"));
	var d2 = new Date(date2.replace(/-/g, "/"));//当前日期：2017-04-24
	var times = d2.getTime() - d1.getTime();
	var days = parseInt(times / (1000 * 60 * 60 * 24));
	return days;
}

function getIntervalDays(nums,type){
	if(type=="日"){
		return nums;		
	}else if(type=="月"){//一个月30天
		return nums*30;
	}else{//一年365天
		return nums*365;
	}		
}

function getByDate(days,dateTime){ //获取下次保养时间	
	 var dd = new Date(dateTime.replace(/-/g, "/"));  
     dd.setDate(dd.getDate()+days);//获取AddDayCount天后的日期  
     var y = dd.getFullYear();   
     var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0  
     var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0  
     return y+"-"+m+"-"+d; 	
}

/**
 * 选择标准
 */
function func_selectStandard(){
	$('<div></div>').dialog({
		id : 'standardWindow',
		title : '选择 &nbsp;<font color="red">检测标准</font> ',
		width : 900,
		height : 450,
		closed : false,
		cache : false,
		href : getContextPath() + '/page/detail/standardList.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			func_statnd_list();
			 
		},
		buttons : [
		{
			text : '确定',
			iconCls : 'icon-ok',
			handler : function() {
				var data="";
				$.each(standMap,function(k,v){
					if(k!=undefined&&k!=null&&k!=""){
						data=data+v+';';
					}		
				});		
				//赋值检测依据
				$("#temp_jcyj").textbox('setValue',data);
				//清空检测项
				 $("#jcitemId").val(''); 
  				 $("#temp_jcitem").textbox('setValue','');   				 
				$("#standardWindow").dialog('destroy');
			}
		} ],
	});
}


/**
 * 选择试验项目
 */
function func_selectStandardItems(){
			
	var jcyj=$("#temp_jcyj").textbox("getValue");
	if(jcyj ==undefined || jcyj==''||jcyj==null){
		alert("请先选择检测依据！");
		return false;
	}
  	$('<div></div>').dialog({
  		id : 'standardItemsWindow',
  		title : '选择 &nbsp;<font color="red">检测项</font> ',
  		width : 700,
  		height :450,
  		closed : false,
  		cache : false,
  		href : getContextPath() + '/page/detail/standardItemList.html',
  		modal : true,
  		onClose : function() {
  			$(this).dialog('destroy');
  		},
  		onLoad : function() {
  			var yjData=[];
  			var yjArray=jcyj.split(";");
  			$.each(yjArray,function(k,v){
  				yjData.push({
  					 "id":k,    
  				    "text":v 
  				});
  			});  			
  			$('#standardNum_search').combobox({     
  			    valueField:'id',    
  			    textField:'text',
  			    data:yjData,
  			    onLoadSuccess:function(){
  				$(this).combobox('select',0);
  			  }
  			});   			
  			func_standardItems_list();
  		},
  		buttons : [
  		{
  			text : '确定',
  			iconCls : 'icon-ok',
  			handler : function() {
  				var itemIds="";
  				var itemNames="";				
  				$.each(_GLOUB_ITEM_MAP,function(k,v){
  					if(k.length>0&&v.length>0){
  				    itemIds=itemIds+k+';'; 
 				    itemNames=itemNames+v+';';
  					}
  				});
  				 $("#jcitemId").val(itemIds); 
  				 $("#temp_jcitem").textbox('setValue',itemNames);  
  				$("#standardItemsWindow").dialog('destroy');
  			}
  		} ],
  	});
  }


/**
 * 根据样品类型的简称获取样品全称
 * @param tag
 * @returns
 */
function getSampleTypeByTag(tag){	
	    var key="Q-其他类";
		if (tag!=null&&tag!=undefined) {
			if ("G"==tag) {
				key = "G-柜体类";
			} else if ("M"==tag) {
				key = "M-母线及桥架类";
			} else if ("X"==tag) {
				key = "X-箱体类";
			} else if ("DK"==tag) {
				key = "DK-电控类";
			} else if ("F"==tag) {
				key = "F-附件类";
			}else if ("DJ"==tag) {
				key = "DJ-电机类";
			}else if ("DL"==tag) {
				key = "DL-线缆类";
			}else if("YJ"==tag){
				key="YJ-元件类"
			} else {
				key = "Q-其他类";
			}
		} 
	  return  key;
}

/**
 * 根据设备标识检测设备是否有待处理的数据
 * @param appType
 * @returns {Boolean}
 */
function func_checkAppInfo(appType){
	var _flag=true;
	$.ajax({
		url : getContextPath() + "/tried_system/systemAppSignAction_checkAppInfo.action",
		type : "post",
		dataType : "json",
		async : false,
		data : "appType=" + appType,
		success : function(DATA, request, settings) {
			if (DATA.STATUS == 'SUCCESS') {
				_flag=DATA.RETURN_DATA;
			}
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	return _flag
}

/**
 * 下载代签名报告
 * @param recordId
 * @param fileColumn
 * @param doflag
 */
function func_signReportFile_list(recordId,fileColumn,doflag) { 
	  if(doflag==undefined){
		  doflag=0;
	  }
	 $.ajax({
				url : getContextPath()+'/system_file/fileManagerAction_list.action',
				type : "post",
				dataType : "json",
				data : "recordId=" + recordId+"&pkColumn="+fileColumn,
				async : false,
				success : function(DATA, request, settings) {
					if (DATA.STATUS == 'SUCCESS') {
						$.each( DATA.RETURN_DATA, function(index, value){
							$.each(value, function(k, v){
								
								 if(k=="pkColumn"){
									 if($("#"+v).length>0&&fileColumn.indexOf(v)!=-1){
										 var _r_fileName=value['filename'];
										 var _r_id=value['id'];
										 var _r_pkId=value['pkId'];
										 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px;color: blue; " >'+value['filename']+' <a href="#" onclick="func_sign_report_down(\''+_r_id+'\',\''+doflag+'\')" >下载</a></td></tr>';
										 $("#"+v).append(_tr) ;
									 }
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
* 下载报告模板
* @param id
*/
function func_sign_report_down(id,doflag){
	  window.location.href=getContextPath() + '/system_file/reportFileAction_reportDownFile.action?id=' + id+'&doflag='+doflag;
}

/**
* 下载报告模板
* @param id
*/
function func_sign_report_downPDF(id,doflag){
	  window.location.href=getContextPath() + '/system_file/reportFileAction_reportDownFilePDF.action?id=' + id+'&doflag='+doflag;
}

function func_PDF_down(id,fileColumn){
	 window.location.href=getContextPath() + '/system_file/fileManagerAction_downPdfFile.action?pkId=' + id+'&pkColumn='+fileColumn;	
}
/**
 * 下载docX和PDF
 */

/**
 * 根据字段查看文件列表
 * @param recordId 业务表id
 * @param fileColumn 对应字段名
 */
function func_docxAndPdf_list(recordId,fileColumn) { 
	
	 $.ajax({
			url : getContextPath()+'/system_file/fileManagerAction_list.action',
			type : "post",
			dataType : "json",
			data : "recordId=" + recordId+"&pkColumn="+fileColumn,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS'&&DATA.RETURN_DATA!=null) {

					$.each( DATA.RETURN_DATA, function(index, value){
						$.each(value, function(k, v){
							
							 if(k=="pkColumn"){
								 if($("#"+v).length>0&&fileColumn.indexOf(v)!=-1){
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];
									 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+' <a href="#" onclick="func_sign_report_down(\''+_r_id+'\',\'0\')" >下载.docx</a> &nbsp;<a href="#" onclick="func_PDF_down(\''+recordId+'\',\''+fileColumn+'\')" >下载.PDF</a></td></tr>';
									 $("#"+v).append(_tr) ;
								 }
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
 * 根据字段查看文件列表
 * @param recordId 业务表id
 * @param fileColumn 对应字段名
 */
function func_docxToPdf_list(recordId,fileColumn) { 
	 $.ajax({
			url : getContextPath()+'/system_file/fileManagerAction_list.action',
			type : "post",
			dataType : "json",
			data : "recordId=" + recordId+"&pkColumn="+fileColumn,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS'&&DATA.RETURN_DATA!=null) {

					$.each( DATA.RETURN_DATA, function(index, value){
						$.each(value, function(k, v){
							
							 if(k=="pkColumn"){
								 if($("#"+v).length>0&&fileColumn.indexOf(v)!=-1){
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];
									 var _tr = '<tr  id=\''+_r_id+'\' ><td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+'<a href="#" onclick="func_PDF_down(\''+recordId+'\',\''+fileColumn+'\')" >下载.PDF</a></td></tr>';
									 $("#"+v).append(_tr) ;
								 }
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
 * 初始化apptoolcombobox
 */
$.fn.setAppToolCombobox = function(defaultVal){
		this.combobox({    
			  url : getContextPath()+ "/tried_system/systemAppToolAction_getAppToollist.action",
			  valueField:'toolAppName',    
			  textField:'toolAppName',
			  onLoadSuccess:function(){
			 	  if(defaultVal!=undefined){
			 	    		$(this).combobox('select',defaultVal);
			 	    }
			 	}
	 	});	 
}
 

 
/**
 * 增加-备注信息
 */
function func_AddModelContext(objId) {
	 $('<div></div>').dialog({
         id : 'modelWindow',
         title : '<font color="red">备注信息</font> ',
         width : 400,
         height : 250,
         closed : false,
         cache : false,
         href : getContextPath() + '/page/jbpm/ModelContext/manager.html',
         modal : true,
         onClose : function() {
             $(this).dialog('destroy');
         },
         onLoad : function() {
        	 $('#beizhu_model_id').val(objId);
 		},
         buttons : [ {
             text : '保存',
             iconCls : 'icon-ok',
             handler : function() {
            	 $.messager.progress({title:'请等待',msg:'保存数据中...'});
    				$('#modelForm').form('submit', {
    					url: getContextPath() + "/system_jbpm/modelContextAction_add.action",
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


/*
* 纳税人识别号验证
* 15位包括地区编码6位+组织机构代码9位
*/
function checkTaxpayerId(taxpayerId){
    if(taxpayerId!="" && taxpayerId.length===18){
        var addressCode = taxpayerId.substring(0,6);
        // 校验地址码
        var check = checkAddressCode(addressCode);
        if(!check){
            return false;
        }
        // 校验组织机构代码
        var orgCode = taxpayerId.substring(6,9);
        check = isValidOrgCode(orgCode);
        if(!check){
            return false;
        }
        return true;
    }else{
        return false;
    }
}
/**
*验证组织机构代码是否合法：组织机构代码为8位数字或者拉丁字母+1位校验码。
*验证最后那位校验码是否与根据公式计算的结果相符。
*/
function isValidOrgCode(value){
    if(value!=""){
        var part1=value.substring(0,8);
        var part2=value.substring(value.length-1,1);
        var ws = [3, 7, 9, 10, 5, 8, 4, 2];
        var str = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        var reg = /^([0-9A-Z]){8}$/;
        if (!reg.test(part1))
        {
            return true
        }
        var sum = 0;
        for (var i = 0; i< 8; i++)
        {
            sum += str.indexOf(part1.charAt(i)) * ws[i];
        }
        var C9 = 11 - (sum % 11);
        var YC9=part2+'';
        if (C9 == 11) {
            C9 = '0';
        } else if (C9 == 10) {
            C9 = 'X' ;
        } else {
            C9 = C9+'';
        }
        return YC9!=C9;
    }
}
/*
*校验地址码
*/

function checkAddressCode(addressCode){
    var provinceAndCitys={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",
        31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",
        45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",
        65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
    var check = /^[1-9]\d{5}$/.test(addressCode);
    if(!check) return false;
    if(provinceAndCitys[parseInt(addressCode.substring(0,2))]){
        return true;
    }else{
        return false;
    }

}
/**
 * 存储单位转换
 * @param value
 * @param row
 * @param index
 * @returns {String}
 */
function formateSizeUnit(value,  row, index) {
    if (value == undefined||value==""||value==null) {
        return "";
    }else{
    	if(value<1024){
    		return value+" B"
    	}
    	               
    	if(1024<=value&&value<1024*1024){
    		return parseFloat(value/1024).toFixed(2)+" KB"
    	}
    	if(1024*1024<=value&&value<1024*1024*1024){
    		return parseFloat(value/1024/1024).toFixed(2)+" MB"
    	}
    	if(1024*1024*1024<=value&&value<1024*1024*1024*1024){
    		return parseFloat(value/1024/1024/1024).toFixed(2)+" GB"
    	}
    	if(1024*1024*1024*1024<=value&&value<1024*1024*1024*1024*1024){
    		return parseFloat(value/1024/1024/1024/1024).toFixed(2)+" TB"
    	}
    }
}
/**
 * 根据字段编辑文件列表
 * @param recordId 业务表id
 * @param fileColumn 对应字段名
 */
function func_singleFileEdit(recordId,fileColumn){
	 $.ajax({
			url : getContextPath()+'/system_file/fileManagerAction_singleFile.action',
			type : "post",
			dataType : "json",
			data : "recordId=" + recordId,
			async : false,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == 'SUCCESS') {
						var value=DATA.RETURN_DATA;
						$.each(value, function(k, v){
							 if(k=="pkColumn"){
								 if($("#"+v).length>0&&fileColumn.indexOf(v)>=0){
									 var _r_fileName=value['filename'];
									 var _r_id=value['id'];
									 var _r_pkId=value['pkId'];
									 var _tr = '<tr id=\''+_r_id+'\' ><td style="border:0px"><img id=\''+_r_id+'_img\' onclick="func_del_file(this,\''+v+'\')" src="'+getContextPath()+'/img/icon/del.png"/></td>'
											   +'<td style="text-align:left; padding-left: 3px;border:0px " >'+value['filename']+'&nbsp;&nbsp;<a href="#" onclick="func_file_id_down(\''+_r_id+'\')" >下载</a></td></tr>';
									 $("#"+v).append(_tr) ;
									 $("#"+_r_id+"_img").attr("src",getContextPath()+'/img/icon/del.png');
								 }
								 var _file_column_ids="";
				         		 var _fileCc=$("#"+v).children();
				         		 if(_fileCc.length>0){
				         			var _trLen= _fileCc.get(0).children.length
					         		for(var i=0;i<_trLen;i++){
					         			var _h=_fileCc.get(0).children[i];
					         			_file_column_ids=_file_column_ids+";"+ $(_h).attr("id");
					         		}
				         		}
				         		 $("#"+v+"_edit").val(_file_column_ids);
							 }
						 });
				}  
			},
			error : function(event, request, settings) {
				func_msg_error("网络异常!");
			}
		});
}
/**
 * 我的项目下载
 */
function func_fileFolder_down_project(tagValue,id){
	  if(tagValue=="我的项目"){
		  location.href = getContextPath() + "/scheduesys_data/projectFileManageAction_generateZipMyPro.action?id="+id;   
	  }
	  if(tagValue=="公开项目"){
		  location.href = getContextPath() + "/scheduesys_data/projectFileManageAction_generateZipPublicPro.action?id="+id;    
	  } 
}

function func_devicerMaxMin(deviceName){
	var resultData;
	$.ajax({
		url : getContextPath() + "/zjsys_testDataSrc/dataKeyMaxMinAction_findKeyMaxMin.action?deviceName="+deviceName,
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			resultData=DATA;
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	return resultData
}
function func_sampleMaxMin(){
	var resultData;
	$.ajax({
		url : getContextPath() + "/zjsys_testDataSrc/dataKeyMaxMinAction_findAllKeyMaxMin.action",
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			resultData=DATA;
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	return resultData
}
/**货币转换**/
function checFieldkMin(data,key,val) {
	debugger;
	 if(data.hasOwnProperty(key)){
	    	if(data[key].min!=null){
	    		if(val!=null){
	    			if(data[key].min>parseFloat(val)){
	    				return false;
	    			}else{
	    				return true;
	    			}
	    		}else{
	    			true;
	    		}
	    	}else{
	    		return true;
	    	}
	    }else{
	    	return true;
	    }
}

//大于设定值，返回为false，其余为true
function checFieldkMax(data,key,val) {
    if(data.hasOwnProperty(key)){
    	if(data[key].max!=null){
    		if(val!=null){
    			if(data[key].max<parseFloat(val)){
    				return false;
    			}else{
    				return true;
    			}
    		}else{
    			true;
    		}
    	}else{
    		return true;
    	}
    }else{
    	return true;
    }
}

/**日期转换**/
function formate0toLine(value,  row, index) {
    if (value == undefined||value==""||value=="0"||value==0) {
        return "-";
    }else{
   	 return value;
    }
}
/**数字保留4位**/
function formate4num(value,  row, index) {
    if (value == undefined||value==""||value=="0"||value==0) {
        return "-";
    }else{
   	 return parseFloat(value).toFixed(4);
    }
}
/**数字保留3位**/
function formate3num(value,  row, index) {
    if (value == undefined||value==""||value=="0"||value==0) {
        return "-";
    }else{
   	 return parseFloat(value).toFixed(3);
    }
}
/**数字保留2位**/
function formate2num(value,  row, index) {
    if (value == undefined||value==""||value=="0"||value==0) {
        return "-";
    }else{
   	 return parseFloat(value).toFixed(2);
    }
}
/**数字保留1位**/
function formate1num(value,  row, index) {
    if (value == undefined||value==""||value=="0"||value==0) {
        return "-";
    }else{
   	 return parseFloat(value).toFixed(1);
    }
}
function formateHmVal(dateTime) {
	 if (dateTime == undefined) {
       return "";
   }else{
   	 var time = new Date(dateTime.time); 
   	 var _hour = time.getHours()<10?("0"+time.getHours()):time.getHours();
	   	 var _minutes = time.getMinutes()<10?("0"+time.getMinutes()):time.getMinutes();
   	 return _hour+":"+_minutes;
   }
}
function getTgsysUrl() {
	 
	return "http://10.34.2.22:7000/";
}
