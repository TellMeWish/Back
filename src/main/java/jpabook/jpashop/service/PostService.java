package jpabook.jpashop.service;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.Photo;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.User;
import jpabook.jpashop.dto.PhotoDTO;
import jpabook.jpashop.dto.post.CreatePostDto;
import jpabook.jpashop.dto.post.GetPostDto;
import jpabook.jpashop.dto.post.UpdatePostDto;
import jpabook.jpashop.repository.PhotoRepository;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PhotoRepository photoRepo;


    @Transactional
    public void insertPost(CreatePostDto.Request reqDto, List<MultipartFile> files) throws Exception{
        User user = userRepo.findById(reqDto.getUserId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        Post post = modelMapper.map(reqDto, Post.class);


        //다중파일처리
        List<Photo> photoList = FileHandler.parseFileInfo(post, files);
        if(!photoList.isEmpty()) {
            for(Photo photo : photoList) {
                post.addPhoto(photoRepo.save(photo));
            }
        }
        post.setPhotos(photoList);

        post.setPost_user_id(user);
        postRepo.save(post);
    }

  /*  public Post updatePost(Post post, Long id) {
        Post findPost = postRepo.findById(id).get();

        findPost.setContent(post.getContent());
        findPost.setTitle(post.getTitle());

        return postRepo.save(findPost);
    }*/

    public void updatePost(UpdatePostDto.Request reqDto, Long id, List<MultipartFile> multipartFileList) throws Exception {
        Post findPost = postRepo.findById(id).get();

        findPost.setContent(reqDto.getContent());
        findPost.setTitle(reqDto.getTitle());
        findPost.setCategory(reqDto.getCategory());
        findPost.setIsParticipate(reqDto.getIsParticipate());
        findPost.setIsPrivate(reqDto.getIsPrivate());
        //Post post = modelMapper.map(reqDto, Post.class);

        //파일처리
        List<MultipartFile> newMultipartFileList = updatePhotoList(multipartFileList, id);
        List<Photo> photoList = FileHandler.parseFileInfo(findPost, newMultipartFileList);
        if(!photoList.isEmpty()){
            photoRepo.saveAll(photoList);
        }
        //findPost.setPhotos(photoList);


        postRepo.save(findPost);
    }

    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }

    public GetPostDto.Response getPost(Long id, List<Long> photoId) {
        Post post =  postRepo.findById(id).orElseThrow(()-> new SowonException(Status.NOT_FOUND));
        //post.setPhotos(photoId);

        GetPostDto.Post resPost = modelMapper.map(post, GetPostDto.Post.class);
        resPost.setPhotoIdList(photoId);

        return GetPostDto.Response.builder().post(resPost)
                .build();
    }


    public Page<Post> findAllPage(Pageable pageable) {

        return postRepo.findAll(pageable);

    }

  /*  public void findAllPage(Pageable pageable) {

         postRepo.findAll(pageable).map(GetPostDto.Response::from);

    }*/

    public List<MultipartFile> updatePhotoList(List<MultipartFile> oldMultiFileList, Long postId) {
        List<Photo> dbPhotoList = photoRepo.findAllByPostId(postId);
        List<MultipartFile> newFileList = new ArrayList<>();

        if (CollectionUtils.isEmpty(dbPhotoList)) { // DB에 아예 존재 x
            if (!CollectionUtils.isEmpty(oldMultiFileList)) { // 전달되어온 파일이 하나라도 존재
                // 저장할 파일 목록에 추가
                newFileList.addAll(oldMultiFileList);
            }
        } else {  // DB에 한 장 이상 존재
            if (CollectionUtils.isEmpty(oldMultiFileList)) { // 전달되어온 파일 아예 x
                // 파일 삭제
                for (Photo dbPhoto : dbPhotoList)
                    photoRepo.deleteById(dbPhoto.getId());
            }else {  // 전달되어온 파일 한 장 이상 존재

                // DB에 저장되어있는 파일 원본명 목록
                List<String> dbOriginNameList = new ArrayList<>();

                // DB의 파일 원본명 추출
                for(Photo dbPhoto : dbPhotoList) {
                    // file id로 DB에 저장된 파일 정보 얻어오기
                    Photo entity = photoRepo.findById(dbPhoto.getId()).orElseThrow(()
                            -> new IllegalArgumentException("파일이 존재하지 않습니다"));

                    PhotoDTO dbPhotoDto = PhotoDTO.builder()
                            .fileOriName(entity.getFileOriName())
                            .fileUrl(entity.getFileUrl())
                            .fileSize(entity.getFileSize())
                            .build();
                    // DB의 파일 원본명 얻어오기
                    String dbOriFileName = dbPhotoDto.getFileOriName();


                    if(!oldMultiFileList.contains(dbOriFileName))  // 서버에 저장된 파일들 중 전달되어온 파일이 존재하지 않는다면
                        photoRepo.deleteById(dbPhoto.getId());  // 파일 삭제
                    else  // 그것도 아니라면
                        dbOriginNameList.add(dbOriFileName);	// DB에 저장할 파일 목록에 추가
                }

                for (MultipartFile multipartFile : oldMultiFileList) { // 전달되어온 파일 하나씩 검사
                    // 파일의 원본명 얻어오기
                    String multipartOrigName = multipartFile.getOriginalFilename();
                    if(!dbOriginNameList.contains(multipartOrigName)){   // DB에 없는 파일이면
                        newFileList.add(multipartFile); // DB에 저장할 파일 목록에 추가
                    }
                }
            }
        }
        return newFileList;
    }

}
