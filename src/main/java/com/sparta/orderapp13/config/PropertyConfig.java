package com.sparta.orderapp13.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:properties/env.properties") // env.properties 파일 소스 등록
})
// Spring 부트스트랩 단계에서 PropertyConfig 클래스가 설정 클래스임을 인식합니다.
// 이때 @PropertySource 애너테이션을 통해 env.properties 파일을
// application.properties에 등록된 값처럼 사용할 수 있게 합니다.
public class PropertyConfig {

}