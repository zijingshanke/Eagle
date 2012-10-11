<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<html>
	<head>
		<title>editSection</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/prototype.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/selectUtil.js" type="text/javascript"></script>

		<script type="text/javascript" language="javascript">
		function getSectionByForum(){			
			var selectedValue=getSelectedValue("forumId");		
		
			var myAjax = new Ajax.Request("../forum/sectionlist.do?thisAction=ajaxList&forumId="+selectedValue,
			{			
				method:"post", 
				onComplete:function (originalRequest) {
						var result = originalRequest.responseText;
						var selectListTD=document.getElementById("selectSectionListTD");		
						selectListTD.innerHTML="";		
						selectListTD.innerHTML=result;						
				}, onException:showException
			 });			
		}
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
		
		function addSection(){
			var actionValue=document.getElementById("thisAction").value;			
			var forumId=document.getElementById("forumId");			
			var sectionId=document.getElementById("sectionId");
			var sectionName=document.getElementById("name");	
			var sectionUrl=document.getElementById("sectionUrl");	
					
			
			if("insert"==actionValue){
				if(forumId==null){
					alert("论坛库中没有论坛,请添加");
				}else if(sectionName==null || ""==sectionName.value){
					alert("请填写板块名称");
				}else if(sectionUrl==null || ""==sectionUrl.value){
					alert("请填写板块路径");
				}else{
					document.forms[0].submit();
				}			   
			}else if("update"==actionValue){
				if(sectionId==null){
					alert("该论坛还没有可用的版块！")
				}else if(forumId==null){
					alert("论坛库中没有论坛,请添加");
				}else {
					document.forms[0].submit();
				}			   
			}else{
				alert("未定义的action value:"+actionValue);
			}			
		}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/forum/section.do">
					<html:hidden property="thisAction" name="section" />
					<html:hidden property="id" name="section" />
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
										name="section">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=版块管理&title2=编辑版块信息"
											charEncoding="UTF-8" />
									</logic:equal>
									<logic:equal value="insert" property="thisAction"
										name="section">
										<c:import url="../_jsp/mainTitle.jsp?title1=版块管理&title2=新建版块"
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
											<logic:equal value="update" property="thisAction"
												name="section">
												<html:select property="forumId" value="${selectedForumId}"
													onchange="getSectionByForum()">
													<c:forEach var="info" items="${forumlist}"
														varStatus="forumId">
														<html:option value="${info.id}">
															<c:out value="${info.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="section">
												<html:select property="forumId" value="${selectedForumId}">
													<c:forEach var="info" items="${forumlist}"
														varStatus="forumId">
														<html:option value="${info.id}">
															<c:out value="${info.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											版块名称
										</td>
										<td style="text-align: left" id="selectSectionListTD">
											<logic:equal value="update" property="thisAction"
												name="section">
												<html:select property="sectionId"
													value="${selectedSectionId}" disabled="true">
													<c:forEach var="info" items="${sectionlist}"
														varStatus="sectionId">
														<html:option value="${info.id}">
															<c:out value="${info.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="section">
												<html:text property="name" name="section"
													styleClass="colorblue2 p_5" style="width:100%;heigh:100%;" />
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											版块路径
										</td>
										<td style="text-align: left">
											<html:text property="sectionUrl" name="section"
												styleClass="colorblue2 p_5" style="width:100%;heigh:100%;" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="section">有效</html:radio>
											<html:radio property="status" value="2" name="section">无效</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="保存"
												�� �    onclick="addSection();">

											<input name="label" type="button" class="button1" value="重置"
												� onclick="document.section.reset();">
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
