<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>欢迎登录论坛群发系统</title>
		<link href="./_css/resetLogin.css" rel="stylesheet" type="text/css" />
		<link href="./_css/style.css" rel="stylesheet" type="text/css" />
		<script src="_js/common.js" type="text/javascript"></script>
		<script language="JavaScript">
		if (self!=top){
		   top.location=self.location;
		 }	
			
		 function submitForm(){
		   document.forms[0].submit();    
		 }
		 
		 function  resetForm(){
		   document.forms[0].userNo.value="";
		   document.forms[0].userPassword.value="";
		   document.forms[0].rand.value="";   
		 }

	</script>
	</head>
	<body onload="document.forms[0].userNo.focus();">
		<form id="uf"
			action="<%=request.getContextPath()%>/user/user.do?thisAction=login"
			method="post">
			<div id="adminLoginContainer">
				<div class="loginPanel">
					<p>
						<font color="red" size="5">论坛群发系统</font>
					</p>
					<p>
						登录帐号：
						<input name="userNo" style="width: 170px;" />
					</p>
					<p>
						登录密码：
						<input name="userPassword" type="password" value=""
							style="width: 170px;" />
					</p>
					<p>
						&nbsp;&nbsp;验证码：
						<input name="rand" title="请输入右侧验证码" size="6" class="colorblue"
							onkeydown="if(event.keyCode==13){submitForm();}" />
						<html:img border="0" page="/servlet/com.neza.base.NumberImage"
							align="absmiddle" height="21" width="64" />
					</p>
					<p class="btnArea">
						<input name="label" type="button" class="button1" value="登 录"
							onClick="submitForm();" />
						<input name="label" type="button" class="button1" value="重 置"
							onclick="resetForm();" />
					</p>
					<p>
						<font color="red"> <c:if test="${err eq 'randError'}">验证码错误!</c:if>
							<c:if test="${err eq 'passError'}">登录密码错误！</c:if> <c:if
								test="${err eq 'nameError'}">登录账号错误！</c:if> <c:if
								test="${err eq 'statusError'}">您的账号已经被停用！请联系管理员！</c:if> </font>
					</p>
				</div>
			</div>
		</form>
	</body>
</html>
