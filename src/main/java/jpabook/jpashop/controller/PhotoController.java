package jpabook.jpashop.controller;

import jpabook.jpashop.domain.wish.Photo;
import jpabook.jpashop.dto.PhotoDTO;
import jpabook.jpashop.dto.PhotoResponseDTO;
import jpabook.jpashop.repository.PhotoRepository;
import jpabook.jpashop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RequiredArgsConstructor
@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final PostService postService;
    private final PhotoRepository photoRepo;

    //썸네일 이미지 조회시
    @CrossOrigin
    @GetMapping(
            value = "/thumbnail/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE}
    )
    public ResponseEntity<byte[]> getThumbnail(@PathVariable Long id) throws IOException {
        String absolutePath =
                new File("").getAbsolutePath() + File.separator + File.separator;
        String path;

        if(id != 0) {
            Photo photo = photoRepo.findById(id).orElseThrow(()
                    -> new IllegalArgumentException("파일이 존재하지 않습니다. ( 0 or 존재하는 photoID 입력 )"));
            path = photo.getFileUrl();
        }
        else {
            String thumbnailFolderPath = "images" + File.separator + "thumbnail" + File.separator;
            path = thumbnailFolderPath + "thumbnail.png";

            //썸네일 디렉토리가 없을시 새로 생성
            File file = new File(thumbnailFolderPath);
            boolean isDirectoryCreated = file.exists();
            if (!isDirectoryCreated) {
                Files.createDirectories(file.toPath());

            }
        }

        System.out.println("당신의 패스는? : " + path);


        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

    //이미지 개별로 조회시
    @CrossOrigin
    @GetMapping(
            value = "/{id}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE}
    )
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        Photo photo = photoRepo.findById(id).orElseThrow(()
                -> new IllegalArgumentException("파일이 존재하지 않습니다"));
        String absolutePath
                = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = photo.getFileUrl();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

//        PhotoResponseDTO photoDTO = new PhotoResponseDTO();
//        photoDTO.setData(imageByteArray);


        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }

}
