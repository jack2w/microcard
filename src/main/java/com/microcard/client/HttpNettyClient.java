package com.microcard.client;

import static io.netty.buffer.Unpooled.copiedBuffer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.ClientCookieEncoder;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder;
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder.ErrorDataEncoderException;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.microcard.log.Logger;
import com.microcard.msg.handler.DefaultHttpHandler;
/**
 * @author jack
 *
 */
public class HttpNettyClient {
	
	private static Logger msgLog = Logger.getMsgLogger();
	//private static Logger operLog = Logger.getOperLogger();

    private final URI uri;
    
    private String scheme;
    
    private String host;
    
    private int port;
    
    private boolean ssl;
    

    public HttpNettyClient(URI uri) {
        this.uri = uri;
        
        scheme = uri.getScheme() == null? "http" : uri.getScheme();
        host = uri.getHost() == null? "localhost" : uri.getHost();
        port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
        	msgLog.error("send wrong url " + uri.toASCIIString());
            throw new IllegalArgumentException("url is illegal,should begin with http:// or https:// , now is " + uri.toASCIIString());
        }

        ssl = "https".equalsIgnoreCase(scheme);
    }
    
    public HttpNettyClient(String uri) throws URISyntaxException {
    	this(new URI(uri));
    }

    public void doGet(ChannelHandler handler) throws InterruptedException {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new HttpClientInitializer(ssl,handler));

            // Make the connection attempt.
            Channel ch = b.connect(host, port).sync().channel();

            // Prepare the HTTP request.
            HttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);

            // Set some example cookies.
            request.headers().set(
                    HttpHeaders.Names.COOKIE,
                    ClientCookieEncoder.encode(
                            new DefaultCookie("my-cookie", "foo"),
                            new DefaultCookie("another-cookie", "bar")));
            
            // Send the HTTP request.
            ch.writeAndFlush(request);
            msgLog.info("doGet() " + uri.toASCIIString());
            // Wait for the server to close the connection.
            ch.closeFuture().sync();
        } finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully();
        }
    }
    
    public void doPost(HttpClientTxtHandler handler,Map<String,String> params) throws InterruptedException, ErrorDataEncoderException {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new HttpClientInitializer(ssl,handler));
            
            // Make the connection attempt.
            Channel channel = b.connect(host, port).sync().channel();
            // Prepare the HTTP request.
            HttpRequest request =
                    new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString());
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
            
            HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if MINSIZE exceed
            HttpPostRequestEncoder bodyRequestEncoder = new HttpPostRequestEncoder(factory, request, false); // false not multipart
            StringBuffer content = null;
            
            if(params != null && params.keySet().size() >0) {
            	content = new StringBuffer();
                for(String key : params.keySet()) {
                	bodyRequestEncoder.addBodyAttribute(key, params.get(key));
                	content.append(key).append("=").append(params.get(key)).append("&");
                }
            }
            
            request = bodyRequestEncoder.finalizeRequest();
            
            // send request
            channel.write(request);

            // test if request was chunked and if so, finish the write
            if (bodyRequestEncoder.isChunked()) {
                // could do either request.isChunked()
                // either do it through ChunkedWriteHandler
                channel.writeAndFlush(bodyRequestEncoder).awaitUninterruptibly();
            }  else {
                channel.flush();
            }
            
            msgLog.info("doPost() " + uri.toASCIIString() + " Content: " + 
                         content == null ? "" : content.toString().substring(0,content.toString().length()-1));
            
            channel.closeFuture().sync();
        }finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully();
        }
    }

    public void doPost(HttpClientTxtHandler handler,String content) throws InterruptedException, ErrorDataEncoderException {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new HttpClientInitializer(ssl,handler));
            
            // Make the connection attempt.
            Channel channel = b.connect(host, port).sync().channel();
            
            if(content == null)
            	content = "";
            
            ByteBuf buf = copiedBuffer(content, CharsetUtil.UTF_8);
            // Prepare the HTTP request.
            HttpRequest request =
                    new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString(),buf);
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.readableBytes());
            
            // send request
            channel.writeAndFlush(request);
            msgLog.info("doPost() " + uri.toASCIIString() + " Content: " + content);
            channel.closeFuture().sync();
        }finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully();
        }
    }
    
    
    public static void main(String[] args) {

            URI uri;
			try {
				uri = new URI("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxe6297940decb5dee&secret=85beed8c47ee619028b34303045f3e42");
				 new HttpNettyClient(uri).doGet(new DefaultHttpHandler());    		

			} catch (URISyntaxException | InterruptedException e) {
				e.printStackTrace();
			}
           
    }
}
