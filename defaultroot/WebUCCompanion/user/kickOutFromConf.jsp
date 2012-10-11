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

<title>	Web宽乐伴侣客户端：踢除与会者</title>
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

function closeWindow()
{
     window.close();
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
	<td width="220" valign="top">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="99%" border="0" cellspacing="0" cellpadding="0" align="right">
              <tr> 
                <td height="30"></td>
              </tr>
              <tr> 
                <td height="45"><font color="#FF0000">Web宽乐伴侣客户端</font></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>

<form name="checkedForm" method="post" action="/<%=webRootDir%>/user/goKickOutFromConf.jsp" onSubmit="return formCheck()">
	  <td width="558" valign="top"> <table width="343" border="0" cellspacing="0" cellpadding="0" >
          <tr> 
            <td width="50%" height="103"></td>
          </tr>
          <tr> 
            <th align="right" height="50"> 踢出与会者号码: </th>
            <td width="50%" align="left"> <input  type="text" name="callee"> </td>
          </tr>
          <tr> 
            <td height="43" colspan="2" align="right"> <div align="center"> 
                <input name="确定" type="submit" value="确定">
                <input name="重置" type="reset" value="重置">
              </div></td>
          </tr>
        </table>
        <table>
          <tr> 
            <td width="207" align="right" valign="top"><a
        style="CURSOR: hand"
        onClick="closeWindow();"><img src="/<%=webRootDir%>/images/submit.gif" width="67" height="23" border="0" align="middle"></a></td>
            <td width="92">&nbsp;</td>
          </tr>
        </table>


</form>
</table>
</body>
</html>

