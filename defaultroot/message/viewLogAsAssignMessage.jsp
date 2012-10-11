<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>main</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td width="10" height="10" class="tblt"></td>
						<td height="10" class="tbtt"></td>
						<td width="10" height="10" class="tbrt"></td>
					</tr>
					<tr>
						<td width="20" class="tbll"></td>
						<td valign="top" class="body">
							<div class="crumb">
								<c:import
									url="../_jsp/mainTitle.jsp?title1=发帖任务管理&title2=查看发帖任务日志"
									charEncoding="UTF-8" />
							</div>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<th>
										<div align="left">
											任务--发帖目标--帖子标题
										</div>
									</th>
									<td style="text-align: left">
										<html:link
											page="/message/assignMessagelist.do?thisAction=view&id=${amloglf.assignMessage.id}">
											<c:out value="${amloglf.assignMessage.resume}" />
										</html:link>
									</td>
								</tr>
							</table>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">
								<tr>
									<th width="35">
										<div>

										</div>
									</th>
									<th>
										<div>
											发生时间
										</div>
									</th>
									<th>
										<div>
											日志类型
										</div>
									</th>
									<th>
										<div>
											日志内容
										</div>
									</th>
									<th>
										<div>
											状态
										</div>
									</th>
								</tr>
								<c:forEach var="info" items="${amloglf.list}" varStatus="status">
									<tr>
										<td>
											<c:out value="${status.count+(dlf.intPage-1)*dlf.perPageNum}" />
										</td>
										<td>
											<c:out value="${info.logTime}" />
										</td>
										<td>
											<c:out value="${info.logTypeMemo}" />
										</td>
										<td>
											<c:out value="${info.content}" />
										</td>
										<td>
											<c:out value="${info.statusInfo}" />
										</td>
									</tr>
								</c:forEach>
							</table>

							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>
										<input name="label" type="button" class="button1" value="返 回"
											onclick="window.history.back();">
									</td>
									<td align="right">
										<div>
											共有记录&nbsp;
											<c:out value="${amloglf.totalRowCount}"></c:out>
											&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
											<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a> |
											<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
											|
											<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
											|
											<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
											页数:
											<c:out value="${amloglf.intPage}" />
											/
											<c:out value="${amloglf.pageCount}" />
											]
										</div>
									</td>
								</tr>

							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
