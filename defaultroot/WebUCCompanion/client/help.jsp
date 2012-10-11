<%@page language = "java" contentType="text/html; charset=GBK" %>

<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>客户端返回错误码对照</title>
</head>
<body>

 <p><b>客户端返回错误码对照:</b></p>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <!--DWLayoutTable-->
  <tr>
    <td height=20></td>
  </tr>

   <tr>
    <td height=10></td>
  </tr>
<tr>
    <td width = "5%"></td><td width = "95%" align=left><FONT  COLOR="">大于0的整数---返回的呼叫标识 或会议ID或者是随机数或者是通道ID</FONT></td>
 </tr>
 <tr>
    <td width = "5%"></td><td width = "95%" align=left><FONT  COLOR="">0---操作成功</FONT></td>
 </tr>

  <tr>
    <td ></td><td align=left><FONT  COLOR="">-1---系统错误</FONT></td>
	</tr>
	<tr>
    <td ></td><td align=left><FONT  COLOR="">-2---加密后的密码验证不通过</FONT></td>
	</tr>
  <tr>
    <td ></td><td  align=left><FONT  COLOR="">-3---随机数字段长度不合法，或随机字符串无法转成Long型，或请求中的UC号码不存在</FONT></FONT></td>
</tr>
<tr>
    <td ></td><td  align=left><FONT  COLOR="">-4---用户类型非法（该用户没有权限完成该操作）</FONT></td>
	</tr>
<tr>
    <td ></td><td  align=left><FONT  COLOR="">-5---随机串超时，被disable了</FONT></td>
</tr>

<tr>
    <td ></td><td  align=left><FONT  COLOR="">-6---与会者号码号码列表为空</FONT></td>
</tr>

<tr>
    <td ></td><td  align=left><FONT  COLOR="">-7---通道ID不存在</FONT></td>
</tr>
<tr>
    <td ></td><td  align=left><FONT  COLOR="">-8---UC余额或限额不足</FONT></td>
</tr>
<tr>
    <td ></td><td  align=left><FONT  COLOR="">-10---该用户查询的CTD呼叫不存在。</FONT></td>
</tr>
<tr>
    <td ></td><td  align=left><FONT  COLOR="">-12---系统超时</FONT></td>
</tr>
<tr>
    <td ></td><td  align=left><FONT  COLOR="">-25---参数不合法。</FONT></td>
</tr>

<tr>
    <td ></td><td  align=left><FONT  COLOR="">-29---呼叫不存在。</FONT></td>
</tr>

<tr>
    <td ></td><td  align=left><FONT  COLOR="">-30---会议不存在。</FONT></td>
</tr>

<tr>
    <td ></td><td  align=left><FONT  COLOR="">-38---已经在录音，无需再启动</FONT></td>
</tr>

 <tr>
    <td ></td><td  align=left><FONT  COLOR="">-68---录音文件格式不正确</FONT></td>
</tr>

 <tr>
    <td ></td><td  align=left><FONT  COLOR=""></FONT></td>
</tr>

<tr>
    <td ></td><td  align=left><FONT  COLOR="">其他的错误码请参考《FIN UCV2.2D300 WEB SERVICE接口说明书》</FONT></td>
</tr>
 
  <tr>
    <td height=30></td>
  </tr>
   <tr align="center">
       <td></td>
    <td width="30%"></td><td width="30%" align="center" valign="top"></td>
  </tr>
  <tr>
    <td align=left height="20"></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>

</table>
</body>
</html>
