<%@page language = "java" contentType="text/html; charset=GBK" %>


<jsp:useBean id="serviceCTD" scope="session" class="com.jiangh.webuc.webclient.CTDWebClient"/>
<jsp:useBean id="util" scope="session" class="com.jiangh.webuc.util.Utility"/>
<%
   String webRootDir = util.getWebRootDir();
%>

<SCRIPT LANGUAGE="JavaScript">
function openfile()
{
	window.open("/<%=webRootDir%>/client/help.jsp");
}
</SCRIPT>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Web宽乐伴侣客户端：点击拨号</title>
</head>
<%
    /*
     功能： 点击呼叫
     方法： DialCall
      String DialCallClient(String uc,String pw, String rand,String [] callee, String connID,String Cee)
     */
  String uc = request.getParameter("uc");
 String pw = request.getParameter("pw");
  session.setAttribute("uc",uc);
  session.setAttribute("pw",pw); 

  String callee = request.getParameter("callee");
  String called = request.getParameter("called");
  String tmp = callee + "|";
  String tmp2 = called + "|";
  String[] call = new String[]{tmp,tmp2};
  String cee = request.getParameter("cee");
  
  System.out.println("uc:"+uc);
  System.out.println("pw:"+pw);
  System.out.println("callee:"+tmp);
  System.out.println("called:"+tmp2);
  System.out.println("cee:"+cee);

  String sessionID = serviceCTD.dialCallClient(uc, pw,call,cee);
  session.setAttribute("sessionID",sessionID);

%>
<table width="98%" height="377" border="0">
  <tr> 
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr> 
    <td width="22%"><font color="#FF0000">Web宽乐伴侣客户端</font></td>
    <td colspan="4">&nbsp;</td>
  </tr>
  <tr> 
    <td height="31" colspan="5">&nbsp;</td>
  </tr>
  <tr> 
    <td height="60">&nbsp;</td>
    <td width="31%"><div align="center"><a href="/<%=webRootDir%>/user/gogetCallStatus.jsp"></a></div></td>
    <td colspan="2"><div align="center"><a href="/<%=webRootDir%>/user/goStopCTD.jsp"><font size="4">终止呼叫</font></a></div></td>
    <td width="21%">&nbsp;</td>
  </tr>
  <tr> 
    <td height="88">&nbsp;</td>
    <td><div align="right">呼叫结果:</div></td>
    <td width="11%"><%=sessionID%></td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr> 
    <td height="106">&nbsp;</td>
    <td><div align="right"><a style="CURSOR: hand"  onClick="javascript:void history.back();"><img src="/<%=webRootDir%>/images/back.gif" width="67" height="23" border="0" align="middle"></a></div></td>
    <td><a
                  style="CURSOR: hand"
                  onClick="openfile();"><font size="" color="blue">帮助</font></a></td>
    <td colspan="2">&nbsp;</td>
  </tr>
</table>
<p>&nbsp;</p>
</html>
