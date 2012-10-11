<%@page language = "java" contentType="text/html; charset=GBK" %>
<jsp:useBean id="util" scope="session" class="com.jiangh.webuc.util.Utility"/>
<jsp:useBean id="serviceCTD" scope="session" class="com.jiangh.webuc.webclient.CTDWebClient"/>

<%
  
  String uc = (String)session.getAttribute("uc");
  String pw = (String)session.getAttribute("pw");
  String sessionID = (String)session.getAttribute("sessionID");
  String returnStr =serviceCTD.releaseCallClient(uc, pw,sessionID);
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
    <title>Stop Background Voice(\</title>
</head>



<TABLE height=446 cellSpacing=0 cellPadding=0 width=750 align=center border=0 valign="top">
  <TR> 
    <td  height="42" colspan="4" align=center vAlign=middle><font color="#FF0000">Web宽乐伴侣客户端</font></td>
    <td width="366"  height="42" align=center vAlign=middle>&nbsp;</td>
  </TR>
  <TR> 
    <td  height="42" colspan="5" align=center vAlign=middle>终止呼叫返回结果为：<%=returnStr%></td>
  </TR>
  <TR> 
    <TD height="12" colspan="5" align=center vAlign=middle> <br> <table>
        <!--DWLayoutTable-->
        <tr> 
          <td width="12" align="center" valign="top"><a
                  style="CURSOR: hand"
                  onClick="javascript:void history.back();"><img src="/<%=webRootDir%>/images/back.gif" width="67" height="23" border="0" align="middle"></a></td>
          <td width="67"><a
                  style="CURSOR: hand"
                  onClick="openfile();"><FONT SIZE="" COLOR="blue">帮助</FONT></a></td>
        </tr>
      </table>
    <TD width="23" align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <tr> 
    <TD height="350" colspan="5" align=center vAlign=middle> </tr>
</table>
</html>
