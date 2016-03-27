package com.tide.controller.front;

import com.tide.bean.*;
import com.tide.service.*;
import com.tide.utils.*;
import com.tide.wechat.WxPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by wengliemiao on 15/12/12.
 */
@Controller
@RequestMapping(value = "/front/Project")
public class FProjectAction {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProductService productService ;

    @Autowired
    private LabelService labelService ;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CateService cateService;

    /**
     * 发布项目视图
     * @param model
     * @return
     */
    @RequestMapping(value = "/addProject")
    public String addProject(String type, Model model, HttpSession session, HttpServletRequest request) {
        // 标签列表
        List<Label> lList = this.labelService.selectAllDisplay();
        model.addAttribute("labelList", lList);

        // 商品列表
        List<Product> pList = this.productService.selectOnSale();
        model.addAttribute("productList", pList);

        String token = TokenUtils.getInstance().makeToken();
        model.addAttribute(DataUtils.TOKEN_ADD_PROJECT_1, token);
        session.setAttribute(DataUtils.TOKEN_ADD_PROJECT_1, token);

        if(request.getQueryString() != null && !"".equals(request.getQueryString())) {
            model.addAttribute("url", request.getRequestURL() + "?" + request.getQueryString());
        } else {
            model.addAttribute("url", request.getRequestURL());
        }


        if(DataUtils.ADD_PROJECT_TYPE_CLOTH.equals(type)) { // 服装类项目
            return "front/project/addProject";
        } else if(DataUtils.ADD_PROJECT_TYPE_COMMONWEAL.equals(type)) {
            return "front/project/addProject-commonweal";
        }
        return "front/error/404";
    }

    /**
     * 发布项目操作: 第一步
     * @return
     */
    @RequestMapping(value = "/addProjectHandle")
    @ResponseBody
    public Object addProjectHandle(@ModelAttribute("project") Project project,
                                   //@RequestParam("projectimgs") MultipartFile[] projectimgs,
                                   String[] serverId,
                                   String[] label,
                                   Integer proid,
                                   String[] color,
                                   String token,
                                   HttpSession session) {
        project.setType(DataUtils.ADD_PROJECT_TYPE_CLOTH_I);
        Map<String, Object> map = addProjectHandleCommon(project, serverId, label, proid, token, session);

        // 插入 project_color 数据
        if(color != null && color.length > 0) {
            Integer projectid = Integer.parseInt(map.get("projectid").toString());
            for (String c : color) {
                ProjectColor pc = new ProjectColor();
                pc.setProjectid(projectid);
                pc.setProid(proid);
                pc.setColor(c);
                this.projectService.insertProjectColor(pc);
            }
        }
        return map;
    }

    /**
     * 保存项目公共数据
     * @param project
     * @param serverId
     * @param label
     * @param token
     * @param session
     * @return
     */
    private Map<String, Object> addProjectHandleCommon(Project project,
                                          String[] serverId,
                                          String[] label,
                                          Integer proid,
                                          String token,
                                          HttpSession session) {
        // 0.判断是否重复提交表单
//        if(token == null || "".equals(token) || ifTokenRight(DataUtils.TOKEN_ADD_PROJECT_1, token, session) == false) {
//            model.addAttribute("errorMsg", "请勿重复提交表单");
//            return "front/error/error";
//            return new HashMap<>().put("error", "请勿重复提交表单");
//        }
//        session.removeAttribute(DataUtils.TOKEN_ADD_PROJECT_1);

        // 1.保存项目基本信息
        Date currDate = new Date();
        if(project != null) {
            project.setReleasedate(DateUtils.getCurrentDate1(currDate));
            project.setStatus(DataUtils.I_PROJECT_WAIT_EXAME);

            // 用户id -- 从微信获取
            Integer uid = 1;
            if(session.getAttribute("userid") != null) {
                uid = Integer.parseInt(session.getAttribute("userid").toString());
            }
            project.setUserid(uid);

            // 生成项目编号
            project.setProjectno(RandomUtils.createProjectNo(uid));

            this.projectService.insert(project) ;
        } else {
//            model.addAttribute("errorMsg", "项目信息为空!");
//            return "admin/error";
            return (Map) new HashMap<>().put("error", "请勿重复提交表单");
        }

        // 2.效果图: 可多张 -- 本地上传到阿里云OSS
//        if(projectimgs != null) {
//            for (MultipartFile projectimg : projectimgs) {
//                if(!projectimg.isEmpty()) {
//                    // 保存设计效果图到阿里云OSS
//                    String savePath = FileUtils.saveFile(projectimg, session);
//
//                    ProjectDesignimg pd = new ProjectDesignimg() ;
//                    pd.setProjectid(project.getId());
//                    pd.setProjectimg(savePath);
//                    this.projectService.insertProjectDesignimg(pd);
//                } else {
//                    System.out.println("projectimg is null...");
//                }
//            }
//        }

        // 2.效果图: 从微信服务器下载图片到阿里云OSS
        if(serverId != null && serverId.length > 0) {
            for (String sId : serverId) {
                // 从微信服务器下载图片到阿里云OSS
                String savePath = FileUtils.downloadImgFromWechat(sId);

                ProjectDesignimg pd = new ProjectDesignimg() ;
                pd.setProjectid(project.getId());
                pd.setProjectimg(savePath);
                this.projectService.insertProjectDesignimg(pd);
            }
        }

        // 3.标签
        if(label != null) {
            for(String l : label) {
                ProjectLabel pl = new ProjectLabel();
                pl.setProjectid(project.getId());
                pl.setLabelid(Integer.parseInt(l));

                this.projectService.insertProjectLabel(pl);
            }
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currDate);
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
        Integer minute = calendar.get(Calendar.MINUTE);
        Map<String, Object> timeMap = new HashMap<>();
        timeMap.put("hour", hour);
        timeMap.put("minute", minute);

        Product product = this.productService.getObjById(proid);

        Map<String, Object> map = new HashMap<>();
        map.put("projectid", project.getId());
        map.put("leastnum", product.getLeastnum());
        map.put("releasedate", project.getReleasedate());
        map.put("enddate", project.getReleasedate());
        map.put("timeMap", timeMap);
        map.put("token", token);
        return map;
    }

    /**
     * 发布公益类项目
     * @param project
     * @param serverId
     * @param label
     * @param proid
     * @param token
     * @param fundtype
     * @param session
     * @return
     */
    @RequestMapping(value = "/addProjectCommonwealHandle")
    @ResponseBody
    public Object addProjectCommonwealHandle(@ModelAttribute("project") Project project,
                                             String[] serverId,
                                             String[] label,
                                             Integer proid,
                                             String token,
                                             Integer fundtype,  // 公益类型: 人, 钱
                                             HttpSession session) {
        project.setType(fundtype);

        Map<String, Object> map = addProjectHandleCommon(project, serverId, label, proid, token, session);
        map.put("fundtype", fundtype);

        // 插入 project_color 数据
        Integer projectid = Integer.parseInt(map.get("projectid").toString());
        ProjectColor pc = new ProjectColor();
        pc.setProjectid(projectid);
        pc.setProid(proid);
        pc.setColor("");
        this.projectService.insertProjectColor(pc);

        return map;
    }

    /**
     * 发布项目第二步
     * @return
     */
    @RequestMapping(value = "/addProjectSecond")
    public String addProjectSecond(RedirectAttributesModelMap modelMap, Model model, HttpSession session) {
        String token = TokenUtils.getInstance().makeToken();//创建令牌
        model.addAttribute(DataUtils.TOKEN_ADD_PROJECT_2, token);
        session.setAttribute(DataUtils.TOKEN_ADD_PROJECT_2, token);
        return "front/project/addProject-second";
    }

    /**
     * 发布项目操作: 第二步
     * @return
     */
    @RequestMapping(value = "/addProjectHandle2")
    public String addProjectHandle2(Integer projectid,
                                    Integer targetnum,
                                    String enddate,
                                    String token,
                                    HttpSession session, Model model) {
        // 判断是否重复提交表单
//        if(token == null || "".equals(token) || ifTokenRight(DataUtils.TOKEN_ADD_PROJECT_2, token, session) == false) {
//            model.addAttribute("errorMsg", "请勿重复提交表单");
//            return "front/error/error";
//        }
//        session.removeAttribute(DataUtils.TOKEN_ADD_PROJECT_2);

        Project project = this.projectService.getObjById(projectid);
        project.setTargetnum(targetnum);
        project.setEnddate(enddate);

        this.projectService.update(project);
//        return "front/project/addProject-success";
        return "redirect:/front/Project/addProjectSuccess.do";
    }

    /**
     * 发布项目成功界面
     * @return
     */
    @RequestMapping(value = "/addProjectSuccess")
    public String addProjectSuccess() {
        return "front/project/addProject-success";
    }

    /**
     * 项目详情页
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/projectInfo")
    public String projectInfo(Integer id, Model model, HttpSession session, HttpServletRequest request) {
        Project project = this.projectService.getObjById(id);
        Map<String, Object> projectMap = this.projectService.getProjectInfo(project, session);

        Integer pCount = this.commentService.getDisplayCommentCountByProjectId(id);
        Integer proid = this.projectService.getProIdByProjectId(project.getId());
        Product product = this.productService.getObjById(proid);
        User user = this.userService.getObjById(project.getUserid());
        List<Label> labelList = this.projectService.getLabelList(id);
        Cate cate = this.cateService.getFirstCateById(this.cateService.getCateById(product.getCateid()));

        projectMap.put("commentnum", pCount);
        projectMap.put("user", user);
        projectMap.put("product", product);
        projectMap.put("labelList", labelList);
        projectMap.put("catename", cate.getCatename());

        model.addAttribute("projectMap", projectMap);
        model.addAttribute("url", request.getRequestURL() + "?" + request.getQueryString());
        return "front/project/projectInfo";
    }

    /**
     * 支持者列表
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/getBackers")
    public String getBackers(Integer id, Model model) {
        List<Map<String, Object>> pbMapList = this.projectService.getProjectBackerList(id);
        model.addAttribute("pbMapList", pbMapList);
        return "front/project/backers";
    }

    /**
     * 评论项目
     * @param projectid
     * @param content
     * @param session
     * @return
     */
    @RequestMapping(value = "/addComment")
    @ResponseBody
    public String addComment(Integer projectid, String content, HttpSession session) {
        Integer uid = Integer.parseInt(session.getAttribute("userid").toString());
//        Integer uid = 1;

        // 1.保存 zc_comment 表数据
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setProjectid(projectid);
        comment.setUserid(uid);
        comment.setLikenum(0);
        comment.setReleasedate(DateUtils.getCurrentDate1());
//        comment.setStatus(DataUtils.I_C_STATUS_WAIT_EXAME);
        comment.setStatus(DataUtils.I_C_STATUS_ALREADY_PASS);
        this.commentService.insertComment(comment);

        // 2.发送评论消息
        User commenter = this.userService.getObjById(uid);
        String url = DataUtils.WEB_PHONE_HOMPAGE + "/front/Project/projectInfo.do?id=" + projectid;
        Project project = this.projectService.getObjById(projectid);
        String projectCreator = this.userService.getOpenidById(project.getUserid());
        WxPayApi.sendCommentMsg(projectCreator, url, commenter.getNickname(), content, DateUtils.getCurrentDate2());
        return "success";
    }

    /**
     * 点赞
     * @param commentid
     * @param session
     * @return
     */
    @RequestMapping(value = "/addZan")
    @ResponseBody
    public String addZan(Integer commentid, HttpSession session) {
        Integer uid = Integer.parseInt(session.getAttribute("userid").toString());
//        Integer uid = 1;

        // 1.zc_comment 表修改 likenum 数据: +1
        Comment comment = this.commentService.getCommentById(commentid);
        comment.setLikenum(comment.getLikenum() + 1);
        this.commentService.updateComment(comment);

        // 2.zc_comment_user 表插入数据
        CommentUser cu = new CommentUser();
        cu.setCommentid(commentid);
        cu.setUserid(uid);
        this.commentService.insertCommentUser(cu);

        return "success";
    }

    /**
     * 取消点赞
     * @param commentid
     * @param session
     * @return
     */
    @RequestMapping(value = "/cancelZan")
    @ResponseBody
    public String cancelZan(Integer commentid, HttpSession session) {
        Integer uid = Integer.parseInt(session.getAttribute("userid").toString());
//        Integer uid = 1;

        // 1.zc_comment 表修改 likenum 数据: -1
        Comment comment = this.commentService.getCommentById(commentid);
        comment.setLikenum(comment.getLikenum() - 1);
        this.commentService.updateComment(comment);

        // 2.zc_comment_user 表删除数据
        this.commentService.deleteCommentUser(commentid, uid);

        return "success";
    }

    /**
     * 评论列表页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/getComments")
    public String getComments(Integer id, Model model, HttpSession session) {
        List<Map<String, Object>> cMapList = this.commentService.getDisplayCommentsByProjectId(id, session);
        model.addAttribute("cMapList", cMapList);
        model.addAttribute("projectid", id);
        return "front/project/comments";
    }

    /**
     * 判断tooken是否正确
     * @param sessionName
     * @param token
     * @param session
     * @return
     */
    private boolean ifTokenRight(String sessionName, String token, HttpSession session) {
        Object oSToken = session.getAttribute(sessionName);
        if(oSToken == null) {
            return false;
        }

        if(!token.equals(oSToken.toString())) {
            return false;
        }
        return true;
    }
}
