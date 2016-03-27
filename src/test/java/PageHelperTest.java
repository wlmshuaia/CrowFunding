import com.github.pagehelper.PageHelper;
import com.tide.bean.Project;
import com.tide.service.ProjectService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wengliemiao on 16/1/4.
 */
public class PageHelperTest extends BaseSpring {

    @Autowired
    private ProjectService projectService;

    @Test
    public void startPage() {
        // 此处定义后, 紧跟着的查询语句会执行分页操作
        PageHelper.startPage(2, 8);
        List<Project> pList = this.projectService.getProjectByStatus("all");
        for (Project p : pList) {
            System.out.println(p.getTitle());
        }
    }
}
