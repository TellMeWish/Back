package jpabook.jpashop.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private Value error;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Value {
        private String code;
        private String message;
    }
}