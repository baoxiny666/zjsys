//父节点id
var pId=0;
//父节点名称
var pName="";

var currentCode = "";


$(function() {

	$("#zhilrbframe").load(function(){ 
		initTree();
    });
	

	reWindowSize();
	
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
					if(companyfactorynew  == '烧结厂' || companyfactorynew == '炼铁厂'){
						var menuzhilnode = menuzhiltree.getNodes()[0].children[0];
						menuzhiltree.selectNode(menuzhilnode);
						setting.callback.onClick(null, menuzhiltree.setting.treeId, menuzhilnode);
					
					}else{
						var menuzhilnode = menuzhiltree.getNodes()[0].children[0].children[0];
						menuzhiltree.selectNode(menuzhilnode);
						setting.callback.onClick(null, menuzhiltree.setting.treeId, menuzhilnode);
					}
					
					
					
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
		debugger;
		currentCode = pName;

		//调用子页面的方法.
		var childWindow = $("#zhilrbframe")[0].contentWindow;
		childWindow.func_creatTable();  
	}
	$("#name").val("");
	
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
