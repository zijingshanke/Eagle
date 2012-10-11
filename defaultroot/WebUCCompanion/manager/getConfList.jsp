<%@page language = "java" contentType="text/html; charset=GBK" %>

<jsp:useBean id="util" scope="session" class="com.jiangh.webuc.util.Utility"/>
<%
    String webRootDir = util.getWebRootDir();

%>

<html>
<style type="text/css">
table {  font-family: "宋体"; font-size: 18px; line-height: 20px}
a:hover {  color: #FF6600; text-decoration: underline}
a:link {  color: #000000; text-decoration: none}
a:active {  text-decoration: none}
a {  text-decoration: none; color: #000000}
a {text-decoration:none}
a:hover {color: red;text-decoration:none}
</style>
<style type="text/css">
.a1{
position:relative;
font-family:Verdana;
font-size:20px;
color:#ff0000;
}
</style>

<title>Web宽乐伴侣客户端：查询CTC会议列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script language="javaScript">
function formCheck()
{

  return true;
}

function isNumber(strString)
{
  var theLen = strString.length;
  var goodName = true;
  for (var i = 0 ; i < theLen ; i ++)
  {
    var theChar = strString.substring(i, i + 1);

    if (theChar >="0" && theChar <= "9")
    {

      continue;
    }
    else
    {

      goodName = false;
      break;
    }
  }

  return goodName;
}

</script>




<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<table width="778" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td bgcolor="C3C2C1"></td>
  </tr>
</table>
<table width="778" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="213" height="14" valign="top">&nbsp; </td>
    <form name="checkedForm" method="post" action="/<%=webRootDir%>/manager/goGetConfList.jsp" onSubmit="return formCheck()">
      <td width="565" rowspan="4" valign="top"> <table width="302" border="0" cellspacing="0" cellpadding="0" >
          <tr> 
            <td height="83"></td>
          </tr>
          <tr> 
            <td height="58">查询会议列表：</td>
          </tr>
          <tr> 
            <th  height="35" align="right"> uc号码: </th>
            <td width="55%" align="left"> <input  type="text" name="uc" value=""> 
            </td>
          </tr>
          <tr> 
            <th  height="35" nowrap="nowrap" align="right"> 密码: </th>
            <td width="55%" align="left"> <input  type="password" name="pw" value=""> 
            </td>
          </tr>
          <tr> 
            <td rowspan="2" align="right">&nbsp; </td>
            <td align="left"> </td>
          </tr>
          <tr> 
            <td align="left"><INPUT name="确定" TYPE="submit" value="确定"> <INPUT name="重置" TYPE="reset" value="重置"></td>
          </tr>
        </table>
        <table>
          <tr> 
            <td height="24"></td>
          </tr>
          <tr> 
            <td width="121"></td>
            <td width="83" align="right" valign="top"><a
        style="CURSOR: hand"
        onClick="javascript:void history.back();"><img src="/<%=webRootDir%>/images/back.gif" width="67" height="23" border="0" align="middle"></a></td>
            <td width="345">&nbsp;</td>
          </tr>
        </table>
    </form>
  <tr>
    <td height="37" valign="top"><font color="#FF0000">Web宽乐伴侣客户端</font></td>
  <tr> 
    <td height="166" valign="top">&nbsp;</td>
  <tr> 
    <td valign="top">&nbsp;</td>
</table>
</body>
</html>

