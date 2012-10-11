<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
	<jsp:useBean id="serviceManager" scope="session"
		class="com.jiangh.webuc.webclient.ServiceManagerWebClient" />
	<jsp:useBean id="util" scope="session"
		class="com.jiangh.webuc.util.Utility" />
	<%
				serviceManager.setLocalWSAddr((String) request
				.getParameter("localServiceAddr"));
		serviceManager.setRegisterWSURL((String) request
				.getParameter("UCRegisterServiceAddr"));
		serviceManager.setCTDWSURL((String) request
				.getParameter("UCCTDServiceAddr"));
		serviceManager.setCTCWSURL((String) request
				.getParameter("UCCTCServiceAddr"));
		serviceManager.setSMSWSURL((String) request
				.getParameter("UCSMSServiceAddr"));
		String accessMode = util.getThirdPtyAppAccessMode();
		//if (accessMode.equals("webservice")) {
		//serviceManager.setThirdPartyServiceURL((String) request
		//.getParameter("ThirdPtyServiceAddr"));
		//}
	%>
	<title>Web宽乐伴侣：点击会议</title>

	<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0"
		marginwidth="0" marginheight="0">
		<form name="checkedForm" method="post"
			action="./goSetServiceParameter.jsp" onSubmit="return formCheck()">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="70">
						<div align="center">
							参数设置成功！
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" valign="top">
						<a style="CURSOR: hand" onClick="closeWindow();"><img
								src="../images/submit.gif" width="67" height="23" border="0"
								align="middle"> </a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>



