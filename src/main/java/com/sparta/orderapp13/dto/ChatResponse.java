package com.sparta.orderapp13.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data  // Lombok을 사용하여 게터, 세터, toString, equals, hashCode 메서드를 자동으로 생성합니다.
@AllArgsConstructor  // 모든 필드를 포함한 생성자를 자동으로 생성합니다.
@NoArgsConstructor  // 기본 생성자를 자동으로 생성합니다.
public class ChatResponse {

    // Gemini API로부터의 응답에서 생성된 답변 목록을 담는 리스트입니다.
    private List<Candidate> candidates;

    /**
     * 응답 메시지의 주요 내용을 담고 있는 Candidate 클래스입니다.
     * 각 Candidate는 하나의 Content 객체를 포함합니다.
     */
    @Data
    public static class Candidate {
        private Content content;
    }

    /**
     * 각 Candidate 객체에서 실제 텍스트 부분을 담고 있는 Content 클래스입니다.
     * Content 객체는 여러 Part 객체를 가질 수 있습니다.
     */
    @Data
    public static class Content {
        private List<Part> parts;
    }

    /**
     * 실제 텍스트 내용이 포함된 Part 클래스입니다.
     * 각 Part 객체는 하나의 텍스트를 포함합니다.
     */
    @Data
    public static class Part {
        private String text;
    }
}
