package com.tide.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyStore;

/**
 * Created by wengliemiao on 16/1/15.
 */
public class HttpUtils {

    /**
     * 获取返回的 html 内容
     * @param url
     * @return
     */
    public static String getResponseHtml(String url) {
        try {
            URL oUrl = new URL(url);
            HttpURLConnection htCon = (HttpURLConnection) oUrl.openConnection();

            int code = htCon.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK) {
                return HttpUtils.getDataFromInputstream(htCon.getInputStream());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 返回获取的 json 字符串
     * @param url
     * @return
     */
    public static String getResponseJson(String url) {
        String result = null;
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);

            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(), "utf-8"); // 设置编码
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 以 post 方式提交 xml 格式数据: 使用 HttpURLConnection
     * @param url
     * @param xml
     * @return
     */
    public static String postXmlCurl(String url, String xml) {
        try {
            byte[] bb = xml.getBytes();
            URL u = new URL(url);
            HttpURLConnection htCon = (HttpURLConnection) u.openConnection();

            htCon.setRequestMethod("POST");
            htCon.setDoInput(true);
            htCon.setDoOutput(true);//如果通过post提交数据，必须设置允许对外输出数据
            htCon.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            htCon.setRequestProperty("Content-Length", String.valueOf(bb.length));
            htCon.connect();
            DataOutputStream out = new DataOutputStream(htCon.getOutputStream());
            out.writeBytes(xml); //写入请求的字符串
            out.flush();
            out.close();

            //请求返回的状态
            if(htCon.getResponseCode() ==200) {
                return HttpUtils.getDataFromInputstream(htCon.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 以 SSL 加密的方式, 提交 xml 格式数据 -- 用于微信
     * @param url
     * @param xml
     * @return
     * @throws Exception
     */
    public static String postXmlCurlSSL(String url, String xml) throws Exception {
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        //String path = "/Users/wengliemiao/Documents/study/IntellijWorkspace/Tide/CrowFunding/target/classes/wechat/apiclient_cert.p12";
        String path = HttpUtils.class.getResource("/").toString() ;
        path = path.split("file:")[1];
        path += "wechat/apiclient_cert.p12";

        FileInputStream instream = new FileInputStream(new File(path));

        try {
            keyStore.load(instream, PropertiesUtils.instance().readValue("MCHID").toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, PropertiesUtils.instance().readValue("MCHID").toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "text/xml; charset=UTF-8");
            httpPost.setEntity(new StringEntity(xml));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            httpclient.close();
        }
        return "";
    }

    /**
     * 以 post 方式提交 json 数据: 以 HttpPost 方式
     * @param url
     * @param paramJson
     * @return
     */
    public static String postJsonCurl(String url, String paramJson) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.addHeader("Content-type","application/json; charset=utf-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(paramJson, Charset.forName("UTF-8")));
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String res = EntityUtils.toString(response.getEntity());
                return res;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从 InputStream 中读取数据
     * @param inputStream
     * @return
     */
    public static String getDataFromInputstream(InputStream inputStream) {
        StringBuffer htmlRes = new StringBuffer("");
        String inputLine;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while( (inputLine = in.readLine()) != null) {
                htmlRes.append(inputLine);
            }
            in.close();
            return htmlRes.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 过滤 emoji 表情
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
        }else{
            return source;
        }
    }
}
