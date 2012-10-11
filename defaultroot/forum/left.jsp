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
						target="mainFrame">论坛管理</a> </span>
					<ul class="contents">
						<li>
							<a href="forumlist.do?thisAction=list" target="mainFrame">论坛列表</a>
						</li>
						<li>
							<a href="sectionlist.do?thisAction=list" target="mainFrame">版块列表</a>
						</li>
						<li>
							<a href="topiclist.do?thisAction=list" target="mainFrame">主题列表</a>
						</li>
						<li>
							<a href="accountlist.do?thisAction=list" target="mainFrame">帐号列表</a>
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
