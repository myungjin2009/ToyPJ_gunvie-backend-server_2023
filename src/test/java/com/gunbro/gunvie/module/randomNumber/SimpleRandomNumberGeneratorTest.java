package com.gunbro.gunvie.module.randomNumber;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRandomNumberGeneratorTest {

    SimpleRandomNumberGenerator generator = new SimpleRandomNumberGenerator();

    @Test
    @DisplayName("6자리 랜덤한 숫자가 나오는지 1만번 테스트")
    void sixRandomNumberTest() {
        int generated;
        for (int i = 0; i < 10000; i++) {
            generated = Integer.parseInt(generator.generate(6));
            Assertions.assertThat(generated).isBetween(100000,999999);
        }
    }
    @Test
    @DisplayName("digit 값이 잘못된 값일 경우 테스트")
    void wrongDigitTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(9);
        });
    }
}