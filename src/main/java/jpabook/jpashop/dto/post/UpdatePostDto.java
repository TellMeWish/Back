package jpabook.jpashop.dto.post;

import lombok.Getter;
import lombok.Setter;

public class UpdatePostDto {
    @Getter
    @Setter
    public static class Request{
        private String content;
        private String title;
        private String category;
        private int isParticipate;
        private int isPrivate;
        private int isCompleted;
        private int isProgress;
    }

    public static class Response{

    }
}
