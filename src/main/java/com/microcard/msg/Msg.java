/**
 * 
 */
package com.microcard.msg;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.microcard.log.Logger;
import com.microcard.util.XMLHelper;

/**
 * @author jack
 *
 */
public class Msg {
	
	private static Logger log = Logger.getOperLogger();

	private String toUserName;
	
	private String fromUserName;
	
	private String createTime;
	
	private String msgId;
	
	private MsgType msgType;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		try{
			long msgCreateTime = Long.parseLong(createTime) * 1000L;  
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			this.createTime = format.format(new Date(msgCreateTime));			
		}catch(Exception e) {
			log.error(e,"convert date from weixin to date formate error!" + createTime);
			this.createTime = "";
		}

	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return the msgType
	 */
	public MsgType getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(MsgType msgType) {
		this.msgType = msgType;
	}
	
	public static Msg xmlToMsg(String content) {
		Msg msg = null;
		String toUser = null;
	    String fromUser = null;
	    String time = null;
	    String type = null;
	    String id = null;
		try {
			Document doc = XMLHelper.parse(content);
		    Node xmlNode = doc.getChildNodes().item(0);
		    NodeList list = xmlNode.getChildNodes();
		    for(int i = 0 ; i < list.getLength() ; i++) {
		    	Node node = list.item(i);
		    	node.getNodeName();
		    	if("ToUserName".equals(node.getNodeName())) {
		    		toUser = node.getTextContent();
		    	}else if("FromUserName".equals(node.getNodeName())) {
		    		fromUser = node.getTextContent();
		    	}else if("MsgType".equals(node.getNodeName())) {
		    		type = node.getTextContent();
		    	}else if("MsgId".equals(node.getNodeName())) {
		    		id = node.getTextContent();
		    	}else if("CreateTime".equals(node.getNodeName())) {
		    		time = node.getTextContent();
		    	}
		    }
		    
	    	if(MsgType.valueOf(type) == MsgType.text) {
	    		
	    		msg = new TxtMsg();
	    		msg = msg.addBeanAttribute(list);
	    	}else if(MsgType.valueOf(type) == MsgType.event) {
	    		
	    		msg = new EventMsg();
	    		msg = msg.addBeanAttribute(list);
	    	}
	    	
    		msg.setCreateTime(time);
    		msg.setFromUserName(fromUser);
    		msg.setMsgId(id);
    		msg.setMsgType(MsgType.valueOf(type));
    		msg.setToUserName(toUser);
		} catch (SAXException e) {
			log.error(e, "pasre " + msg + " error!");
		} catch (IOException e) {
			log.error(e, "pasre " + msg + " error!");
		} catch (ParserConfigurationException e) {
			log.error(e, "pasre " + msg + " error!");
		}		
		return msg;
	}
	
	protected Msg addBeanAttribute(NodeList list) {
		return null;//do nothing
	}
	
	protected void addXMLAttribute(Document doc,Element root) {
		
	}
	
	public static String msgToXml(Msg msg) {
		
		try {
			Document doc = XMLHelper.getDocumentBuilder().newDocument();
			Element root = doc.createElement("xml");
			doc.appendChild(root);
			
			Element toUser = doc.createElement("ToUserName");
			toUser.appendChild(doc.createTextNode(msg.getFromUserName()));
			root.appendChild(toUser);
			
			Element fromUser = doc.createElement("FromUserName");
			fromUser.appendChild(doc.createTextNode(msg.getToUserName()));
			root.appendChild(fromUser);
			
			Element time = doc.createElement("CreateTime");
			time.appendChild(doc.createTextNode(String.valueOf(System.currentTimeMillis() / 1000)));
			root.appendChild(time);
			
			Element type = doc.createElement("MsgType");
			type.appendChild(doc.createTextNode(msg.getMsgType().toString()));
			root.appendChild(type);
			
			msg.addXMLAttribute(doc,root);

			String xml = XMLHelper.xmlToString(doc);
			return xml.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
			
		} catch (ParserConfigurationException e) {
			log.error(e, "gen xml error!");
		} catch (TransformerConfigurationException e) {
			log.error(e, "gen xml error!");
		} catch (TransformerException e) {
			log.error(e, "gen xml error!");
		}
		return null;
	}
}
