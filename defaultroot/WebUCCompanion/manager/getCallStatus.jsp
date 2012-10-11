<%@ page language="java" pageEncoding="utf-8"%>
<html>
	<title>Web宽乐伴侣管理端： 查询CTD呼叫状态</title>
	<script language="javaScript">
function formCheck(){
  return true;
}

function isNumber(strString){
  var theLen = strString.length;
  var goodName = true;
  for (var i = 0 ; i < theLen ; i ++)  {
    var theChar = strString.substring(i, i + 1);
    if (theChar >="0" && theChar <= "9")  {
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
		<form name="checkedForm" method="post" action="./gogetCallStatus.jsp"
			onSubmit="return formCheck()">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="33">
						查询呼叫信息：
					</td>
				</tr>
				<tr>
					<th height="35" align="right">
						uc号码:
					</th>
					<td width="44%" align="left">
						<input type="text" name="uc" value="">
					</td>
				</tr>
				<tr>
					<th height="35" nowrap="nowrap" align="right">
						密码:
					</th>
					<td width="44%" align="left">
						<input type="password" name="pw" value="">
					</td>
				</tr>
				<tr>
					<th>
					</th>
					<td>
						<input name="确定" type="submit" value="确定">
						<input name="重置" type="reset" value="重置">
						<a style="CURSOR: hand" onClick="javascript:void history.back();"><img
								src="../images/back.gif" width="67" height="23" border="0"
								align="middle"> </a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

