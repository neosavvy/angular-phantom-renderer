package com.neosavvy.angular;

import com.yammer.dropwizard.logging.Log;
import org.apache.commons.io.IOUtils;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: aparrish
 * Date: 8/15/12
 * Time: 3:23 PM
 */
@Path("/")
public class PhantomResource {

    private static final Log LOG = Log.forClass(PhantomResource.class);

    private PhantomRendererConfiguration configuration;

    public PhantomResource(PhantomRendererConfiguration hboGoOriginConfiguration) {

        configuration = hboGoOriginConfiguration;

    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{name:.+}")
    public String get(@PathParam("name") String name) throws Exception {
        return callPhantom(name);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String get(@Context UriInfo uriInfo) throws Exception {
        String name = uriInfo.getPath();
        return callPhantom(name);
    }

    private String callPhantom(String name) throws IOException {

        try {
            checkInputValid( name );
        }
        catch ( InvalidInputError e )
        {
            LOG.info("Invalid input attempted to go through Origin >>> " + name);
            return "";
        }

        String pathToCache = configuration.baseUrl + name;
        File file = new File(configuration.renderJSScript);

        LOG.info("Caching up " + name + " in the varnish cache");
        LOG.info("Caching the following path: " + pathToCache);
        LOG.info("Running this file: "+file.getAbsolutePath());

        pathToCache = convertPathToCacheableUrl( pathToCache, name );
        LOG.info("=======CACHEABLEURL: > " + pathToCache + " <========");

        ProcessBuilder pb = new ProcessBuilder(
                "/usr/local/bin/phantomjs"
                ,file.getAbsolutePath()
                ,pathToCache);
        pb.redirectErrorStream(true);
        Process p = pb.start();

        InputStream input = p.getInputStream();

        StringWriter generated = new StringWriter();
        IOUtils.copy(input, generated, "UTF-8");
        return generated.toString();
    }

    private static final String BINDING_PATTERN =
            "\\{\\{.*\\}\\}";

    private void checkInputValid(String name) {
        Pattern startsWithBinding = Pattern.compile(BINDING_PATTERN);

        Matcher starts = startsWithBinding.matcher(name);

        if( starts.matches() )
        {
            throw new InvalidInputError();
        }
    }

    private String convertPathToCacheableUrl(String pathToCache, String nameOfResource) {

        pathToCache = configuration.baseUrl + "/" + nameOfResource;

        return pathToCache;

    }
}
