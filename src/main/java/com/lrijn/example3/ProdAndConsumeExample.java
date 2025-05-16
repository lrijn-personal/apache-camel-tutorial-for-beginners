
package com.lrijn.example3;
import org.apache.camel.CamelContext;          // Dit is de hoofdwerkomgeving van Camel
import org.apache.camel.ConsumerTemplate;      // Dit is voor het ontvangen van berichten
import org.apache.camel.Exchange;              // Dit is voor het doorgeven van berichten
import org.apache.camel.Processor;             // Dit is voor het verwerken van berichten
import org.apache.camel.ProducerTemplate;      // Dit is voor het versturen van berichten
import org.apache.camel.builder.RouteBuilder;   // Dit is voor het maken van routes
import org.apache.camel.impl.DefaultCamelContext; // Dit is de standaard Camel werkomgeving

public class ProdAndConsumeExample {
    

    public static void main(String[] args) throws Exception {
        

        CamelContext context = new DefaultCamelContext();
        

        context.addRoutes(new RouteBuilder() {
            
            @Override
            public void configure() throws Exception {

                from("direct:start")

                .process(new Processor() {
                    

                    public void process(Exchange exchange) throws Exception {

                        String message = exchange.getIn().getBody(String.class);
                        message = message + " - Lisa van rijn";
                        exchange.getOut().setBody(message);
                    }
                    
                })
                .to("seda:end");

                from("direct:start2")
                .to("seda:end2");
            }
            
        });
        

        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();

        producerTemplate.sendBody("direct:start", "Hello Everyone");
        producerTemplate.sendBody("direct:start2", "Hello Everyone 2");
        producerTemplate.sendBody("direct:start2", "Hello Everyone 3");
        

        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();

        String message = consumerTemplate.receiveBody("seda:end", String.class);
        String message2 = consumerTemplate.receiveBody("seda:end2", String.class);
        String message3 = consumerTemplate.receiveBody("seda:end2", String.class);

        System.out.println(message);
        System.out.println(message2);
        System.out.println(message3);
    }
}