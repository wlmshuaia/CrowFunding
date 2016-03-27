package com.tide.wechat;

import com.tide.utils.Md5Utils;
import com.tide.utils.PropertiesUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 数据对象基础类,定义数据类最基本的行为,包括:
 * 计算/设置/获取签名, 输出xml格式的参数, 从xml中读取数据对象等
 * Created by wengliemiao on 16/2/27.
 */
@Component
public class WxPayDataBase {
    // 参数 map
//    private String body ;
//    private String attach ;
//    private String out_trade_no ;
//    private String total_fee ;
//    private String notify_url ;
//    private String trade_type ;
//    private String openid ;
//    private String appid ;
//    private String mch_id ;
//    private String spbill_create_ip ;
//    private String nonce_str ;
//    private String sign ;
//    private String input_charset;

    protected Map<String, String> values = new HashMap<>();

    private PropertiesUtils propUtils = new PropertiesUtils();

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    /**
     * 转化为 xml 字符
     **/
    public String toXml(Map<String, String> pMap) {
        if(pMap.isEmpty()) {
            try {
                throw new WxPayException("数组异常");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        StringBuffer xml = new StringBuffer("<xml>");
        Iterator iter = pMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            String value = pMap.get(key);

            if(isNumeric(key) == true) {
                xml.append("<" + key + ">" + value + "</" + key + ">");
            } else {
                xml.append("<" + key + "><![CDATA[" + value + "]]></" + key + ">");
            }

        }
        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 将 xml 转为 map
     */
    public Map fromXml(String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            Element nodeElement = document.getRootElement();
            List node = nodeElement.elements();
            Map<String, String> resMap = new HashMap<>();
            for (Iterator iter = node.iterator(); iter.hasNext();) {
                Element ele = (Element) iter.next();
//                this.values.put(ele.getName(), ele.getText());
                resMap.put(ele.getName(), ele.getText());
            }
//            return this.values;
            return resMap;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 xml 转为 map
     * @param xml
     * @return
     */
    public static Map<String, String> init(String xml) {
        WxPayDataBase wdTools = new WxPayDataBase();
        Map<String, String> vMap = wdTools.fromXml(xml);
        if(vMap != null) {
            wdTools.setValues(vMap);
            if(!wdTools.getValues().get("return_code").equals("SUCCESS")){
                return wdTools.getValues();
            }
            wdTools.checkSign();
            return wdTools.getValues();
        }
        return null;
    }

    /**
     * SHA1 签名算法
     * @param str
     * @return
     */
    public static String SHA1(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

            byte[] strBytes = str.getBytes("UTF-8");
            messageDigest.update(strBytes);

            byte[] signByte = messageDigest.digest();
//            return new String(signByte, "UTF-8");
            return WxPayDataBase.byteArrayToHexString(signByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";


//        try {
//            MessageDigest digest = java.security.MessageDigest.getInstance("SHA");
//            digest.update(str.getBytes());
//            byte messageDigest[] = digest.digest();
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            // 字节数组转换为 十六进制 数
//            for (int i = 0; i < messageDigest.length; i++) {
//                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
//                if (shaHex.length() < 2) {
//                    hexString.append(0);
//                }
//                hexString.append(shaHex);
//            }
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
    }

    /**
     * 转换字节数组为十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(byteToHexString(bytes[i]));
        }
        return sb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     * @param b 字节数组
     * @return 字符串
     */
    public static String byteToHexString(byte b) {
        String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
        int ret = b;
        if (ret < 0) {
            ret += 256;
        }
        int m = ret / 16;
        int n = ret % 16;
        return hexDigits[m] + hexDigits[n];
    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     *
     * 产生随机字符串，不长于32位
     * @return 产生的随机字符串
     */
    public static String getNonceStr() {
        int length = 32;
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i ++ )  {
            int pos = random.nextInt(chars.length() - 1);
            str += chars.substring(pos, pos + 1);
        }
        return str;
    }

    /**
     * 生成签名算法
     * @return
     */
    public String makeSign() {
        //签名步骤一：按字典序排序参数
        Map<String, String> pMap = new TreeMap<>(this.getValues());
//        ksort(paramMap);
        String str = this.ToUrlParams(pMap);
        //签名步骤二：在string后加入KEY
        str = str + "&key=" + propUtils.readValue("KEY");
        //签名步骤三：MD5加密
        str = Md5Utils.MD5(str);
        //签名步骤四：所有字符转为大写
        String result = str.toUpperCase();
        return result;
    }

    /**
     * 判断签名是否正确
     * @return
     */
    public boolean checkSign() {
        String sign = this.makeSign() ;
        if(this.getValues().get("sign").equals(sign)) {
            return true;
        }
        try {
            throw new Exception("签名错误");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setSign() {
        this.values.put("sign", this.makeSign());
    }

    /**
     * 获取签名
     * @return
     */
    public String getSign() {
        return this.values.get("sign");
    }

    /**
     * 将 map 对象拼接为地址字符串
     * @param paramMap
     * @return
     */
    public String ToUrlParams(Map<String, String> paramMap) {
        StringBuffer buff = new StringBuffer("");
        Iterator iter = paramMap.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next().toString();
            String value = paramMap.get(key);
            if(!"sign".equals(key) && !"".equals(value)) {
                buff.append(key + "=" + value + "&");
            }
        }
        return buff.toString().substring(0, buff.length() - 1);
    }

    /**
     * 格式化输出xml数据
     * @param paramXml
     */
    public static void printXml(String paramXml) {
        WxPayDataBase wxPayDataBase = new WxPayDataBase();
        Map<String, String> paramMap = wxPayDataBase.fromXml(paramXml);
        for (Iterator iter = paramMap.keySet().iterator(); iter.hasNext();) {
            String key = iter.next().toString();
            String value = paramMap.get(key);
            System.out.println(key + ": " + value);
        }
    }

    /**
     * 格式化输出 xml
     * @param inputXML
     * @return
     * @throws Exception
     */
    public static String formatXML(String inputXML) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new StringReader(inputXML));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        String requestXML = null;
        XMLWriter writer = null;
        if (document != null) {
            try {
                StringWriter stringWriter = new StringWriter();
                OutputFormat format = new OutputFormat("    ", true);
                writer = new XMLWriter(stringWriter, format);
                writer.write(document);
                writer.flush();
                requestXML = stringWriter.getBuffer().toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return requestXML;
    }

    public void setBody(String body) {
        this.values.put("body", body);
    }

    public void setAttach(String attach) {
        this.values.put("attach", attach);
    }


    public void setOut_trade_no(String out_trade_no) {
        this.values.put("out_trade_no", out_trade_no);
    }

    public void setTotal_fee(String total_fee) {
        this.values.put("total_fee", total_fee);
    }

    public void setNotify_url(String notify_url) {
        this.values.put("notify_url", notify_url);
    }

    public void setTrade_type(String trade_type) {
        this.values.put("trade_type", trade_type);
    }

    public void setOpenid(String openid) {
        this.values.put("openid", openid);
    }

    public void setAppid(String appid) {
        this.values.put("appid", appid);
    }

    public void setMch_id(String mch_id) {
        this.values.put("mch_id", mch_id);
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.values.put("spbill_create_ip", spbill_create_ip);
    }

    public void setNonce_str(String nonce_str) {
        this.values.put("nonce_str", nonce_str);
    }

    public void setInput_charset(String input_charset) {
        this.values.put("input_charset", input_charset);
    }
}
