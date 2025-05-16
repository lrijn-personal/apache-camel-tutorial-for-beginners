package com.lrijn.example2;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class FileCopy {

    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

		context.addRoutes(new FileCopyRoute());

        while(true)
            context.start();
    }
}