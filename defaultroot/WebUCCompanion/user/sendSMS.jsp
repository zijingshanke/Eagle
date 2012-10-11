<%@ page language="java" pageEncoding="utf-8"%>
<html>
	<title>发送短信</title>
	<script language="javaScript">
function formCheck(){
  return true;
}

function isNumber(strString){
  var theLen = strString.length;
  var goodName = true;
  for (var i = 0 ; i < theLen ; i ++) {
    var theChar = strString.substring(i, i + 1);
    if (theChar >="0" && theChar <= "9") {
      continue;
    } else  {
      goodName = false;
      break;
    }
  }
  return goodName;
}
</script>
	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0">
		<form action=" ./goSendSMS.jsp" name="checkedForm"
			method="post  onSubmit="returnformCheck()">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<font color="#0000FF">发送短信</font>
					</td>
				</tr>
				<tr>
					<th align="center" height="35">
						uc号码:
					</th>
					<td width="55%" align="left">
						<input type="text" name="uc" value="2167717">
					</td>
				</tr>
				<tr>
					<th width="55%" height="35" align="center">
						密码:
					</th>
					<td align="left">
						<input type="password" name="pw" value="12345678">
					</td>
				</tr>
				<tr>
					<th height="35" align="center">
						接收短信号码:
					</th>
					<td align="left">
						<input type="text" name="receiver" value="15811681469" size="16"
							maxlength="32">
					</td>
				</tr>
				<tr>
					<th height="35" align="center">
						短信内容:
					</th>
					<td align="left">
						<textarea name="content" cols="55%" rows="5">123</textarea>
					</td>
				</tr>
				<tr>
					<th></th>
					<td align="left">
						<input name="确定" type="submit" value="确定">
						<input name="重置" type="reset" value="重置">
						<a style="CURSOR: hand" onClick="javascript:void history.back();">
							<img src="../images/back.gif" width="67" height="23" border="0"
								align="middle"> </a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

