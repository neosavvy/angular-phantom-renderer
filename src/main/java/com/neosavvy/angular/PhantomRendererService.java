package com.neosavvy.angular;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

/**
 * Created with IntelliJ IDEA.
 * User: aparrish
 * Date: 8/15/12
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhantomRendererService extends Service<PhantomRendererConfiguration> {

    public static void main(String[] args) throws Exception
    {
        new PhantomRendererService().run(args);
    }

    protected PhantomRendererService() {
        super("phantom-renderer-service");
    }

    protected void initialize(
            PhantomRendererConfiguration hboGoOriginConfiguration
            ,Environment environment) throws Exception {
        environment.addResource(new PhantomResource( hboGoOriginConfiguration ));
    }
}
