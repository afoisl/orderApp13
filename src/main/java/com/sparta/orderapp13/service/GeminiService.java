package com.sparta.orderapp13.service;

import com.sparta.orderapp13.dto.ChatRequest;
import com.sparta.orderapp13.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Qualifier("geminiRestTemplate") // RestTemplate 주입 명확히 지정
    private final RestTemplate restTemplate; // HTTP 요청을 처리하기 위한 RestTemplate 객체

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;


    public String getDescriptionFromAI(String foodName) {
        // AI에게 요청할 prompt(질문) 생성. 응답은 최대 30자로 제한
        String prompt = foodName + "을 설명해줘 30자 이내로";
        ChatRequest request = new ChatRequest(prompt); // 생성한 prompt로 ChatRequest 객체 초기화

        // 요청 URL에 API 키를 포함하여 최종 요청 URL 생성
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        // Gemini API에 요청을 전송하고 응답을 ChatResponse 객체로 받음
        ChatResponse response = restTemplate.postForObject(requestUrl, request, ChatResponse.class);

//        ChatResponse.class는 RestTemplate에서 postForObject 메서드를 사용할 때, 응답이 어떤 타입의 객체로 변환될지를 지정
//        RestTemplate은 서버에서 받은 응답 데이터를 ChatResponse 클래스의 형태로 자동으로 변환해줍니다.

        // 응답이 비어있지 않은 경우, 첫 번째 설명 부분을 반환
        if (response != null && !response.getCandidates().isEmpty()) {
            // 응답의 첫 번째 Candidate에서 첫 번째 Part의 텍스트를 추출하여 반환
            return response.getCandidates().get(0).getContent().getParts().get(0).getText();
        } else {
            // 응답이 없거나 오류가 발생한 경우 예외 발생
            throw new RuntimeException("Failed to receive response from Gemini API");
        }
    }
}
