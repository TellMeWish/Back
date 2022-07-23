package jpabook.jpashop.controller;

import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.User;
import jpabook.jpashop.dto.CreatePostDto;
import jpabook.jpashop.dto.GetPostDto;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import jpabook.jpashop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;
/*

    @Autowired // Constructor 를 통한 Di
    public PostController(PostService postService) {
        this.postService = postService;
    }
*/


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreatePostDto.Request reqDto) {
        postService.insertPost(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto.Response> getPost(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.getPost(id));
    }
    /*@PutMapping("/update/{id}")
    public ResponseEntity<Post> update(@RequestBody Post post, @PathVariable Long id) {
        User user1 = new User();
        user1.setUser_id("testid");
        user1.setPw("testpw");
        user1.setNickname("test");
        user1.setPhone_num("010-1234-1234");
        userRepository.save(user1);

        post.setPost_user_id(user1);

        return ResponseEntity.ok()
                .body(postService.updatePost(post, id));
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestBody Post post, @PathVariable Long id) {
        return ResponseEntity.ok()
                .body(postService.updatePost(post,id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postService.deletePost(id);
    }


/*    @GetMapping("/list")
    public ResponseEntity<Post> read(@RequestParam Optional<Integer> page, @RequestParam Optional<String> sortBy){
        return ResponseEntity.ok()
                .body(postService.getPostListPage(page, sortBy));
    }*/

    @GetMapping("/postList")
    public Page<Post> getPosts(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy)
    {
        return postRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.ASC, sortBy.orElse("id")
                )
        );
    }

}
