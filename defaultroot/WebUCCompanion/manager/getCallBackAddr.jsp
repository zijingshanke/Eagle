<%@ page language="java" pageEncoding="utf-8"%>

<html>
	<title>设置回调地址</title>
	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0">
		<form name="checkedForm" method="post"
			action="./goGetCallBackAddr.jsp" onSubmit="return submitButton()">

			<table width="302" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="70" colspan="2">
						连接宽乐通信业务
					</td>
				</tr>
				<tr>
					<th height="35" align="right">
						管理员uc号码:
					</th>
					<td width="64%" align="left">
						<input type="text" name="uc" size="32" maxlength="18">
					</td>
				</tr>
				<tr>
					<th width="36%" height="35" align="right">
						密码:
					</th>
					<td width="64%" align="left">
						<input type="password" name="pw" size="16" maxlength="18">
					</td>
				</tr>
				<tr>
					<th></th>
					<td nowrap align="left">
						<INPUT name="确定" TYPE="submit" value="确定">
						<INPUT name="重置" TYPE="reset" value="重置">
					</td>
				</tr>

				<tr>
					<th></th>
					<td width="12" align="right" valign="top">
						<a style="CURSOR: hand" onClick="javascript:void history.back();"><img
								src="../images/back.gif" width="67" height="23" border="0"
								align="middle"> </a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>

