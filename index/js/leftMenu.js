// 导航菜单的间隔像素


var menuCell = 1;
function initMenu() {
	layui.use('element', function(){
	    var element = layui.element;
	    var $ = layui.jquery;
	   var data; 
		$.ajax({
		url : getContextPath() + "/tried_system/systemMenuAction_initTree.action",
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			debugger;
			if (DATA.STATUS == 'SUCCESS') {
				
				data = DATA.RETURN_DATA;
				if(data==null){
					relogin();
				}
			} else {
				relogin();
			}
		},
		error : function(event, request, settings) {
			relogin();
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
			
		}
	});
//  data = JSON.parse(data);
    var liStr= "";
    // 遍历生成主菜单
    for( var i = 0; i <data.length; i++){
        // 判断是否存在子菜单
        if(data[i].childMenus!=null&&data[i].childMenus.length>0){
            liStr+="<li class=\"layui-nav-item\"><a class=\"\" href=\"javascript:;\"><i class='layui-icon "+ data[i].icon +"'></i> "+data[i].name+"</a>\n"+
                        "<dl class=\"layui-nav-child\">\n";
            // 遍历获取子菜单
            for( var k = 0; k <data[i].childMenus.length; k++){
                liStr+=getChildMenu(data[i].childMenus[k],0);
            }
            liStr+="</dl></li>";
        }else{
            liStr+="<li class=\"layui-nav-item\"><a class=\"\" href=\"javascript:;\" data-url=\""+data[i].url+"\"><i class='layui-icon "+ data[i].icon +"'></i> "+data[i].name+"</a></li>";
        }
    };
    $("#menu").html("<ul class=\"layui-nav layui-nav-tree\"  lay-filter=\"test\">\n" +liStr+"</ul>");
    element.init();
    
    // 页面切换
    $(document).on('click', '#menu a', function(){
    	var menuUrl = $(this).attr("data-url");
    	var menuName = $(this).text();
    	if(typeof(menuUrl) != "undefined"&&menuUrl!=""){
    		if ($('#tabContent').tabs('exists', menuName)){
    			 $('#tabContent').tabs('select', menuName);
    		} else {
    			var  url = getContextPath()+menuUrl + "?_t=" + Math.random();
    			var tabInfo = "<div id='tab_" + menuName + "' style='width:100%;height:100%;'><iframe scrolling='yes' frameborder='0'  src='" + url+ "' style='width:100%;height:100%;'></iframe></div>"
	    			$('#tabContent').tabs('add', {
	    				title : menuName,
	    				content : tabInfo,
	    				closable : true
	    			});
    		}
    	}
    });
});
}
// 递归生成子菜单
function getChildMenu(subMenu,num) {
   
    num++;
    var subStr = "";
    if(subMenu.childMenus!=null&&subMenu.childMenus.length>0){
        subStr+="<dd><ul><li class=\"layui-nav-item\" ><a style=\"text-indent: "+num*menuCell+"em\" class=\"\" href=\"javascript:;\"><i class='layui-icon "+ subMenu.icon +"'></i> "+subMenu.name+"</a>" +
                "<dl class=\"layui-nav-child\">\n";
        for( var j = 0; j <subMenu.childMenus.length; j++){
            subStr+=getChildMenu(subMenu.childMenus[j],num);
        }
        subStr+="</dl></li></ul></dd>";
    }else{
        subStr+="<dd><a style=\"text-indent:"+num*menuCell+"em\" href=\"javascript:;\" data-url=\""+subMenu.url+"\"><i class='layui-icon "+ subMenu.icon +"'></i> "+subMenu.name+"</a></dd>";
    }
    return subStr;
} 