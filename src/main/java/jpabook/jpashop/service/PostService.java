package jpabook.jpashop.service;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.User;
import jpabook.jpashop.dto.CreatePostDto;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Post updatePost(Post post, Long id) {
        Post findPost = postRepo.findById(id).get();

        findPost.setContent(post.getContent());
        findPost.setTitle(post.getTitle());

        return postRepo.save(findPost);
    }

    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }

    public Optional<Post> getPost(Long id) {
        return postRepo.findById(id);
    }


    /*public Page<Post> getPostList(Post post) {
        Pageable pageable = PageRequest.of(0,10, Sort.Direction.DESC, "seq");
        return postRepo.getPostList(pageable);
    }*/
    public Page<Post> findAllPage(Pageable pageable) {
        return postRepo.findAll(pageable);
    }

    // PostService
  /*  public Page<Post> getPostListPage(Optional<Integer> page, Optional<String> sortBy) {
        return postRepo.findAll(
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }*/
}
