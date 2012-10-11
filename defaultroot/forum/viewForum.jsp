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
						<td width="10" class="tbll"></td>
						<td valign="top" class="body">
							<div class="crumb">
								<c:import url="../_jsp/mainTitle.jsp?title1=论坛管理&title2=查看论坛"
									charEncoding="UTF-8" />

							</div>
							<hr>
							<table width="100%" cellpadding="0" cellspacing="0" border="0"
								class="dataList">

								<tr>
									<td class="lef">
										<div>
											ID
									</td>
									<td style="text-align: left">
										<c:out value="${forum.id}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										<div>
											名称
										</div>
									</td>
									<td style="text-align: left">
										<c:out value="${forum.name}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										登录地址
									</td>
									<td style="text-align: left">
										<c:out value="${forum.loginSite}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										登录端口
									</td>
									<td style="text-align: left">
										<c:out value="${forum.loginPort}" />
									</td>
								</tr>

								<tr>
									<td class="lef">
										状态
									</td>
									<td style="text-align: left">
										<c:out value="${forum.statusInfo}" />
									</td>
								</tr>
							</table>
							<table width="100%" style="margin-top: 5px;">
								<tr>
									<td>
										<input name="label" type="button" class="button1" value="返 回"
											onclick="window.history.back();">
									</td>
								</tr>
							</table>
							<html:form action="/forum/sectionlist.do">
								<html:hidden property="thisAction" />
								<html:hidden property="lastAction" />
								<html:hidden property="intPage" />
								<html:hidden property="pageCount" />
								<table width="100%" cellpadding="0" cellspacing="0" border="0"
									class="dataList">
									<tr>
										<th>
											<div>
												版块名称
											</div>
										</th>
										<th>
											<div>
												版块URL
											</div>
										</th>
										<th>
											<div>
												状态
											</div>
										</th>
									</tr>
									<c:forEach var="info" items="${slf.list}" varStatus="status">
										<tr>
											<td>
												<html:link
													page="/forum/sectionlist.do?thisAction=view&id=${info.id}">
													<c:out value="${info.name}" />
												</html:link>
											</td>
											<td>
												<c:out value="${info.sectionUrl}" />
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
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${slf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${slf.intPage}" />
												/
												<c:out value="${slf.pageCount}" />
												]
											</div>
										</td>
									</tr>
								</table>
								<div class="clear"></div>
							</html:form>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
