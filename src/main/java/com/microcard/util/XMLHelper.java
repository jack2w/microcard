/**
 * 
 */
package com.microcard.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.microcard.msg.Msg;
import com.microcard.msg.processor.AbstractMsgProcessFactory;

/**
 * @author jack
 *
 */
public class XMLHelper {

	private static DocumentBuilder builder;

	private static Transformer trans;

	private XMLHelper() {

	}

	public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		if (builder == null) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			builder = factory.newDocumentBuilder();
		}
		return builder;
	}

	public static Transformer getTransformer() throws TransformerConfigurationException {
		if (trans == null) {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			trans = tFactory.newTransformer();
		}
		return trans;
	}

	public static String xmlToString(Document xml)throws TransformerConfigurationException, TransformerException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Transformer transformer = getTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.transform(new DOMSource(xml), new StreamResult(bos));
		return bos.toString();
	}

	public static String xmlFileToString(File file) throws SAXException, IOException, ParserConfigurationException,TransformerConfigurationException, TransformerException {
		Document doc = null;
		doc = getDocumentBuilder().parse(file);
		return xmlToString(doc);
	}

	public static Document parse(String text) throws SAXException, IOException,ParserConfigurationException {
		return getDocumentBuilder().parse(new InputSource(new StringReader(text)));
	}

	public static void main(String arg[]) {
		StringBuffer str = new StringBuffer();
		str.append("<xml><ToUserName><![CDATA[gh_1c25e0b75543]]></ToUserName>").append('\n');
		str.append("<FromUserName><![CDATA[oOrfwjk7S2-meQ6I51v92upnCGYM]]></FromUserName>").append('\n');
		str.append("<CreateTime>1395157159</CreateTime>").append('\n');
		str.append("<MsgType><![CDATA[text]]></MsgType>").append('\n');
		str.append("<Content><![CDATA[??????]]></Content>").append('\n');
		str.append("<MsgId>5992154370885374638</MsgId>").append('\n');
		str.append("</xml>");
		Msg msg = Msg.xmlToMsg("<xml><ToUserName>oOrfwjk7S2-meQ6I51v92upnCGYM</ToUserName><FromUserName>MobileLife</FromUserName><CreateTime>1395158233</CreateTime><MsgType>text</MsgType><Content>Weclome to Mobile Life. (message sent from wechatdm.cloudapp.net) --??????</Content><MsgId>5992159808313971406</MsgId></xml>");
		String responseMsg = AbstractMsgProcessFactory.getProcessorFactory(msg).getMsgProcessor().proccess(msg);
		System.out.println(responseMsg);
	}
}
