<%@page language = "java" contentType="text/html; charset=GBK" %>


<jsp:useBean id="serviceCTC" scope="session" class="com.jiangh.webuc.webclient.CTCWebClient"/>
<jsp:useBean id="util" scope="session" class="com.jiangh.webuc.util.Utility"/>

<%
  
  String uc = request.getParameter("uc");
  String pw = request.getParameter("pw");
  String webRootDir = util.getWebRootDir();
  
  String[] returnStr = serviceCTC.getConfList(uc, pw);
  

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
    <title>Web宽乐伴侣客户端：查询会议列表</title>
</head>



<TABLE height=740 cellSpacing=0 cellPadding=0 width=750 align=center border=0 valign="top">
  <TR> 
    <td  height="42" colspan="8" align=center vAlign=middle>&nbsp;</td>
  </TR>
  <TR> 
    <td  height="42" colspan="8" align=center vAlign=middle><div align="left"><font color="#FF0000">Web宽乐伴侣管理端</font></div></td>
  </TR>
  <TR> 
    <%
      if(returnStr[0].compareTo("0")<0){     %>
    <td  height="42" colspan="8" align=center vAlign=middle>会议列表查询失败：<%=returnStr[0]%></td>
  </TR>
  <%}else{%>
  <td  height="42" colspan="2" align=center vAlign=middle>会议列表查询成功，共返回<%=returnStr[1]%>个会议：</td>
  </TR>
  <TR> 
    <TD height="36" align=center vAlign=middle>&nbsp; 
	<TD width="90" height="36" colspan="-2" align=center vAlign=middle>&nbsp; 
    <TD width="87" colspan="-2" align=center vAlign=middle>会议ID 
    <TD width="88" colspan="-2" align=center vAlign=middle>&nbsp; 
    <TD align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <%
	        int confNum = Integer.parseInt(returnStr[1]);
			
		  if(confNum > 0){
	       for(int i=0;i<confNum;i++){

		%>
  <TR> 
    <TD height="36" align=center vAlign=middle>&nbsp; 
    <TD width="90" height="36" colspan="-2" align=center vAlign=middle>&nbsp; 
    <TD width="114" colspan="-2" align=center vAlign=middle><a href="/<%=webRootDir%>/user/goGetConfInfo.jsp?confID=<%=returnStr[2+i]%>"><font size="4"><%=returnStr[2+i]%></font></a> 
    <TD width="88" colspan="-2" align=center vAlign=middle>&nbsp; 
    <TD align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <%					  
          }
		 }
	  }	  
%>
  <TR> 
    <TD height="12" colspan="8" align=center vAlign=middle> <br> <table>
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
    <TD width="20" align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <tr> 
    <TD height="350" colspan="8" align=center vAlign=middle> </tr>
</table>
</html>
