<%@ page language="java" pageEncoding="utf-8"%>
<html>
	<head>
		<title>Web宽乐伴侣</title>
	</head>
	<body>
		<script language="javaScript">
	function onSelect(){    
		if(document.form1.RadioGroup1[0].checked ){
		   window.open("../WebUCCompanion/manager/loginSuccess.jsp");
		 }else if(document.form1.RadioGroup1[1].checked ){
		   window.open("../WebUCCompanion/user/loginSuccess.jsp");
		}   
	}
</script>
		<form name="form1" method="post" onSubmit="onSelect()">
			<b> </b>
			<table width="778" border="0" cellspacing="0" cellpadding="0">
				<tr align="center">
					<td width="1" height="20">
						&nbsp;
					</td>
					<td width="48"></td>
					<td width="304"></td>
					<td width="192"></td>
					<td width="233">
						&nbsp;
					</td>
				</tr>
				<tr align="center">
					<td height="101">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td colspan="3">
						<font color="blue">
							<p>
								<b>欢迎使用广东电信<font color="#FF0000" size="+3">Web宽乐伴侣</font>业务</b>
							</p> </font>
					</td>
				</tr>
				<tr align="center">
					<td height="32">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<div align="right">
							<b>用户名：</b>
						</div>
					</td>
					<td>
						<div align="left">
							<input type="text" value="test" name="UserName">
							&nbsp;
						</div>
						<div align="left">
						</div>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr align="center">
					<td height="19">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr align="center">
					<td height="32">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<div align="right">
							<b>密码： </b>
						</div>
					</td>
					<td>
						<div align="left">
							<b> <input type="password" value="test" name="Password">
							</b>
						</div>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr align="center">
					<td height="18">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr align="center">
					<td height="38">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<div align="right">
							<b>登陆类型：</b>
						</div>
					</td>
					<td>
						<div align="left">
							<label>
								<input name="RadioGroup1" type="radio" value="manager" checked>
								管理员
							</label>
							<input type="radio" name="RadioGroup1" value="user">
							用户
							<label>
							</label>
						</div>
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr align="center">
					<td height="55">
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;&nbsp; &nbsp;
						<input type="submit" name="Submit" value="提交">
						&nbsp;&nbsp;
						<input type="reset" name="reset" value="重置">
						&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;
						&nbsp; &nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
			<p>
				&nbsp;
			</p>
			<p>
				&nbsp;
			</p>
		</form>
	</body>
</html>
