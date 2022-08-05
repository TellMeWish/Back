package jpabook.jpashop.controller;

import io.swagger.annotations.ApiOperation;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.post.*;
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

import javax.validation.Valid;
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


    @ApiOperation(value = "게시글 등록")
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

    @ApiOperation(value = "게시글 id별 게시글 조회")
    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestBody UpdatePostDto.Request reqDto, @PathVariable Long id) {
        postService.updatePost(reqDto,id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postService.deletePost(id);
    }



    @ApiOperation(value = "게시글 목록 조회")
    @GetMapping("/postList")
    public ResponseEntity<GetPostListDto.Response> getPostList(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy)
    {
        List<Post> postList =  postService.getPostList(page,size,sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postList.stream().map(post -> modelMapper.map(post, GetPostListDto.Post.class)).collect(Collectors.toList()))
                .build());

    }

    @ApiOperation(value = "사용자 id별 게시글들 조회")
    @GetMapping("/postList/{id}")
    public ResponseEntity<GetPostListDto.Response> getPostListByUserId(@PathVariable Long id, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy)
    {
        List<Post> postList =  postService.getPostListByUserId(id, page,size,sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postList.stream().map(post -> modelMapper.map(post, GetPostListDto.Post.class)).collect(Collectors.toList()))
                .build());

    }

    @ApiOperation(value = "게시글 좋아요", notes = "좋아요 안되어있을시 좋아요, 좋아요 되어있을시 좋아요 취소")
    @PostMapping("/like")
    public ResponseEntity<LikesDto> likes(@RequestBody @Valid LikesDto likesDto) {
        likesService.likes(likesDto);
       // return new ResponseEntity<>(likesDto, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "좋아요 한 게시글 목록", notes = "파라미터 값은 사용자 아이디")
    @GetMapping("/likedPostList/{id}")
    public ResponseEntity<GetPostListDto.Response> getLikedPostList(@PathVariable Long id, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy)
    {
        List<Post> postList =  postService.getLikedPostList(id, page,size,sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postList.stream().map(post -> modelMapper.map(post, GetPostListDto.Post.class)).collect(Collectors.toList()))
                .build());
    }
}
