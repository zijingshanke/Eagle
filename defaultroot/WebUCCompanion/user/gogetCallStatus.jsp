<%@page language = "java" contentType="text/html; charset=GBK" %>


<jsp:useBean id="serviceCTD" scope="session" class="com.jiangh.webuc.webclient.CTDWebClient"/>
<jsp:useBean id="util" scope="session" class="com.jiangh.webuc.util.Utility"/>
<%
  
  String uc = request.getParameter("uc");
  String pw = request.getParameter("pw");
  String rand = service.getRandom();
  pw = service.getMM(rand + pw + pw);
  String port = Integer.toString(request.getServerPort());
  service.setHttpPort(port);
  String webRootDir = util.getWebRootDir();

  String[] returnStr = service.getCallStatus(uc, pw,rand);
System.out.println("size = " + returnStr.length);
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
    <title>Web宽乐伴侣客户端：查询呼叫信息</title>
</head>



<TABLE height=740 cellSpacing=0 cellPadding=0 width=750 align=center border=0 valign="top">
  <TR> 
    <td  height="42" colspan="8" align=center vAlign=middle>&nbsp;</td>
  </TR>
  <TR> 
    <td  height="42" colspan="8" align=center vAlign=middle><div align="left"><font color="#FF0000">Web宽乐伴侣客户端</font></div></td>
  </TR>
  <TR> 
    <%
      if(returnStr[0].compareTo("0")<0){     %>
    <td  height="42" colspan="8" align=center vAlign=middle>呼叫信息查询失败：<%=returnStr[0]%></td>
  </TR>
  <%}else{%>
  <td  height="42" colspan="2" align=center vAlign=middle>呼叫信息查询成功，返回信息：</td>
  </TR>
  <TR> 
    <TD height="36" align=center vAlign=middle>&nbsp; 
    <TD width="90" height="36" colspan="-2" align=center vAlign=middle>呼叫标识 
    <TD width="114" colspan="-2" align=center vAlign=middle>开始时间 
    <TD width="116" colspan="-2" align=center vAlign=middle>持续时间(秒) 
    <TD width="95" colspan="-2" align=center vAlign=middle>主叫 
    <TD width="87" colspan="-2" align=center vAlign=middle>被叫 
    <TD width="87" colspan="-2" align=center vAlign=middle>呼叫状态 
    <TD width="88" colspan="-2" align=center vAlign=middle>&nbsp; 
    <TD align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <%
	        int callNum = Integer.parseInt(returnStr[1]);
			
		  if(callNum > 0){
	       for(int i=0;i<callNum;i++){
                      String str =   returnStr[2 + i].replace('|','-');
					  String[] call = str.split("-");
					  String callState = null;
					  switch(Integer.parInt(call[5])){
					  case 1:
					        callState = "正在接通主叫";
						break;
					  case 2:
					        callState = "成功接通主叫，正在接通被叫"; 	   
						break;
					  case 3:
					        callState = "呼叫建立，正在通话";	 
						break;
					  case 4:
					        callState = "呼叫结束"; 
						break;
					  }
		%>
  <TR> 
    <TD height="36" align=center vAlign=middle>&nbsp; 
    <TD width="90" height="36" colspan="-2" align=center vAlign=middle><%=call[0]%> 
    <TD width="114" colspan="-2" align=center vAlign=middle><%=call[1]%> 
    <TD width="116" colspan="-2" align=center vAlign=middle><%=call[2]%> 
    <TD width="95" colspan="-2" align=center vAlign=middle><%=call[3]%> 
    <TD width="87" colspan="-2" align=center vAlign=middle><%=call[4]%> 
    <TD width="87" colspan="-2" align=center vAlign=middle><%=callState%> 
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
