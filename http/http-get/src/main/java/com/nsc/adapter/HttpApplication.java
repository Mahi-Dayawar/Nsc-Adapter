package com.nsc.adapter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ImportResource("http-outbound-gateway.xml")
@Slf4j
public class HttpApplication {

	@Autowired
	@Qualifier("get_send_channel")
	MessageChannel getSendChannel;

	@Autowired
	@Qualifier("get_receive_channel")
	PollableChannel getReceiveChannel;
	
	@Value("${nsc.url}")
	String url;

	public static void main(String[] args) {
		SpringApplication.run(HttpApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			Message<?> message = MessageBuilder.withPayload("testdata").build();
			getSendChannel.send(message);
			String resp = getReceiveChannel.receive().getPayload().toString();
			System.out.println("payload from api call = " + resp);

			try {
				JSONParser parser = new JSONParser();
				JSONArray jsonArray = (JSONArray) parser.parse(resp);
				log.info("** parsed resp = {}", jsonArray.size());
			} catch (Exception e) {
				e.printStackTrace();
			}

			log.info("url from yml = {}", url);
		};
	}
}