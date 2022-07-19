package jpabook.jpashop.service;

import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepo;

    @Autowired // Constructor 를 사용한 Autowired
    public PostService(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public Post insertPost(Post post) {
       return postRepo.save(post);
    }

    public Post updatePost(Post post) {
        Post findPost = postRepo.findById(post.getId()).get();

        findPost.setContent(post.getContent());

        return postRepo.save(findPost);
    }

    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }

    public Optional<Post> getPost(Long id) {
        return postRepo.findById(id);
    }


    public Page<Post> getPostList(Post post) {
        Pageable pageable = PageRequest.of(0,10, Sort.Direction.DESC, "seq");
        return postRepo.getPostList(pageable);
    }

}
