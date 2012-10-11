<%@page language = "java" contentType="text/html; charset=GBK" %>


<jsp:useBean id="util" scope="session" class="com.jiangh.webuc.util.Utility"/>
<%
    String webRootDir = util.getWebRootDir();
    int ret = util.clearObjectPool("CTD");
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
    <title>Web宽乐伴侣管理端：高级维护操作</title>
</head>

<table width="98%" height="377" border="0">
  <tr> 
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr> 
    <td width="22%"><font color="#FF0000">Web宽乐伴侣管理端</font></td>
    <td colspan="4">&nbsp;</td>
  </tr>
  <tr> 
    <td height="31" colspan="5">&nbsp;</td>
  </tr>
  <tr> 
    <td height="88">&nbsp;</td>
    <td width="31%"><div align="right">操作结果:</div></td>
    <td width="11%"><%=ret%></td>
    <td width="21%" colspan="2">&nbsp;</td>
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
