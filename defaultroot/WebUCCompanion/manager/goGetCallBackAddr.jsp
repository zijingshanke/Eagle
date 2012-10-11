<%@page language="java" contentType="text/html; charset=GBK"%>

<jsp:useBean id="service" scope="session"
	class="com.jiangh.webuc.webclient.RegisterWebClient" />
<jsp:useBean id="util" scope="session"
	class="com.jiangh.webuc.util.Utility" />

<%
	/*
	参数strUserID为全能聊用户号码。
	参数nSPNumber为充值用户所在地编号。
	参数strStartTime为查询充值信息开始时间，年月日时分秒，14位字符串（YYYYMMDDhhmmss）。
	参数strStopTime为查询充值信息结束时间，年月日时分秒，14位字符串（YYYYMMDDhhmmss）。
	返回二维字符串数组String[][]
	public String[][] GetCallRecordsDuration(String strUserID , int nSPNumber ,
	                                        String strStartTime, String strStopTime)
	 */
	String uc = request.getParameter("uc");
	String pw = request.getParameter("pw");

	System.out.println(uc);
	System.out.println(pw);

	String returnStr = service.registerToService(uc, pw);
	System.out.println(returnStr);
%>

<SCRIPT LANGUAGE="JavaScript">
function openfile(){
	window.open("./WebUCCompanion/client/help.jsp");
}
</SCRIPT>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>连接并注册宽乐通信业务</title>
	</head>
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border=1 align=center>


		<tr>
			<td colspan="12" align=center>
				WebService调用完成，返回结果为：
			</td>
		</tr>
		<tr>
			<td colspan="12" align=center><%=returnStr%></td>
		</tr>
		<tr>
			<td colspan=12>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td align="center" width="50%" colspan=12>
				<a style="CURSOR: hand" onClick="javascript:void history.back();">
					<img src="../images/back.gif" width="67" height="23" border="0"
						align="middle"> </a>&nbsp;&nbsp;
				<a style="CURSOR: hand" onClick="openfile();"><FONT SIZE=""
					COLOR="blue">帮助</FONT> </a>
			</td>

		</tr>
	</table>
</html>
