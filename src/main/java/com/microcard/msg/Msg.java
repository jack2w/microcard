/**
 * 
 */
package com.microcard.msg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microcard.log.Logger;
import com.microcard.msg.processor.IMsgProcessor;
import com.microcard.msg.processor.MsgProcessorAnnotation;

/**
 * @author jack
 *
 */
public class Msg {
	
	private static Logger log = Logger.getOperLogger();

	@MsgFieldAnnotation("ToUserName")
	private String toUserName;
	
	@MsgFieldAnnotation("FromUserName")
	private String fromUserName;
	
	@MsgFieldAnnotation("CreateTime")
	private String createTime;
	
	@MsgFieldAnnotation("MsgType")
	private String msgType;
	
	//private MsgType msgType;

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

	/**
	 * @return the msgType
	 */
	public MsgType getMsgType() {
		MsgTypeAnnotation msgType = this.getClass().getAnnotation(MsgTypeAnnotation.class);
		return msgType.msg();
	}	
	
	public void setMsgType(String msgType) {
		
		this.msgType = msgType;
	}
	
	/**
	 * 
	 * @return
	 */
	public EventType getEventType() {
		MsgTypeAnnotation msgType = this.getClass().getAnnotation(MsgTypeAnnotation.class);
		return msgType.event();
	}
	
	/**
	 * 返回商铺消息处理器
	 * @return
	 */
	public IMsgProcessor getShopMsgProcessor() {
		if(this.getClass().isAnnotationPresent(MsgProcessorAnnotation.class)) {
			
			MsgProcessorAnnotation annotation = this.getClass().getAnnotation(MsgProcessorAnnotation.class);
			Class<? extends IMsgProcessor> processorClass = annotation.ShopMsgClass();
			
			try {
				return processorClass.newInstance();
			} catch (Exception e) {
				log.error(e, "can't initial a processor class: " + processorClass.getName());
			}
		}
		return null;
	}
	
	public IMsgProcessor getUserMsgProcessor() {
		if(this.getClass().isAnnotationPresent(MsgProcessorAnnotation.class)) {
			
			MsgProcessorAnnotation annotation = this.getClass().getAnnotation(MsgProcessorAnnotation.class);
			Class<? extends IMsgProcessor> processorClass = annotation.UserMsgClass();
			
			try {
				return processorClass.newInstance();
			} catch (Exception e) {
				log.error(e, "can't initial a processor class: " + processorClass.getName());
			}
		}
		return null;	
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ToUserName:" + this.toUserName);
		buffer.append("FromUserName:" + this.fromUserName);
		buffer.append("CreateTime:" + this.createTime);
		buffer.append("MsgType:" + this.getMsgType());
		buffer.append("MsgType:" + this.getEventType());
		return buffer.toString();
	}
}
