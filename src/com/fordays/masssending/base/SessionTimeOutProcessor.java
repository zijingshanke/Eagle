//package com.fordays.masssending.base;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.struts.action.ActionServlet;
//import org.apache.struts.action.RequestProcessor;
//import org.apache.struts.config.ModuleConfig;
//import com.fordays.masssending.right.UserRightInfo;
//
//public class SessionTimeOutProcessor extends RequestProcessor {
//
//	public boolean processPreprocess(HttpServletRequest request,
//			HttpServletResponse response) {
//
//		UserRightInfo uri;
//		if (request.getSession().getAttribute("URI") != null)
//			uri = (UserRightInfo) request.getSession().getAttribute("URI");
//		else
//			uri = null;
//		String actionName = request.getQueryString();
//		if (actionName == null)
//			actionName = "";
//		String path = request.getServletPath();
//		String url = request.getServletPath() + "?" + actionName;
//		String temp = "";
//		if (uri == null || uri.getUser() == null) {
//			try {
//				if (path.indexOf("/cooperate/gateway.do") >= 0) {
//					return true;
//				}
//				for (int i = 0; i < Constant.url.size(); i++) {
//					if (Constant.url.get(i).endsWith(".*"))
//						temp = Constant.url.get(i).replace("*", "do");
//					else
//						temp = Constant.url.get(i).replace(".",
//								".do?thisAction=");
//
//					if (url.indexOf(temp) >= 0)
//						return true;
//
//				}			
//
//				response.sendRedirect(request.getContextPath() + "/login.jsp");
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return false;
//		} else {
//			// if(uri.hasRight(rightName, event))
//			return true;
//		}
//
//	}
//
//	@Override
//	public void init(ActionServlet servlet, ModuleConfig moduleConfig)
//			throws ServletException {
//		// TODO Auto-generated method stub
//		super.init(servlet, moduleConfig);
//	}
//
//	public static final String COMMON_SERIAL_NUMBER = "4B306C05";
//
//	public static void main(String argv[]) throws IOException {
//		// test oInstance = new test();
//		// oInstance.addItem("
//		// http://192.168.1.89:1234/fdpay-client/cooperate/gateway.do?service=test",
//		// "c:/test.txt");
//		// oInstance.downLoadByList();
//		BigInteger x = new BigInteger("4B306C05", 16);
//		System.out.println(x);
//		System.out.println(new BigInteger(
//				SessionTimeOutProcessor.COMMON_SERIAL_NUMBER, 16));
//
//	}
//
//}
