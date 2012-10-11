<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<html>
	<head>
		<title>editMessage</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript">
			function addMessage(){
			    document.forms[0].submit();
			}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/message/message.do">
					<html:hidden property="thisAction" name="message" />
					<html:hidden property="id" name="message" />
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
										name="message">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=帖子管理&title2=编辑帖子信息"
											charEncoding="UTF-8" />
									</logic:equal>
									<logic:equal value="insert" property="thisAction"
										name="message">
										<c:import url="../_jsp/mainTitle.jsp?title1=帖子管理&title2=新建帖子"
											charEncoding="UTF-8" />
									</logic:equal>
								</div>
								<hr>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef">
											标题
										</td>
										<td style="text-align: left">
											<html:text property="title" name="message"
												styleClass="colorblue2 p_5" style="width:100%;heigh:100%;"/>
										</td>
									</tr>								
									<tr>
										<td class="lef">
											内容
										</td>										
										<td style="text-align: left">
											<html:textarea property="content" name="message"
												styleClass="colorblue2 p_5" style="width:100%;heigh:100%;height:340px" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="message">有效</html:radio>
											<html:radio property="status" value="2" name="message">无效</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="保存"
												�� �    onclick="addMessage();">

											<input name="label" type="button" class="button1" value="重置"
												� onclick="document.message.reset();">
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
