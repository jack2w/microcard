/**
 * 
 */
package com.microcard.msg.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.microcard.log.Logger;

/**
 * @author jack
 *
 */
public class TwoDimensionImgHandler extends SimpleChannelInboundHandler<HttpObject> {

	private Logger log = Logger.getMsgLogger();
	private FileOutputStream stream = null;
	private File file;
	private int cum = 0;
	
	public TwoDimensionImgHandler(File file) throws FileNotFoundException {
		this.file = file; 
		stream = new FileOutputStream(file);
	}
	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
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
	            stream.write(content.content().array(), content.content().readerIndex(), content.content().readableBytes());
	            log.info("write stream to file " + file.getName() + " " + ++cum);
	            if (content instanceof LastHttpContent) {
	            	stream.close();
	            	log.info("} END OF CONTENT");
	            }
	        }
		
	}
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	log.error(cause, "write two dimensional code to file " + file.getName() + " error!");
        ctx.close();
    }
}
