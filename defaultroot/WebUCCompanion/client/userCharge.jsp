<%@page language = "java" contentType="text/html; charset=GBK" %>

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

<title>充值</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script language="javaScript">
function formCheck()
{

  return true;
}

/*
    Description:judge integer such as "01";
    判断是否为正整数，不可以为空。
*/
function isPosInteger(inputVal)
{
    if(inputVal == null || inputVal == "")
    {
        return false;
    }
    inputStr=inputVal.toString();
    for (var i = 0;i < inputStr.length;i++ )
    {
        var oneChar = inputStr.charAt(i);
        if (oneChar < "0" || oneChar > "9" )
        {
            return false;
        }
    }
    return true;
}

function submitButton(){
	if(!isPosInteger(document.checkedForm.nChargeAmount.value)){
		alert("用户充值金额必须为正整数！！");
	    document.checkedForm.nChargeAmount.focus();
		return false;
	}
    return true;
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
	<td width="476" valign="top">
	      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <table width="99%" border="0" cellspacing="0" cellpadding="0" align="right">
             <tr><td height="20"></td></tr>
			 <tr><td><font color="blue">测试"充值"接口能否正确返回0</font></td></tr>
			 <tr><td height="15"></td></tr>
             <tr><td><font color="red">函数原形：</font></td></tr>
             <tr><td><font color="red">String UserCharge(String strUserID, int nSPNumber, String strSPPassword int nChargeAmount, String strChargeType, String strTransaction， String ChargeDataTime, String strStartDate)</font></td></tr>
			 <tr><td><font color="red">1、参数strUserID为全能聊ID；</font></td></tr>
             <tr><td><font color="red">2、参数nSPNumber为用户所在地(门户，只有门户可以充值)</font></td></tr>
        	 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0：中游；  </font></td></tr>
        	 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1：碧聊； </font></td></tr>
        	 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2：21CN；</font></td></tr>
	         <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;254：门户（966066）；</font></td></tr>
        	 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;255：IM（客户端）。</font></td></tr>
             <tr><td><font color="red">3、参数strSPPassword为nSPNumber对应的密码；</font></td></tr>
             <tr><td><font color="red">4、参数nChargeAmount为预付费用户充值的金额，或者时长单位数，或者月份数；</font></td></tr>
             <tr><td><font color="red">5、参数strChargeType为充值类型，0表示减钱，1表示加钱，2：充负时长，3：充正时长，                   4：充负月份，5：充正月份</font></td></tr>
			 <tr><td><font color="red">6、参数strTransaction为订单号，唯一标识一次充值，用于以后的对帐；</font></td></tr>
			 <tr><td><font color="red">7、参数ChargeDataTime为充值日期，年月日时分秒 （提供对帐时间用。）；</font></td></tr>
             <tr><td><font color="red">8、参数strStartDate: 预付费包月用户包月起始时间 （年月日，8位整数，YYYYMMDD）如果不是预付费包月用户充值，该参数填null，或者"" (空字符串)；</font></td></tr>
             <tr><td><font color="red">9、安全用户：WebService安全验证用户；</font></td></tr>
             <tr><td><font color="red">10、安全密码：WebService安全验证密码。</font></td></tr>
        	 <tr><td><font color="red">11、如果执行成功则返回0，否则返回不成功原因：</font></td></tr>
             <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1 系统错误；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-00004 语法错误或命令码非法；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-10003:	全能聊号码不存在；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-10004: 全能聊号码未加载；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-10005:	全能聊号码正在使用；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-10022: SP号码已上黑名单；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-10023: 充值时间参数不合法 ；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-21300: 充值金额不能为0;</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-20306: 余额不足；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-21301：用户充值类型错误（用户的付费类型不允许做该项充值）；</font></td></tr>
			 <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-21306: 充值金额过大。</font></td></tr>
            <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-22801: 订单号重复。</font></td></tr>
            <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-21500: SP密码鉴权不通过。</font></td></tr>
            <tr><td><font color="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-22103:预付费包月用户不在有效期内，请输入新的充值起始日期。</font></td></tr>
            <tr><td><font color="red">处理流程说明：</font></td></tr>
            <tr><td><font color="red">如果是充正值，华为WebService会检查用户充值类型与付费类型是否匹配，如果不匹配就报错。如果是充负值，不检查用户充值类型与付费类型是否一致。
                （后付费用户可以通过该接口扣除以前预付费遗留的金额。）</font></td></tr>
            <tr><td><font color="red">预付费包月用户充值如下：</font></td></tr>
            <tr><td><font color="red">1）如果数据库中充值起始时间为空，（就是第一次充值），写入充值月份数和预付费包月起始时间2个参数</font></td></tr>
            <tr><td><font color="red">2）如果数据库中有充值起始时间，如果用户传入参数：预付费包月起始时间 不为空，再判断预付费包月用户是否已经超期，
                如果没有超期，不修改预付费包月起始时间字段，只在月份数上增加</font></td></tr>
            <tr><td><font color="red">如果已经超期，就把 充值起始时间 修改为 新传入的 充值起始时间，月份数修改为新的月份数</font></td></tr>
            <tr><td><font color="red">3）如果数据库中有充值起始时间，如果用户传入参数：充值起始时间 为空，则判断预付费包月用户是否已经超期，如果没有超期，只在月份数上增加。
			如果已经超期，或者充值起始时间为空，就报错，“预付费包月用户不在有效期内，请输入新的充值起始日期”。</font></td></tr>
</table>
          </td>
        </tr>
      </table>
    </td>
<form name="checkedForm" method="post" action="/callme2/client/goUserCharge.jsp" onSubmit="return submitButton()">
	<td width="302" valign="top">
 	 <table width="302" border="0" cellspacing="0" cellpadding="0" >
  <tr><td height="70"></td></tr>
  <tr>
    <th  height="35" align="right">
      全能聊ID:
    </th>
    <td width="55%" align="left">
      <input  type="text" name="strUserID" size="16">
    </td>
  </tr>

  <tr>
    <th  width="55%" height="35" align="right">
      用户所在地:
    </th>
    <td align="left">
      <select name="nSPNumber">
	     <option selected value="0">中游</option>
		 <option value="1">碧聊</option>
		 <option value="2">21CN</option>
		 <option value="254">门户（966066）</option>
		 <option value="255">IM（客户端）</option>
	  </select>
    </td>
  </tr>

 <tr>
    <th  width="55%" nowrap="nowrap" height="35" align="right">
      用户当前所在地密码:
    </th>
    <td align="left">
      <input  type="password" name="strSPPassword " size="16">
	</td>
  </tr>

  <tr>
    <th  width="55%" height="35" align="right">
      用户充值金额:
    </th>
    <td align="left">
      <input  type="text" name="nChargeAmount" size="16">
    </td>
  </tr>

  <tr>
    <th  width="55%" height="35" align="right">
      充值类型:
    </th>
    <td align="left">
	  <select name="strChargeType">
	    <option selected value="0">减钱</option>
		<option value="1">加钱</option>
        <option value="2">充负时长</option>
		<option value="3">充正时长</option>
		<option value="4">充负月份</option>
        <option value="5">充正月份</option>
      </select>
    </td>
  </tr>

  <tr>
    <th  width="55%" height="35" align="right">
      订单号:
    </th>
    <td align="left">
      <input  type="text" name="strTransaction" value="">
    </td>
  </tr>

  <tr>
    <th  width="55%" height="35" align="right">
     充值日期:
    </th>
    <td align="left">
      <input  type="text" name="ChargeDataTime" value="20041216115001">
    </td>
  </tr>

   <tr>
    <th  width="55%" height="35" align="right">
     包月起始时间:
    </th>
    <td align="left">
      <input  type="text" name="strStartDate">
    </td>
  </tr>


  <tr>
    <th  height="35" align="right">
      安全用户:
    </th>
    <td align="left">
      <input  type="text" name="wsName" value="<%=session.getAttribute("UserName")%>" size="16" maxlength="18">
    </td>
  </tr>
  <tr>
    <th  height="35" align="right">
      安全密码:
    </th>
    <td align="left">
      <input  type="password" name="wsPassword" value="<%=session.getAttribute("Password")%>" size="16" maxlength="18">
    </td>
  </tr>

  <tr>
    <td rowspan="2" align="right">&nbsp;
    </td>
    <td align="left">

    </td>
  </tr>
  <tr>
    <td align="left"><INPUT name="确定" TYPE="submit" value="确定">
      <INPUT name="重置" TYPE="reset" value="重置"></td>
  </tr>

</table>
<table>
  <tr><td height="20"></td></tr>
  <tr>
    <td width="543"></td><td width="12" align="right" valign="top"><a
        style="CURSOR: hand"
        onClick="javascript:void history.back();"><img src="/callme2/images/back.gif" width="67" height="23" border="0" align="middle"></a></td>
    <td width="67">&nbsp;</td>
  </tr>
</table>
</form>
</table>
</body>
</html>

