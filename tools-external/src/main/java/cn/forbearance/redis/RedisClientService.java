package cn.forbearance.redis;

/**
 * @author cristina
 */
public class RedisClientService {

//    private final CompletableFuture<Object> responseCallback;
//
//    private ChannelFuture channelFuture;
//
//    public static final AttributeKey<CompletableFuture<Object>> RES_COMPLETABLE_FUTURE = AttributeKey.valueOf("res_completable_future");
//
//    public RedisClientService() {
//        this.responseCallback = new CompletableFuture<Object>();
//    }
//
//    public CompletableFuture<Object> getResponseCallback() {
//        return responseCallback;
//    }
//
//    public ChannelFuture getChannelFuture() {
//        return channelFuture;
//    }
//
//    @Override
//    public ChannelFuture call() {
//        Bootstrap client = new Bootstrap();
//        client.group(new NioEventLoopGroup());
//        client.channel(NioSocketChannel.class);
//        ChannelFuture future = client.handler(new ChannelInitializer<SocketChannel>() {
//            @Override
//            protected void initChannel(SocketChannel ch) {
//                ch.pipeline().addLast(new RedisDecoder());
//                ch.pipeline().addLast(new RedisBulkStringAggregator());
//                ch.pipeline().addLast(new RedisArrayAggregator());
//                ch.pipeline().addLast(new RedisEncoder());
//                ch.pipeline().addLast(new ChannelDuplexHandler() {
//
//                    @Override
//                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//                        String[] commands = ((String) msg).split("\\s+");
//                        List<RedisMessage> children = new ArrayList<>(commands.length);
//                        for (String cmdString : commands) {
//                            children.add(new FullBulkStringRedisMessage(ByteBufUtil.writeUtf8(ctx.alloc(), cmdString)));
//                        }
//                        RedisMessage request = new ArrayRedisMessage(children);
//                        ctx.write(request, promise);
//                    }
//
//                    @Override
//                    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//                        RedisMessage buf = (RedisMessage) msg;
//                        responseCallback.complete(aggregatedRedisResponse(buf));
//                        ReferenceCountUtil.release(buf);
//                    }
//
//                    @Override
//                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//                        ctx.close();
//                    }
//                });
//            }
//        }).connect(new InetSocketAddress("localhost", 6379)).syncUninterruptibly();
//
//        this.channelFuture = future;
//
//        // 传递 CompletableFuture 用于同步接收结果, 目前设定是 Channel : CompletableFuture 一对一绑定.
//        future.channel().attr(RES_COMPLETABLE_FUTURE).set(this.responseCallback);
//        return future;
//    }
//
//    private static Object aggregatedRedisResponse(RedisMessage msg) {
//        if (msg instanceof SimpleStringRedisMessage) {
//            return ((SimpleStringRedisMessage) msg).content();
//        } else if (msg instanceof ErrorRedisMessage) {
//            return ((ErrorRedisMessage) msg).content();
//        } else if (msg instanceof IntegerRedisMessage) {
//            return ((IntegerRedisMessage) msg).value();
//        } else if (msg instanceof FullBulkStringRedisMessage) {
//            return getString((FullBulkStringRedisMessage) msg);
//        } else if (msg instanceof ArrayRedisMessage) {
//            for (RedisMessage child : ((ArrayRedisMessage) msg).children()) {
//                aggregatedRedisResponse(child);
//            }
//        } else {
//            throw new CodecException("unknown message type: " + msg);
//        }
//        return null;
//    }
//
//    private static String getString(FullBulkStringRedisMessage msg) {
//        if (msg.isNull()) {
//            return "(null)";
//        }
//        return msg.content().toString(CharsetUtil.UTF_8);
//    }
//
//    public static void main(String[] args) throws Exception {
//        NettyClientPool nettyClientPool = NettyClientPool.getInstance();
//        Channel channel = nettyClientPool.getChannel(new InetSocketAddress("localhost", 6379));
//
////        RedisClientService redisClientService = new RedisClientService();
////        Future<ChannelFuture> future = Executors.newFixedThreadPool(2).submit(redisClientService);
////        future.get();
//        RedisCommandServiceImpl<Object, Object> service = new RedisCommandServiceImpl<Object, Object>(channel);
//
//        service.opsForValue().get("name");
//        System.out.println();
//        System.out.println("name: " + service.opsForValue().get("name"));
//    }
}
