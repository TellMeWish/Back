package jpabook.jpashop.controller;

import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired // Constructor 를 통한 Di
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/create")
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return ResponseEntity.ok()
                .body(postService.insertPost(post));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Post> read(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(postService.getPost(id).get());
    }
    @PutMapping("/update")
    public ResponseEntity<Post> read(@RequestBody Post post) {
        return ResponseEntity.ok()
                .body(postService.updatePost(post));
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
