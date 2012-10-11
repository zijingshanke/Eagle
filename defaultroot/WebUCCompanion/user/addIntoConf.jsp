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

	<title>Web宽乐伴侣客户端：主持人加入与会者</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script language="javaScript">
function formCheck()
{
	

  return true;
}

function isNumber(strString)
{
  var theLen = strString.length;
  var goodName = true;
  for (var i = 0 ; i < theLen ; i ++)
  {
    var theChar = strString.substring(i, i + 1);

    if (theChar >="0" && theChar <= "9")
    {

      continue;
    }
    else
    {

      goodName = false;
      break;
    }
  }

  return goodName;
}

</script>




	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0">

		<table width="778" height="66" border="0" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td bgcolor="#FFFFFF"></td>
			</tr>
			<tr>
				<td bgcolor="#FFFFFF">
					<font color="#FF0000">Web宽乐伴侣客户端</font>
				</td>
			</tr>
		</table>
		<table width="778" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr>
				<td width="228" height="379" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<table width="99%" border="0" cellspacing="0" cellpadding="0"
									align="right">
									<tr>
										<td height="5"></td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>

				<form name="checkedForm" method="post"
					action="/<%=webRootDir%>/user/goAddIntoConf.jsp"
					onSubmit="return formCheck()">
				<td width="550" valign="top">
					<table width="366" height="243" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="47" colspan="2"></td>
						</tr>
						<tr>
							<td height="57" colspan="2">
								<font size="+2">请输入与会者号码：</font>
							</td>
						</tr>
						<tr>
							<th width="36%" align="right">
								与会者号码:
							</th>
							<th width="46%" height="35" align="right">
								<input type="text" name="callee" value="" maxlength="32">
							</th>
							<td width="18%" align="left">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td height="104" colspan="2" align="left">
								<div align="center">
									<input name="确定" type="submit" value="确定">
									<input name="重置" type="reset" value="重置">
								</div>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td width="129"></td>
							<td width="73" align="right" valign="top">
								<a style="CURSOR: hand"
									onClick="javascript:void history.back();"><img
										src="/<%=webRootDir%>/images/back.gif" width="67" height="23"
										border="0" align="middle"> </a>
							</td>
							<td width="149">
								&nbsp;
							</td>
						</tr>
					</table>


					</form>
		</table>
	</body>
</html>

