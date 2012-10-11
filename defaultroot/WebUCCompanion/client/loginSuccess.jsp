<%@page language = "java" contentType="text/html; charset=GBK" %>
<%
String UserName = request.getParameter("UserName");
String Password = request.getParameter("Password");
session.setAttribute("UserName",UserName);
session.setAttribute("Password",Password);
%>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>UCV2.2D300 WebService 客户端测试</title>
</head>
<body>

 <p><b>UCV2.2D300 WebService 客户端测试:</b></p>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <!--DWLayoutTable-->
  <tr>
    <td height=20></td>
  </tr>

  <tr align="center">
    <td width = "25%"></td><td width = "30%" align="left" ><a href="/testWS/client/getrandom.jsp"> 获取随机数</a></td>
    <td width = "30%" align=left><a href="/testWS/client/getCallBackAddr.jsp">注册回调地址</a></td>

  </tr>
  
  <tr>
    <td height=10></td>
  </tr>
  <tr align="center">
    <td width = "25%"></td><td width = "30%" align="left"><a href="/testWS/client/dialCall.jsp">发起CTD呼叫 </a></td>
    <td align=left><a href="/testWS/client/stopCTD.jsp">终止CTD呼叫</a></td>

  </tr>
  <tr>
    <td height=10></td>
  </tr>
  <tr>
	<td width = "25%"></td><td width = "30%" align=left><a href="/testWS/client/getCallStatus.jsp"> 查询CTD呼叫状态</a></td>
    <td width = "30%" align=left><a href="/testWS/client/getConfAnnID.jsp">查询会场音乐ID</a></td>


  </tr>
  <tr>
    <td height=10></td>
  </tr>

  <tr>
    <td width = "25%"></td><td width = "30%" align=left><a href="/testWS/client/createConf.jsp">发起CTC呼叫</a></td>
    <td align=left><a href="/testWS/client/stopCTC.jsp">终止CTC会议</a></td>


  </tr>
  <tr>
    <td height=10></td>
  </tr>
   <tr>
	<td width = "25%"></td><td width="30%" align=left><a href="/testWS/client/getConfInfo.jsp">查询会场信息</a></td>
    <td align=left><a href="/testWS/client/getConfMember.jsp">查询会议与会成员</a></td>


  </tr>
  <tr>
    <td height=10></td>
  </tr>
  <tr>
	<td width = "25%"></td><td width="30%" align=left><a href="/testWS/client/getConfList.jsp">查询CTC会议列表</a></td>
    <td width="30%" align=left><a href="/testWS/client/addIntoConf.jsp">添加与会者</a></td>


  </tr>
  <tr>
    <td height=10></td>
  </tr>
  <tr>
	<td width = "25%"></td><td align=left><a href="/testWS/client/kickOutFromConf.jsp">踢出与会者</a></td>
    <td align=left><a href="/testWS/client/playBackgroundVoice.jsp">播放会场背景音</a></td>


  </tr>
  <tr>
    <td height=10></td>
  </tr>
   <tr>
	<td width = "25%"></td><td align=left><a href="/testWS/client/stopBackgroundVoice.jsp">停止会场背景音</a></td>
    <td align=left><a href="/testWS/client/startRecording.jsp">启动会场录音</a></td>


  </tr>
  <tr>
    <td height=10></td>
  </tr>
  <tr>
	<td width = "25%"></td><td align=left><a href="/testWS/client/stopRecording.jsp">停止会场录音</a></td>
    <td align=left><a href="/testWS/client/setCalleeRight.jsp">修改与会者发话权</a></td>


  </tr>
  <tr>
    <td height=10></td>
  </tr>
  <tr>
	<td width = "25%"></td><td align=left><a href="/testWS/client/getConfRecordVoice.jsp">获得会场录音</a></td>
     <td align=left><a href="/testWS/client/deleteConfRecordVoice.jsp">删除会场录音</a></td>


 
  <tr>
    <td height=30></td>
  </tr>
   <tr align="center">
       <td></td>
    <td width="30%"></td><td width="30%" align="center" valign="top"><a
        style="CURSOR: hand"
        onClick="javascript:void history.back();"><img src="/testWS/images/back.gif" width="67" height="23" border="0" align="middle"></a></td>
  </tr>
  <tr>
    <td align=left height="20"></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>

</table>
</body>
</html>
