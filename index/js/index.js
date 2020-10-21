var loginName = getUrlParam("loginName");
var tabIndex = 0;
$(function() {
	var _beforeUnload_time = 0,_gap_time =0;
	var is_fireFox = navigator.userAgent.indexOf("Firefox")>-1;
	window.onunload = function(){
		_gap_time = new Date().getTime() - _beforeUnload_time;
		if(_gap_time<=5){
			exitSys();
		}else{
			//exitSys();
		}
	}
	window.onbeforeunload = function(e) {  
		var e = window.event||e;  
		e.returnValue=("确定离开当前页面吗？");
		_beforeUnload_time = new Date().getTime();
		if(is_fireFox){
			exitSys();
		}
	};
	initMenu();
	// 获取当前用户信息
	$.ajax({
		url : getContextPath() + "/systemLoginAction_getLoginUser.action",
		type : "post",
		dataType : "json",
		async : true,
		success : function(DATA, request, settings) {
			$("#loginName").html(DATA.userName);
			if(DATA.userSex=="女"){
				$("#user_head_id").attr("src","../img/png/woman.png");
			}else{
				$("#user_head_id").attr("src","../img/png/man.png");
			}
			 
		},
		error : function(event, request, settings) {
			$.messager.progress('close');
			relogin();
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	reWindowSize();
	// tab菜单右击
	$('#tabContent').tabs({
		onContextMenu : function(e, title, index) {
			tabIndex = index;
			e.preventDefault();
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});
	
	//初始化待办信息
});

/**
 * 点击菜单链接
 */
function func_menuClick(menuName,menuUrl) {
	if (menuUrl == undefined || menuUrl == "") {
		return;
	}
	if ($('#tabContent').tabs('exists', menuName)) {
		$('#tabContent').tabs('select', menuName);
	} else {

		var  url = getContextPath()+menuUrl + "?_t=" + Math.random();
		var tabInfo = "<div id='tab_" + menuName + "' style='width:100%;height:100%;'><iframe scrolling='yes' frameborder='0'  src='" + url
				+ "' style='width:100%;height:100%;'></iframe></div>"
		$('#tabContent').tabs('add', {
			title : menuName,
			content : tabInfo,
			closable : true
		});
	}
}
 
/**
 * 菜单内容
 * 
 * @param item
 */
function menuHandler(item) {
	$('#tabContent').tabs('select',tabIndex);
	if (item.name == 'allColose') {
		var tabs = $('#tabContent').tabs('tabs');
		var allTabtitle = [];
		$.each(tabs, function(i, n) {
			var opt = $(n).panel('options');
			if (opt.index != 0) {
				allTabtitle.push(opt.title);
			}
		});
		for ( var i = 0; i < allTabtitle.length; i++) {
			$('#tabContent').tabs('close', allTabtitle[i]);
		}
		 $('#tabContent').tabs('select', indx);
	}
	if (item.name == 'otherClose') {
		var tabs = $('#tabContent').tabs('tabs');
		var allTabtitle = [];
		$.each(tabs, function(i, n) {
			var opt = $(n).panel('options');
			if (opt.index != 0 && opt.index != tabIndex) {
				allTabtitle.push(opt.title);
			}
		});
		for ( var i = 0; i < allTabtitle.length; i++) {
			$('#tabContent').tabs('close', allTabtitle[i]);
		}
		$('#tabContent').tabs('select', tabIndex);
	}
	if (item.name == 'rightClose') {
		var tabs = $('#tabContent').tabs('tabs');
		var allTabtitle = [];
		$.each(tabs, function(i, n) {
			var opt = $(n).panel('options');
			if (opt.index > tabIndex) {
				allTabtitle.push(opt.title);
			}
		});
		for ( var i = 0; i < allTabtitle.length; i++) {
			$('#tabContent').tabs('close', allTabtitle[i]);
		}
		$('#tabContent').tabs('select', tabIndex);
	}
	if (item.name == 'reload') {
		var _currTab = $('#tabContent').tabs('getTab', tabIndex);
		var _content = $(_currTab).panel('options').content;
		$('#tabContent').tabs('update', {
			tab: _currTab,
			options: {
				content: _content  // 新内容的URL
			}
		});

	}
}

 
function launchFullScreen(element) {
	 if(element.requestFullscreen) {
	  element.requestFullscreen();
	 } else if(element.mozRequestFullScreen) {
	  element.mozRequestFullScreen();
	 } else if(element.webkitRequestFullscreen) {
	  element.webkitRequestFullscreen();
	 } else if(element.msRequestFullscreen) {
	  element.msRequestFullscreen();
	 }
	
	}

function exitFullscreen() {
	 if(document.exitFullscreen) {
	  document.exitFullscreen();
	 } else if(document.mozCancelFullScreen) {
	  document.mozCancelFullScreen();
	 } else if(document.webkitExitFullscreen) {
	  document.webkitExitFullscreen();
	 }
	}
/**
 * 全屏切换
 */
function func_size_window(obj) {
	 
	var _src = $(obj).attr('src');
	if (_src.indexOf("to_full.png") >= 0) {
		launchFullScreen(document.documentElement);		
		reWindowSize();
		$("#bodyId").layout('collapse', 'north');
		$(obj).attr('src', './../img/png/to_raw.png');
		 
	}

	else {
		exitFullscreen();
		reWindowSize();
		$("#bodyId").layout('expand', 'north');
		$(obj).attr('src', './../img/png/to_full.png');
	}

	

}

 
 

function reSetContent(event, treeId, treeNode, clickFlag) {
	if (treeNode.address == undefined || treeNode.address == "") {
		return;
	}
	if ($('#tabContent').tabs('exists', treeNode.name)) {
		$('#tabContent').tabs('select', treeNode.name);
	} else {

		var url = "";
		if (treeNode.address.indexOf("?") != -1) {
			url = treeNode.address + "&tempId=" + treeNode.tempId + "&_t=" + Math.random();
		} else {
			url = treeNode.address + "?tempId=" + treeNode.tempId + "&_t=" + Math.random();
		}
		var tabInfo = "<div id='tab_" + treeNode.id + "' style='width:100%;height:100%;'><iframe scrolling='yes' frameborder='0'  src='" + url
				+ "' style='width:100%;height:100%;'></iframe></div>"
		$('#tabContent').tabs('add', {
			title : treeNode.name,
			content : tabInfo,
			closable : true
		});
		_tabTitle=treeNode.name;
	}
}
/**
 * 退出系统
 */
function exitSys() {
	$.ajax({
		url : getContextPath() + "/systemLoginAction_logout.action",
		type : "post",
		dataType : "json",
		async : false,
		success : function(DATA, request, settings) {
			relogin();
		},
		error : function(event, request, settings) {
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
			relogin();
		}
	});

}

function change_key() {
	$('<div></div>').dialog({
		id : 'modelWindow',
		title : '<font color="red">个人账户密码修改</font> ',
		width : 600,
		height : 260,
		closed : false,
		cache : false,
		href : getContextPath() + '/index/key_manager.html',
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
			func_checkKey();
		}
	});
}
/*******************************************************************************
 * 查询密码是否与原密码相同
 */
function func_checkKey() {
	$("#oldPassword").textbox({
		onChange : function(value) {
			$.ajax({
				url : getContextPath() + "/tried_system/systemUserAction_checkPass.action",
				type : "post",
				dataType : "json",
				data : "oldPassword=" + value,
				async : true,
				success : function(DATA, request, settings) {
					if (DATA.STATUS == 'SUCCESS') {// 正确图标
						$("#messimg").html("");
						$("#messimg").append('<img src="'+getContextPath() +'/img/icon/ok.png"/>');
					} else {// 错误
						$("#messimg").html("");
						$("#messimg").append('<img src="'+getContextPath() +'/img/icon/no.png"/>');
					}
				},
				error : function(event, request, settings) {
					relogin();
					func_msg_error("网络异常!");
				}
			});
		}
	});
}

/**
 * 个人密码修改
 */
function func_confirm() {
	// 如果新密码与确认密码不同则返回
	var _oldPassword = $("#oldPassword").textbox("getValue");
	var _nowPassword = $("#nowPassword").textbox("getValue");
	var _confirmPassword = $("#confirmPassword").textbox("getValue");
	if (_nowPassword != _confirmPassword) {
		func_msg_info("两次密码不一致");
		return;
	} else {
		$.ajax({
			url : getContextPath() + "/tried_system/systemUserAction_changeKey.action",
			type : "post",
			data : "oldPassword=" + _oldPassword + "&nowPassword=" + _nowPassword,
			dataType : "json",
			async : true,
			success : function(DATA, request, settings) {
				if (DATA.STATUS == "SUCCESS") {
					func_msg_info(DATA.RETURN_DATA);
				} else {
					func_msg_error(DATA.RETURN_DATA);
				}
			},
			error : function(event, request, settings) {
				relogin();
				$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
			}
		});
	}
}

 

/**
 * 关闭窗口
 */
function func_cancel() {
	$("#modelWindow").dialog('destroy');
}

function func_big() {

	$("#bodyId").layout('collapse', 'north');
	$("#bodyId").layout('collapse', 'west');
	$("#bodyId").layout('collapse', 'south');
}
$(window).resize(function() {
	reWindowSize();
}).resize();
function reWindowSize() {
	var _height = $(window).height() - 90;
	$('#tabContent').tabs({
		height : _height
	});
	var tabCunrrent = $('#tabContent').tabs('getSelected');
	var indx = $('#tabContent').tabs('getTabIndex',tabCunrrent);
	 $('#tabContent').tabs('select',indx);
}
 
/**
 * 刷新或关闭浏览器时
 */
$(window).bind('beforeunload',function(){
});