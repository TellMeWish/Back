package jpabook.jpashop.dto.post;

import lombok.Getter;
import lombok.Setter;

public class UpdateCommentDto {

    @Getter
    @Setter
    public static class Request{
        private String content; //댓글내용
        private Boolean secret; //비밀댓글여부
    }
}
