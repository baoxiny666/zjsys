$(function() {
	reWindowSize();
	initTabs();
})
/**
 * 初始化tabs
 */
function initTabs(){
	$('#tabs').tabs({    
	    border:false,
	    tabHeight:22,
	    collapsible:true
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
	$("#divConId-index").css("height",_height);
	$('#tabs').css("height","97%");
}
