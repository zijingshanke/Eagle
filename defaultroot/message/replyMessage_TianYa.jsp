<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<html>
	<head>
		<title>editAssignMessage</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/prototype.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/selectUtil.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">		
		function addAssignMessage(){
			document.forms[0].submit();
		}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/message/assignMessagelist.do">
					<html:hidden property="thisAction" value="replyMessage" />
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<div class="crumb">
									<c:import url="../_jsp/mainTitle.jsp?title1=发帖管理&title2=天涯快速回帖"
										charEncoding="UTF-8" />
								</div>
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											论坛
										</td>
										<td style="text-align: left">
											天涯社区
											<html:hidden property="loginSite" value="login.tianya.cn"></html:hidden>
										</td>
									</tr>
									<tr>
										<td class="lef">
											帖子路径
										</td>
										<td style="text-align: left">
											<html:text property="messageUrl"
												value="http://www.tianya.cn/techforum/content/398/14593.shtml"
												style="width:100%"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											回帖内容
										</td>
										<td style="text-align: left">
											<html:textarea property="messageContent"
												style="heith:100%;width:100%;height:200px" value="请问买望远镜还有瑞士军刀送吗？"></html:textarea>
										</td>
									</tr>
									<tr>
										<td class="lef">
											发帖帐号
										</td>
										<td style="text-align: left" id="selectListTD">
											<html:text property="loginName" value="psfww"></html:text>
										</td>
									</tr>
									<tr>
										<td class="lef">
											密码
										</td>
										<td style="text-align: left" id="selectListTD">
											<html:text property="loginPassword" value="0756888"></html:text>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1"
												value="立即回帖" �� �    onclick="addAssignMessage();">
											<input name="label" type="button" class="button1" value="重置"
												� onclick="document.assignMessage.reset();">
											|
										</td>
									</tr>
								</table>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
