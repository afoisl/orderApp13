package com.sparta.orderapp13.config;

import com.sparta.orderapp13.entity.User;
import com.sparta.orderapp13.entity.UserRoleEnum;
import com.sparta.orderapp13.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class MasterAccountSetup {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createMasterAccount() {
        return args -> {
            String username = "master";
            if (userRepository.findByUserEmail("master@example.com").isEmpty()) {
                User master = new User();
                master.setUserEmail("master@example.com");
                master.setName("master"); // name을 "master"로 설정
                master.setPassword(passwordEncoder.encode("master1234")); // 초기 비밀번호 설정
                master.setRole(UserRoleEnum.MASTER);
                userRepository.save(master);
            }
        };
    }
}
