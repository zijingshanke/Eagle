<%@ page language="java" pageEncoding="utf-8"%>

<%
	String UserName = request.getParameter("UserName");
	String Password = request.getParameter("Password");
	session.setAttribute("UserName", UserName);
	session.setAttribute("Password", Password);
%>
<html>
	<head>
		<title>Web宽乐伴侣客户端</title>
	</head>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<!--DWLayoutTable-->
		<tr align="center">
			<td width="30%" align="center">
				<a href="./dialCall.jsp"><font size="5">发起点对点呼叫 </font> </a>
			</td>
		</tr>
		<tr>

			<td width="30%" align="center">
				<a href="./createConf.jsp"><font size="5">发起点击会议呼叫</font> </a>
			</td>
		</tr>
		<tr>

			<td width="30%" align="center">
				<a href="./sendSMS.jsp"><font size="5">发送短信</font> </a>
			</td>
		</tr>
		<tr align="center">
			<td width="30%">
				<a style="CURSOR: hand" onClick="javascript:void history.back();"><img
						src="../images/back.gif" width="67" height="23" border="0"
						align="middle"> </a>
			</td>
		</tr>
	</table>
	</body>
</html>
