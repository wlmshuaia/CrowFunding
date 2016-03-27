import com.tide.service.ThemeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wengliemiao on 15/12/20.
 */
public class ThemeTest extends BaseSpring {

//    @Autowired
//    private ThemeLabelMapper themeLabelMapper;

    @Autowired
    private ThemeService themeService;

    @Test
    public void getByThemeLabelId() {
//        ThemeLabel tl = this.themeLabelMapper.getByThemeLabelId(1, 1);
//        System.out.println(tl.getId());
    }

    @Test
    public void getThemeMapList() {
//        List<Map<String, Object>> tMapList = this.themeService.getThemeMapList();
//
//        for (Map map : tMapList) {
//            Theme t = (Theme) map.get("theme");
//            List<Map<String, Object>> pList = (List<Map<String, Object>>) map.get("pMapList");
//
//            System.out.println("主题: "+t.getName());
//            for (Map<String, Object> m : pList) {
//                Project p = (Project) m.get("project");
//                System.out.println(p.getTitle()+", "+m.get("percent"));
//            }
//            System.out.println();
//        }
    }

    @Test
    public void getThemeMapList2() {
//        List<Map<String, Object>> tMapList = this.themeService.getThemeMapList();
//        System.out.println(tMapList.size());
//
//        for (Map map : tMapList) {
//            List<Map<String, Object>> pList = (List<Map<String, Object>>) map.get("pMapList");
//            System.out.println(pList.size());
////            for (Map<String, Object> m : pList) {
////                Project p = (Project) m.get("project");
////                System.out.println(p.getTitle()+", "+m.get("percent"));
////            }
////            System.out.println();
//        }
    }

    @Test
    public void getThemeProject() {
//        List<Map<String, Object>> pList = this.themeService.getThemeProjectByThemeid(16);
//
//        System.out.println(pList.size());
//
//        for (Map<String, Object> map  :pList) {
//
//        }
    }

}
