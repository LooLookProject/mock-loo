package io.github.shanepark.mockloo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpRequestConfig {

    @Value("${api.base.url}")
    private String baseUrl;

    @Bean
    public RequestApi requestApi() {
        WebClient client = WebClient.create(baseUrl);
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(client))
                .build()
                .createClient(RequestApi.class);
    }

}
