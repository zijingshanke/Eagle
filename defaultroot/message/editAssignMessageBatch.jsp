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
		
		function getAccountByMessageSectionByFlag(flag){
			//alert("flag"+flag)
			var selectedValue=getSelectedValue("messageSectionId"+flag);	
			
			var myAjax = new Ajax.Request("../forum/accountlist.do?thisAction=ajaxList&messageSectionId="+selectedValue+"&batchFlag="+flag,
			{			
				method:"post", 
				onComplete:function (originalRequest) {
						var result = originalRequest.responseText;
						var selectListTD=document.getElementById("selectListTD"+flag);		
						selectListTD.innerHTML="";		
						selectListTD.innerHTML=result;				
				}, onException:showException
			 });			
		}
		
		function showException(originalRequest, ex) {
			alert("Exception:" + ex.message);
		}
			
		function addAssignMessageBatch(){
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
									<c:import
										url="../_jsp/mainTitle.jsp?title1=发帖任务管理&title2=批量添加发帖任务"
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
								<html:radio property="status" value="1" name="assignMessage">有效</html:radio>
								<html:radio property="status" value="2" name="assignMessage">无效</html:radio>
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
		msstr+='任务：';
		msstr+='<select name="assignmentId" id="assignmentId" >';
		msstr+='<c:forEach var="assignInfo" items="${assignmentlist}" varStatus="assignmentId">';
		msstr+='<option value="<c:out value="${assignInfo.id}"/> "><c:out value="${assignInfo.name}" /></option>';
		msstr+='</c:forEach></select>—&nbsp;';
		
		msstr+='目标:';
		msstr+='<select name="messageSectionId" id="messageSectionId" onchange="getAccountByMessageSection();">';
		msstr+='<c:forEach var="messageSection" items="${messageSectionlist}" varStatus="messageSectionId">';
		msstr+='<option value="<c:out value="${messageSection.id}"/> "> <c:out value="${messageSection.summary}" /></option></c:forEach></select>—&nbsp;';
		
		msstr+='帐号:<span id="selectListTD">';
		msstr+='<select name="accountId" id="accountId">';
		msstr+='<c:forEach var="account" items="${accountlist}" varStatus="accountId">';
		msstr+='<option value="<c:out value="${account.id}"/> "><c:out value="${account.loginName}" /></option></c:forEach></select></span>—&nbsp;';
		
		msstr+='执行人:';
		msstr+='<select name="userId" id="userId" >';
		msstr+='<c:forEach var="userInfo" items="${sysuserlist}" varStatus="userId">';		
		msstr+='<option value="<c:out value="${userInfo.userId}"/> "><c:out value="${userInfo.userName}" /></option></c:forEach></select><BR><hr>';
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
					
					var msstrDefault2='<a href=javascript:ClickDelete("") title=清空>';		
					var msstr2=msstrDefault2+'<img src=http://static.tianya.cn/img/static/2010/icon-.gif border=0></a>&nbsp;';
						msstr2+='任务：';
						msstr2+='<select name="assignmentId" id="assignmentId'+i+'" >';
						msstr2+='<c:forEach var="assignInfo" items="${assignmentlist}" varStatus="assignmentId">';
						msstr2+='<option value="<c:out value="${assignInfo.id}"/> "><c:out value="${assignInfo.name}" /></option>';
						msstr2+='</c:forEach></select>—&nbsp;';
						
						msstr2+='目标:';
						msstr2+='<select name="messageSectionId" id="messageSectionId'+i+'" onchange="getAccountByMessageSectionByFlag('+i+');">';
						msstr2+='<c:forEach var="messageSection" items="${messageSectionlist}" varStatus="messageSectionId">';
						msstr2+='<option value="<c:out value="${messageSection.id}"/> "> <c:out value="${messageSection.summary}" /></option></c:forEach></select>—&nbsp;';
						
						msstr2+='帐号:<span id="selectListTD'+i+'">';
						msstr2+='<select name="accountId" id="accountId'+i+'">';
						msstr2+='<c:forEach var="account" items="${accountlist}" varStatus="accountId">';
						msstr2+='<option value="<c:out value="${account.id}"/> "><c:out value="${account.loginName}" /></option></c:forEach></select></span>—&nbsp;';
						
						msstr2+='执行人:';
						msstr2+='<select name="userId" id="userId'+i+'" >';
						msstr2+='<c:forEach var="userInfo" items="${sysuserlist}" varStatus="userId">';		
						msstr2+='<option value="<c:out value="${userInfo.userId}"/> "><c:out value="${userInfo.userName}" /></option></c:forEach></select><BR><hr>';
											
					//alert(msstr2);
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
									�� �    onclick="addAssignMessageBatch();">
								<input name="label" type="button" class="button1" value="重置"
									� onclick="document.assignMessage.reset();">
							</td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>

