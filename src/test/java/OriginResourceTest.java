import com.neosavvy.angular.PhantomRendererConfiguration;
import com.neosavvy.angular.PhantomResource;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: aparrish
 * Date: 8/15/12
 * Time: 5:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class OriginResourceTest extends ResourceTest {

    @Test
    @Ignore
    public void testGetIndex() {
        client().resource("/").get(String.class);
    }

    @Test
    @Ignore
    public void testGetEnUSHome() {
        client().resource("/en_us/home").get(String.class);
    }

    @Test
    @Ignore
    public void testBadBindingIgnore() {
        String output = client().resource("/%7B%7BselectedProvider.url%7D%7D").get(String.class);
    }

    protected void setUpResources() throws Exception {

        PhantomRendererConfiguration configuration = new PhantomRendererConfiguration();

        configuration.baseUrl = "http://127.0.0.1";
        configuration.renderJSScript = "/HBO/hbo_go_html/hbogo-origin-poc/src/main/resources/render.js";

        addResource(new PhantomResource(configuration));
    }
}
