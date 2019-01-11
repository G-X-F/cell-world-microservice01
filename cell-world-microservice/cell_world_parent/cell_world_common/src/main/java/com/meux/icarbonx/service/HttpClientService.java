package com.meux.icarbonx.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.meux.icarbonx.proto.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpClientService {


    /**
     * 向指定服务器发送post请求
     */
    public ProtobuffFrame.Response sendPost(ProtobuffFrame.Request request,String targetUrl ) throws InvalidProtocolBufferException {

        String content_type = "application/octet-stream;charset=utf-8";
        byte[] result = sendPost(targetUrl,request.toByteArray(),content_type);
        return ProtobuffFrame.Response.parseFrom(result);
    }



    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param data 请求数据
     * @return 所代表远程资源的响应结果
     */
    private byte[] sendPost(String url, byte[] data,String content_type){
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn =(HttpURLConnection)  realUrl.openConnection();
            conn.setConnectTimeout(15000);
            // 设置通用的请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", content_type); //application/x-www-form-urlencoded application/json application/octet-stream;charset=utf-8
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            OutputStream out = conn.getOutputStream();
            // 发送请求参数
            out.write(data);
            // flush输出流的缓冲
            out.flush();
            out.close();
            if(conn.getResponseCode() == 200 ) {
                // 定义BufferedReader输入流来读取URL的响应
                InputStream in = conn.getInputStream();

                byte[] buff = new byte[1024];
                int length;
                while ((length = in.read(buff)) != -1) {
                    swapStream.write(buff,0,length);
                }
                in.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(conn!= null) conn.disconnect();
        }

        return swapStream.toByteArray();
    }
}
