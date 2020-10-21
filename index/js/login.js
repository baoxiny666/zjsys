$(function() {
	document.onkeydown = function(e) {
		var ev = document.all ? window.event : e;
		if (ev.keyCode == 13) {
			login()
		}
	}
	
//$("#loginName").textbox("setBackColor","textbox-background-transparent");	
$('#loginName').textbox('textbox').css('background','transparent');	
$('#loginPass').textbox('textbox').css('background','transparent');	
$(".textbox").css('background','transparent').css('border','1px solid #000');
$(".textbox-text").css('font-size','16px');
$(".calendar").css("border-color","#E59B15");
$(".calendar-title").css('background','#E59B15').css('height','30px');
$(".calendar table th").css('font-size','15px').css('height','30px');
$(".calendar table td").css('font-size','17px');
$(".calendar-header").css('height','30px');

});

/**
 * 登录
 */
function login() {
	debugger;
	var _loginName = $("#loginName").val();
	var _loginPass = $("#loginPass").val();
	var _retVar=func_checkRepeat(_loginName);
	if(!_retVar){return;}
	$.ajax({
		url : getContextPath() + "/systemLoginAction_login.action",
		type : "post",
		dataType : "json",
		data : "loginName=" + _loginName + "&loginPass=" + _loginPass,
		async : false,
		success : function(DATA, request, settings) {
			if (DATA.STATUS == 'SUCCESS') {
				window.location.href = getContextPath() + "/index/index.html";
			} else {
				$.messager.show({
					title : '警告',
					msg : '<font style="font-weight: bold;font-size: 15;color: red">' + DATA.RETURN_DATA + '</font>',
					showType : 'fade',
					timeout : 1000,
					style : {
						right : '',
						bottom : ''
					}
				});
			}
		},
		error : function(event, request, settings) {
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
}

function func_checkRepeat(_loginName){
	debugger;
	var retVar=false;
	var _onlineUser;
	$.ajax({
		url : getContextPath() + "/systemLoginAction_checkRepeat.action",
		type : "post",
		dataType : "json",
		data : "loginName="+_loginName,
		async : false,
		success : function(DATA, request, settings) {
			if (DATA.STATUS == 'SUCCESS') {
				 _onlineUser = DATA.RETURN_DATA;
				 if(_onlineUser!='YES'){
					 retVar = confirm("用户IP"+_onlineUser.loginIP+"正在登陆,是否继续登陆？");
				 }else{
					 retVar = true;
				 }
			}
		},
		error : function(event, request, settings) {
			$.messager.alert('信息', '<font style="font-weight: bold;font-size: 15;color: red">网络异常!</font>', 'error');
		}
	});
	return retVar;
}

