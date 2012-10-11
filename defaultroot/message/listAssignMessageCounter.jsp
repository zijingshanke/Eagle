<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>统计发帖</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/calendar/WdatePicker.js" type="text/javascript"></script>

		<script type="text/javascript" language="javascript">
			function searchAssignMessage(){
			   document.forms[0].thisAction.value="assignMessageCounter";
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
								<c:import url="../_jsp/mainTitle.jsp?title1=发帖管理&title2=发帖统计"
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
												论坛：
												<html:text property="forumName" styleClass="colorblue2 p_5" />
											</td>
											<td>
												执行人：
												<html:text property="userName" styleClass="colorblue2 p_5" />
											</td>
											<td>
												状态：
												<html:select property="status">
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
												帖子标题
											</div>
										</th>
										<th>
											<div>
												发表时间
											</div>
										</th>

										<th>
											<div>
												状态
											</div>
										</th>
									</tr>
									<c:forEach var="info" items="${amclf.list}" varStatus="status">
										<tr>
											<td>
												<c:out
													value="${status.count+(amlf.intPage-1)*amlf.perPageNum}" />
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
											</td>
											<td>
												<c:out value="${info.messageSection.message.title}" />
											</td>
											<td>
												<c:out value="${info.sendedtime}" />
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
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${amclf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${amclf.intPage}" />
												/
												<c:out value="${amclf.pageCount}" />
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
