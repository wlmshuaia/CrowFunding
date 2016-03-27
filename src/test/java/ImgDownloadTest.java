import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wengliemiao on 16/1/3.
 */
public class ImgDownloadTest extends BaseSpring {

    @Test
    public void downloadImgs() {
//        String url = "http://www.arriveguide.com/index.php/Product/index/id/135";
//        String url = "http://www.arriveguide.com/index.php/Product/index/id/108";
//        String url = "http://www.arriveguide.com/index.php/Product/index/id/112";
//        String url = "https://modao.cc/app/GaBXyNJNMSuTLYPczMeuD?inapp=1";
//        String url = "http://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=index&fr=&sf=1&fmq=&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=%E7%BE%8E%E5%A5%B3&oq=%E7%BE%8E%E5%A5%B3&rsp=-1#z=0&pn=&ic=0&st=-1&face=0&s=0&lm=-1";
//        String url = "https://www.google.co.jp/search?hl=zh-CN&biw=1440&bih=805&site=imghp&tbm=isch&sa=1&q=%E7%BE%8E%E5%A5%B3&oq=%E7%BE%8E%E5%A5%B3&gs_l=img.3...18.18.0.301.1.1.0.0.0.0.0.0..0.0....0...1c..64.img..1.0.0.4-xcylEa5Z0";


//
//        String htmlRes = getHtmlString(url);
//        System.out.println(url);
//        List<String> imgList = getImgList(htmlRes);
//        String folderName = new Date().getTime()+"";
//        for (String s : imgList) {
//            System.out.println(s);
//            downloadImg(s, folderName);
//        }
//
//        String a = "http://www.bilibilijj.com/Files/DownLoad/5521963.mp4/www.bilibilijj.com.mp4?mp3=true";
//
//        System.out.println(a.indexOf("mp4"));

        String[] aVideo = {"av3475385", "av3475389", "av3534639", "av3534814", "av3534815", "av3534820"};
        List<String> vList = new ArrayList<>();
        for (String s : aVideo) {
            String url = "http://www.ibilibili.com/video/"+ s +"/";
            String htmlRes = getHtmlString(url);
            vList.add(getVideoUrl(s, htmlRes));
        }

        for (String s : vList) {
            System.out.println(s);
        }

    }

    public List<String> getVideoList(String content) {
        Matcher firMatcher = Pattern.compile("id=\"v_bgm_list_data.*?</div>").matcher(content);
        if(firMatcher.find()) {
            String firCon = firMatcher.group();
            Matcher secMatcher = Pattern.compile("href=\".*?\"").matcher(firCon);
            List<String> aVId = new ArrayList<>();
            while(secMatcher.find()) {
                String secCon = secMatcher.group();
                Matcher thiMatcher = Pattern.compile("\".*?\"").matcher(secCon);
                if(thiMatcher.find()) {
                    String thiCon = thiMatcher.group();
                    aVId.add(thiCon.substring(1, thiCon.length() - 1));
                }
            }
        }
        return null;
    }

    public String getVideoUrl(String ID, String content) {

        Matcher matcher = Pattern.compile("id=\"download.*?</ul>").matcher(content);
        if(matcher.find()) {
            String con = matcher.group();
            System.out.println(ID+" 0 "+con);
            Matcher firMatcher = Pattern.compile("<a .*?</a>").matcher(con);
            while(firMatcher.find()) {
                String firCon = firMatcher.group();
                System.out.println(ID+" 1 "+firCon);
                System.out.println("index: "+firCon.indexOf("mp4"));
                if(firCon.indexOf("mp4") != -1) {
                    System.out.println(ID+" "+firCon);
                    Matcher fMatcher = Pattern.compile("=\".*?\"").matcher(firCon);
                    if(fMatcher.find()) {
                        String fCon = fMatcher.group();
                        Matcher secMatcher = Pattern.compile("\".*?\"").matcher(fCon);
                        if(secMatcher.find()) {
                            String secCon = secMatcher.group();
                            return ID+":"+secCon.substring(1, secCon.length() - 1);
                        }
                        return ID;
                    }
                    return ID;
                }
            }
            return ID;
        }
        return ID;
    }

    /**
     * 下载网络图片
     * @return
     */
    public String downloadImg (String imgUrl, String folderName) {
        String domain = "https://modao.cc";
//        String domain = "http://www.arriveguide.com";
        String imgSrc = domain + imgUrl;
//        String imgSrc = imgUrl ;

        Integer imgCount = 1;
        try {
            URL url = new URL(imgSrc);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inStream = conn.getInputStream();
            try {
                // 将输入流转化为二进制数组
                byte[] imgData = readInputStream(inStream);

                // 文件保存文件夹
                String basePath = "/Users/wengliemiao/Desktop/productImgs/"+folderName+"/";

                // 判断文件夹是否存在
                File dir = new File(basePath) ;
                if(!dir.exists()) {
                    dir.mkdirs();
                }

                // 拼接文件保存路径
                String imgName = new Date().getTime() + (imgCount++) + ".jpg";
                String imgPath = basePath + imgName ;

                // 将二进制数组写到输出流
                File imgFile = new File(imgPath);
                FileOutputStream out = new FileOutputStream(imgFile);
                out.write(imgData);
                out.close();

                return imgPath;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从输入流中获取二进制文件数据
     * @param inStream
     * @return
     * @throws Exception
     */
    public byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流把数据读取到buffer里
        while( (len=inStream.read(buffer)) != -1 ){
            //将buffer数组里的数据写到输出流中，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 正则获取img地址列表
     * @param content
     * @return
     */
    public List<String> getImgList(String content) {
        List<String> imgList = new ArrayList<>();
        Matcher firMatcher = Pattern.compile("<img.*?>").matcher(content);
        while(firMatcher.find()) {
            String firCon = firMatcher.group().toString();
            Matcher secMatcher = Pattern.compile("src=\".*?\"").matcher(firCon);
            if(secMatcher.find()) {
                String secCon = secMatcher.group().toString();
                Matcher thiMatcher = Pattern.compile("\".*?\"").matcher(secCon);
                if(thiMatcher.find()) {
                    String thiCon = thiMatcher.group().toString();

                    imgList.add(thiCon.substring(1, thiCon.length() - 1));
                }
            }
        }
        return imgList;
    }

    /**
     * 获取网页html内容
     * @param sWebUrl
     * @return
     */
    public String getHtmlString(String sWebUrl) {
        String htmlRes = "", error = "";
        try {
            URL url = new URL(sWebUrl);
            HttpURLConnection htCon = (HttpURLConnection) url.openConnection();


            int code = htCon.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(htCon.getInputStream()));
                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    htmlRes += inputLine;
                }
                in.close();
                return htmlRes;
            }  else if(code == HttpURLConnection.HTTP_MOVED_TEMP){
                error = "302 重定向";
            } else if(code == HttpURLConnection.HTTP_VERSION){
                error = "505 服务器不支持请求中所用的 HTTP 协议版本";
            } else {
                error = "不能访问该网站" ;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error;
    }
}
