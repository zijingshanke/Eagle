<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<html>
	<head>
		<title>帖子版块列表</title>
		<link href="../_css/reset.css" rel="stylesheet" type="text/css" />
		<link href="../_css/global.css" rel="stylesheet" type="text/css" />
		<script src="../_js/common.js" type="text/javascript"></script>
		<script src="../_js/checkAll.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
function addMessageSection(){
    document.forms[0].thisAction.value="add";
    document.forms[0].submit();
}

function addMessageSectionBatch(){
    document.forms[0].thisAction.value="addBatch";
    document.forms[0].submit();
}


function editMessageSection(){
	if(document.forms[0].selectedItems==null){
		alert("没有数据，无法操作！");
	}else if(sumCheckedBox(document.forms[0].selectedItems)<1){
    	alert("您还没有选择帖子版块！");
    }else if (sumCheckedBox(document.forms[0].selectedItems)>1){
		alert("您一次只能选择一个帖子版块！");
    }else{
    	document.forms[0].thisAction.value="edit";
    	document.forms[0].submit();
  	}
}

function delMessageSection(){
	if(document.forms[0].selectedItems==null){
		   	alert("没有数据，无法操作！");
	}else if(sumCheckedBox(document.forms[0].selectedItems)<1){
		alert("您还没有选择帖子版块！");
	}else if(confirm("您真的要删除选择的这些帖子版块吗？")){
		document.forms[0].thisAction.value="delete";
		document.forms[0].submit();
  	}
}

function searchMessageSection(){
   document.forms[0].thisAction.value="list";
   document.forms[0].submit();
}
</script>
	</head>
	<body>
		<div id="mainContainer">
			<div id="container">
				<html:form action="/message/messageSectionlist.do">
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
									url="../_jsp/mainTitle.jsp?title1=帖子版块管理&title2=帖子版块管理"
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
												帖子标题：
												<html:text value="" property="messageTitle"
													styleClass="colorblue2 p_5" />
											</td>

											<td>
												状态：
												<html:select property="status">
													<html:option value="0">请选择</html:option>
													<html:option value="1">有效</html:option>
													<html:option value="2">无效</html:option>
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
												论坛
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
									<c:forEach var="info" items="${mslf.list}" varStatus="status">
										<tr>
											<td>
												<c:out
													value="${status.count+(amlf.intPage-1)*amlf.perPageNum}" />
											</td>
											<td>
												<html:multibox property="selectedItems" value="${info.id}"></html:multibox>
											</td>
											<td>
												<html:link page="/forum/forumlist.do?thisAction=view&id=1">
													<c:out value="${info.section.forum.name}" />
												</html:link>
											</td>
											<td>
												<html:link
													page="/forum/sectionlist.do?thisAction=view&id=${info.section.id}">
													<c:out value="${info.section.name}" />
												</html:link>
											</td>
											<td>
												<html:link
													page="/forum/topiclist.do?thisAction=view&id=${info.topic.id}">
													<c:out value="${info.topic.name}" />
												</html:link>
											</td>
											<td>
												<html:link
													page="/message/messagelist.do?thisAction=view&id=${info.message.id}">
													<c:out value="${info.message.title}" />
												</html:link>
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
											<input name="label" type="button" class="button1" value="新 增"
												onclick="addMessageSection();">
											<input name="label" type="button" class="button1"
												value="批量新增" onclick="addMessageSectionBatch();">
											<input name="label" type="button" class="button1" value="修 改"
												onclick="editMessageSection();">
											<input name="label" type="button" class="button1" value="删 除"
												onclick="delMessageSection();">
										</td>
										<td align="right">
											<div>
												共有记录&nbsp;
												<c:out value="${mslf.totalRowCount}"></c:out>
												&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp; [
												<a href="JavaScript:turnToPage(document.forms[0],0)">首页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],1)">上一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],2)">下一页</a>
												|
												<a href="JavaScript:turnToPage(document.forms[0],3)"> 末页</a>]
												页数:
												<c:out value="${mslf.intPage}" />
												/
												<c:out value="${mslf.pageCount}" />
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
