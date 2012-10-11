// Decompiled by DJ v2.9.9.60 Copyright 2000 Atanas Neshkov  Date: 2010-3-22 13:58:24
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ThirdPartyServerApp.java

package com.jiangh.webuc.servicemanager; 

import com.jiangh.webuc.data.ServiceData;
import com.jiangh.webuc.servicemanager.CTCManager;
import com.jiangh.webuc.servicemanager.CTDManager;
import com.jiangh.webuc.servicemanager.RegisterManager;
import com.jiangh.webuc.servicemanager.SMSManager;
import com.jiangh.webuc.util.*;
import java.io.PrintStream;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.*;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.InputSource;

// Referenced classes of package com.jiangh.webuc.servicemanager:
//            RegisterManager, CTDManager, CTCManager, SMSManager

public class ThirdPartyServerApp extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public ThirdPartyServerApp()
    {
    }

    public void init(ServletConfig config)
    {
      	String prefix = config.getServletContext().getRealPath("/"); 

        String logCfgPath = config.getInitParameter("log4j config file path");
        String webUCCfgPath = config.getInitParameter("webUCCompanion config file path");
        String webRootDir = config.getInitParameter("web application root dir");
        System.out.println("Now begin to initialize service module...");
        System.out.println("the log4j config file path is: " + logCfgPath);
        System.out.println("the webUCCompanion config file path is:" + webUCCfgPath);
        System.out.println("web application root directory is: " + webRootDir);
        System.out.println("webRootDir: " + prefix+logCfgPath);
        PropertyConfigurator.configure(prefix+logCfgPath);
        Utility.doLog(0, "ThirdPartyServerApp", "");
        Utility.doLog(0, "ThirdPartyServerApp", "------------------Web UC Companion initializating------------");
        Utility.doLog(0, "ThirdPartyServerApp", "");
        Utility.doLog(0, "ThirdPartyServerApp", "Log service initialized.");
        ServiceData.setWebRootDir(webRootDir);
        try
        {
            ReadConfigV2(prefix+webUCCfgPath);
            Utility.doLog(0, "ThirdPartyServerApp", "Read config finished.");
        }
        catch(Exception ex1)
        {
            Utility.doLog(3, "ThirdPartyServerApp", "Read config WebUCCfong.xml error:" + ex1.toString() + ", stop application initializing procession.");
            return;
        }
        ServiceData.msgQueue = new MessageQueue(false, -1);
        Utility.doLog(0, "ThirdPartyServerApp", "SMS send queue created.");
        RegisterManager.init();
        CTDManager.init();
        CTCManager.init();
        SMSManager.init();
        try
        {
            ServiceData.ctdObjPool = new ObjectPool(ServiceData.getCtdObjPoolSize(), false, com.jiangh.webuc.data.CTDObject.class, "CTD");
            ServiceData.ctcObjPool = new ObjectPool(ServiceData.getCtcObjPoolSize(), false, com.jiangh.webuc.data.CTCObject.class, "CTC");
        }
        catch(Exception ex)
        {
            Utility.doLog(3, "ThirdPartyServerApp", "Object Pool initialize failed:" + ex.toString());
        }
        System.out.println("succeed");
        Utility.doLog(0, "ThirdPartyServerApp", "Service module initialize succeed.");
        Utility.doLog(0, "ThirdPartyServerApp", "------------------Web UC Companion initializated------------");
        Utility.doLog(0, "ThirdPartyServerApp", "");
    }

    private void ReadConfig(String cfgFilePath)
        throws Exception
    {
        InputSource xmlSource = new InputSource(cfgFilePath);
        DOMParser parser = new DOMParser();
        parser.parse(xmlSource);
        Document xmlDocument = parser.getDocument();
        Node root = xmlDocument.getDocumentElement();
        NodeIterator ni = ((DocumentTraversal)xmlDocument).createNodeIterator(root, -1, null, false);
        Utility.doLog(0, "ThirdPartyServerApp", "Now reading WebUCConfig.xml for application config data:");
        if((ni = getNodeIteratorByName(ni, "CTDPool")) != null)
        {
            if((ni = getNodeIteratorByName(ni, "PoolSize")) != null)
            {
                String value = getTextNodeValue(ni);
                ServiceData.setCtdObjPoolSize(Integer.parseInt(value));
                Utility.doLog(0, "ThirdPartyServerApp", "CTD pool size:" + Integer.parseInt(value));
            }
            if((ni = getNodeIteratorByName(ni, "ObjReleaseTime")) != null)
            {
                String value = getTextNodeValue(ni);
                ServiceData.setCtdObjRelDelay(Long.parseLong(value));
                Utility.doLog(0, "ThirdPartyServerApp", "CTD object release delay time:" + Long.parseLong(value));
            }
        }
        if((ni = getNodeIteratorByName(ni, "CTCPool")) != null)
        {
            if((ni = getNodeIteratorByName(ni, "PoolSize")) != null)
            {
                String value = getTextNodeValue(ni);
                ServiceData.setCtcObjPoolSize(Integer.parseInt(value));
                Utility.doLog(0, "ThirdPartyServerApp", "CTC pool size:" + Integer.parseInt(value));
            }
            if((ni = getNodeIteratorByName(ni, "ObjReleaseTime")) != null)
            {
                String value = getTextNodeValue(ni);
                ServiceData.setCtcObjRelDelay(Long.parseLong(value));
                Utility.doLog(0, "ThirdPartyServerApp", "CTC object release delay time:" + Long.parseLong(value));
            }
        }
        if((ni = getNodeIteratorByName(ni, "SMSSendThread")) != null)
        {
            if((ni = getNodeIteratorByName(ni, "ThreadNum")) != null)
            {
                String value = getTextNodeValue(ni);
                ServiceData.setSmsSendThreadNum(Integer.parseInt(value));
                Utility.doLog(0, "ThirdPartyServerApp", "SMS send thread number:" + Integer.parseInt(value));
            }
            if((ni = getNodeIteratorByName(ni, "SendOprInterval")) != null)
            {
                String value = getTextNodeValue(ni);
                ServiceData.setSmsSendOprInterval(Long.parseLong(value));
                Utility.doLog(0, "ThirdPartyServerApp", "SMS send operation interval time in thread:" + Long.parseLong(value));
            }
        }
    }

    private void ReadConfigV2(String cfgFilePath)
        throws Exception
    {
        InputSource xmlSource = null;
        DOMParser parser = null;
        Document xmlDocument = null;
        Node root = null;
        NodeIterator ni = null;
        Node tempNode = null;
        try
        {
            xmlSource = new InputSource(cfgFilePath);
            parser = new DOMParser();
            parser.parse(xmlSource);
            xmlDocument = parser.getDocument();
            root = xmlDocument.getDocumentElement();
            ni = ((DocumentTraversal)xmlDocument).createNodeIterator(root, -1, null, false);
            Utility.doLog(0, "ThirdPartyServerApp", "Now reading WebUCConfig.xml for application config data:");
            if((ni = getNodeIteratorByName(ni, "InterfaceAccess")) != null)
            {
                String value = getTextNodeValue(ni);
                ServiceData.setInfAccessMode(value);
                Utility.doLog(0, "ThirdPartyServerApp", "The third party application access mode:" + value);
            }
            if((tempNode = getNodeByName(ni, "CTDPool")) != null)
            {
                String attrValue = getAttributeOfNode(tempNode, "functionFlag");
                if(attrValue != null && attrValue.trim() != null)
                {
                    ServiceData.setIsCTDPoolFunction(Boolean.valueOf(attrValue).booleanValue());
                    Utility.doLog(0, "ThirdPartyServerApp", "The CTD object pool function flag:" + attrValue);
                }
                if((ni = getNodeIteratorByName(ni, "PoolSize")) != null)
                {
                    String value = getTextNodeValue(ni);
                    ServiceData.setCtdObjPoolSize(Integer.parseInt(value));
                    Utility.doLog(0, "ThirdPartyServerApp", "CTD pool size:" + Integer.parseInt(value));
                }
                if((ni = getNodeIteratorByName(ni, "ObjReleaseTime")) != null)
                {
                    String value = getTextNodeValue(ni);
                    ServiceData.setCtdObjRelDelay(Long.parseLong(value));
                    Utility.doLog(0, "ThirdPartyServerApp", "CTD object release delay time:" + Long.parseLong(value));
                }
                if((tempNode = getNodeByName(ni, "daemonThread")) != null)
                {
                    attrValue = getAttributeOfNode(tempNode, "functionFlag");
                    if(attrValue != null && attrValue.trim() != null)
                    {
                        ServiceData.setIsCTDDaemonThreadFunction(Boolean.valueOf(attrValue).booleanValue());
                        Utility.doLog(0, "ThirdPartyServerApp", "The CTD object pool daemon thread function flag:" + attrValue);
                    }
                    if((ni = getNodeIteratorByName(ni, "daemonInterval")) != null)
                    {
                        String value = getTextNodeValue(ni);
                        ServiceData.setCtdDaemonThreadInterval(Long.parseLong(value));
                        Utility.doLog(0, "ThirdPartyServerApp", "CTD object pool daemon thread active interval:" + Long.parseLong(value));
                    }
                }
            }
            if((tempNode = getNodeByName(ni, "CTCPool")) != null)
            {
                String attrValue = getAttributeOfNode(tempNode, "functionFlag");
                if(attrValue != null && attrValue.trim() != null)
                {
                    ServiceData.setIsCTCPoolFunction(Boolean.valueOf(attrValue).booleanValue());
                    Utility.doLog(0, "ThirdPartyServerApp", "The CTC object pool funcation flag:" + attrValue);
                }
                if((ni = getNodeIteratorByName(ni, "PoolSize")) != null)
                {
                    String value = getTextNodeValue(ni);
                    ServiceData.setCtcObjPoolSize(Integer.parseInt(value));
                    Utility.doLog(0, "ThirdPartyServerApp", "CTC pool size:" + Integer.parseInt(value));
                }
                if((ni = getNodeIteratorByName(ni, "ObjReleaseTime")) != null)
                {
                    String value = getTextNodeValue(ni);
                    ServiceData.setCtcObjRelDelay(Long.parseLong(value));
                    Utility.doLog(0, "ThirdPartyServerApp", "CTC object release delay time:" + Long.parseLong(value));
                }
                if((tempNode = getNodeByName(ni, "daemonThread")) != null)
                {
                    attrValue = getAttributeOfNode(tempNode, "functionFlag");
                    if(attrValue != null && attrValue.trim() != null)
                    {
                        ServiceData.setIsCTCDaemonThreadFunction(Boolean.valueOf(attrValue).booleanValue());
                        Utility.doLog(0, "ThirdPartyServerApp", "The CTC object pool daemon thread function flag:" + attrValue);
                    }
                    if((ni = getNodeIteratorByName(ni, "daemonInterval")) != null)
                    {
                        String value = getTextNodeValue(ni);
                        ServiceData.setCtdDaemonThreadInterval(Long.parseLong(value));
                        Utility.doLog(0, "ThirdPartyServerApp", "CTD object pool daemon thread active interval:" + Long.parseLong(value));
                    }
                }
            }
            if((tempNode = getNodeByName(ni, "SMSSend")) != null && (tempNode = getNodeByName(ni, "SMSSendThread")) != null)
            {
                String attrValue = getAttributeOfNode(tempNode, "functionFlag");
                if(attrValue != null && attrValue.trim() != null)
                {
                    ServiceData.setIsSMSSendThreadFunction(Boolean.valueOf(attrValue).booleanValue());
                    Utility.doLog(0, "ThirdPartyServerApp", "The SMS send thread function flag:" + attrValue);
                }
                if((ni = getNodeIteratorByName(ni, "ThreadNum")) != null)
                {
                    String value = getTextNodeValue(ni);
                    ServiceData.setSmsSendThreadNum(Integer.parseInt(value));
                    Utility.doLog(0, "ThirdPartyServerApp", "SMS send thread number:" + Integer.parseInt(value));
                }
                if((ni = getNodeIteratorByName(ni, "SendOprInterval")) != null)
                {
                    String value = getTextNodeValue(ni);
                    ServiceData.setSmsSendOprInterval(Long.parseLong(value));
                    Utility.doLog(0, "ThirdPartyServerApp", "SMS send operation interval time in thread:" + Long.parseLong(value));
                }
            }
        }
        catch(Exception ex)
        {
            ni = null;
            root = null;
            xmlDocument = null;
            parser = null;
            xmlSource = null;
            tempNode = null;
            throw new Exception(ex.toString());
        }
    }

    private NodeIterator getNodeIteratorByName(NodeIterator nI, String nodeName)
        throws Exception
    {
        Node node;
        while((node = nI.nextNode()) != null) 
            if(node.getNodeName().equals(nodeName))
                return nI;
        throw new Exception("The config item " + nodeName + " not found!");
    }

    private Node getNodeByName(NodeIterator nI, String nodeName)
        throws Exception
    {
        Node node;
        while((node = nI.nextNode()) != null) 
            if(node.getNodeName().equals(nodeName))
                return node;
        throw new Exception("The config item " + nodeName + " not found!");
    }

    private String getTextNodeValue(NodeIterator nI)
        throws Exception
    {
        Node node = nI.nextNode();
        if(node != null && node.getNodeType() == 3)
            return node.getNodeValue();
        else
            throw new Exception("Can not read the value of config item!");
    }

    private String getAttributeOfNode(Node node, String attrName)
        throws Exception
    {
        if(node.hasAttributes())
        {
            NamedNodeMap nnm = node.getAttributes();
            for(int i = 0; i < nnm.getLength(); i++)
            {
                Node _node = nnm.item(i);
                if(_node.getNodeName().equals(attrName))
                    return _node.getNodeValue();
            }

        }
        throw new Exception("The attribute " + attrName + " not found in node " + node.getNodeName());
    }

    private static final String CLASS_DESC = "ThirdPartyServerApp";
    public static Logger logger = Logger.getLogger("webuc");

}