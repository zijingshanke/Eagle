<%@page language = "java" contentType="text/html; charset=GBK" %>
<%
  //去掉浏览器缓存：
  response.setHeader("Cache-Control","no-store"); //HTTP 1.1
  response.setHeader("Pragma","no-cache"); //HTTP 1.0
  response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

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
</SCRIPT>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Web宽乐伴侣：点击会议</title>
</head>
<%
   String uc = request.getParameter("uc");
   session.setAttribute("uc",uc);
  String pw = request.getParameter("pw");
  session.setAttribute("pw",pw); 
  
  String Cee = request.getParameter("Cee");
  
  String calleesBuf = new String();
  
  String OCallee = request.getParameter("OCallee");
  if(OCallee.length() != 0){
        calleesBuf+= OCallee + "|1|" +"--";
   }		
  String TCallee1 = request.getParameter("TCallee1");
  if(TCallee1.length()!= 0){
         calleesBuf += TCallee1 + "|1|" +"--";
  }
  String TCallee2 = request.getParameter("TCallee2");
  if(TCallee2.length()!= 0){
         calleesBuf += TCallee2 + "|1|" +"--";
  }
  String TCallee3 = request.getParameter("TCallee3");
  if(TCallee3.length() != 0){
         calleesBuf += TCallee3 + "|1|" +"--";
  }
  String TCallee4 = request.getParameter("TCallee4");
  if(TCallee4.length() != 0){
         calleesBuf += TCallee4 + "|1|" +"--";
  }
  String TCallee5 = request.getParameter("TCallee5");
  if(TCallee5.length() != 0){
         calleesBuf += TCallee5 + "|1|" +"--";
  }
  
  String[] callees = calleesBuf.split("--");
  
  String sbj = request.getParameter("sbj");
  String confpw = request.getParameter("confpw");
  
  String confID = serviceCTC.createConf(uc, pw,Cee,callees,0,sbj,confpw);
  session.setAttribute("confID",confID); 
  
%>


<TABLE height=694 cellSpacing=0 cellPadding=0 width=750 align=center border=0 valign="top">
  <TR> 
    <td  height="42" colspan="7" align=center vAlign=middle>&nbsp;</td>
  </TR>
  <TR> 
    <td  height="42" colspan="7" align=center vAlign=middle><div align="left"><font color="#FF0000">Web宽乐伴侣客户端</font></div></td>
  </TR>
  <TR> 
    <TD height="47" colspan="2" align=center vAlign=middle>&nbsp; 
    <TD height="47" align=center vAlign=middle><a  href="/<%=webRootDir%>/user/goGetConfInfo.jsp"><font color="#0000FF" size="4">查询会议信息</font></a> 
    <TD height="47" align=center vAlign=middle><a  href="/<%=webRootDir%>/user/addIntoConf.jsp"><font color="#0000FF" size="4">加入与会者</font></a> 
    <TD height="47" align=center vAlign=middle><a  href="/<%=webRootDir%>/user/kickOutFromConf.jsp"><font color="#0000FF" size="4">踢除与会者</font></a> 
    <TD width="153" align=center vAlign=middle><a  href="/<%=webRootDir%>/user/gostopCTC.jsp"><font color="#0000FF" size="4">解散会议</font></a>
    <TD width="84" align=center vAlign=middle>&nbsp;</TR>
  <TR> 
    <td  height="120" colspan="7" align=center vAlign=middle>会议成功发起，返回会议号：<%=confID%></td>
  </TR>
  <TR> 
    <TD height="47" colspan="2" align=center vAlign=middle>&nbsp; 
    <TD width="147" height="47" align=center vAlign=middle>&nbsp; 
    <TD width="150" height="47" align=center vAlign=middle>&nbsp; 
    <TD width="155" height="47" align=center vAlign=middle>&nbsp; 
    <TD colspan="2" align=center vAlign=middle>&nbsp; 
    <TD width="25" align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <TR> 
    <TD height="12" colspan="7" align=center vAlign=middle> <br> <table>
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
    <TD align=middle vAlign=top bgColor=#FFFFFF></TD>
  </TR>
  <tr> 
    <TD height="350" colspan="7" align=center vAlign=middle> </tr>
</table>
</html>
