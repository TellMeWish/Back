package jpabook.jpashop.dto.post;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShareDto {

    private Long postId;
    private int progress;
}
