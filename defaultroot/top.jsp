<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<c:if test="${URI==null}">
	<script language="JavaScript">
   	top.location="./login.jsp" 
	</script>
</c:if>

<html>
	<head>
		<title>top</title>
		<link href="_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="_css/global.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" language="javascript"
			src="./_js/jquery-1.3.2.min.js"></script>
	</head>
	<body>
		<div id="header">
			<div class="logo">
				Logo
			</div>
			<div class="mainNav">
				<ul class="navContent">
					<li>
						<a href="left.jsp" target="leftFrame">首页</a>
					</li>
					<li>
						<a href="forum/left.jsp" target="leftFrame">论坛管理</a>
					</li>

					<li>
						<a href="message/left.jsp" target="leftFrame">发帖管理</a>
					</li>

					<li>
						<a href="user/left.jsp" target="leftFrame">用户管理</a>
					</li>

					<li>
						<a href="right/left.jsp" target="leftFrame">权限管理</a>
					</li>

					<li>
						<a href="system/left.jsp" target="leftFrame">系统管理</a>
					</li>	
				</ul>

				<ul class="userPanel">
					<li>
						欢迎：
						<FONT color="red"><c:out value="${URI.user.userName}" /> </FONT>
					</li>
					<li>
						<a href="user/user.do?thisAction=exit">退出</a>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>
