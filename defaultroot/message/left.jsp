<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>left</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="mainContainer">
			<div class="fixedSideBar"></div>
			<div id="sideBar">
				<div class="sideBarItem webAdmin">
					<span class="title"><a href="forumlist.do?thisAction=list"
						target="mainFrame">发帖管理</a> </span>
					<ul class="contents">
						<li>
							<a href="assignmentlist.do?thisAction=list" target="mainFrame">任务列表</a>
						</li>
						<li>
							<a href="messagelist.do?thisAction=list" target="mainFrame">帖子列表</a>
						</li>
						<li>
							<a href="messageSectionlist.do?thisAction=list"
								target="mainFrame">发帖目标管理</a>
						</li>
						<li>
							<a href="assignMessagelist.do?thisAction=list" target="mainFrame">发帖任务管理</a>
						</li>
						<li>
							<a
								href="assignMessagelist.do?thisAction=listExecuteAssignMessage&status=1"
								target="mainFrame">我要发帖</a>
						</li>
						<li>
							<a href="replyMessage.jsp" target="mainFrame">我要回帖</a>
						</li>
						<li>
							<a href="assignMessagelist.do?thisAction=assignMessageCounter"
								target="mainFrame">发帖统计</a>
						</li>
						<li>
							<a href="assignMessageLoglist.do?thisAction=list"
								target="mainFrame">发帖任务日志</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="closeSiseBar">
				<span class="btn"></span>
			</div>
		</div>
	</body>
</html>
