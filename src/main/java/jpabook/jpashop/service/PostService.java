package jpabook.jpashop.service;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.*;

import jpabook.jpashop.dto.PhotoDTO;
import jpabook.jpashop.dto.post.*;
import jpabook.jpashop.repository.*;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final LocationRepository locationRepo;
    private final ModelMapper modelMapper;
    private final PhotoRepository photoRepo;
    private final LikesRepository likesRepo;

    private final ShareRepository shareRepository;


    @Transactional
    public void insertPost(CreatePostDto.Request reqDto, List<MultipartFile> files, Long userId) throws Exception {
        User user = userRepo.findById(userId).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        Post post = modelMapper.map(reqDto, Post.class);

        Location location = new Location();
        location.setPost(post);
        location.setLatitude(reqDto.getLocation().getLatitude());
        location.setLongitude(reqDto.getLocation().getLongitude());

        //다중파일처리
        List<Photo> photoList = FileHandler.parseFileInfo(post, files);
        if (!photoList.isEmpty()) {
            for (Photo photo : photoList) {
                post.addPhoto(photoRepo.save(photo));
            }
        }
        post.setPhotos(photoList);
        post.setPost_user_id(user);
        post.setLocation(location);
        postRepo.save(post);
    }


    @Transactional
    public void updatePost(UpdatePostDto.Request reqDto, Long id, List<MultipartFile> multipartFileList, Long userId) throws Exception {
        Post findPost = postRepo.findById(id).get();
        if(!findPost.getPost_user_id().getUserId().equals(userId)){
            throw new SowonException(Status.ACCESS_DENIED);
        }

        findPost.setContent(reqDto.getContent());
        findPost.setTitle(reqDto.getTitle());
        findPost.setCategory(reqDto.getCategory());
        findPost.setIsParticipate(reqDto.getIsParticipate());
        findPost.setIsPrivate(reqDto.getIsPrivate());
        findPost.setIsProgress(reqDto.getIsProgress());
        findPost.setIsCompleted(reqDto.getIsCompleted());
        Location location = locationRepo.findLocationByPostId(findPost.getId());
        location.setLongitude(reqDto.getLocation().getLongitude());
        location.setLatitude(reqDto.getLocation().getLatitude());

        //파일처리
        List<MultipartFile> newMultipartFileList = updatePhotoList(multipartFileList, id);
        List<Photo> photoList = FileHandler.parseFileInfo(findPost, newMultipartFileList);
        for(Photo photo : photoList) {
            System.out.println("리스트 - " + photo.getFileOriName());
        }
        if (!photoList.isEmpty()) {
            photoRepo.saveAll(photoList);
        }
        //findPost.setPhotos(photoList);

        postRepo.save(findPost);
    }

    @Transactional
    public void deletePost(Long id, Long userId) {

        Post findPost = postRepo.findById(id).get();

        if(!findPost.getPost_user_id().getUserId().equals(userId)){
            throw new SowonException(Status.ACCESS_DENIED);
        }

        postRepo.delete(findPost);

    }

    public GetPostDto.Response getPost(Long userId, Long id, List<Long> photoId) {
        Post post = postRepo.findById(id).orElseThrow(() -> new SowonException(Status.NOT_FOUND));
        //post.setPhotos(photoId);
        // 해당 유저가 해당 포스트에 좋아요 했는지
        Optional<Likes> likesOptional = likesRepo.findByPostAndUserUserId(post, userId);

        //해당 유저의 포스트인지
        Optional<Post> postOptional = postRepo.findByPostAndUserId(id, userId);

        GetPostDto.Post resPost = modelMapper.map(post, GetPostDto.Post.class);
        resPost.setPhotoIdList(photoId);
        resPost.setIsLike(likesOptional.isPresent());
        resPost.setIsMyPost(postOptional.isPresent());

        Optional<Share> shareOptional = shareRepository.findByPostAndUserUserId(post, userId);

        resPost.setIsShare(shareOptional.isPresent());

        if(shareOptional.isPresent()){ // 공유한 글일 경우
            Share share = shareRepository.findByPostAndUserId(post, userId);
            resPost.setMyProgress(share.getProgress());

        }

        return GetPostDto.Response.builder().post(resPost)
                .build();
    }


    public List<Post> getPostList(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {

        Page<Post> pagePost = postRepo.findAll(
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        return pagePost.getContent();

    }

    public List<GetPostListDto.Post> getPostListDtoWithPhotoIdSetting(List<Post> postList) {

        List<GetPostListDto.Post> resPostList = postList.stream()
                .map(post -> modelMapper.map(post, GetPostListDto.Post.class))
                .collect(Collectors.toList());
        Long plID = 0L;

        for(int i =0; i < postList.size(); i++) {
            if(!postList.get(i).getPhotos().isEmpty())  //첨부파일 존재
                plID = postList.get(i).getPhotos().get(0).getId();
            else
                plID = 0L;

            resPostList.get(i).setPhotoId(plID);
        }

        return resPostList;
    }

    public List<GetMyPostListDto.Post> getMyPostListDtoWithPhotoIdSetting(List<Post> postList) {

        List<GetMyPostListDto.Post> resPostList = postList.stream()
                .map(post -> modelMapper.map(post, GetMyPostListDto.Post.class))
                .collect(Collectors.toList());
        Long plID = 0L;

        for(int i =0; i < postList.size(); i++) {
            if(!postList.get(i).getPhotos().isEmpty())  //첨부파일 존재
                plID = postList.get(i).getPhotos().get(0).getId();
            else
                plID = 0L;

            resPostList.get(i).setPhotoId(plID);
        }

        return resPostList;
    }

    public List<Post> getPostListByUserId(Long id, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {

        Page<Post> pagePost = postRepo.findAllByUserId(id,
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        List<Post> postList = pagePost.getContent();
        return postList;

    }

    public List<Post> getPostListByUserIdIncludeShare(Long id, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {

        Page<Post> pagePost = postRepo.findPostByIdIncludeShare(id,
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        List<Post> postList = pagePost.getContent();
/*
        ResponseEntity.ok().body(GetCommentListDto.Response.builder()
                        .commentList(commentList.stream()
                                .map(comment -> modelMapper.map(comment, GetCommentListDto.Comment.class))
                                .collect(Collectors.toList()))*/

//        List<GetMyPostListDto.Post> collect = postList.stream()
//                .map(post -> modelMapper.map(post, GetMyPostListDto.Post.class))
//                .collect(Collectors.toList());
        return postList;
    }

    public List<Post> getPostListByKeyword(String category, String keyword, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        Page<Post> pagePost = null;
        if(category.equals("all")){
            pagePost = postRepo.findPostsByKeyword(
                    keyword,
                    PageRequest.of(
                            page.orElse(0),
                            size.orElse(30),
                            Sort.Direction.DESC, sortBy.orElse("id")
                    )
            );
        } else {
            pagePost = postRepo.findPostsByKeywordCategory(
                    category,
                    keyword,
                    PageRequest.of(
                            page.orElse(0),
                            size.orElse(30),
                            Sort.Direction.DESC, sortBy.orElse("id")
                    )
            );
        }

        List<Post> postList = pagePost.getContent();
        return postList;
    }



    public List<Post> getLikedPostList(Long userId, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {

        Page<Post> pagePost = postRepo.findLikedPostById(userId,
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        List<Post> postList = pagePost.getContent();

        return postList;

    }

    public List<MultipartFile> updatePhotoList(List<MultipartFile> oldMultiFileList, Long postId) {
        List<Photo> dbPhotoList = photoRepo.findAllByPostId(postId);
        List<MultipartFile> newFileList = new ArrayList<>();

        if (CollectionUtils.isEmpty(dbPhotoList)) { // DB에 아예 존재 x
            if (!CollectionUtils.isEmpty(oldMultiFileList)) { // 전달되어온 파일이 하나라도 존재
                newFileList.addAll(oldMultiFileList);
            }
        } else {  // DB에 한 장 이상 존재
            if (CollectionUtils.isEmpty(oldMultiFileList)) { // 전달되어온 파일 아예 x
                // 파일 삭제
                for (Photo dbPhoto : dbPhotoList)
                    photoRepo.deleteById(dbPhoto.getId());
            } else {  // 전달되어온 파일 한 장 이상 존재
                List<String> oldMultiFileNameList = oldMultiFileList.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.toList());
                List<String> dbOriginNameList = new ArrayList<>();
                for (Photo dbPhoto : dbPhotoList) {
                    Photo entity = photoRepo.findById(dbPhoto.getId()).orElseThrow(()
                            -> new IllegalArgumentException("파일이 존재하지 않습니다"));

                    PhotoDTO dbPhotoDto = PhotoDTO.builder()
                            .fileOriName(entity.getFileOriName())
                            .fileUrl(entity.getFileUrl())
                            .fileSize(entity.getFileSize())
                            .build();

                    String dbOriFileName = dbPhotoDto.getFileOriName();


                    if (!oldMultiFileNameList.contains(dbOriFileName)) {
                        photoRepo.deleteById(dbPhoto.getId());
                    }
                    else
                        dbOriginNameList.add(dbOriFileName);
                }

                for (MultipartFile multipartFile : oldMultiFileList) {
                    String multipartOrigName = multipartFile.getOriginalFilename();
                    if (!dbOriginNameList.contains(multipartOrigName)) {
                        newFileList.add(multipartFile);
                    }
                }


            }
        }
        return newFileList;
    }

    public List<Post> getMyCommentedPostList(Long id, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        Page<Post> pagePost = postRepo.findMyCommentedPostListById(id,
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        List<Post> postList = pagePost.getContent();
        return postList;
    }

    public List<Post> getPostListByCoord(Float lat, Float lng, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy){
        Float r = 0.9f;

        Page<Post> pagePost = postRepo.findPostByCoord((double)lat, (double)lng, (double)r,
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(40),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );
        List<Post> postList = pagePost.getContent();
        return postList;
    }

    @Transactional
    public int updateView(Long id){
        return postRepo.updateView(id);
    }
}
