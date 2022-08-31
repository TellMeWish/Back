package jpabook.jpashop.controller;

import io.swagger.annotations.ApiOperation;
import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.CustomUserDetails;
import jpabook.jpashop.domain.wish.Photo;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.post.*;
import jpabook.jpashop.jwt.JwtFilter;
import jpabook.jpashop.repository.PhotoRepository;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import jpabook.jpashop.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private final PhotoService photoService;

    private final UserService userService;

    private final LikesService likesService;

    private final ShareService shareService;

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    ModelMapper modelMapper;


    @ApiOperation(value = "게시글 등록")
    @PostMapping
    public ResponseEntity<Void> create(@RequestPart(value = "img", required = false) List<MultipartFile> files,
                                       @RequestPart(value = "dto") CreatePostDto.Request reqDto,
                                       @AuthenticationPrincipal CustomUserDetails user) throws Exception {
        postService.insertPost(reqDto, files, user.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "게시글 id별 게시글 조회")
    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto.Response> getPost( @AuthenticationPrincipal CustomUserDetails user, @PathVariable Long id) {
        List<Photo> photoList = photoRepository.findAllByPostId(id);
        List<Long> photoId = new ArrayList<>();

        for (Photo photo : photoList) {
            photoId.add(photo.getId());
        }
        postService.updateView(id); // views ++
        return ResponseEntity.ok().body(postService.getPost(user.getId(), id, photoId));
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@RequestPart(value = "dto") UpdatePostDto.Request reqDto,
                                       @RequestPart(value = "img", required = false) List<MultipartFile> files,
                                       @AuthenticationPrincipal CustomUserDetails user,
                                       @PathVariable Long id) throws Exception {


        postService.updatePost(reqDto, id, files, user.getId());

        return ResponseEntity.status(HttpStatus.OK).build();

    }


    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long id) {

        postService.deletePost(id, user.getId());
    }


    @ApiOperation(value = "게시글 목록 조회")
    //@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/postList")
    public ResponseEntity<GetPostListDto.Response> getPostList(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy) {
        List<Post> postList = postService.getPostList(page, size, sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postService.getPostListDtoWithPhotoIdSetting(postList))
                .build());

    }

    @ApiOperation(value = "사용자 id별 + 사용자가 공유받은 게시글들 조회")
    @GetMapping("/myPostList/share")
    public ResponseEntity<GetPostListDto.Response> getPostListByUserIdIncludeShare(@AuthenticationPrincipal CustomUserDetails user, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy) {
        List<Post> postList = postService.getPostListByUserIdIncludeShare(user.getId(), page, size, sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postService.getPostListDtoWithPhotoIdSetting(postList))
                .build());
    }

    @ApiOperation(value = "사용자 id별 게시글들 조회")
    @GetMapping("/myPostList")
    public ResponseEntity<GetPostListDto.Response> getPostListByUserId(@AuthenticationPrincipal CustomUserDetails user, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy) {
        List<Post> postList = postService.getPostListByUserId(user.getId(), page, size, sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postService.getPostListDtoWithPhotoIdSetting(postList))
                .build());
    }

    @ApiOperation(value = "특정 위치 반경 내 게시물들 조회")
    @GetMapping("/locations")
    public ResponseEntity<GetPostListDto.Response>  getPostLocations(@RequestParam Float lat, @RequestParam Float lng, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy){
        // locationService.getLocationsByCenter(lat, lng);
        List<Post> postList = postService.getPostListByCoord(lat, lng, page, size, sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postService.getPostListDtoWithPhotoIdSetting(postList))
                .build());
    }

    @ApiOperation(value = "게시글 검색") //카테고리 검색은 곧 추가
    @PostMapping("/search")
    public ResponseEntity<GetPostListDto.Response> getPostListByKeyword(@RequestParam(defaultValue = "all", required = false) String category, @RequestParam(defaultValue = "") String keyword, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy) {
        List<Post> postList = postService.getPostListByKeyword(category, keyword, page, size, sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postService.getPostListDtoWithPhotoIdSetting(postList))
                .build());
    }

    @ApiOperation(value = "게시글 좋아요", notes = "좋아요 안되어있을시 좋아요, 좋아요 되어있을시 좋아요 취소")
    @PostMapping("/like")
    public ResponseEntity<LikesDto> likes(@AuthenticationPrincipal CustomUserDetails user, @RequestBody @Valid LikesDto likesDto) {
        likesService.likes(user.getId(), likesDto);
        // return new ResponseEntity<>(likesDto, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "좋아요 한 게시글 목록")
    @GetMapping("/myLikedPostList")
    public ResponseEntity<GetPostListDto.Response> getLikedPostList(@AuthenticationPrincipal CustomUserDetails user, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy) {
        List<Post> postList = postService.getLikedPostList(user.getId(), page, size, sortBy);

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postService.getPostListDtoWithPhotoIdSetting(postList))
                .build());
    }

    @ApiOperation(value = "내가 댓글 쓴 게시글 목록")
    @GetMapping("/myCommentedPostList")
    public ResponseEntity<GetPostListDto.Response> getMyCommentedPostList(@AuthenticationPrincipal CustomUserDetails user, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy) {
        List<Post> postList = postService.getMyCommentedPostList(user.getId(), page, size, sortBy);
        System.out.println("내가 댓글 쓴 게시글 개수 : " + postList.size());

        return ResponseEntity.ok().body(GetPostListDto.Response.builder()
                .postList(postService.getPostListDtoWithPhotoIdSetting(postList))
                .build());
    }

    @ApiOperation(value = "게시글 공유받기")
    @PostMapping("/share")
    public ResponseEntity<ShareDto> getSharePost(@AuthenticationPrincipal CustomUserDetails user, @RequestBody @Valid ShareDto shareDto) {
        String shareMsg = shareService.getShare(user.getId(), shareDto);
        System.out.println(shareMsg);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "공유받은 게시글 취소하기")
    @DeleteMapping("/share/delete")
    public ResponseEntity<ShareDto> deleteSharePost(@AuthenticationPrincipal CustomUserDetails user, @RequestBody @Valid ShareDto shareDto) {
        shareService.deleteShare(user.getId(), shareDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "공유받은 글 진행상황 바꾸기")
    @PutMapping("/share")
    public ResponseEntity<UpdateShareDto> updateSharePost(@AuthenticationPrincipal CustomUserDetails user, @RequestBody @Valid UpdateShareDto updateShareDto){
        shareService.updateShare(user.getId(), updateShareDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
