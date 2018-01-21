package com.tstyle.client.tio;

import com.tstyle.client.ProtocolHandlerFactory;
import com.tstyle.constants.Constants;
import com.tstyle.listener.AioCommonListenerImpl;
import com.tstyle.protocol.TStyleChannelContext;
import com.tstyle.protocol.packet.MessagePacket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.Node;

/**
 * tio客户端
 * 
 * @author weichanghuan
 *
 */
@Component
public class TStyleClient {

    private static final Logger logger = LoggerFactory.getLogger(TStyleClient.class);

    @Value("${tio.client.host}")
    private static String host;

    @Value("${tio.client.port}")
    private static int port;

    @Value("${protocol.type}")
    private String protocolType;

    @Autowired
    private TStyleClientAioHandler tStyleClientAioHandler;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    // 服务器节点
    public static Node serverNode = new Node(host, port);

    // handler, 包括编码、解码、消息处理
    public static ClientAioHandler aioClientHandler = new TStyleClientAioHandler();

    // 事件监听器，可以为null，
    public static ClientAioListener aioListener = new AioCommonListenerImpl();

    // 断链后自动连接的，不想自动连接请设为null
    private static ReconnConf reconnConf = new ReconnConf(5000L);

    // 一组连接共用的上下文对象
    public static ClientGroupContext clientGroupContext = new ClientGroupContext(aioClientHandler, aioListener, reconnConf);
    // 一组连接共用的上下文对象
    public static ChannelContext channelContext = new TStyleChannelContext(clientGroupContext, null);

    public static AioClient aioClient = null;
    public static ClientChannelContext clientChannelContext = null;

    /**
     * 连接状态
     */
    private volatile boolean isConnected = false;

    /**
     * 启动
     * 
     */
    @PostConstruct
    public void start() {
        if (ProtocolHandlerFactory.PROTOCOL_TYPE_SOCKET.equals(protocolType)) {
            logger.info("tio client start");
            long start = System.currentTimeMillis();
            clientGroupContext.setHeartbeatTimeout(Constants.TIMEOUT);
            try {
                aioClient = new AioClient(clientGroupContext);
                clientChannelContext = aioClient.connect(serverNode);
                isConnected = true;
                logger.info("tio client end cost={}", System.currentTimeMillis() - start);
            } catch (Exception e) {
                logger.error("连接发生异常", e);
            }
        }

    }

    /**
     * 同步发送消息
     * 
     * @param request
     * @return
     */
    public MessagePacket syncSendMessage(MessagePacket request) {
        logger.debug("syncSendMessage start requestId:{}", request.getRequestId());
        long start = System.currentTimeMillis();
        Aio.synSend(clientChannelContext, request, Constants.SYNTIMEOUT);// 同步发送
        MessagePacket result = tStyleClientAioHandler.getResponse(request.getRequestId());
        logger.info("syncSendMessage end cost={},requestId:{}", System.currentTimeMillis() - start, result.getRequestId());
        return result;
    }

    /**
     * 异步发送消息
     * 
     * @param request
     * @return
     */
    public MessagePacket asyncSendMessage(MessagePacket request) {
        Aio.send(clientChannelContext, request);// 异步发送
        return tStyleClientAioHandler.getResponse(request.getRequestId());
    }

    @PreDestroy
    public void stop() {
        if (ProtocolHandlerFactory.PROTOCOL_TYPE_SOCKET.equals(protocolType)) {
            logger.info("destroy client resources");
            Aio.close(channelContext, "remark");
        }
    }

    public final boolean isConnected() {
        return isConnected;
    }

}
