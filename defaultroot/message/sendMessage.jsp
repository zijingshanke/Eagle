<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>发布帖子</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript">
		function sendMessage(){
		    document.forms[0].thisAction.value="add";
		    document.forms[0].submit();
		}

</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/message/messagelist.do">

					</table>
				</html:form>
			</div>
		</div>
	</body>
</html>
