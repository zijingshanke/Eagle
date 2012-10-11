<%@ page language="java" pageEncoding="utf-8"%>
<jsp:useBean id="util" scope="session"
	class="com.jiangh.webuc.util.Utility" />
<%
String webRootDir = util.getWebRootDir();
%>
<html>

	<style type="text/css">
table {
	font-family: "宋体";
	font-size: 18px;
	line-height: 20px
}

a:hover {
	color: #FF6600;
	text-decoration: underline
}

a:link {
	color: #000000;
	text-decoration: none
}

a:active {
	text-decoration: none
}

a {
	text-decoration: none;
	color: #000000
}

a {
	text-decoration: none
}

a:hover {
	color: red;
	text-decoration: none
}
</style>

	<style type="text/css">
.a1 {
	position: relative;
	font-family: Verdana;
	font-size: 20px;
	color: #ff0000;
}
</style>
	<title>Web宽乐伴侣：点击会议</title>
	<script language="javaScript">

function formCheck(){  
 if(checkedForm.OCallee.value== null || checkedForm.OCallee.value ==""){
	 alert("主持人号码不能为空");
     checkedForm.OCallee.focus();
	 return false;
 }
 
 if(checkedForm.OCallee.value.length != 0){
    if(isNumber(checkedForm.OCallee.value) == false){
          alert("主持人号码格式错误，请重新填写");
	      checkedForm.OCallee.focus();
	      return false;
    }
  }
   
   if(checkedForm.TCallee1.value.length != 0){
       if(isNumber(checkedForm.TCallee1.value) == false){
             alert("与会者1号码格式错误，请重新填写");
	         checkedForm.TCallee1.focus();
	         return false;
      }
  }
  
  if(checkedForm.TCallee2.value.length != 0){
       if(isNumber(checkedForm.TCallee2.value) == false){
             alert("与会者1号码格式错误，请重新填写");
	         checkedForm.TCallee2.focus();
	         return false;
      }
  }
  
  if(checkedForm.TCallee3.value.length != 0){
       if(isNumber(checkedForm.TCallee3.value) == false){
             alert("与会者3号码格式错误，请重新填写");
	         checkedForm.TCallee3.focus();
	         return false;
      }
  }
  
  if(checkedForm.TCallee4.value.length != 0){
       if(isNumber(checkedForm.TCallee4.value) == false){
             alert("与会者4号码格式错误，请重新填写");
	         checkedForm.TCallee4.focus();
	         return false;
      }
  }
  
  if(checkedForm.TCallee5.value.length != 0){
       if(isNumber(checkedForm.TCallee5.value) == false){
             alert("与会者5号码格式错误，请重新填写");
	         checkedForm.TCallee5.focus();
	         return false;
      }
  }
  return true;
}



function isNumber(strString){
  var theLen = strString.length;
  var goodName = true;

  for (var i = 0 ; i < theLen ; i ++) {
    var theChar = strString.substring(i, i + 1);
    if (theChar >="0" && theChar <= "9")    {
      continue;
    } else {
      goodName = false;
      break;
    }
  }
  return goodName;
}
</script>
	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0">
		<table width="778" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td bgcolor="C3C2C1"></td>
			</tr>
		</table>
		<table width="778" border="0" cellspacing="0" cellpadding="0"
			align="center">

			<tr>

				<td width="185" valign="top">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">

						<tr>

							<td>

								<table width="99%" border="0" cellspacing="0" cellpadding="0"
									align="right">

									<tr>
										<td height="5"></td>
									</tr>

									<tr>
										<td height="63">
											<font color="#FF0000">Web宽乐伴侣客户端</font>
										</td>
									</tr>

									<tr>
										<td height="20"></td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td>
											<font color="red">&nbsp; </font>
										</td>
									</tr>


								</table>

							</td>

						</tr>

					</table>

				</td>



				<form name="checkedForm" method="post"
					action="/<%=webRootDir%>/user/goCreateConf.jsp"
					onSubmit="return formCheck()">
				<td width="593" valign="top">

					<table width="437" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="70">
								发起点击会议：
							</td>
						</tr>
						<tr>
							<th height="32" align="right">
								uc号码:
							</th>
							<td width="55%" align="left">
								<input type="text" name="uc" value="">
							</td>
						</tr>
						<tr>
							<th width="55%" height="35" align="right">
								密码:
							</th>
							<td align="left">
								<input type="password" name="pw" value="">
							</td>
						</tr>
						<tr>
							<th width="55%" height="35" align="right">
								来电显示号码:
							</th>
							<td align="left">
								<input type="text" name="Cee">
							</td>
						</tr>
						<tr>
							<th height="35" align="right">
								主持人号码:
							</th>
							<td align="left">
								<input type="text" name="OCallee">
							</td>
						</tr>
						<tr>
							<th height="35" align="right">
								与会者1号码:
							</th>
							<td align="left">
								<input type="text" name="TCallee1">
							</td>
						</tr>
						<tr>
							<th height="35" align="right">
								与会者2号码:
							</th>
							<td align="left">
								<input type="text" name="TCallee2">
							</td>
						</tr>
						<tr>
							<th width="55%" height="35" align="right">
								与会者3号码:
							</th>
							<td align="left">
								<input type="text" name="TCallee3">
							</td>
						</tr>
						<tr>
							<th width="55%" height="35" align="right">
								与会者4号码:
							</th>
							<td align="left">
								<input type="text" name="TCallee4">
							</td>
						</tr>
						<tr>
							<th height="35" align="right">
								与会者5号码:
							</th>
							<td align="left">
								<input type="text" name="TCallee5">
							</td>
						</tr>
						<tr>
							<th width="55%" nowrap="nowrap" height="35" align="right">
								会议主题:
							</th>
							<td align="left">
								<input type="text" name="sbj" value="">
							</td>
						</tr>
						<tr>
							<th nowrap="nowrap" height="35" align="right">
								会议密码:
							</th>
							<td align="left">
								<input type="text" name="confpw" value="">
							</td>
						</tr>
						<tr>
							<th height="35" colspan="2" align="right" nowrap="nowrap">
								<div align="center">
									<input name="确定" type="submit" value="确定">
									<INPUT name="重置" TYPE="reset" value="重置">
								</div>
							</th>
						</tr>
						<tr>
							<td align="left">
								<div align="center">
								</div>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td height="2"></td>
						</tr>
						<tr>

							<td width="543"></td>
							<td width="12" align="right" valign="top">
								<a style="CURSOR: hand"
									onClick="javascript:void history.back();"><img
										src="/<%=webRootDir%>/images/back.gif" width="67" height="23"
										border="0" align="middle"> </a>
							</td>
							<td width="67">
								&nbsp;
							</td>
						</tr>
					</table>
					</form>
		</table>
	</body>
</html>



