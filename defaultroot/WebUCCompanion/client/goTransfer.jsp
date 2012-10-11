<%@page language = "java" contentType="text/html; charset=GB2312" %>

<jsp:useBean id="service" scope="session" class="com.huawei.unica.svc.sip.callme.webservice.testclient.TestClient"/>
<SCRIPT LANGUAGE="JavaScript">
function openfile()
{
	window.open("/testWS/client/help.jsp");
}
</SCRIPT>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <title>Transfer Amount</title>
</head>
<%
    /* 12:
     功能： 转帐
     方法： Transfer STRSOUTID=, SPINNUMBER=, NSPNUMBER=, STRSINID=, NAMOUNT
     参数strOutID：转出用户全能聊ID
     参数strOutPassword：转出用户全能聊密码
     参数nSPNumber：用户当前所在地(中游、碧聊或者全能聊等SP的编码)
     参数strInID：转入用户全能聊ID
     参数nChargeAmount：转帐金额
     如果转帐成功则返回true ,否则返回false.
     */
  String strOutID = request.getParameter("strOutID");
  String strOutPassword =  request.getParameter("strOutPassword");

  int nSPNumber = java.lang.Integer.parseInt(request.getParameter("nSPNumber").toString());
  String strSPPassword =request.getParameter("strSPPassword");
  String strInID = request.getParameter("strInID");
  int nChargeAmount =  java.lang.Integer.parseInt(request.getParameter("nChargeAmount"));
  int nChargeUnit = java.lang.Integer.parseInt(request.getParameter("nChargeUnit").toString());
  String wsName = request.getParameter("wsName");
  String wsPassword = request.getParameter("wsPassword");
  String port = Integer.toString(request.getServerPort());
  service.SetHttpPort(port);

  String strRtn = service.TransferClient("Transfer", strOutID, strOutPassword, nSPNumber,strSPPassword,strInID, nChargeAmount,nChargeUnit, wsName, wsPassword);

%>

<TABLE height=407 cellSpacing=0 cellPadding=0 width=750 align=center border=0
valign="top">
          <TR>
		    <% if (strRtn.equals("(401)Unauthorized")) {%>
			<td  height="42" colspan="5" align=center vAlign=middle>用户名或者密码错误，WebService服务被禁止使用！</td>
			<%}else{%>
			         <td  height="42" colspan="5" align=center vAlign=middle>WebService调用完成，返回结果为：<%=strRtn%></td>
			<%}%>
		  </TR>
		  <TR>
              <TD height="12" colspan="5" align=center vAlign=middle> <br> <table>
                  <!--DWLayoutTable-->
                  <tr>
                    <td width="12" align="center" valign="top"><a
                  style="CURSOR: hand"
                  onClick="javascript:void history.back();"><img src="/callme2/images/back.gif" width="67" height="23" border="0" align="middle"></a></td>
                   <td width="67"><a
                  style="CURSOR: hand"
                  onClick="openfile();"><FONT SIZE="" COLOR="blue">帮助</FONT></a></td>
                  </tr>
                </table>
              <TD align=middle vAlign=top bgColor=#6699cc></TD>
            </TR>
			<tr>
			  <TD height="350" colspan="5" align=center vAlign=middle>
			</tr>
</table>
</html>
