import com.tide.utils.FileUtils;
import org.junit.Test;

/**
 * Created by wengliemiao on 16/3/10.
 */
public class OSSTest extends BaseSpring {

    @Test
    public void deleteObject() {
        String filename = "upload/1457594071640-5.png";
        FileUtils.deleteOSSFile(filename);
    }

}
