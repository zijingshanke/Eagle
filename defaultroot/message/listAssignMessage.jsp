<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>发帖任务列表</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/checkAll.js" type="text/javascript"></script>

		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>

		<script type="text/javascript" language="javascript">
function addAssignMessage(){
    document.forms[0].thisAction.value="add";
    document.forms[0].submit();
}
function addAssignMessageBatch(){
    document.forms[0].thisAction.value="addBatch";
    document.forms[0].submit();
}

function editAssignMessage(){
	if(document.forms[0].selectedItems==null){
		alert("没有数据，无法操作！");
	}else if(sumCheckedBox(document.forms[0].selectedItems)<1){
    	alert("您还没有选择发帖任务！");
    }else if (sumCheckedBox(document.forms[0].selectedItems)>1){
		alert("您一次只能选择一个发帖任务！");
    }else{
    	document.forms[0].thisAction.value="edit";
    	document.forms[0].submit();
  	}
}

function delAssignMessage(){
	if(document.forms[0].selectedItems==null){
		   	alert("没有数据，无法操作！");
	}else if(sumCheckedBox(document.forms[0].selectedItems)<1){
		alert("您还没有选择发帖任务！");
	}else if(confirm("您真的要删除选择的这些发帖任务吗？")){
		document.forms[0].thisAction.value="delete";
		document.forms[0].submit();
  	}
}

function searchAssignMessage(){
   document.forms[0].thisAction.value="list";
   document.forms[0].submit();
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
									url="../_jsp/mainTitle.jsp?title1=发帖任务管理&title2=发帖任务管理"
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
												<html:text value="" property="assignmentName"
													styleClass="colorblue2 p_5" />
											</td>
											<td>
												论坛：
												<html:text value="" property="forumName"
													styleClass="colorblue2 p_5" />
											</td>
											<td>
												版块：
												<html:text value="" property="sectionName"
													styleClass="colorblue2 p_5" />
											</td>
											<td>
												执行人：
												<html:text value="" property="userName"
													styleClass="colorblue2 p_5" />
											</td>
											<td>
												状态：
												<html:select value="" property="status">
													<html:option value="0">请选择</html:option>
													<html:option value="1">等待发送</html:option>
													<html:option value="2">发送成功</html:option>
													<html:option value="3">发表失败</html:option>
													<html:option value="4">已取消</html:option>
													<html:option value="88">隐藏</html:option>
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
												论坛
											</div>
										</th>
										<th>
											<div>
												发帖帐号
											</div>
										</th>
										<th>
											<div>
												版块
											</div>
										</th>
										<th>
											<div>
												主题
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
												<c:if
													test="${info.status==1 || info.status==3|| info.status==4}">
													<html:multibox property="selectedItems" value="${info.id}"></html:multibox>
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
												<c:out value="${info.messageSection.section.forum.name}" />
											</td>
											<td>
												<c:out value="${info.account.loginName}" />
												<c:out value="${info.account.loginPassword}" />
											</td>
											<td>
												<c:out value="${info.messageSection.section.name}" />
											</td>
											<td>
												<c:out value="${info.messageSection.topic.name}" />
											</td>
											<td>
												<c:out value="${info.messageSection.message.shortTitle}" />
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
											<input name="label" type="button" class="button1" value="新 增"
												onclick="addAssignMessage();">
											<input name="label" type="button" class="button1"
												value="批量新增" onclick="addAssignMessageBatch();">
											<input name="label" type="button" class="button1" value="修 改"
												onclick="editAssignMessage();">
											<input name="label" type="button" class="button1" value="删 除"
												onclick="delAssignMessage();">
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
