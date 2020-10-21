<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String yinggytype = request.getParameter("yinggytype");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Expires" content="0"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Cache-control" content="no-cache"/>
<meta http-equiv="Cache" content="no-cache"/>
	<script type="text/javascript">
		var path = "<%=path%>";
		var basePath = "<%=basePath%>";
		var yinggytype = "<%=yinggytype%>";
	</script>
	<link rel="stylesheet" type="text/css" href="./../../../css/themes/material/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="./../../../css/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="./../../../js/layui/css/layui.css" />
	<link rel="stylesheet" type="text/css" href="./../../../css/tried.css" />
	<script type="text/javascript" src="./../../../js/jquery.min.js"></script>
	<script type="text/javascript" src="./../../../js/jquery.tried.min.js"></script>
	<script type="text/javascript" src="./../../../js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="./../../../js/tried.js"></script>
	<script type="text/javascript" src="js/list.js"></script>
	<script type="text/javascript" src="js/fly.js"></script>
</head>
<body style="overflow: hidden;margin: 0,0,0,0">
<div id="divConId"> 
 <div id='layoutId' class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',title:''" style="border: 0px" >
      <div id="datagrid"   style="width:97%" data-options="fit:true"></div>
      <div id="tb" style="padding:2px 5px;" >
	    <table cellpadding="0" cellspacing="0" border="0" width="100%">
	    <tr>
	    	<td>
	    	        样品编号： <input  id="sampleNum_search"   class= "easyui-textbox" style="width:120px"/>
	    	              日期：<input  id="objStartTime_search"  data-options="onSelect:func_search" class= "easyui-datebox" style="width:100px"/>
	    	                      状态:<select id="dataStatus_search" class="easyui-combobox" data-options="onSelect:func_search,panelHeight:100"  style="width: 100px"> 
								<option selected="selected" value="原始数据" >原始数据</option>
								<option value="已提交">已提交</option>
								<option value="作废数据">作废数据</option>
					         </select>      
               <a href="#" class="layui-btn  layui-btn-sm" onclick="func_search()">   <i class="layui-icon">&#xe615;</i>检索 </a>				
			</td>
			<td align="right">	
				    <a href="#" class="layui-btn  layui-btn-sm layui-btn-danger" onclick="func_del()">   <i class="layui-icon">&#xe640;</i> 删除数据 </a>
					<a href="#" class="layui-btn  layui-btn-sm" onclick="func_recovery()">   <i class="layui-icon">&#xe65c;</i> 恢复数据 </a>
			        <a href="#" class="layui-btn  layui-btn-sm layui-btn-normal" onclick="func_upload()">   <i class="layui-icon">&#xe62f;</i>手动刷新</a>			 
			        <a href="#" class="layui-btn  layui-btn-sm layui-btn-warm" onclick="func_fly()">   <i class="layui-icon"> &#xe609;</i>提交</a>
			</td>
		</tr>
	</table>
	</div>
      </div>   
     <div data-options="region:'south',title:'发送数据',split:true" style="height:260px;border: 0px">   
        <div id="flygrid"  style="width:100%;" data-options="fit:true" ></div>
         <div id="tbExc" style="padding:2px 5px;" >
	</div>
     </div>     
    </div> 



</div>

	
</body>
</html>
