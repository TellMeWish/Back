package jpabook.jpashop.service;

import jpabook.jpashop.domain.wish.Photo;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.PhotoDTO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FileHandler {

    public final static String rootPath = System.getProperty("user.dir");

    public static List<Photo> parseFileInfo(
            Post post,
            List<MultipartFile> multipartFiles) throws IOException {
        if (CollectionUtils.isEmpty(multipartFiles)) {
            return Collections.emptyList();
        }

        List<Photo> photoList = new ArrayList<>();

        //파일 이름 생성 + 경로설정
        String absolutePath = new File("").getAbsolutePath() + File.separator;
        String path = createFilePath();


        //업로드 디렉토리 생성
        File file = new File(path);
        boolean isDirectoryCreated = file.exists();
        if (!isDirectoryCreated) {
            System.out.println("폴더생성");
            System.out.println(file.getPath());
            System.out.println(file.getAbsolutePath());
            Files.createDirectories(file.toPath());
            //isDirectoryCreated = file.mkdir();
            System.out.println("폴더생성 완료");
        }
        System.out.println("폴더생성 구간을 지났습니다");


        //다중 파일 처리
        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileExtension;
            String contentType = multipartFile.getContentType();

            //파일 확장자 처리
            if(ObjectUtils.isEmpty(contentType)) {
                break;
            }
            else {
                if(contentType.contains("image/jpeg") || contentType.contains("image/jpg"))
                    originalFileExtension = ".jpg";
                else if(contentType.contains("image/png"))
                    originalFileExtension = ".png";
                else if(contentType.contains("image/gif"))
                    originalFileExtension = ".gif";
                else
                    break;
            }
            String new_file_name = System.nanoTime() + originalFileExtension;

            PhotoDTO photoDto = PhotoDTO.builder()
                    .fileOriName(multipartFile.getOriginalFilename())
                    .fileUrl(path + File.separator + new_file_name)
                    .fileSize(multipartFile.getSize())
                    .build();

            Photo photo = new Photo(
                    photoDto.getFileOriName(),
                    photoDto.getFileUrl(),
                    photoDto.getFileSize()
            );

            if(post.getId() != null) {
                photo.setPost(post);
            }

            photoList.add(photo);

            file = new File(absolutePath + path + File.separator + new_file_name);
            multipartFile.transferTo(file);
            file.setWritable(true);
            file.setReadable(true);

        }
        return photoList;
    }

    public static String createFilePath() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);

        return "images" + File.separator + current_date + File.separator;
    }


}

