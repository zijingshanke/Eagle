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
									<c:import url="../_jsp/mainTitle.jsp?title1=帐号管理&title2=批量添加帐号"
										charEncoding="UTF-8" />
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
											状态
										</td>
										<td style="text-align: left">
											<html:radio property="status" value="1" name="account">有效</html:radio>
											<html:radio property="status" value="2" name="account">无效</html:radio>
										</td>
									</tr>
								</table>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<td class="lef" colspan="2">
											点击[
											<input type="button" name="Create" value="+"
												onClick="ClickCreate()"
												style="font-size: 14px; border: 0px; height: 18px; width: 20px; background-color: #F5F9FA; font-weight: bold; color: #FF0000; font-family: Arial; cursor: hand;">
											]&nbsp;(批量添加说明)
											<br>
										</td>
									</tr>
								</table>
								<SCRIPT LANGUAGE="JavaScript">
		document.write('<table border="0" cellspacing="0" cellpadding="0">');
		document.write('<tbody id="PicBody">');
		document.write('<tr id="strAlbumPicTr">');
		document.write('<td align="left"><a href=javascript:ClickDelete("") title=清空><img src=http://static.tianya.cn/img/static/2010/icon-.gif border=0></a>&nbsp;');
		document.write('登录名:<input type="text" name="loginName" id="loginName" styleClass="colorblue2 p_5" />&nbsp;');
		document.write('登录密码:<input type="text" name="loginPassword" id="loginPassword" value="" styleClass="colorblue2 p_5" />');
		document.write('<BR></td></tr>');		
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
		
	 function ClickCreate()	{
			for(var i=1;i<PicList.length;i++)	{				
				if(!document.getElementById("strAlbumPicTr")){
					tr=document.createElement("tr");
					tr.id="strAlbumPicTr";
					td=document.createElement("td");
					td.id="strAlbumPicTd";
					tr.appendChild(td);
					PicBody.appendChild(tr);
					document.getElementById("strAlbumPicTd").innerHTML='<a href=javascript:ClickDelete("") title=清空><img src=http://static.tianya.cn/img/static/2010/icon-.gif border=0></a>&nbsp;登录名:<input type="text" name="loginName" id="loginName"	styleClass="colorblue2 p_5" /> 登录密码:<input type="text" name="loginPassword" id="loginPassword" value="" styleClass="colorblue2 p_5" /><BR>';
															
					break;
				}

				if(PicList[i]=="")	{
					tr=document.createElement("tr");
					tr.id="strAlbumPicTr"+i;
					td=document.createElement("td");
					td.id="strAlbumPicTd"+i;
					tr.appendChild(td);
					PicBody.appendChild(tr);
					document.getElementById("strAlbumPicTd"+i).innerHTML='<a href=javascript:ClickDelete("'+i+'") title=清空><img src=http://static.tianya.cn/img/static/2010/icon-.gif border=0></a>&nbsp;登录名:<input type="text" name="loginName" id="loginName'+i+'"	styleClass="colorblue2 p_5" /> 登录密码:<input type="text" name="loginPassword" id="loginPassword'+i+'"  value=""	styleClass="colorblue2 p_5" /><BR>';
					
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
			var m = 0;
			for(var n=0;n<5;n++){
				if(n==0){
					if(document.getElementById("loginPassword")){
						m++;
					}
				}
				else{
					if(document.getElementById("loginPassword"+n)){
						m++;
					}
				}
			}			

			if(m==1){		
				if(document.getElementById("loginPassword"))
					document.getElementById("loginPassword").value = "";	
				if(document.getElementById("loginPassword1"))
					document.getElementById("loginPassword1").value = "";			
				if(document.getElementById("loginPassword2"))
					document.getElementById("loginPassword2").value = "";
				if(document.getElementById("loginPassword3"))
					document.getElementById("loginPassword3").value = "";
				if(document.getElementById("loginPassword4"))
					document.getElementById("loginPassword4").value = "";
				return;			
			}

			var tr=document.getElementById("strAlbumPicTr"+i);
			document.getElementById("loginPassword"+i).value="";
			tr.parentNode.removeChild(tr);
			PicList[i]="";
		}
		</SCRIPT>
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
