/**
 * 
 */
package com.microcard.msg;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
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
@MsgClassAnnotation(className={TxtMsg.class,ScanEventMsg.class,SubscribeEventMsg.class,UnsubscribeEventMsg.class,MenuClickMsg.class,
		                       MenuViewMsg.class,TxtMsg.class,LocationEventMsg.class})
public class MsgFactory {
	
	private static Logger log = Logger.getOperLogger();

	private static Class<? extends Msg>[] classes = null;
	
	private static HashMap<Class<? extends Msg>,List<Entry>> msgMap = new HashMap<Class<? extends Msg>,List<Entry>>();
	
	static {
		try{
			MsgClassAnnotation msgClassAnnotation = MsgFactory.class.getAnnotation(MsgClassAnnotation.class);
			classes = msgClassAnnotation.className();	
			
			for(Class<? extends Msg> msgClass : classes) {
				
				try{
					if(msgClass.isAnnotationPresent(MsgTypeAnnotation.class)) {
						MsgTypeAnnotation msgTypeAnnotation = msgClass.getAnnotation(MsgTypeAnnotation.class);
						MsgType msgType = msgTypeAnnotation.msg();
						EventType eventType = msgTypeAnnotation.event();
						
						//根据类定义的类型，添加到map中，用于在解析xml时对应属于那个类
						List<Entry> list = new ArrayList<Entry>();
						list.add(new Entry("MsgType",msgType.name()));
						if(eventType != EventType.NULL)
							list.add(new Entry("Event",eventType.name()));
						
						msgMap.put(msgClass, list);			
					}
					
				}catch(Exception e) {
					log.error(e, " can't get " +msgClass.getName()+ "'s @MsgTypeAnnotation");
					continue;
				}
				
			}
			
		}catch(Exception e) {
			
			log.error(e, " can't get MsgFactory class's @MsgClassAnnotation");
		}
		
	}
	
	private MsgFactory() {
		
	}
	
	/**
	 * 根据微信发过来的xml内容转换成实体类
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static Msg createMsg(String content) throws Exception {
		
		Document doc;
		try {
			doc = XMLHelper.parse(content);
		    Node xmlNode = doc.getChildNodes().item(0);
		    
		    Msg msg = createMsg((Element)xmlNode);
		    
		    if(msg == null)
		    	throw new Exception("can't create Msg instance according to the xml content :" + content);
		    
		    NodeList list = xmlNode.getChildNodes();
		    for(int i = 0 ; i < list.getLength() ; i++) {
		    	setNodeValue(msg,list.item(i));
		    }
		    return msg;
		} catch (SAXException | IOException | ParserConfigurationException e) {
			log.error(e, "pasre " + content + " error!");
			throw new Exception("pasre " + content + " error!",e);
		}
	}
	
	/**
	 * 根据微信发过来的xml内容转换成实体类
	 * @param element
	 * @return
	 */
	public static Msg createMsg(Element element) {
		
		for(Class<? extends Msg> msgClass : msgMap.keySet()) {
			
			List<Entry> entries = msgMap.get(msgClass);
			
			boolean isMatch = true;
			
			for(Entry entry : entries) {
				
				try {
					
					Node node = element.getElementsByTagName(entry.getName()).item(0);
					if(!entry.getValue().equals(node.getTextContent())) {
						isMatch = false;
						break;
					}
				}catch(Exception e) {
					log.error(e, " current Element not belong to " + msgClass.getName());
					isMatch = false;
					break;
				}
				
			}
			
			if(isMatch) {
				try {
					return msgClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					log.error(e, "can't initial a class + " + msgClass.getName());
				}
			}
			
		}
		
		return null;
	}
	
	/**
	 * 获取该类，以及父类的field
	 * @param msgClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Field> getClassField(Class<? extends Msg> msgClass) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = msgClass.getDeclaredFields();
		for(Field field : fields) {
			list.add(field);
		}
		Class<?> c = msgClass.getSuperclass();
		if(isChildFromMsgClass(c))
			list.addAll(getClassField((Class<? extends Msg>)c));
		
		return list;
	}
	
	private static boolean isChildFromMsgClass(Class<?> msgClass) {
		
		if(msgClass == null) return false;
		
		if(msgClass.isAssignableFrom(Msg.class))
			return true;

		return isChildFromMsgClass(msgClass.getSuperclass());
	}
	
	/**
	 * 根据node的名称找到Field的MsgFieldAnnotation中标识的名称<br>
	 * 然后把node的value设置到找到的field中
	 * @param msg
	 * @param node
	 * @throws Exception
	 */
	private static void setNodeValue(Msg msg,Node node) throws Exception  {
		
		List<Field> fields = getClassField(msg.getClass());
		
		for(Field field : fields) {
			
			if(field.isAnnotationPresent(MsgFieldAnnotation.class)) {
				MsgFieldAnnotation annotation = field.getAnnotation(MsgFieldAnnotation.class);
				String value = annotation.value();
				if(value.equals(node.getNodeName())) {
					try {//这里注意Msg实体类，以及其子类目前只能按照String类型设置，后续根据根据需要可根据method parameter类型进行转换
						Method method = msg.getClass().getMethod(toMethodName("set",field.getName()),String.class);
						method.invoke(msg, node.getTextContent());
						return;
					} catch (IllegalArgumentException
							| IllegalAccessException | DOMException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
						throw new Exception("can't set " + msg.getClass().getName() + "'s field [" + field.getName() +"]'s value " + node.getTextContent(),e);
					}
				}				
			}

		}
		log.warn("can't find msg field by node name: " + node.getNodeName());
	}
	
	private static String toMethodName(String suffix,String field) {
		
		return suffix + field.substring(0,1).toUpperCase() + field.substring(1);
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
			
			if(msg instanceof TxtMsg){
				Element content = doc.createElement("Content");
				content.appendChild(doc.createTextNode(((TxtMsg)msg).getContent()));
				root.appendChild(content);	
			}


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
	
	private static class Entry {
		
		private String name;
		private String value;
		
		public Entry(String name , String value) {
			
			this.name = name;
			this.value = value;
		}
		
		public String getName() {
			
			return this.name;
		}
		
		public String getValue() {
			
			return this.value;
		}
	}
	
}
