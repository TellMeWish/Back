package jpabook.jpashop.dto.post;

import jpabook.jpashop.domain.wish.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdatePostDto {
    @Getter
    @NoArgsConstructor
    public static class Request{
        private String content;
        private String title;
        private String category;
        private int isParticipate;
        private int isPrivate;
        private int isCompleted;
        private int isProgress;
        private Location location;

        @Builder
        public Request(String content, String title, String category, int isParticipate, int isPrivate, int isCompleted, int isProgress, Location location) {
            this.content = content;
            this.title = title;
            this.category = category;
            this.isParticipate = isParticipate;
            this.isPrivate = isPrivate;
            this.isCompleted = isCompleted;
            this.isProgress = isProgress;
            this.location = location;
        }

    }

    @Getter
    @Setter
    public static class Location{
        private float latitude;
        private float longitude;
    }

}
