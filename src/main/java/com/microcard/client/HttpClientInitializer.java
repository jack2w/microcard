/**
 * 
 */
package com.microcard.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author jack
 *
 */
public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {

    private final boolean ssl;
    
    private final ChannelHandler handler;

    public HttpClientInitializer(boolean ssl,ChannelHandler handler) {
        this.ssl = ssl;
        this.handler = handler;
    }
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline p = ch.pipeline();

        p.addLast("log", new LoggingHandler(LogLevel.INFO));
        // Enable HTTPS if necessary.
        if (ssl) {
            SSLEngine engine =
                SslContextFactory.getClientContext().createSSLEngine();
            engine.setUseClientMode(true);

            p.addLast("ssl", new SslHandler(engine));
        }

        p.addLast("codec", new HttpClientCodec());

        // Remove the following line if you don't want automatic content decompression.
        p.addLast("inflater", new HttpContentDecompressor());

        // Uncomment the following line if you don't want to handle HttpChunks.
        //p.addLast("aggregator", new HttpObjectAggregator(1048576));

        p.addLast("handler", handler);
	}

}
