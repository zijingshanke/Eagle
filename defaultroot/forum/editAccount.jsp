<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<html>
	<head>
		<title>editAccount</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>

		<script type="text/javascript" language="javascript">
		function addAccount(){
			var forumId=document.getElementById("forumId");		
			var loginName=document.getElementById("loginName");		
			var loginPassword=document.getElementById("loginPassword");		
				
			if(forumId==null){
				alert("论坛库中没有论坛,请添加");
			}else if(loginName==null){
				alert("用户名不能为空");
			}else if(loginPassword==null){
				alert("登录密码不能为空");
			}else
			    document.forms[0].submit();
		}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/forum/account.do">
					<html:hidden property="thisAction" name="account" />
					<html:hidden property="id" name="account" />
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
									<logic:equal value="update" property="thisAction" name="user">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=帐号管理&title2=编辑帐号信息"
											charEncoding="UTF-8" />
									</logic:equal>
									<logic:equal value="insert" property="thisAction" name="user">
										<c:import url="../_jsp/mainTitle.jsp?title1=帐号管理&title2=新建帐号"
											charEncoding="UTF-8" />
									</logic:equal>
								</div>
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											所属论坛
										</td>
										<td style="text-align: left">
											<html:select property="forumId">
												<c:forEach var="info" items="${forumlist}"
													varStatus="forumId">
													<html:option value="${info.id}">
														<c:out value="${info.name}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											登录名
										</td>
										<td style="text-align: left">
											<html:text property="loginName" name="account"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											登录密码
										</td>
										<td style="text-align: left">
											<html:text property="loginPassword" name="account"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="account">有效</html:radio>
											<html:radio property="status" value="2" name="account">无效</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="保存"
												�� �    onclick="addAccount();">

											<input name="label" type="button" class="button1" value="重置"
												� onclick="document.account.reset();">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
