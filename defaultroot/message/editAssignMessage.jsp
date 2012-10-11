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
		function getAccountByMessageSection(){
			var selectedValue=getSelectedValue("messageSectionId");	
			
			var myAjax = new Ajax.Request("../forum/accountlist.do?thisAction=ajaxList&messageSectionId="+selectedValue,
			{			
				method:"post", 
				onComplete:function (originalRequest) {
						var result = originalRequest.responseText;
						var selectListTD=document.getElementById("selectListTD");						
						selectListTD.innerHTML="";		
						selectListTD.innerHTML=result;				
				}, onException:showException
			 });			
		}
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
			
		function addAssignMessage(){
			document.forms[0].submit();
		}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/message/assignMessage.do">
					<html:hidden property="thisAction" name="assignMessage" />
					<html:hidden property="id" name="assignMessage" />
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
									<logic:equal value="update" property="thisAction"
										name="assignMessage">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=发帖任务管理&title2=编辑发帖任务信息"
											charEncoding="UTF-8" />
									</logic:equal>
									<logic:equal value="insert" property="thisAction"
										name="assignMessage">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=发帖任务管理&title2=新建发帖任务"
											charEncoding="UTF-8" />
									</logic:equal>
								</div>
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											所属任务
										</td>
										<td style="text-align: left">
											<logic:equal value="update" property="thisAction"
												name="assignMessage">
												<html:hidden property="assignmentId"
													value="${assignMessage.assignment.id}" name="assignMessage"></html:hidden>
												<html:text property="assignment.name" name="assignMessage"
													readonly="true"></html:text>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="assignMessage">
												<html:select property="assignmentId">
													<c:forEach var="assignInfo" items="${assignmentlist}"
														varStatus="assignmentId">
														<html:option value="${assignInfo.id}">
															<c:out value="${assignInfo.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											论坛-版块-帖子
										</td>
										<td style="text-align: left">
											<logic:equal value="update" property="thisAction"
												name="assignMessage">
												<html:hidden property="messageSectionId"
													value="${assignMessage.messageSection.id}"
													name="assignMessage"></html:hidden>
												<html:text property="messageSection.summary"
													name="assignMessage" readonly="true"
													styleClass="colorblue2 p_5" style="width:100%;heigh:100%;"></html:text>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="assignMessage">
												<html:select property="messageSectionId"
													onchange="getAccountByMessageSection()">
													<c:forEach var="messageSection"
														items="${messageSectionlist}" varStatus="messageSectionId">
														<html:option value="${messageSection.id}">
															<c:out value="${messageSection.summary}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											发帖帐号
										</td>
										<td style="text-align: left" id="selectListTD">
											<logic:equal value="update" property="thisAction"
												name="assignMessage">
												<html:hidden property="accountId"
													value="${assignMessage.account.id}" name="assignMessage"></html:hidden>
												<html:text property="account.loginName" name="assignMessage"
													readonly="true"></html:text>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="assignMessage">
												<html:select property="accountId">
													<c:forEach var="account" items="${accountlist}"
														varStatus="accountId">
														<html:option value="${account.id}">
															<c:out value="${account.loginName}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											执行人
										</td>
										<td style="text-align: left">
											<logic:equal value="update" property="thisAction"
												name="assignMessage">
												<html:hidden property="userId"
													value="${assignMessage.sysUser.userId}"
													name="assignMessage"></html:hidden>
												<html:text property="sysUser.userName" name="assignMessage"
													readonly="true"></html:text>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="assignMessage">
												<html:select property="userId">
													<c:forEach var="userInfo" items="${sysuserlist}"
														varStatus="userId">
														<html:option value="${userInfo.userId}">
															<c:out value="${userInfo.userName}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="assignMessage">有效(点此帖子将进入待发送状态)</html:radio>
											<html:radio property="status" value="4" name="assignMessage">无效（点此将取消此发帖任务）</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="保存"
												�� �    onclick="addAssignMessage();">

											<input name="label" type="button" class="button1" value="重置"
												� onclick="document.assignMessagelist.reset();">
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
