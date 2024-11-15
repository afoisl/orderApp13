package com.sparta.orderapp13.util;

import java.util.Arrays;
import java.util.List;

public class SizeValidator {

    // 허용된 페이지 사이즈 목록
    private static final List<Integer> ALLOWED_SIZES = Arrays.asList(10, 30, 50);
    private static final int DEFAULT_SIZE = 10;

    /**
     * 입력된 사이즈를 검증하고 허용된 값으로 제한합니다.
     * @param size 입력된 페이지 사이즈
     * @return 허용된 사이즈 값
     */
    public static int validateSize(int size) {
        if (!ALLOWED_SIZES.contains(size)) {
            return DEFAULT_SIZE; // 허용되지 않은 값일 경우 기본값 반환
        }
        return size; // 허용된 값일 경우 그대로 반환
    }
}
