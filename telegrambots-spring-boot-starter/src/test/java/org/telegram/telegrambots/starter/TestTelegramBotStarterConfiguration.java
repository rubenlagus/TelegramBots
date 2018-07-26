package org.telegram.telegrambots.starter;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import static org.mockito.Mockito.*;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

public class TestTelegramBotStarterConfiguration {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(MockTelegramBotsApi.class, TelegramBotStarterConfiguration.class));

	@Test
	public void createMockTelegramBotsApiWithDefaultSettings() {
		this.contextRunner.run((context) -> {
			assertThat(context).hasSingleBean(TelegramBotsApi.class);
			assertThat(context).hasSingleBean(TelegramBotInitializer.class);
			assertThat(context).doesNotHaveBean(LongPollingBot.class);
			assertThat(context).doesNotHaveBean(WebhookBot.class);
			verifyNoMoreInteractions(context.getBean(TelegramBotsApi.class));
		});
	}
	
	@Test
	public void createOnlyLongPollingBot() {
		this.contextRunner.withUserConfiguration(LongPollingBotConfig.class)
				.run((context) -> {
					assertThat(context).hasSingleBean(LongPollingBot.class);
					assertThat(context).doesNotHaveBean(WebhookBot.class);
					
					TelegramBotsApi telegramBotsApi = context.getBean(TelegramBotsApi.class);
			        
					verify(telegramBotsApi, times(1)).registerBot( context.getBean(LongPollingBot.class) );
			        verifyNoMoreInteractions(telegramBotsApi);
		});
	}
	
	@Test
	public void createOnlyWebhookBot() {
		this.contextRunner.withUserConfiguration(WebhookBotConfig.class)
				.run((context) -> {
					assertThat(context).hasSingleBean(WebhookBot.class);
					assertThat(context).doesNotHaveBean(LongPollingBot.class);
					
					TelegramBotsApi telegramBotsApi = context.getBean(TelegramBotsApi.class);
					
			        verify(telegramBotsApi, times(1)).registerBot( context.getBean(WebhookBot.class) );
			        verifyNoMoreInteractions(telegramBotsApi);
		});
	}
	
	@Test
	public void createLongPoolingBotAndWebhookBot() {
		this.contextRunner.withUserConfiguration(LongPollingBotConfig.class, WebhookBotConfig.class)
				.run((context) -> {
					assertThat(context).hasSingleBean(LongPollingBot.class);
					assertThat(context).hasSingleBean(WebhookBot.class);

					TelegramBotsApi telegramBotsApi = context.getBean(TelegramBotsApi.class);
					
			        verify(telegramBotsApi, times(1)).registerBot( context.getBean(LongPollingBot.class) );
			        verify(telegramBotsApi, times(1)).registerBot( context.getBean(WebhookBot.class) );
			        //verifyNoMoreInteractions(telegramBotsApi);
		});
	}

	@Configuration
	static class MockTelegramBotsApi{

		@Bean
		public TelegramBotsApi telegramBotsApi() {
			return mock(TelegramBotsApi.class);
		}
	}
	
	@Configuration
	static class LongPollingBotConfig{
		@Bean
		public LongPollingBot longPollingBot() {
			return mock(LongPollingBot.class);
		}
	}
	
	@Configuration
	static class WebhookBotConfig{
		@Bean
		public WebhookBot webhookBot() {
			return mock(WebhookBot.class);
		}
	}
}
