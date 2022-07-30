package jpabook.jpashop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PhotoDTO {

    private String fileUrl;
    private String fileOriName;
    private String fileName;
    private Long fileSize;

    @Builder
    public PhotoDTO(String fileOriName, String fileUrl, Long fileSize) {
        this.fileOriName = fileOriName;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }

}
