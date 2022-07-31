package jpabook.jpashop.controller;

import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.post.CreatePostDto;
import jpabook.jpashop.dto.post.GetPostDto;
import jpabook.jpashop.dto.post.GetPostListDto;
import jpabook.jpashop.dto.post.UpdatePostDto;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import jpabook.jpashop.service.LikesService;
import jpabook.jpashop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final LikesService likesService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreatePostDto.Request reqDto) {
        postService.insertPost(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto.Response> getPost(@PathVariable Long id) {

        postService.updateView(id); // views ++

        return ResponseEntity.ok().body(postService.getPost(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestBody UpdatePostDto.Request reqDto, @PathVariable Long id) {
        postService.updatePost(reqDto,id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postService.deletePost(id);
    }



    @GetMapping("/postList")
    public ResponseEntity<GetPostListDto.Response> getPostList(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy)
    {
       /* Page<Post> pagePost =  postRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );

        List<Post> postList = pagePost.getContent();*/



        List<Post> postList =  postService.getPostList(page,size,sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postList.stream().map(post -> modelMapper.map(post, GetPostListDto.Post.class)).collect(Collectors.toList()))
                .build());

    }


    @PostMapping("/like/{postId}/{userId}")
    public void likes(@PathVariable Long postId, @PathVariable Long userId){
        likesService.likes(postId, userId);
    }

    @DeleteMapping("/unLike/{postId}/{userId}")
    public void unLikes(@PathVariable Long postId, @PathVariable Long userId){
        likesService.unLikes(postId, userId);
    }

}
