package com.lrijn.example2;

import org.apache.camel.builder.RouteBuilder;

public class FileCopyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:input?noop=true")
        .to("file:output");
    }
    
}
    

