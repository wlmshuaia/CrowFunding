package com.tide.service.impl;

import com.github.pagehelper.PageHelper;
import com.tide.bean.*;
import com.tide.dao.*;
import com.tide.service.CateService;
import com.tide.service.OrderService;
import com.tide.service.ProjectService;
import com.tide.service.UserService;
import com.tide.utils.DataUtils;
import com.tide.utils.DateUtils;
import com.tide.utils.FileUtils;
import com.tide.utils.PropertiesUtils;
import com.tide.wechat.WxPayApi;
import com.tide.wechat.WxPayOrderQuery;
import com.tide.wechat.WxPayRefund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by wengliemiao on 15/12/12.
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectDesignimgMapper projectDesignimgMapper;

    @Autowired
    private ProjectLabelMapper projectLabelMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private ProjectBackerMapper projectBackerMapper;

    @Autowired
    private FocusUserProjectMapper focusUserProjectMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FocusUserMapper focusUserMapper;

    @Autowired
    private CateService cateService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectColorMapper projectColorMapper;

    /**
     * 删除项目
     * @param id
     * @return
     */
    @Override
    public int delete(Integer id, HttpSession session) {
        // 删除 project
        this.projectMapper.deleteByPrimaryKey(id) ;

        // 删除 project_backer
        this.projectBackerMapper.deleteByProjectId(id);

        // 删除 project_designimg 效果图片
        List<ProjectDesignimg> pdList = this.projectDesignimgMapper.getDesignimgList(id);
        for(ProjectDesignimg pd : pdList) {
            // 删除服务器图片文件
//            FileUtils.deleteFile(FileUtils.getFileRealPath(session, pd.getProjectimg()));

            // 删除 OSS 图片文件
            FileUtils.deleteOSSFile(pd.getProjectimg());
        }

        // 删除 project_designimg 数据
        this.projectDesignimgMapper.deleteByProjectId(id);

        // 删除 project_label
        this.projectLabelMapper.deleteByProjectId(id);

        // 删除 focus_user_project
        this.focusUserProjectMapper.deleteByProjectId(id);

        // 删除 project_color
        this.projectColorMapper.deleteByProjectId(id);

        // 删除 comment
        this.commentMapper.deleteByProjectId(id);

        return 1;
    }


    @Override
    public int insert(Project project) {
        return this.projectMapper.insert(project);
    }

    @Override
    public int insertProjectDesignimg(ProjectDesignimg pd) {
        return this.projectDesignimgMapper.insert(pd);
    }

    @Override
    public List<Project> getProjectAddByStatus(Integer uid, String status) {
        Integer iStatus = DataUtils.getProjectStatus(status);
        if(iStatus == -1) {
            return this.projectMapper.getProjectByUid(uid);
        }
        return this.projectMapper.getProjectAddByStatus(uid, iStatus);
    }

    @Override
    public List<Project> getProjectFocusByStatus(Integer uid, String status) {
        Integer iStatus = DataUtils.getProjectStatus(status);
        List<FocusUserProject> fupList = this.focusUserProjectMapper.getFocusProject(uid);
        List<Project> pList = new ArrayList<>();
        for(FocusUserProject fup : fupList) {
            Project p = this.projectMapper.selectByPrimaryKey(fup.getProjectid());
            if(p.getStatus() == iStatus || iStatus == -1) {
                pList.add(p);
            }
        }
        return pList;
    }

    @Override
    public List<Project> getProjectBackByStatus(Integer uid, String status) {
        Integer iStatus = DataUtils.getProjectStatus(status);
        List<ProjectBacker> pbList = this.projectBackerMapper.getByUserId(uid);
        List<Project> pList = new ArrayList<>();
        for (ProjectBacker pb : pbList) {
            Project p = this.projectMapper.selectByPrimaryKey(pb.getProjectid());
            if(p.getStatus() == iStatus || iStatus == -1) {
                pList.add(p);
            }
        }
        return pList;
    }

    @Override
    public int insertProjectLabel(ProjectLabel pl) {
        return this.projectLabelMapper.insert(pl);
    }

    @Override
    public Project getObjById(Integer id) {
        return this.projectMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Project> selectAll() {
        return this.projectMapper.selectAll();
    }

    @Override
    public int getProjectBackerNum(Integer projectid) {
        return this.projectBackerMapper.getProjectBackerNum(projectid);
    }

    @Override
    public List<Project> getProjectByStatus(Integer status) {
        return this.projectMapper.getProjectByStatus(status);
    }

    @Override
    public List<Project> getProjectByStatus(String status) {
        Integer iStatus = DataUtils.getProjectStatus(status);

        if(iStatus == -1) {
            return this.projectMapper.selectAll();
        } else{
            return this.projectMapper.getProjectByStatus(iStatus);
        }
    }

    @Override
    public List<Project> getProjectByType(String type) {
        Integer iType = DataUtils.getProjectType(type);

        if(iType == -1) {
            return this.projectMapper.selectAll();
        } else {
            return this.projectMapper.getProjectByType(iType);
        }
    }

    @Override
    public List<Map<String, Object>> getProjectByType(String type, HttpSession session) {
        List<Project> pList = new ArrayList<>();
        if(type.equals(DataUtils.PROJECT_TYPE_ALL) || type.equals(DataUtils.PROJECT_TYPE_NEWST)) { // 全部 / 最新
//            pList = this.projectMapper.selectAll();
            pList = this.projectMapper.getProjectDisplay();
        } else if(DataUtils.PROJECT_STATUS_CROWFUNDING.equals(type)) { // 筹集中
            pList = this.getProjectByStatus(DataUtils.I_PROJECT_CROWFUNDING);
        } else if(DataUtils.PROJECT_TYPE_HOTST.equals(type)) { // 最热
//            pList = this.projectMapper.getProjectByBackNumDesc();
            pList = this.projectMapper.getProjectByBackNumDescDisplay();
        } else if(DataUtils.PROJECT_STATUS_SUCCESS.equals(type)) { // 已达成
            pList = this.getProjectByStatus(DataUtils.I_PROJECT_SUCCESS);
        }

        return this.getProjectMapList(pList, session);
    }

//    @Override
//    public int getProjectByTypeCount(String type) {
//        if(type.equals(DataUtils.PROJECT_TYPE_ALL) || type.equals(DataUtils.PROJECT_TYPE_NEWST)) { // 全部 / 最新
//
//        } else if(DataUtils.PROJECT_STATUS_CROWFUNDING.equals(type)) { // 筹集中
//
//        } else if(DataUtils.PROJECT_TYPE_HOTST.equals(type)) { // 最热
//
//        } else if(DataUtils.PROJECT_STATUS_SUCCESS.equals(type)) { // 已达成
//
//        }
//
//        this.projectMapper.getTypeCount();
//
//        return 0;
//    }

    @Override
    public int update(Project project) {
        return this.projectMapper.updateByPrimaryKey(project);
    }

    @Override
    public List<ProjectDesignimg> getDesignimgList(Integer projectid) {
        return this.projectDesignimgMapper.getDesignimgList(projectid);
    }

    @Override
    public List<ProjectLabel> getProjectLabelList(Integer projectid) {
        return this.projectLabelMapper.getLabelList(projectid);
    }

    @Override
    public int getProjectFundNum(Integer projectid) {
        List<ProjectBacker> pbList = this.projectBackerMapper.getByProjectId(projectid);

        Integer num = 0;
        for (ProjectBacker pb : pbList) {
            num += pb.getNum();
        }

        return num;
    }

    @Override
    public List<Label> getLabelList(Integer projectid) {

        List<ProjectLabel> plList = this.projectLabelMapper.getLabelList(projectid);
        List<Label> labelList = new ArrayList<>();
        for (ProjectLabel pl : plList) {
            Label label = this.labelMapper.selectByPrimaryKey(pl.getLabelid());
            labelList.add(label);
        }

        return labelList;
    }

    @Override
    public Map<String, Object> getProjectInfo(Project p, HttpSession session) {
        Map<String, Object> pMap = new HashMap<>();

        Object obj = session.getAttribute("userid");
        System.out.println(obj);
        Integer ifFocus = 0;
        if(obj != null) {
            Integer uid = Integer.parseInt(obj.toString());
            if(this.focusUserProjectMapper.getFocusProjectByUP(uid, p.getId()) != null) {
                ifFocus = 1;
            }
        }

        // 设计效果图
        List<ProjectDesignimg> pdList = this.getDesignimgList(p.getId());
//        Integer backernum = this.getProjectBackerNum(p.getId());
        Integer backernum = p.getBackernum();
        backernum = backernum == null ? 0 : backernum;

//        Integer fundnum = this.getProjectFundNum(p.getId());
        Integer fundnum = p.getFundnum();
        fundnum = fundnum == null ? 0 : fundnum;

        //格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        Integer targetnum = p.getTargetnum();
        if(p.getTargetnum() == null) {
            targetnum = 1;
        }
        Double percent = 100 * (fundnum * 1.0) / targetnum;

        int leftdays = DateUtils.getDaysBetween(new Date(), p.getEnddate());
        if(leftdays < 0) {
            leftdays = 0;
        }

        pMap.put("project", p);     // 项目信息
//        pMap.put("designimg", pdList.get(0).getProjectimg());  // 设计效果图地址
        pMap.put("designimg", pdList);  // 设计效果图地址
        pMap.put("percent", df.format(percent));    // 达成百分比
        pMap.put("fundnum", fundnum);    // 已筹集件数
        pMap.put("backernum", backernum);  // 支持者数量
        pMap.put("leftdays", leftdays);   // 剩余天数
        pMap.put("ifFocus", ifFocus);
        return pMap;
    }

    @Override
    public Map<String, Object> getProjectInfo(Integer id, HttpSession session) {
        Project p = this.getObjById(id);
        return this.getProjectInfo(p, session);
    }

    @Override
    public Map<String, Object> getProjectBaseInfo(Integer id, HttpSession session) {
        Project p = this.projectMapper.selectByPrimaryKey(id);
        return this.getProjectBaseInfo(p, session);
    }

    @Override
    public Map<String, Object> getProjectBaseInfo(Project p, HttpSession session) {
        Map<String, Object> pMap = new HashMap<>();

        List<ProjectDesignimg> pdList = this.projectDesignimgMapper.getDesignimgList(p.getId());

        Object obj = session.getAttribute("userid");
//        System.out.println(obj);
        Integer ifFocus = 0;
        if(obj != null) {
            Integer uid = Integer.parseInt(obj.toString());
            if(this.focusUserProjectMapper.getFocusProjectByUP(uid, p.getId()) != null) {
                ifFocus = 1;
            }
        }
        pMap.put("project", p);
        if(pdList.size() > 0) {
            pMap.put("designimg", pdList.get(0).getProjectimg());
        } else {
            pMap.put("designimg", null);
        }
        pMap.put("ifFocus", ifFocus);
        return pMap;
    }

    @Override
    public Project getProjectByTradeno(String tradeno) {
        ProjectBacker pb;
        if((pb = this.projectBackerMapper.getByTradeno(tradeno)) != null) {
            return this.projectMapper.selectByPrimaryKey(pb.getProjectid());
        }
        return null;
    }

    @Override
    public int getCount() {
        return this.projectMapper.getCount();
    }

    @Override
    public List<Project> getProjectByUid(Integer uid) {
        return this.projectMapper.getProjectByUid(uid);
    }

    @Override
    public List<Map<String, Object>> getProjectBackerList(Integer projectid) {
        List<Map<String, Object>> pbMapList = new ArrayList<>();
        List<ProjectBacker> pbList = this.projectBackerMapper.getByProjectId(projectid);

        for (ProjectBacker pb : pbList) {
            Order order = this.orderService.getOrderByTradeNo(pb.getTradeno());
            if(order.getTransactionid() != null) { // 判断是否完成支付
                User user = this.userMapper.selectByPrimaryKey(pb.getUserid());
                Map<String, Object> map = new HashMap<>();
                map.put("user", user);
                map.put("pb", pb);
                pbMapList.add(map);
            }
        }

        return pbMapList;
    }

    @Override
    public List<Map<String, Object>> getProjectBaseMapByUid(Integer uid, HttpSession session) {
        List<Map<String, Object>> pMapList = new ArrayList<>() ;

        PageHelper.startPage(1, DataUtils.DESIGNER_PROJECT_NUM);
        List<Project> pList = this.getProjectByUid(uid);
        for (Project p : pList) {
            if(p.getStatus() != DataUtils.I_PROJECT_WAIT_EXAME && p.getStatus() != DataUtils.I_PROJECT_AGAINST) {
                Map<String, Object> m = this.getProjectBaseInfo(p, session);
                pMapList.add(m);
            }
        }
        return pMapList;
    }

    @Override
    public List<Map<String, Object>> getProjectBySearch(String content, HttpSession session) {
        List<Map<String, Object>> pMapList = new ArrayList<>();
        List<Project> pList = this.projectMapper.getProjectBySearch(content);
        for (Project p : pList) {
            if(p.getStatus() != DataUtils.I_PROJECT_WAIT_EXAME && p.getStatus() != DataUtils.I_PROJECT_AGAINST) {
                pMapList.add(this.getProjectInfo(p, session));
            }
        }
        return pMapList;
    }

    @Override
    public List<Map<String, Object>> getProjectByLabelId(Integer labelid, HttpSession session) {
        List<ProjectLabel> pLabelList = this.projectLabelMapper.getProjectList(labelid);
        List<Project> pList = new ArrayList<>();

        for (ProjectLabel pl : pLabelList) {
            Project p = this.projectMapper.selectByPrimaryKey(pl.getProjectid());
            if(p.getStatus() != DataUtils.I_PROJECT_WAIT_EXAME && p.getStatus() != DataUtils.I_PROJECT_AGAINST) {
                pList.add(p);
            }
        }

        return this.getProjectMapList(pList, session);
    }

    @Override
    public List<Map<String, Object>> getProjectByCateId(Integer cateid, HttpSession session) {
        List<Project> projectList = new ArrayList<>();

        List<Cate> cList = this.cateService.getCateByCateid(cateid);
        for (Cate c : cList) {
//            System.out.println("catename: " + c.getCatename());
                List<Integer> proIdList = this.productMapper.getProductIdByCateId(c.getId());
                for (Integer proid : proIdList) {
//                    List<Project> pList = this.projectMapper.getProjectByProid(proid);
                    List<Project> pList = this.getProjectByProid(proid);
                    projectList.addAll(pList);
                }
        }

        return this.getProjectMapList(projectList, session);
    }

    @Override
    public List<Map<String, Object>> getProjectByCateId(Integer cateid, Integer start, HttpSession session) {
        List<Project> projectList = new ArrayList<>();

        PageHelper.startPage(start, 2);
        List<Cate> cList = this.cateService.getCateByCateid(cateid);
        for (Cate c : cList) {
            System.out.println("catename: " + c.getCatename());
            List<Integer> proIdList = this.productMapper.getProductIdByCateId(c.getId());

            for (Integer proid : proIdList) {
//                List<Project> pList = this.projectMapper.getProjectByProid(proid);
                List<Project> pList = this.getProjectByProid(proid);
                projectList.addAll(pList);
            }
        }

        return this.getProjectMapList(projectList, session);
    }

    /**
     * 根据商品id, 获取项目列表
     * @param proid
     * @return
     */
    private List<Project> getProjectByProid(Integer proid) {
        List<Integer> pIdList = this.projectColorMapper.getProjectIdsByProid(proid);
        List<Project> projectList = new ArrayList<>();
        for (Integer id : pIdList) {
            Project p = this.getObjById(id);
            if(p.getStatus() != DataUtils.I_PROJECT_WAIT_EXAME && p.getStatus() != DataUtils.I_PROJECT_AGAINST) {
                projectList.add(p);
            }
        }
        return projectList;
    }

    /**
     * 将项目列表转化为map类型列表
     * @param pList
     * @param session
     * @return
     */
    protected  List<Map<String, Object>> getProjectMapList(List<Project> pList, HttpSession session) {
        List<Map<String, Object>> pMapList = new ArrayList<>();
        for (Project p : pList) {
            pMapList.add(this.getProjectInfo(p, session));
        }
        return pMapList;
    }

    @Override
    public int insertProjectBacker(ProjectBacker projectBacker) {
        return this.projectBackerMapper.insert(projectBacker);
    }

    @Override
    public int deleteProjectBacker(Integer id) {
        return this.projectBackerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteProjectBackerByTradeno(String tradeno) {
        return this.projectBackerMapper.deleteByTradeNo(tradeno);
    }

    @Override
    public ProjectBacker getProjectBackerById(Integer id) {
        return this.projectBackerMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProjectBacker getProjectBackerByTradeno(String tradeno) {
        return this.projectBackerMapper.getByTradeno(tradeno);
    }

    @Override
    public int updateProjectBacker(ProjectBacker projectBacker) {
        return this.projectBackerMapper.updateByPrimaryKey(projectBacker);
    }

    /**
     * 发送成功模板消息
     * @param projectid
     * @return
     */
    public String sendBuySuccessMsgToBackers(Integer projectid) {
        return this.sendMsgToBackers(projectid, DataUtils.BUY_SUCCESS);
    }

    /**
     * 发送失败模板消息
     * @param projectid
     * @return
     */
    public String sendBuyFailMsgToBackers(Integer projectid) {
        return this.sendMsgToBackers(projectid, DataUtils.BUY_FAIL);
    }

    /**
     * 发送模板消息给用户
     * @param projectid
     * @return
     */
    private String sendMsgToBackers(Integer projectid, String type) {
        String url = DataUtils.WEB_PHONE_HOMPAGE + "/front/Project/projectInfo.do?id=" + projectid;

        // 1. 发送消息给项目发布者
        Project project = this.getObjById(projectid);
        String releaseOpenid = this.userService.getObjById(project.getUserid()).getOpenid();
        String releaseName = "发布的项目名称:" + project.getTitle() + ", 发布的项目编号: " + project.getProjectno();

        if(DataUtils.BUY_SUCCESS.equals(type)) { // 众筹成功: 发送成功模板消息
            WxPayApi.sendBuySuccessMsg(releaseOpenid, url, releaseName);
        } else { // 众筹失败: 发送失败模板消息
            WxPayApi.sendBuyFailMsg(releaseOpenid, url, releaseName);
        }

        // 2. 发送消息给支持者
        List<Map<String, Object>> userList = this.getProjectBackerList(projectid);
        for (Map<String, Object> map : userList) {
            String openid = ((User) map.get("user")).getOpenid();
            ProjectBacker pb = (ProjectBacker) map.get("pb");
            Project p = this.getObjById(pb.getProjectid());
            StringBuffer name = new StringBuffer("支持的项目名称:" + p.getTitle() + ", ");
            name.append("支持的项目编号: " + p.getProjectno() + ", ");
            name.append("订单编号: " + pb.getTradeno() + ", ");
            name.append("支持数量: " + pb.getNum());
            if(DataUtils.BUY_SUCCESS.equals(type)) { // 众筹成功: 发送成功模板消息
                WxPayApi.sendBuySuccessMsg(openid, url, name.toString());
            } else { // 众筹失败: 发送失败模板消息
                WxPayApi.sendBuyFailMsg(openid, url, name.toString());
            }
        }

        return "";
    }

    /**
     * 退还款项给所有支持者
     * @param projectid
     * @return
     */
    @Override
    public String refundToBackers(Integer projectid) {
        List<Map<String, Object>> userList = this.getProjectBackerList(projectid);

        for (Map<String, Object> map : userList) {
            // 1.获取 out_trade_no
            ProjectBacker pb = (ProjectBacker) map.get("pb");
            String outTradeNo = pb.getTradeno();

            // 2.获取 transaction_id
            Order order = this.orderService.getOrderByTradeNo(outTradeNo);
            String transactionId = order.getTransactionid();

            // 3.调用查询订单接口获取 total_fee
            WxPayOrderQuery wxPayOrderQuery = new WxPayOrderQuery();
            wxPayOrderQuery.setTransactionId(transactionId);
            Map<String, String> orderQueryMap = WxPayApi.orderQuery(wxPayOrderQuery);

            if(orderQueryMap.containsKey("return_code") && !"SUCCESS".equals(orderQueryMap.get("return_code"))
                    && orderQueryMap.containsKey("result_code") && !"SUCCESS".equals(orderQueryMap.get("result_code"))) {
                return "error";
            }
            String totalFee = orderQueryMap.get("total_fee");

            // 4.调用退款接口
            WxPayRefund wxPayRefund = new WxPayRefund();
            wxPayRefund.setOutTradeNo(outTradeNo);
            wxPayRefund.setTotalFee(totalFee);
            wxPayRefund.setRefundFee(totalFee);
            wxPayRefund.setOutRefundNo(outTradeNo);
            wxPayRefund.setOpUserId(PropertiesUtils.instance().readValue("MCHID"));

            Map<String, String> resMap = WxPayApi.refund(wxPayRefund);

            System.out.println("resMap: " + outTradeNo + ", " + resMap);
        }
        return "success";
    }

    @Override
    public int insertProjectColor(ProjectColor projectColor) {
        return this.projectColorMapper.insert(projectColor);
    }

    @Override
    public int getProIdByProjectId(Integer projectid) {
        List<Integer> proidList = this.projectColorMapper.getProIdByProjectId(projectid);
        if(proidList.size() > 0) {
            return proidList.get(0);
        }
        return 0;
    }
}
