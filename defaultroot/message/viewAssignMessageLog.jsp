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
									<td class="lef">
										发帖目标
									</td>
									<td style="text-align: left">
										<c:out value="${assignMessageLog.assignMessage.resume}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										时间
									</td>
									<td style="text-align: left">
										<c:out value="${assignMessageLog.logTime}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										日志类型
									</td>
									<td style="text-align: left">
										<c:out value="${assignMessageLog.logType}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										日志内容
									</td>
									<td style="text-align: left">
										<c:out value="${assignMessageLog.content}" />
									</td>
								</tr>
								<tr>
									<td class="lef">
										状态
									</td>
									<td style="text-align: left">
										<c:out value="${assignMessageLog.statusInfo}" />
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
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>
