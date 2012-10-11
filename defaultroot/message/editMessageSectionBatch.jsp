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
		
		function getSectionByForumByFlag(flag){					
			var selectedValue=getSelectedValue("forumId"+flag);		
			
			var myAjax = new Ajax.Request("../forum/sectionlist.do?thisAction=ajaxList&forumId="+selectedValue+"&batchFlag="+flag,
			{			
				method:"post", 
				onComplete:function (originalRequest) {
						var result = originalRequest.responseText;
						var selectListTD=document.getElementById("selectSectionListTD"+flag);		
											
						selectListTD.innerHTML="";	
						selectListTD.innerHTML=result;						
				}, onException:showException
			 });			
		}
		
		function getTopicBySectionByFlag(flag){
			var selectedValue=getSelectedValue("sectionId"+flag);
			
			var requestURL="../forum/topiclist.do?thisAction=ajaxList&sectionId="+selectedValue+"&batchFlag="+flag;
				
			var myAjax = new Ajax.Request(requestURL,
			{			
				method:"post", 
				onComplete:function (originalRequest) {
						var result = originalRequest.responseText;
						
						var selectListTD=document.getElementById("selectTopicListTD"+flag);	
									
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
			var selectValue=obj.value;			
			return selectValue;		
		}		
		
		function addMessageSectionBatch(){
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
									<c:import
										url="../_jsp/mainTitle.jsp?title1=发帖管理&title2=批量添加发帖目标"
										charEncoding="UTF-8" />
								</div>
								<hr>
							</td>
						</tr>
					</table>
					<table width="100%" cellpadding="0" cellspacing="0" border="0"
						class="dataList">
						<tr>
							<td>
								点击[
								<input type="button" name="Create" value="+"
									onClick="ClickCreate()"
									style="font-size: 14px; border: 0px; height: 18px; width: 20px; background-color: #F5F9FA; font-weight: bold; color: #FF0000; font-family: Arial; cursor: hand;">
								]&nbsp;(再添加一个)
							</td>
							<td>
								<input type="button" value="添加" onclick="batchClickCreate();">
								<input type="text" id="batchAddCount" style="width: 50px">
								个 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" value="删除" onclick="batchClickDelete();">
								<input type="text" id="batchDelCount" style="width: 50px">
								个
							</td>
							<td>
								状态
								<html:radio property="status" value="1" name="messageSection">有效</html:radio>
								<html:radio property="status" value="2" name="messageSection">无效</html:radio>
							</td>
						</tr>
					</table>
					<hr>
					<SCRIPT LANGUAGE="JavaScript">
		document.write('<table width="100%" border="0" cellspacing="0" cellpadding="0" clias="dataList">');
		document.write('<tbody id="PicBody">');
		document.write('<tr id="strAlbumPicTr">');
		document.write('<td align="left">');
		
		var msstrDefault='<a href=javascript:ClickDelete("") title=清空>';		
		var msstr=msstrDefault+'<img src=http://static.tianya.cn/img/static/2010/icon-.gif border=0></a>&nbsp;';
		msstr+='发帖目标：';
		msstr+='论坛<select name="forumId" id="forumId" onchange="getSectionByForum();">';
		msstr+='<c:forEach var="forumInfo" items="${forumlist}" varStatus="forumId2">';
		msstr+='<option value="<c:out value="${forumInfo.id}"/> "><c:out value="${forumInfo.name}" /></option>';
		msstr+='</c:forEach></select>&nbsp;——&nbsp;';
		
		msstr+='版块<span  id="selectSectionListTD">';
		msstr+='<select name="sectionId" id="selectId" onchange="getTopicBySection();">';
		msstr+='<c:forEach var="sectionInfo" items="${sectionlist}" varStatus="sectionId">';
		msstr+='<option value="<c:out value="${sectionInfo.id}"/> "> <c:out value="${sectionInfo.name}" /></option></c:forEach></select></span>&nbsp;——&nbsp;';
		
		msstr+='主题<span  id="selectTopicListTD">';
		msstr+='<select name="topicId" id="topicId">';
		msstr+='<c:forEach var="topicInfo" items="${topiclist}" varStatus="topicId2">';
		msstr+='<option value="<c:out value="${topicInfo.id}"/> "><c:out value="${topicInfo.name}" /></option></c:forEach></select></span>&nbsp;——&nbsp;';
		
		msstr+='帖子';
		msstr+='<select name="messageId" id="messageId" value="${selectedMessageId}" >';
		msstr+='<c:forEach var="messageInfo" items="${messagelist}" varStatus="messageId2">';		
		msstr+='<option value="<c:out value="${messageInfo.id}"/> "><c:out value="${messageInfo.title}" /></option></c:forEach></select></span><BR><hr>';
		document.write(msstr);
		
		document.write('</tr>');		
		document.write('</tbody>');
		document.write('</table>');		
											

		var PicList=new Array();
		for(var i=0;i<10;i++){
			PicList[i]="";
		}
		function AlertList(){
			for(var i=0;i<10;i++){
				alert((i+1)+":"+PicList[i]);
			}
		}
		PicList[0]=1;
		var PicBody;
		function init()	{
			PicBody=document.getElementById("PicBody");
		}
		window.onload=init;
		
		
	function batchClickCreate()	{
		var count=document.getElementById("batchAddCount").value;
		//alert(count);
		if(count>1){
			for(var i=1;i<count;i++){
				ClickCreate();
			}
		}
	}
	
	function batchClickDelete()	{
		var count=document.getElementById("batchDelCount").value;
		//alert(count);
		if(count>1){
			for(var i=0;i<count;i++){
				if(i==0){
					ClickDelete("");					
				}else{
					ClickDelete(i+1);
				}				
			}
		}
	}	
		
	 function ClickCreate()	{
			for(var i=1;i<PicList.length;i++)	{				
				if(!document.getElementById("strAlbumPicTr")){
					tr=document.createElement("tr");
					tr.id="strAlbumPicTr";
					td=document.createElement("td");
					td.id="strAlbumPicTd";
					tr.appendChild(td);
					PicBody.appendChild(tr);
					document.getElementById("strAlbumPicTd").innerHTML=msstr;
															
					break;
				}

				if(PicList[i]=="")	{
					tr=document.createElement("tr");
					tr.id="strAlbumPicTr"+i;
					td=document.createElement("td");
					td.id="strAlbumPicTd"+i;
					tr.appendChild(td);
					PicBody.appendChild(tr);
					
					var msstrDefault2='<a href=javascript:ClickDelete("'+i+'") title=清空>';					
					var msstr2=msstrDefault2+'<img src=http://static.tianya.cn/img/static/2010/icon-.gif border=0></a>&nbsp;';
						
						msstr2+='发帖目标：';						
						msstr2+='论坛<select name="forumId" id="forumId'+i+'" onchange="getSectionByForumByFlag('+i+');">';
						msstr2+='<c:forEach var="forumInfo" items="${forumlist}" varStatus="forumId">';
						msstr2+='<option value="<c:out value="${forumInfo.id}"/> "><c:out value="${forumInfo.name}" /></option>';
						msstr2+='</c:forEach></select></td>&nbsp;——&nbsp;';
						
						msstr2+='版块<span id="selectSectionListTD'+i+'">';
						msstr2+='<select name="sectionId" id="sectionId'+i+'" onchange="getTopicBySectionByFlag('+i+');">';
						msstr2+='<c:forEach var="sectionInfo" items="${sectionlist}" varStatus="sectionId">';
						msstr2+='<option value="<c:out value="${sectionInfo.id}"/>"> <c:out value="${sectionInfo.name}" /></option></c:forEach></select></span>&nbsp;——&nbsp;';
					
					    msstr2+='主题<span id="selectTopicListTD'+i+'">';
						msstr2+='<select name="topicId" id="topicId'+i+'">';
						msstr2+='<c:forEach var="topicInfo" items="${topiclist}" varStatus="topicId2">';
						msstr2+='<option value="<c:out value="${topicInfo.id}"/>"><c:out value="${topicInfo.name}" /></option></c:forEach></select></span>&nbsp;——&nbsp;';
						
						msstr2+='帖子';
						msstr2+='<select name="messageId" id="messageId'+i+'" value="${selectedMessageId}" >';
						msstr2+='<c:forEach var="messageInfo" items="${messagelist}" varStatus="messageId2">';		
						msstr2+='<option value="<c:out value="${messageInfo.id}"/>"><c:out value="${messageInfo.title}" /></option></c:forEach></select></td><BR><hr>';
						
					document.getElementById("strAlbumPicTd"+i).innerHTML=msstr2;
					PicList[i]=i+1;
					break;
				}
				if(i==PicList.length-1 && PicList[PicList.length-1]!=""){
					alert("对不起，最多只能加10行！");
					return false;
				}
			}
		}
		function ClickDelete(i){			
			var tr=document.getElementById("strAlbumPicTr"+i);
			if(tr){
				tr.parentNode.removeChild(tr);
				PicList[i]="";
			}			
		}
		</SCRIPT>
					<table width="100%" style="margin-top: 5px;">
						<tr>
							<td>
								<input name="label" type="button" class="button1" value="保存"
									�� �    onclick="addMessageSectionBatch();">
								<input name="label" type="button" class="button1" value="重置"
									� onclick="document.messageSection.reset();">
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
