package jpabook.jpashop.dto;

import jpabook.jpashop.domain.wish.Photo;
import lombok.Getter;

@Getter
public class PhotoResponseDTO {
    private Long fileId;

    public PhotoResponseDTO(Photo entity){
        this.fileId = entity.getId();
    }
}
