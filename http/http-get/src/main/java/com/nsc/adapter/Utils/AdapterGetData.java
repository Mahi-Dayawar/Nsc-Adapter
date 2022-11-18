package com.nsc.adapter.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AdapterGetData {

	@Autowired
	@Qualifier("get_send_channel")
	MessageChannel getSendChannel;

	@Autowired
	@Qualifier("get_receive_channel")
	PollableChannel getReceiveChannel;

	@Value("${nsc.url}")
	String url;
	
	@Autowired
	@Qualifier("get_input_channel")
	MessageChannel getInputChannel;

	@Autowired
	@Qualifier("get_output_channel")
	PollableChannel getOutputChannel;

	@Value("${nsc.url1}")
	String url1;

	public String getApiData() {
		Message<?> message = MessageBuilder.withPayload("").build();
		getSendChannel.send(message);
		//System.out.println("payload from api call = " + getReceiveChannel.receive().getPayload().toString());
		log.info("url from yml = {}", url);
		return getReceiveChannel.receive().getPayload().toString();
	}
	
	public String getApiDataAcc() {
		Message<?> message = MessageBuilder.withPayload("").build();
		getInputChannel.send(message);
		//System.out.println("payload from api call = " + getReceiveChannel.receive().getPayload().toString());
		log.info("url from yml = {}", url1);
		return getOutputChannel.receive().getPayload().toString();
	}
}
