package com.kingbom.rsocketclient;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;

@SpringBootApplication
public class RsocketClientApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(RsocketClientApplication.class, args);
		System.in.read();
	}

	@Bean
	public RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
		return builder.connectTcp("localhost", 8888).block();
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> client(RSocketRequester client) {
		return arguments -> {
			var greetingResponse = client.route("greetings").data(new GreetingRequest("Kingbom")).retrieveFlux(GreetingResponse.class);
			greetingResponse.subscribe(System.out::println);
		};
	}
}


