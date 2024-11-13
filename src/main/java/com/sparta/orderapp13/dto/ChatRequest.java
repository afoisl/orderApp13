package com.sparta.orderapp13.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    // Gemini API에 요청할 내용들을 담고 있는 리스트
    private List<Content> contents;

//    Content 객체로 래핑해서 contents 리스트에 추가

    public ChatRequest(String prompt) {
        // 전달된 메시지 텍스트로 Content 객체를 생성해 리스트로 초기화합니다.
        this.contents = List.of(new Content(prompt));
    }


    @Data
    public static class Content {

        // 여러 개의 Part 객체를 포함한 리스트입니다.
        private List<Part> parts;

        public Content(String text) {
            // Part 객체로 래핑된 텍스트를 리스트로 초기화합니다.
            this.parts = List.of(new Part(text));
        }
    }


    @Data
    public static class Part {

        // 요청 메시지의 텍스트를 나타냅니다.
        private String text;

        public Part(String text) {
            this.text = text;
        }
    }
}
