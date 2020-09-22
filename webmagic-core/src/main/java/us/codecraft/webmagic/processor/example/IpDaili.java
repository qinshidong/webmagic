package us.codecraft.webmagic.processor.example;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class IpDaili {
    public static void main(String[] args) {
        String url = "http://exercise.kingname.info/exercise_middleware_ip";

        // 代理
        String hostname = "200.105.203.106";
        int port = 8888;
        //Proxy.Type 可以选择socket和http
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, port));

        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(proxy);
        OkHttpClient okHttpClient = builder.build();

        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Response response = call.execute();
                    System.out.println("run: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
