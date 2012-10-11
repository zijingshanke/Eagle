<%@page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	String UserName = request.getParameter("UserName");
	String Password = request.getParameter("Password");
	session.setAttribute("UserName", UserName);
	session.setAttribute("Password", Password);
%>
<html>
	<head>
		<title>UCV2.2D300 WebService 客户端测试</title>
	</head>
	<body>
		<p>
			<font color="#FF0000">Web宽乐伴侣管理端</font>
		</p>
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<!--DWLayoutTable-->
			<tr>
				<td height=20></td>
			</tr>
			<tr align="center">
				<td height="37"></td>
				<td align="left">
					<a href="./setServiceParameter.jsp">设置业务参数</a>
				</td>
			</tr>
			<tr align="center">
				<td width="25%" height="18"></td>
				<td width="30%" align="left">
					<a href="./getCallBackAddr.jsp"> 连接宽乐通信业务平台</a>
				</td>
			</tr>
			<tr align="center">
				<td width="25%"></td>
				<td width="30%" align="left">
					<a href="./getCallStatus.jsp">查询CTD呼叫</a>
				</td>
			</tr>
			<tr>
				<td width="25%"></td>
				<td width="30%" align=left>
					<a href="./getConfList.jsp">查询CTC会议列表</a>
				</td>
			</tr>
			<tr align="center">
				<td width="30%"></td>
				<td width="30%" align="center" valign="top">
					<!--DWLayoutEmptyCell-->
					&nbsp;
				</td>
			</tr>
			<tr>
				<td align=left height="20"></td>
				<td>
					<div align="center">
						<a style="CURSOR: hand" onClick="javascript:void history.back();"><img
								src="../images/back.gif" width="67" height="23" border="0"
								align="middle"> </a>
					</div>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>
