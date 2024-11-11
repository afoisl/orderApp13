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
    private String apiUrl; // Gemini API의 URL을 저장할 변수

    @Value("${gemini.api.key}")
    private String geminiApiKey; // Gemini API의 인증 키를 저장할 변수

    /**
     * 주어진 음식 이름을 사용하여 AI에게 음식 설명을 요청합니다.
     *
     * @param foodName 설명을 요청할 음식의 이름
     * @return AI가 생성한 음식 설명 문자열
     */
    public String getDescriptionFromAI(String foodName) {
        // AI에게 요청할 prompt(질문) 생성. 응답은 최대 30자로 제한
        String prompt = foodName + "을 설명해줘 30자 이내로";
        ChatRequest request = new ChatRequest(prompt); // 생성한 prompt로 ChatRequest 객체 초기화

        // 요청 URL에 API 키를 포함하여 최종 요청 URL 생성
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        // Gemini API에 요청을 전송하고 응답을 ChatResponse 객체로 받음
        ChatResponse response = restTemplate.postForObject(requestUrl, request, ChatResponse.class);

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
