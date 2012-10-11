<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.util.Calendar"%>
<jsp:useBean id="serviceSMS" scope="session"
	class="com.jiangh.webuc.webclient.SMSWebClient" />
<jsp:useBean id="util" scope="session"
	class="com.jiangh.webuc.util.Utility" />
<%
	/*
	功能： 发送短信
	方法： SendSMS
	 String SendSMS(String uc,String pw, String rand,String callee[], String isreturn, String cont,int msgid,String connID)
	 */
	String uc = request.getParameter("uc");
	String pw = request.getParameter("pw");
	session.setAttribute("uc", uc);
	session.setAttribute("pw", pw);

	String receiver = request.getParameter("receiver");
	String[] callee = new String[] { receiver };
	String isreturn = "0";
	String cont = new String(request.getParameter("content").getBytes(
			"8859_1"));

	System.out.println("uc:" + uc);
	System.out.println("pw:" + pw);
	System.out.println("callee:" + receiver);
	System.out.println("content:" + cont);

	//计算msgid
	Calendar calendar = Calendar.getInstance();
	StringBuffer strBuf = new StringBuffer();
	//strBuf.append(calendar.get(Calendar.YEAR));
	//strBuf.append(calendar.get(Calendar.MONTH));
	//strBuf.append(calendar.get(Calendar.DAY_OF_MONTH));
	strBuf.append(calendar.get(Calendar.HOUR_OF_DAY));
	strBuf.append(calendar.get(Calendar.MINUTE));
	strBuf.append(calendar.get(Calendar.SECOND));
	strBuf.append(calendar.get(Calendar.MILLISECOND));
	int msgid = Integer.parseInt(strBuf.toString());
	System.out.println("msgid:" + String.valueOf(msgid));

	//String sessionID = null;
	String sessionID = serviceSMS.SMSWebClient(uc, pw, callee,/*msgid,*/
	cont, isreturn);
	session.setAttribute("sessionID", sessionID);

	System.out.println("sessionID:" + sessionID);
%>

<SCRIPT LANGUAGE="JavaScript">
function openfile(){
	window.open("./client/help.jsp");
}
</SCRIPT>
<html>
	<head>
		<title>Web宽乐伴侣客户端：点击拨号</title>
	</head>
	<table width="98%" height="377" border="0">
		<tr>
			<td height="88">
				&nbsp;
			</td>
			<td>
				<div align="right">
					发送结果:
				</div>
			</td>
			<td width="11%"><%=sessionID%></td>
			<td colspan="2">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td height="106">
				&nbsp;
			</td>
			<td>
				<div align="right">
					<a style="CURSOR: hand" onClick="javascript:void history.back();"><img
							src="../images/back.gif" width="67" height="23" border="0"
							align="middle"> </a>
				</div>
			</td>
			<td>
				<a style="CURSOR: hand" onClick="openfile();"><font size=""
					color="blue">帮助</font> </a>
			</td>
			<td colspan="2">
				&nbsp;
			</td>
		</tr>
	</table>
</html>
