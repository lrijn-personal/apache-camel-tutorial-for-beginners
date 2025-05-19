package com.lrijn.example4;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class ActiveMQConsumer {
	public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        context.addComponent("activemq", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:my_queue")
                .to("seda:end");
            }
        }); 
        
        context.start();
        
        ConsumerTemplate template = context.createConsumerTemplate();
        String body = template.receiveBody("seda:end", String.class);
        
        System.out.println(body);

        context.stop();
	}

}
