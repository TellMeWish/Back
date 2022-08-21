package jpabook.jpashop.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenUserDTO {
    private String token;
    private Long id;
    private String username;
    private String nickname;
}