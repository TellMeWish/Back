package jpabook.jpashop.service;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.User;
import jpabook.jpashop.dto.post.CreatePostDto;
import jpabook.jpashop.dto.post.GetPostDto;
import jpabook.jpashop.dto.post.UpdatePostDto;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    public void insertPost(CreatePostDto.Request reqDto) {
        User user = userRepo.findById(reqDto.getUserId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        Post post = modelMapper.map(reqDto, Post.class);
        post.setPost_user_id(user);
        postRepo.save(post);
    }

    public void updatePost(UpdatePostDto.Request reqDto, Long id) {
        Post findPost = postRepo.findById(id).get();

        findPost.setContent(reqDto.getContent());
        findPost.setTitle(reqDto.getTitle());
        findPost.setCategory(reqDto.getCategory());
        findPost.setIsParticipate(reqDto.getIsParticipate());
        findPost.setIsPrivate(reqDto.getIsPrivate());
        //Post post = modelMapper.map(reqDto, Post.class);
        postRepo.save(findPost);
    }

    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }

    public GetPostDto.Response getPost(Long id) {
        Post post =  postRepo.findById(id).orElseThrow(()-> new SowonException(Status.NOT_FOUND));
        GetPostDto.Post resPost = modelMapper.map(post, GetPostDto.Post.class);

        return GetPostDto.Response.builder().post(resPost).build();
    }


    public List<Post> getPostList(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {

        Page<Post> pagePost =  postRepo.findAll(
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        List<Post> postList = pagePost.getContent();

        return postList;

    }

    public List<Post> getPostListByUserId(Long id, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {

        Page<Post> pagePost =  postRepo.findAllByUserId(id,
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        List<Post> postList = pagePost.getContent();

        return postList;

    }


    public List<Post> getLikedPostList(Long id, Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {

        Page<Post> pagePost =  postRepo.findLikedPostById(id,
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        List<Post> postList = pagePost.getContent();

        return postList;

    }

  /*  public void findAllPage(Pageable pageable) {

         postRepo.findAll(pageable).map(GetPostDto.Response::from);

    }*/


    @Transactional
    public int updateView(Long id) {
        return postRepo.updateView(id);
    }

}
