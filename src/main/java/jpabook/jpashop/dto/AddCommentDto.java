package jpabook.jpashop.dto;


import lombok.Getter;
import lombok.Setter;


public class AddCommentDto {

    @Getter
    @Setter
    public static class Request {
        private Long userId;//로그인 사용자 PK
        private Long postId; //포스트 PK
        private Long parentId;//댓글이면 null, 대댓글이면 commentId
        private String content; //댓글내용
        private Boolean secret; //비밀댓글여부
    }
}
