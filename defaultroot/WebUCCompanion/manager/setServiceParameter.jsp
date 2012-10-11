<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
	<jsp:useBean id="serviceManager" scope="session"
		class="com.jiangh.webuc.webclient.ServiceManagerWebClient" />
	<jsp:useBean id="util" scope="session"
		class="com.jiangh.webuc.util.Utility" />

	<%
	String accessMode = util.getThirdPtyAppAccessMode();
	%>

	<title>Web宽乐伴侣：点击会议</title>

	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0">
		<form name="checkedForm" method="post"
			action="./goSetServiceParameter.jsp">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="70" colspan=2>
						设置业务参数：
					</td>
				</tr>
				<tr>
					<th height="32" align="right">
						本地消息回调服务地址:
					</th>
					<td align="left">
						<input type="text" size="65" name="localServiceAddr"
							value="<%=serviceManager.getLocalWSAddr()%>">
					</td>
				</tr>
				<tr>
					<th width="55%" height="35" align="right">
						宽乐通信Register服务地址:
					</th>
					<td align="left">
						<input type="text" size="65" name="UCRegisterServiceAddr"
							value="<%=serviceManager.getRegisterWSURL()%>">
					</td>
				</tr>
				<tr>
					<th width="55%" height="35" align="right">
						宽乐通信CTD服务地址:
					</th>
					<td align="left">
						<input type="text" size="65" name="UCCTDServiceAddr"
							value="<%=serviceManager.getCTDWSURL()%>">
					</td>
				</tr>
				<tr>
					<th height="35" align="right">
						宽乐通信CTC服务地址:
					</th>
					<td align="left">
						<input type="text" size="65" name="UCCTCServiceAddr"
							value="<%=serviceManager.getCTCWSURL()%>">
					</td>
				</tr>
				<tr>
					<th height="35" align="right">
						宽乐通信SMS服务地址:
					</th>
					<td align="left">
						<input type="text" size="65" name="UCSMSServiceAddr"
							value="<%=serviceManager.getSMSWSURL()%>">
					</td>
				</tr>
				
				<tr>
					<th></th>
					<td>
						<input name="确定" type="submit" value="确定" />
						<INPUT name="重置" TYPE="reset" value="重置" />
						<a style="CURSOR: hand" onClick="javascript:void history.back();"><img
								src="../images/back.gif" width="67" height="23" border="0"
								align="middle"> </a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>