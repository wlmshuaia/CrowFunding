import com.tide.bean.Project;
import com.tide.service.ProjectService;
import com.tide.utils.DataUtils;
import com.tide.utils.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by wengliemiao on 16/1/28.
 */
public class TaskTest extends BaseSpring {

    @Autowired
    public ProjectService projectService;

    @Test
    public void statusTest() {
        // 待审核的项目
        List<Project> pExamList = this.projectService.getProjectByStatus(DataUtils.I_PROJECT_WAIT_EXAME);
        for (Project p : pExamList) {
            Integer bwn = DateUtils.getDaysBetween(new Date(), p.getEnddate());
            System.out.println("id: "+p.getId()+", "+bwn);
            if(bwn < 0) { // 时间到尚未通过审核则为默拒
                System.out.println("overtime: "+p.getId());
                p.setStatus(DataUtils.I_PROJECT_AGAINST);
                this.projectService.update(p);
            }
        }
    }

    @Test
    public void dateTest() {
        Integer bwn = DateUtils.getDaysBetween(new Date(), "2016-01-27 18:00:00");
        System.out.println(bwn);
    }

    @Test
    public void stringTest() {
        String a = "a";
        String b = "a";
        System.out.println(a == b);
        System.out.println(a.equals(b));
    }

}
