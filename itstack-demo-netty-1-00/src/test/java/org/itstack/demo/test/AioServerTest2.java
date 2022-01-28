package org.itstack.demo.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.*;

public class AioServerTest2 {

    public final static int PORT = 7397;
    private AsynchronousServerSocketChannel serverSocketChannel;

    public AioServerTest2() throws IOException {
        serverSocketChannel = AsynchronousServerSocketChannel.open(AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10)).bind(new InetSocketAddress(PORT));
    }

    public void startWithFuture() throws Exception {
        while (true) {
            // loop receive client request
            Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
            AsynchronousSocketChannel socket = future.get();
            handleWithFuture(socket);
        }
    }

    public void handleWithFuture(AsynchronousSocketChannel channel) throws Exception {
        ByteBuffer readBuf = ByteBuffer.allocate(1024);
        readBuf.clear();

        while (true) {
            // get sure read finish, time out can avoid dos attack, if client don't send message ,then do time out handler
            Integer integer = channel.read(readBuf).get(10, TimeUnit.SECONDS);
            System.out.println("read:" + integer);
            if (integer == -1) {
                break;
            }
            readBuf.flip();
            System.out.println("received:" + Charset.forName("utf-8").decode(readBuf));
            readBuf.clear();
        }
    }

    public void startWithCompletionHandler() throws InterruptedException, ExecutionException, TimeoutException {
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                serverSocketChannel.accept(null, this);
                // receive this client again
                handleWithCompletionHandler(result);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
    }

    public void handleWithCompletionHandler(final AsynchronousSocketChannel channel) {
        try {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            final long timeout = 10L;
            channel.read(buffer, timeout, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("read:" + result);
                    if (result == -1) {
                        try {
                            channel.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    buffer.flip();
                    System.out.println(" received message: " + Charset.forName("GBK").decode(buffer));
                    buffer.clear();
                    channel.read(buffer, timeout, TimeUnit.SECONDS, null, this);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    exc.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        new AioServerTest2().startWithCompletionHandler();
        Thread.sleep(100000);
    }

}

