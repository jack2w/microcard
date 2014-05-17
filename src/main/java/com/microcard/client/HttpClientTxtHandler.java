/**
 * 
 */
package com.microcard.client;

import com.microcard.log.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

/**
 * @author jack
 *
 */
public abstract class HttpClientTxtHandler extends SimpleChannelInboundHandler<HttpObject> {
	private Logger log = Logger.getMsgLogger();
	private StringBuffer buffer = new StringBuffer();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg)
			throws Exception {
		 if (msg instanceof HttpResponse) {
	            HttpResponse response = (HttpResponse) msg;

	            log.info("from: " + response.toString());
	            log.info("STATUS: " + response.getStatus());
	            log.info("VERSION: " + response.getProtocolVersion());

	            if (!response.headers().isEmpty()) {
	                for (String name: response.headers().names()) {
	                    for (String value: response.headers().getAll(name)) {
	                    	log.info("HEADER: " + name + " = " + value);
	                    }
	                }
	            }

	            if (HttpHeaders.isTransferEncodingChunked(response)) {
	            	log.info("CHUNKED CONTENT {");
	            } else {
	            	log.info("CONTENT {");
	            }
	        }
	        if (msg instanceof HttpContent) {
	            HttpContent content = (HttpContent) msg;
	            String txt = content.content().toString(CharsetUtil.UTF_8);
	            buffer.append(txt);
	            log.info(txt);
	            if (content instanceof LastHttpContent) {
	            	process(buffer.toString());
	            	log.info("} END OF CONTENT");
	            }
	        }
		
	}
	
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.error(cause, "Http Handle Error!");
        ctx.close();
    }
    
    public abstract void process(String msg);
    
}
