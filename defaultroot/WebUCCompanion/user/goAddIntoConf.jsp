<%@page language = "java" contentType="text/html; charset=GBK" %>

<jsp:useBean id="serviceCTC" scope="session" class="com.jiangh.webuc.webclient.CTCWebClient"/>
<jsp:useBean id="util" scope="session" class="com.jiangh.webuc.util.Utility"/>
<%
   String webRootDir = util.getWebRootDir();
%>
<SCRIPT LANGUAGE="JavaScript">
function openfile()
{
	window.open("/<%=webRootDir%>/client/help.jsp");
}

function closeWindow()
{
     window.close();
}
</SCRIPT>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Web宽乐伴侣客户端：主持人加入与会者</title>
    </head>
<%
  String uc = (String)session.getAttribute("uc");
  String pw = (String)session.getAttribute("pw");
  String confID = (String)session.getAttribute("confID");
  
  String[] callee = new String[]{request.getParameter("callee")+"|1|"};
  
  String returnStr = serviceCTC.addIntoConf(uc,pw,callee,confID);

%>


<TABLE height=581 cellSpacing=0 cellPadding=0 width=750 align=center border=0 valign="top">
  <TR> 
    <td  height="42" colspan="5" align=center vAlign=middle>&nbsp;</td>
  </TR>
  <TR> 
    <td  height="42" colspan="5" align=center vAlign=middle><div align="left"><font color="#FF0000">Web宽乐伴侣客户端</font></div></td>
  </TR>
  <TR> 
    <td  height="42" colspan="5" align=center vAlign=middle>加入与会者成功：<%=returnStr%></td>
  </TR>
  <TR>
    <TD height="59" colspan="5" align=center vAlign=middle><a
                  style="CURSOR: hand"
                  onClick="closeWindow();"><img src="/<%=webRootDir%>/images/submit.gif" width="67" height="23" border="0" align="middle"></a> 
      <a
                  style="CURSOR: hand"
                  onClick="openfile();"><FONT SIZE="" COLOR="blue">帮助</FONT></a> 
    <TD align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <TR> 
    <TD height="12" colspan="5" align=center vAlign=middle> <br>
    <TD align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <tr> 
    <TD height="350" colspan="5" align=center vAlign=middle> </tr>
</table>
</html>
