package com.winson.apibasedemo.net

import android.os.Bundle
import android.util.Log
import android.view.View
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.sctp.nio.NioSctpChannel
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.MessageToByteEncoder
import io.netty.util.concurrent.DefaultThreadFactory
import java.net.InetSocketAddress
import java.util.*
import kotlin.concurrent.thread

/**
 * @date 2020/4/2
 * @author Winson
 */
class NettyActivity : BaseActivity() {

    override fun onBind(savedInstanceState: Bundle?) {
        super.onBind(savedInstanceState)
        setContentView(R.layout.act_netty)

        findViewById<View>(R.id.connect).setOnClickListener {
            thread {
                connect()
            }
        }

    }

    private fun connect() {
        Log.d("TAG", "netty connect start ---------> ")
        val bootstrap = Bootstrap()
        bootstrap.group(NioEventLoopGroup(1, DefaultThreadFactory("winson1")))
        bootstrap.option(ChannelOption.TCP_NODELAY, true)
        bootstrap.channel(NioSocketChannel::class.java)
        bootstrap.handler(NettyClientInitializer())

        try {
            val future: ChannelFuture =
                bootstrap.connect(InetSocketAddress("172.16.2.20", 19040)).sync()
            future.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Log.d("TAG", "netty connect exception ---------> ")
        }
        Log.d("TAG", "netty connect end ---------> ")

    }

    class NettyClientInitializer : ChannelInitializer<SocketChannel>() {

        override fun initChannel(ch: SocketChannel?) {
            val pipeline = ch!!.pipeline();
            pipeline.addLast("Encoder", Encoder())
            pipeline.addLast("Decoder", Decoder())
            pipeline.addLast(NettyClientHandler())
        }

    }

    class NettyClientHandler : ChannelInboundHandlerAdapter() {

    }

    class Encoder : MessageToByteEncoder<Any>() {
        override fun encode(p0: ChannelHandlerContext?, p1: Any?, p2: ByteBuf?) {

        }

    }

    class Decoder : ByteToMessageDecoder() {
        override fun decode(p0: ChannelHandlerContext?, p1: ByteBuf?, p2: MutableList<Any>?) {

        }

    }

}