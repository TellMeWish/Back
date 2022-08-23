package jpabook.jpashop.dto.post;

import jpabook.jpashop.domain.wish.Post;
import lombok.*;

public class GetPostLocationDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private Post post;
    }

    @Getter
    @Setter
    public static class Post{
        private Long id;
        private Location location;
    }

    @Getter
    @Setter
    public static class Location{
        private float latitude;
        private float longitude;
    }
}
