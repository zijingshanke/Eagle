<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<html>
	<head>
		<title>editTopic</title>
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
						var selectListTD=document.getElementById("sectionListTD");						
						selectListTD.innerHTML="";		
						selectListTD.innerHTML=result;						
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
		
		function addTopic(){
			var forumId=document.getElementById("forumId");			
			var sectionId=document.getElementById("sectionId");
			
			if(forumId==null){
				alert("论坛库中没有论坛,请添加");
			}else if(sectionId==null){
				alert("该论坛还没有可用的版块！")
			}else {
				document.forms[0].submit();
			}			   
		}
		</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/forum/topic.do">
					<html:hidden property="thisAction" name="topic" />
					<html:hidden property="id" name="topic" />
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
									<logic:equal value="update" property="thisAction" name="topic">
										<c:import
											url="../_jsp/mainTitle.jsp?title1=主题管理&title2=编辑主题信息"
											charEncoding="UTF-8" />
									</logic:equal>
									<logic:equal value="insert" property="thisAction" name="topic">
										<c:import url="../_jsp/mainTitle.jsp?title1=主题管理&title2=主题版块"
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
											<html:select property="forumId"
												onchange="getSectionByForum()" name="topic">
												<c:forEach var="forumInfo" items="${forumlist}"
													varStatus="forumId2">
													<html:option value="${forumInfo.id}">
														<c:out value="${forumInfo.name}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											所属版块
										</td>
										<td style="text-align: left" id="sectionListTD">
											<html:select property="sectionId" name="topic">
												<c:forEach var="sectionInfo" items="${sectionlist}"
													varStatus="sectionId2">
													<html:option value="${sectionInfo.id}">
														<c:out value="${sectionInfo.name}" />
													</html:option>
												</c:forEach>
											</html:select>
										</td>
									</tr>
									<tr>
										<td class="lef">
											主题名称
										</td>
										<td style="text-align: left">
											<html:text property="name" name="topic" value=" "
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											主题Value
										</td>
										<td style="text-align: left">
											<html:text property="value" name="topic" value="1"
												styleClass="colorblue2 p_5" />
										</td>
									</tr>
									<tr>
										<td class="lef">
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="topic">有效</html:radio>
											<html:radio property="status" value="2" name="topic">无效</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1" value="保存"
												�� �    onclick="addTopic();">

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
