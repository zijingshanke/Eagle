<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>

<html>
	<head>
		<title>editAssignment</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />

		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>

		<script type="text/javascript" language="javascript">
			function addAssignment(){
			    document.forms[0].submit();
			}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/message/assignment.do">
					<html:hidden property="thisAction" name="assignment" />
					<html:hidden property="id" name="assignment" />
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
										name="assignment">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=任务管理&title2=编辑任务信息"
											charEncoding="UTF-8" />
									</logic:equal>
									<logic:equal value="insert" property="thisAction" name="user">
										<c:import url="../_jsp/mainTitle.jsp?title1=任务管理&title2=新建任务"
											charEncoding="UTF-8" />
									</logic:equal>
								</div>
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											创建者
										</td>
										<td style="text-align: left">
											<c:out value="${URI.user.userName}" />
											<html:hidden value="${URI.user.userName}" property="founder"
												name="assignment" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											任务名称
										</td>
										<td style="text-align: left">
											<html:text property="name" name="assignment"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											开始时间
										</td>
										<td style="text-align: left">
											<html:text property="beginTime" name="assignment"
												styleClass="colorblue2 p_5"
												onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
												readonly="true" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											结束时间
										</td>
										<td style="text-align: left">
											<html:text property="finishTime" name="assignment"
												styleClass="colorblue2 p_5"
												onfocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
												readonly="true" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											内容介绍
										</td>
										<td style="text-align: left">
											<html:textarea property="content" name="assignment"
												styleClass="colorblue2 p_5" style="width:100%;height: 157px"/>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="assignment">有效</html:radio>
											<html:radio property="status" value="2" name="assignment">无效</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="保存"
												�� �    onclick="addAssignment();">

											<input name="label" type="button" class="button1" value="重置"
												� onclick="document.assignment.reset();">
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
