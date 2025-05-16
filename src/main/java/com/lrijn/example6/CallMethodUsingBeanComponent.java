package com.lrijn.example6;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SimpleRegistry;


public class CallMethodUsingBeanComponent {
	public static void main(String[] args) throws Exception {
		

		MyService myService = new MyService();

		SimpleRegistry registry = new SimpleRegistry();

		registry.bind("myBeanService", myService);

		CamelContext context = new DefaultCamelContext(registry);
		

		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {

				from("direct:start")
				.to("bean:myBeanService?method=doSomething");
				
			}
		});

		context.start();

		ProducerTemplate template = context.createProducerTemplate();

		template.sendBody("direct:start", "Hello from bean");
		
	}

}
