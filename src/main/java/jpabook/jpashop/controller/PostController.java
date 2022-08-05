package jpabook.jpashop.controller;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.Photo;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.User;
import jpabook.jpashop.dto.PhotoResponseDTO;
import jpabook.jpashop.dto.post.CreatePostDto;
import jpabook.jpashop.dto.post.GetPostDto;
import jpabook.jpashop.dto.post.UpdatePostDto;
import jpabook.jpashop.repository.PhotoRepository;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import jpabook.jpashop.service.PhotoService;
import jpabook.jpashop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PhotoService photoService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    @PostMapping
    public ResponseEntity<Void> create(  @RequestPart(value="img", required=false) List<MultipartFile> files,
                                         @RequestPart(value = "dto") CreatePostDto.Request reqDto) throws Exception{
        postService.insertPost(reqDto, files);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto.Response> getPost(@PathVariable Long id) {
        List<Photo> photoList = photoRepository.findAllByPostId(id);
        List<Long> photoId = new ArrayList<>();

        for(Photo photo : photoList) {
            photoId.add(photo.getId());
        }

        return ResponseEntity.ok().body(postService.getPost(id,photoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestPart(value = "dto") UpdatePostDto.Request reqDto,
                                       @RequestPart(value = "img", required=false) List<MultipartFile> files,
                                       @PathVariable Long id) throws Exception {
        postService.updatePost(reqDto,id, files);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postService.deletePost(id);
    }



    @GetMapping("/postList")
    public Page<Post> getPosts(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy)
    {
        return postRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        size.orElse(30),
                        Sort.Direction.DESC, sortBy.orElse("id")
                )
        );
    }

  /*  @GetMapping("/postList")
    public ResponseEntity getPostList(Pageable pageable){

        return ResponseEntity.ok().body(postService.findAllPage(pageable));
    }*/

}
