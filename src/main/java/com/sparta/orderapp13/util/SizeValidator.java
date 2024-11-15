package com.sparta.orderapp13.util;

import java.util.Arrays;
import java.util.List;

public class SizeValidator {

    // 허용된 페이지 사이즈 목록
    private static final List<Integer> ALLOWED_SIZES = Arrays.asList(10, 30, 50);
    private static final int DEFAULT_SIZE = 10;

    public static int validateSize(int size) {
        if (!ALLOWED_SIZES.contains(size)) {
            return DEFAULT_SIZE; // 허용되지 않은 값일 경우 기본값 반환
        }
        return size;
    }
}
