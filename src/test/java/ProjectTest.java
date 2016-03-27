import com.tide.bean.Project;
import com.tide.bean.ProjectColor;
import com.tide.bean.ProjectDesignimg;
import com.tide.bean.ProjectLabel;
import com.tide.dao.CommentMapper;
import com.tide.dao.FocusUserProjectMapper;
import com.tide.dao.ProjectColorMapper;
import com.tide.dao.ProjectMapper;
import com.tide.service.ProjectService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wengliemiao on 15/12/14.
 */
public class ProjectTest extends BaseSpring {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private FocusUserProjectMapper focusUserProjectMapper;

    @Autowired
    private ProjectColorMapper projectColorMapper;

    @Test
    public void getLabelList() {
        List<ProjectLabel> plList = this.projectService.getProjectLabelList(1);
        System.out.println(plList.size());
    }

    @Test
    public void getDesignimgList() {
        List<ProjectDesignimg> pdList = this.projectService.getDesignimgList(1);
        System.out.println(pdList.size());
    }

    @Test
    public void deleteProject() {
//        this.projectService.delete(2, session);
    }

    @Test
    public void deleteComment() {
        this.commentMapper.deleteByProjectId(1);
    }

    @Test
    public void deleteFUProject() {
        this.focusUserProjectMapper.deleteByProjectId(2);
    }

    @Test
    public void getProjectBacker() {
        Integer num = this.projectService.getProjectBackerNum(6);
        System.out.println(num);
    }

    @Test
    public void getProjectFund(){
        Integer num = this.projectService.getProjectFundNum(6);
        System.out.println(num);
    }

    @Test
    public void getProjectInfo() {
//        Map<String, Object> pMap = this.projectService.getProjectInfo(6);
//        System.out.println(pMap.get("product"));
    }

    @Test
    public void getProjectByBacker(){
//        Project p = this.projectService.getProjectByTradeno("1491765317697140");
//        System.out.println(p.getTitle());
    }

    /**
     * 生成项目编号: 时间+随机数+用户id
     */
    @Test
    public void createProjectNo() {
//        System.out.println(new Date().getTime());

//        Map<String, Integer> map = new HashMap<String, Integer>();
//        for (int i = 0; i < 10000; i ++) {
//            String projectNo = getRandom(4, 10).toString();
//            if(map.containsKey(projectNo)) {
//                map.put(projectNo, map.get(projectNo) + 1);
//            } else {
//                map.put(projectNo, 1);
//            }
//        }
//
//        System.out.println(map.keySet().size());

        for (int i = 0; i < 100; i ++) {
            testProjectNo();
        }

    }

    public void testProjectNo() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < 100; i ++) {
            String projectNo = getProjectNo() ;
            if(map.containsKey(projectNo)) {
                map.put(projectNo, map.get(projectNo) + 1);
            } else {
                map.put(projectNo, 1);
            }
        }

        System.out.println(map.keySet().size());
    }

    public String getProjectNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

//        String time = sdf.format(new Date());

        long time = new Date().getTime();

        Integer uid = 1;
        String random = getRandom(4, 10).toString();

        return  time + random + uid ;
    }

    public Object getRandom(Integer num, Integer range) {
        String random = "";
        for(int i = 0; i < num; i ++){
            random += (int)(Math.random()*range);
        }
        return random;
    }

    public Integer getRandom(Integer n) {
        return (int) (Math.random() * n);
    }

    @Test
    public void selectAll() {
        List<Project> pList = this.projectService.selectAll();
        for(Project p : pList) {
            System.out.println(p.getTitle());
        }
    }

    @Test
    public void getProjectAddTest() {
        Integer uid = 1;
//        String status = "all";
        String status = "crowfunding";
        List<Project> pList = this.projectService.getProjectAddByStatus(uid, status);
        for (Project p : pList) {
            System.out.println(p.getTitle());
        }
    }

    @Test
    public void getProjectsTest() {
//        List<Project> pList = this.projectMapper.selectAll();
//        System.out.println(pList.size());
//        for (Project p : pList) {
//            System.out.println(p.getTitle());
//        }
    }

    @Test
    public void searchTest() {
        List<Project> pList = this.projectMapper.getProjectBySearch("发布");
        System.out.println(pList.size());
    }

    @Test
    public void insertProjectColor() {

        List<ProjectColor> list = this.projectColorMapper.selectAll();
        for (ProjectColor pc : list) {
            pc.setColor("002黑色");
            this.projectColorMapper.updateByPrimaryKey(pc);
        }

    }

    @Test
    public void updateProject() {
        List<Project> list = this.projectService.selectAll();
        for (Project p : list) {
            p.setType(0);
            this.projectService.update(p);
        }
    }

}


