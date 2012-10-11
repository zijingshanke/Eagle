<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>

<html>
	<head>
		<title>执行发帖任务</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="../_js/jquery-1.3.1.min.js"></script>
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/checkAll.js" type="text/javascript"></script>

		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>

		<script type="text/javascript" language="javascript">
		function executeAssignMessage(){		
		   if(baseCheck()){
		    	document.forms[0].thisAction.value="sendMessage";
		    	document.forms[0].submit();	
		    }	    	    
		}
		
		function sendMessageASpace(){
			if(baseCheck()){
		    	document.forms[0].thisAction.value="sendMessageASpace";
		    	document.forms[0].submit();
		    }	
		}
		
		
		function baseCheck(){
			if(document.forms[0].selectedItems==null){
				alert("没有数据，无法操作！");
				return false
			}else if(sumCheckedBox(document.forms[0].selectedItems)<1){
		    	alert("您还没有选择发帖任务！");
		    	return false
		    }
		    return true;
		}	  	
	
		function executeTimedAssignMessage(){
		   alert("您暂时不能使用定时执行发帖任务功能!");
		}	
		
		
		function showSendMessageASpace(){
			var spanSpaceTime=document.getElementById("spanSpaceTime");
			spanSpaceTime.style.display="";
		}	
	</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/message/assignMessagelist.do">
					<html:hidden property="thisAction" />
					<html:hidden property="lastAction" />
					<html:hidden property="intPage" />
					<html:hidden property="pageCount" />
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<td width="10" height="10" class="tblt"></td>
							<td height="10" class="tbtt"></td>
							<td width="10" height="10" class="tbrt"></td>
						</tr>
						<tr>
							<td width="10" class="tbll"></td>
							<td valign="top" class="body">
								<c:import
									url="../_jsp/mainTitle.jsp?title1=发帖任务管理&title2=执行发帖任务"
									charEncoding="UTF-8" />
								<div class="searchBar">
									<p>
										搜索栏
									</p>
									<hr />
									<table cellpadding="0" cellspacing="0" border="0"
										class="searchPanel">
										<tr>
											<td>
												任务：
												<html:text property="assignmentName"
													styleClass="colorblue2 p_5" />
											</td>
											<td>
												帖子标题：
												<html:text property="messageTitle"
													styleClass="colorblue2 p_5" />
											</td>
											<td>
												状态：
												<html:select property="status">
													<html:option value="0">请选择</html:option>
													<html:option value="0">-全部-</html:option>
													<html:option value="1">等待发送</html:option>
													<html:option value="2">发送成功</html:option>
													<html:option value="3">发表失败</html:option>
												</html:select>
											</td>

											<td>
												<input type="submit" name="button" id="button" value="提交"
													class="submit greenBtn" />
											</td>
										</tr>
									</table>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th width="35">
											<div>
												序号
											</div>
										</th>
										<th width="35">
											<div
												style="height: 100%; width: 100%; vertical-align: center; padding-top: 4px;">
												<input type="checkbox"
													onclick="checkAll(this, 'selectedItems')" name="sele" />
											</div>
										</th>
										<th>
											<div>
												任务
											</div>
										</th>
										<th>
											<div>
												执行人
											</div>
										</th>
										<th>
											<div>
												发帖帐号
											</div>
										</th>
										<th>
											<div>
												帖子标题
											</div>
										</th>
										<th>
											<div>
												状态
											</div>
										</th>
									</tr>
									<c:forEach var="info" items="${amlf.list}" varStatus="status">
										<tr>
											<td>
												<c:out
													value="${status.count+(amlf.intPage-1)*amlf.perPageNum}" />
											</td>
											<td>
												<c:if test="${info.status==1 || info.status==3}">
													<html:multibox property="selectedItems" value="${info.id}"
														onclick="checkItem(this, 'sele')">
													</html:multibox>
												</c:if>
											</td>
											<td>
												<html:link
													page="/message/assignmentlist.do?thisAction=view&id=${info.assignment.id}">
													<c:out value="${info.assignment.name}" />
												</html:link>
											</td>

											<td>
												<c:out value="${info.sysUser.userName}" />
											</td>
											<td>
												<c:out value="${info.account.loginName}" />
												<c:out value="${info.account.loginPassword}" />
											</td>
											<td>
												<html:link
													page="/message/messagelist.do?thisAction=view&id=${info.messageSection.message.id}">
													<c:out value="${info.messageSection.message.title}" />
												</html:link>
											</td>
											<td>
												<c:out value="${info.statusInfo}" />
												<c:if test="${info.status==3}">
													<html:link
														page="/message/assignMessagelist.do?thisAction=viewMemo&id=${info.id}">
														查询详细
														</html:link>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</table>
								<table width="100%" style="margin-top: 5px;">
									<tr>
										<td>
											<input name="label" type="button" class="button1"
												value="立即发帖" onclick="executeAssignMessage();">
											<input name="label" type="button" class="button1"
												value="定时发帖" onclick="executeTimedAssignMessage();">
											<input name="label" type="button" class="button1"
												value="间隔发帖" onclick="showSendMessageASpace()">
										</td>
										<td id="spanSpaceTime" style="display: none">
											<html:text property="spaceTime" styleClass="colorblue2 p_5" />
											分钟
											<input name="label" type="button" class="button1" value="确定"
												onclick="sendMessageASpace()" />
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${amlf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${amlf.intPage}" />
												/
												<c:out value="${amlf.pageCount}" />
												]
											</div>
										</td>
									</tr>
								</table>
								<div class="clear"></div>
							</td>
							<td width="10" class="tbrr"></td>
						</tr>
						<tr>
							<td width="10" class="tblb"></td>
							<td class="tbbb"></td>
							<td width="10" class="tbrb"></td>
						</tr>
					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
