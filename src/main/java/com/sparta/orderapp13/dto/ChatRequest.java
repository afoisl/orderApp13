package com.sparta.orderapp13.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data  // Lombok을 사용하여 게터, 세터, toString, equals, hashCode 메서드를 자동으로 생성합니다.
@AllArgsConstructor  // 모든 필드를 포함한 생성자를 자동으로 생성합니다.
@NoArgsConstructor  // 기본 생성자를 자동으로 생성합니다.
@Builder  // 빌더 패턴을 사용해 객체를 생성할 수 있도록 합니다.
public class ChatRequest {
//    아래와 같은 형태로 보내기위해서 각각 쪼개는거임
//    {
//        "contents": [
//        {
//            "parts":[
//            {
//                "text": "짜장면을 설명해줘 30자 이내로"
//            }
//          ]
//        }
//      ]
//    }

    // Gemini API에 요청할 내용들을 담고 있는 리스트입니다.
    private List<Content> contents;

    /**
     * ChatRequest 생성자
     * 사용자로부터 전달받은 메시지 내용을 Content 객체로 래핑하여 contents 리스트에 추가합니다.
     *
     * @param prompt 요청할 메시지 텍스트
     */
    public ChatRequest(String prompt) {
        // 전달된 메시지 텍스트로 Content 객체를 생성해 리스트로 초기화합니다.
        this.contents = List.of(new Content(prompt));
    }

    /**
     * API 요청의 "contents" 속성을 구성하는 Content 클래스입니다.
     * 각 Content 객체는 여러 Part 객체로 구성됩니다.
     */
    @Data
    public static class Content {

        // 여러 개의 Part 객체를 포함한 리스트입니다.
        private List<Part> parts;

        /**
         * Content 생성자
         * 주어진 텍스트를 Part 객체로 변환하여 parts 리스트에 추가합니다.
         *
         * @param text 요청할 메시지 텍스트
         */
        public Content(String text) {
            // Part 객체로 래핑된 텍스트를 리스트로 초기화합니다.
            this.parts = List.of(new Part(text));
        }
    }

    /**
     * API 요청에서 각 메시지 텍스트를 담고 있는 Part 클래스입니다.
     * 각 Part 객체는 하나의 텍스트만을 포함합니다.
     */
    @Data
    public static class Part {

        // 요청 메시지의 텍스트를 나타냅니다.
        private String text;

        /**
         * Part 생성자
         * 주어진 텍스트를 Part의 text 필드에 설정합니다.
         *
         * @param text 요청할 메시지 텍스트
         */
        public Part(String text) {
            this.text = text;
        }
    }
}
