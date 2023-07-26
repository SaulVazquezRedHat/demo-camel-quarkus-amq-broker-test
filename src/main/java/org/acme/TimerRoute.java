package org.acme;
import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.Exchange;

public class TimerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        //from("timer:java?period=1000").id("route-java")
        //.log("Hello World from Java DSL");

        rest("/api")
            .post("/hello")
            .consumes("plain/text").type(String.class)
            .to("direct:greet");

            from("direct:greet")
                .log("sending ${body} to JMS queue")
                //.setBody(constant("Hello World ${body}"));
                .to("jms:queue:orders");

            from("jms:queue:orders") 
                .log("received ${body} from JMS queue");


    }
}