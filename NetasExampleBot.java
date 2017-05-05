package com.spring.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.spring.socket.NetasBotUtil;

//@Component
public class NetasExampleBot {

	@Autowired
	@Qualifier("exampleBot")
	NetasBotUtil exampleBot;

	@Bean
	public NetasBotUtil exampleBot() {
		System.out.println("bean created " + System.currentTimeMillis());
		NetasBotUtil exampleBot = new NetasBotUtil();
		exampleBot.setHost("47.168.150.191");
		exampleBot.setPort(8080);
		exampleBot.setBotName("netasbot");
		exampleBot.setPassword("1234");
		exampleBot.setRegisteredKeywords(new String[] { "?", "help" });
		try {
			exampleBot.start(exampleBot, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exampleBot;
	}

	public void handleMessage(String from, String keyword, String message) {
		
		try {
			System.out.println("Message from " + from + " message is " + message);
			exampleBot.sendMessage(from, "You wrote "  + message);
			Thread.sleep(500);
			//exampleBot.sendFile(from, "static/hang/man1.png", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
