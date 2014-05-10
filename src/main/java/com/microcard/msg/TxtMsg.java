/**
 * 
 */
package com.microcard.msg;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author jack
 *
 */
public class TxtMsg extends Msg {

	private String content;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see com.ilive.weixin.parse.Msg#addAttribute(org.w3c.dom.NodeList)
	 */
	@Override
	protected Msg addBeanAttribute(NodeList list) {
	    for(int i = 0 ; i < list.getLength() ; i++) {
	    	Node node = list.item(i);
	    	if("Content".equals(node.getNodeName())) {
	    		this.setContent(node.getTextContent());
	    	}
	    }
	    return this;
	}

	/* (non-Javadoc)
	 * @see com.ilive.weixin.parse.Msg#addAttribute(org.w3c.dom.Document, org.w3c.dom.Element)
	 */
	@Override
	protected void addXMLAttribute(Document doc, Element root) {
		Element content = doc.createElement("Content");
		content.appendChild(doc.createTextNode(this.content));
		root.appendChild(content);
	}
	
	
	
}
