package com.sparta.orderapp13.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeminiRestTemplateConfig {

    @Value("${gemini.api.key}")  // application.properties에 정의된 Gemini API 키를 주입
    private String apiKey;


//     Gemini API와의 HTTP 요청을 위해 설정된 RestTemplate Bean을 생성
//     이 RestTemplate에는 요청 시 API 키를 자동으로 추가하도록 인터셉터가 포함

    @Bean
    public RestTemplate geminiRestTemplate() {
        // 새로운 RestTemplate 인스턴스를 생성
        RestTemplate restTemplate = new RestTemplate();

        // API 요청 시 자동으로 API 키를 추가하도록 인터셉터를 추가
        restTemplate.getInterceptors().add((request, body, execution) -> {
            // 현재 요청의 URI에 API 키를 쿼리 파라미터로 추가
            String urlWithKey = request.getURI().toString() + "?key=" + apiKey;

            // Content-Type 및 Accept 헤더를 JSON 형식으로 설정
            request.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
            request.getHeaders().set(HttpHeaders.ACCEPT, "application/json");

            // 수정된 URL을 통해 요청을 전송
            return execution.execute(request, body);
        });

        // 구성된 RestTemplate을 반환하여 애플리케이션 내에서 사용할 수 있도록 합니다.
        return restTemplate;
    }
}
