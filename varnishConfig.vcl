#Looks extremely helpful: http://kly.no/varnish/regex.txt

backend default {
      .host = "127.0.0.1";
      .port = "8080";
}

backend webserver {
      .host = "127.0.0.1";
      .port = "7777";
}


sub vcl_recv {

    if (req.url ~ "\.(png|gif|jpg|css|ico|svg|jpeg|js|otf|json|woff)$") {
        set req.backend = webserver;
    }
    else if ( req.url ~"\.js") {
        set req.backend = webserver;
    }
    else if ( req.url ~ "partial" )
    {
        set req.backend = webserver;
    }
    else
    {
        set req.backend = default;
    }
}

sub vcl_fetch {
    if ( beresp.ttl < 3600s ) {

        set beresp.ttl = 3600s;
    }
}



