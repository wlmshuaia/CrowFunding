package com.tide.utils;

/**
 * Created by wengliemiao on 15/12/8.
 */
public class DataUtils {
    // 文件域名: 阿里云OSS配置
    public static String DOMAIN_FILE = "http://file.zm-college.com";

    // 网站主页
    public static String WEB_PHONE_HOMPAGE = "http://www.zm-college.com";

    // 图片数量计数
    public static Integer imgCount = 1;

    // 每页数据
    public static Integer PAGE_NUM = 8;
//    public static Integer PAGE_NUM = 1;

    // 页面数: 首页项目数, 主题数, 设计师数
    public static Integer OBJ_NUM = 4;

    // 首页主题显示数据条数: 一个主题最好只对应一个标签
    public static Integer THEME_NUM_INDEX = 1; // 首页主题数量
    public static Integer THEME_PROJECT_NUM_INDEX = 4; // 首页主题项目数

    // 详情项目数
    public static Integer INRO_PROJECT_NUM = 8;

    // 设计师简介项目数
    public static Integer DESIGNER_PROJECT_NUM = 3;

    // 属性名称
    public static String ATTR_COLOR = "color" ;
    public static String ATTR_SIZE = "size" ;

    // 用户类型
    public static String USER_TYPE_ALL = "all";
    public static String USER_TYPE_NORMAL = "normal";
    public static String USER_TYPE_DESIGNER = "designer";
    public static Integer I_TYPE_NORMAL = 0;    // 普通用户
    public static Integer I_TYPE_DESIGNER = 1;  // 设计师

    // 项目类型
    public static String PROJECT_TYPE_CHINESE_CLOTH = "服装";
    public static String PROJECT_TYPE_CHINESE_COMMONWEAL = "公益";
    public static String ADD_PROJECT_TYPE_CLOTH = "cloth"; // 服装类
    public static String ADD_PROJECT_TYPE_COMMONWEAL = "commonweal"; // 公益类-筹钱
    public static String ADD_PROJECT_TYPE_COMMONWEAL_PERSON = "commonweal-person"; // 公益类-筹人
    public static Integer ADD_PROJECT_TYPE_CLOTH_I = 0; // 服装类
    public static Integer ADD_PROJECT_TYPE_COMMONWEAL__PERSON_I = 1; // 公益类-筹人
    public static Integer ADD_PROJECT_TYPE_COMMONWEAL_MONEY_I = 2; // 公益类-筹钱

    // 项目状态
    public static String PROJECT_TYPE_ALL = "all";
    public static String PROJECT_TYPE_NEWST = "newst";
    public static String PROJECT_TYPE_HOTST = "hotst";
    public static String PROJECT_STATUS_WAIT_EXAME = "wait_exame";
    public static String PROJECT_STATUS_CROWFUNDING = "crowfunding";
    public static String PROJECT_STATUS_SUCCESS= "success";
    public static String PROJECT_STATUS_FAIL = "fail";
    public static String PROJECT_STATUS_PASS = "pass";
    public static String PROJECT_STATUS_AGAINST = "against";
    public static Integer I_PROJECT_WAIT_EXAME = 0;  // 待审核
    public static Integer I_PROJECT_CROWFUNDING = 1; // 通过
    public static Integer I_PROJECT_AGAINST = 2;     // 否决
    public static Integer I_PROJECT_SUCCESS = 3;     // 已达成
    public static Integer I_PROJECT_FAIL = 4;        // 未达成

    // 评论状态
    public static String C_STATUS_ALL = "all";
    public static String C_STATUS_WAIT_EXAME = "wait_exame";
    public static String C_STATUS_ALREADY_PASS = "already_pass";
    public static String C_STATUS_ALREADY_AGAINST = "already_against";
    public static Integer I_C_STATUS_WAIT_EXAME = 0;    // 待审核
    public static Integer I_C_STATUS_ALREADY_PASS = 1;  // 已通过
    public static Integer I_C_STATUS_ALREADY_AGAINST = 2;// 已否决

    // 商品状态
    public static String PRO_ALL_S = "all";
    public static String PRO_ONSALE_S = "onSale";
    public static String PRO_INWAREHOUSE_S = "inWarehouse";
    public static Integer PRO_ONSALE_I = 1;   // 在售中
    public static Integer PRO_INWAREHOUSE_I = 0; // 在仓库

    // 订单状态
    public static String O_STATUS_ALL = "all";
    public static String O_STATUS_WAIT_DELIVER = "wait_deliver";
    public static String O_STATUS_WAIT_TAKE = "wait_take";
    public static String O_STATUS_ALREADY_TAKE = "already_take";
    public static String O_STATUS_REFUNDING = "refunding";
    public static String O_STATUS_REFUNDING_SUCCESS = "refunding_success";
    public static String O_STATUS_REFUNDING_FAIL = "refunding_fail";
    public static String O_STATUS_WAIT_PAY = "wait_pay";
    public static Integer O_WAIT_DELIVER = 0;   // 待发货
    public static Integer O_WAIT_TAKE = 1;      // 待收货
    public static Integer O_ALREADY_TAKE = 2;   // 已收货
    public static Integer O_REFUNDING = 3;      // 退款中
    public static Integer O_REFUND_SUCCESS = 4; // 退款成功
    public static Integer O_REFUND_FAIL = 5;  // 退款失败
    public static Integer O_WAIT_PAY = 6;  // 订单待支付

    // 个人中心状态
    public static String S_PERSON_HEADER_RELEASE = "header-add";
    public static String S_PERSON_HEADER_BACK = "header-back";
    public static String S_PERSON_HEADER_FOCUS = "header-focus";
    public static String S_PERSON_TYPE_DESIGNER = "type-designer";
    public static String S_PERSON_TYPE_PROJECT = "type-project";

    // token 名称
    public static String TOKEN_ADD_PROJECT_1 = "token_add_project_1";
    public static String TOKEN_ADD_PROJECT_2 = "token_add_project_2";

    // 支付类型
    public static String PAY_TYPE_WECHAT = "pay-wechat";
    public static String PAY_TYPE_ALIPAY = "pay-alipay";

    // 模板消息相关字符常量
    public static String BUY_SUCCESS = "SUCCESS";
    public static String BUY_FAIL = "FAIL";

    /**
     * 根据图片类型返回后缀
     * @param imgType
     * @return
     */
    public static String getSuffixByType(String imgType) {
        String suffix = "" ;

        switch (imgType) {
            case "image/jpeg":
                suffix = ".jpg";
                break;
            case "image/png":
                suffix = ".png";
                break;
            case "image/gif":
                suffix = ".gif";
                break;
            default:
                suffix = ".jpg";
                break;
        }

        return suffix;
    }

    /**
     * 项目状态字符串转化为数字
     * @param status
     * @return
     */
    public static Integer getProjectStatus(String status) {
        Integer iStatus ;
        if(DataUtils.PROJECT_STATUS_WAIT_EXAME.equals(status)) { // 待审核
            iStatus = DataUtils.I_PROJECT_WAIT_EXAME;
        } else if(DataUtils.PROJECT_STATUS_CROWFUNDING.equals(status)) { // 进行中
            iStatus = DataUtils.I_PROJECT_CROWFUNDING;
        } else if(DataUtils.PROJECT_STATUS_AGAINST.equals(status)) { // 被否决
            iStatus = DataUtils.I_PROJECT_AGAINST;
        } else if(DataUtils.PROJECT_STATUS_SUCCESS.equals(status)) { // 已达成
            iStatus = DataUtils.I_PROJECT_SUCCESS;
        } else if(DataUtils.PROJECT_STATUS_FAIL.equals(status)) { // 未达成
            iStatus = DataUtils.I_PROJECT_FAIL;
        } else { // 全部
            iStatus = -1;
        }
        return iStatus;
    }

    /**
     * 根据项目状态获取筹集名称
     * @param type
     * @return
     */
    public static String getProjectType(Integer type) {
        if(type == DataUtils.ADD_PROJECT_TYPE_CLOTH_I) {
            return "件";
        } else if(type == DataUtils.ADD_PROJECT_TYPE_COMMONWEAL_MONEY_I) {
            return "元";
        } else if(type == DataUtils.ADD_PROJECT_TYPE_COMMONWEAL__PERSON_I) {
            return "人";
        }
        return "";
    }

    /**
     * 根据筹集名称获取项目状态
     * @param type
     * @return
     */
    public static Integer getProjectType(String type) {
        if(DataUtils.ADD_PROJECT_TYPE_CLOTH.equals(type)) {
            return DataUtils.ADD_PROJECT_TYPE_CLOTH_I;
        } else if(DataUtils.ADD_PROJECT_TYPE_COMMONWEAL.equals(type)) {
            return DataUtils.ADD_PROJECT_TYPE_COMMONWEAL_MONEY_I;
        } else if(DataUtils.ADD_PROJECT_TYPE_COMMONWEAL_PERSON.equals(type)) {
            return DataUtils.ADD_PROJECT_TYPE_COMMONWEAL__PERSON_I;
        }
        return -1;
    }
}
