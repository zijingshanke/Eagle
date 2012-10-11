package com.fordays.masssending.message.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fordays.masssending.httpclient.HttpClientInfo;
import com.fordays.masssending.httpclient.HttpClientUtil;
import com.fordays.masssending.message.Message;
import com.fordays.masssending.message.biz.MessageBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class MessageAction extends BaseAction {
	private MessageBiz messageBiz;

	public ActionForward testGetMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		
		
		String rootpath=com.neza.base.Constant.WEB_INFO_PATH;
		
		System.out.println("---"+rootpath);
		
		int LOGIN_PORT = 80;
		String LOGIN_SITE = "www.gzuc.net";
		String CHARSET = "gbk";
		String url = "http://www.gzuc.net/login.php";

		LOGIN_SITE = "192.168.1.88";
		CHARSET = "UTF-8";
		url = "http://192.168.1.88:8080/rencaiduo/login.jsp";

		// LOGIN_SITE = "passport.csdn.net";
		// CHARSET = "UTF-8";
		// url = "http://passport.csdn.net/UserLogin.aspx";
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding(CHARSET);

//		try {
//			HttpClientInfo clientInfo = HttpClientUtil.initHttpClientInfo(
//					LOGIN_SITE, LOGIN_PORT, CHARSET);// 初始化HttpClientInfo
//
//			GetMethod redirect = new GetMethod(url);
//			int statusCode = clientInfo.getHttpclient().executeMethod(redirect);
//			System.out.println("statusCode:" + statusCode);
//
//			String htmlcontent = HttpClientUtil.printResponseHtml(redirect,
//					true, false);
//			PrintWriter out = response.getWriter();
//			out.print(htmlcontent);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Message message = (Message) form;
		Inform inf = new Inform();
		try {
			Message tempMessage = messageBiz.setTempForm(message);

			messageBiz.update(tempMessage);

			request.setAttribute("message", tempMessage);

			inf.setMessage("你已成功更新了帖子");
			inf.setForwardPage("/message/messagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("更新帖子出错！错误信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Message message = (Message) form;
		Inform inf = new Inform();

		try {
			Message tempMessage = messageBiz.setTempForm(message);

			messageBiz.save(tempMessage);
			request.setAttribute("message", tempMessage);
			inf.setMessage("你已成功增加了帖子");
			inf.setForwardPage("/message/messagelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加帖子失败，出错信息是:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);

		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setMessageBiz(MessageBiz messageBiz) {
		this.messageBiz = messageBiz;
	}
}
