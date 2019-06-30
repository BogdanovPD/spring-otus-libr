package ru.otus.spring.libr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@IntegrationComponentScan
@EnableIntegration
public class LibrConfig {

    @Bean
    public QueueChannel newBooksChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel bookRequestsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean (name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
    }

    @Bean
    public IntegrationFlow newBooksFlow() {
        return IntegrationFlows.from("newBooksChannel")
                .split()
                .handle("newBookProcessingService", "save")
                .get();
    }

    @Bean
    public IntegrationFlow bookRequestFlow() {
        return IntegrationFlows.from("bookRequestsChannel")
                .split()
                .handle("bookRequestProcessingService", "processRequest")
                .get();
    }

}
