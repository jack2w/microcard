/**
 * 
 */
package com.microcard.msg;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author jack
 *
 */
public class EventMsg extends Msg {

	private EventType event;

	/**
	 * @return the event
	 */
	public EventType getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(EventType event) {
		this.event = event;
	}

	/* (non-Javadoc)
	 * @see com.ilive.weixin.msg.Msg#addBeanAttribute(org.w3c.dom.NodeList)
	 */
	@Override
	protected Msg addBeanAttribute(NodeList list) {
	    for(int i = 0 ; i < list.getLength() ; i++) {
	    	Node node = list.item(i);
	    	if("Event".equals(node.getNodeName())) {
	    		String eventType = node.getTextContent();
	    		
	    		if(EventType.valueOf(eventType) == EventType.subscribe || EventType.valueOf(eventType) == EventType.unsubscribe ){

	    			this.setEvent(EventType.valueOf(eventType));	
	    		}
	    	}
	    }
	    
	    return this;
	}
	
	
}
