<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>

<html>
	<head>
		<title>editMessageSection</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/prototype.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>

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
		
		function getTopicBySection(){
			var selectedValue=getSelectedValue("sectionId");	
		
			var myAjax = new Ajax.Request("../forum/topiclist.do?thisAction=ajaxList&sectionId="+selectedValue,
			{			
				method:"post", 
				onComplete:function (originalRequest) {
						var result = originalRequest.responseText;
						var selectListTD=document.getElementById("selectTopicListTD");						
						selectListTD.innerHTML="";		
						selectListTD.innerHTML=result;	
						//alert(result)					
				}, onException:showException
			 });			
		}			
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
		
		function printSelects(id){
		 	var s=document.getElementById(id); 
  			for(var i = 1; i < 6; i++ ){
 				 s.options[s.options.length] = new Option(i,i);
 			 } 
		}
		
		function getSelectedValue(selectName){
			var obj=document.getElementById(selectName);
			return obj.value;		
		}		
		
		function addMessageSection(){
			var forumId=document.getElementById("forumId");
			var sectionId=document.getElementById("sectionId");
			var messageId=document.getElementById("messageId");
			if(forumId==null){
				alert("论坛库中没有论坛,请添加");
			}else if(sectionId==null){
				alert("此论坛没有版块,请添加");
			}else if(messageId==null){
				alert("帖子库为空,请添加");
			}else{
			 	document.forms[0].submit();
			}
		}			
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/message/messageSection.do">
					<html:hidden property="thisAction" name="messageSection" />
					<html:hidden property="id" name="messageSection" />
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
										name="messageSection">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=帖子版块管理&title2=编辑帖子版块信息"
											charEncoding="UTF-8" />
									</logic:equal>
									<logic:equal value="insert" property="thisAction"
										name="messageSection">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=帖子版块管理&title2=新建帖子版块"
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
												name="messageSection">
												<html:select property="forumId" value="${selectedForumId}"
													onchange="getSectionByForum()" name="messageSection">
													<c:forEach var="forumInfo" items="${forumlist}"
														varStatus="forumId2">
														<html:option value="${forumInfo.id}">
															<c:out value="${forumInfo.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="messageSection">
												<html:select property="forumId"
													onchange="getSectionByForum()" name="messageSection">
													<c:forEach var="forumInfo" items="${forumlist}"
														varStatus="forumId2">
														<html:option value="${forumInfo.id}">
															<c:out value="${forumInfo.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											所属版块
										</td>
										<td style="text-align: left" id="selectSectionListTD">
											<logic:equal value="update" property="thisAction"
												name="messageSection">
												<html:select property="sectionId"
													onchange="getTopicBySection()" value="${selectedSectionId}">
													<c:forEach var="sectionInfo" items="${sectionlist}"
														varStatus="sectionId">
														<html:option value="${sectionInfo.id}">
															<c:out value="${sectionInfo.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="messageSection">
												<html:select property="sectionId"
													onchange="getTopicBySection()">
													<c:forEach var="sectionInfo" items="${sectionlist}"
														varStatus="sectionId">
														<html:option value="${sectionInfo.id}">
															<c:out value="${sectionInfo.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											主题
										</td>
										<td style="text-align: left" id="selectTopicListTD">
											<logic:equal value="update" property="thisAction"
												name="messageSection">
												<html:select property="topicId" value="${selectedTopicId}"
													name="messageSection">
													<c:forEach var="topicInfo" items="${topiclist}"
														varStatus="topicId2">
														<html:option value="${topicInfo.id}">
															<c:out value="${topicInfo.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
											<logic:equal value="insert" property="thisAction"
												name="messageSection">
												<html:select property="topicId" name="messageSection">
													<c:forEach var="topicInfo" items="${topiclist}"
														varStatus="topicId2">
														<html:option value="${topicInfo.id}">
															<c:out value="${topicInfo.name}" />
														</html:option>
													</c:forEach>
												</html:select>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td class="lef">
											帖子
										</td>
										<td style="text-align: left">
											<html:select property="messageId"
												value="${selectedMessageId}" name="messageSection">
												<c:forEach var="messageInfo" items="${messagelist}"
													varStatus="messageId2">
													<html:option value="${messageInfo.id}">
														<c:out value="${messageInfo.title}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="messageSection">有效</html:radio>
											<html:radio property="status" value="2" name="messageSection">无效</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="保存"
												�� �    onclick="addMessageSection();">
											<input name="label" type="button" class="button1" value="重置"
												� onclick="document.messageSection.reset();">
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
