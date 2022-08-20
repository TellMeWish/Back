package jpabook.jpashop.controller;

import io.swagger.annotations.ApiOperation;
import jpabook.jpashop.domain.wish.Comment;
import jpabook.jpashop.domain.wish.CustomUserDetails;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.post.AddCommentDto;
import jpabook.jpashop.dto.post.GetCommentListDto;
import jpabook.jpashop.dto.post.UpdateCommentDto;
import jpabook.jpashop.dto.post.UpdatePostDto;
import jpabook.jpashop.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @ApiOperation(value = "댓글 작성", notes = "부모 댓글 없을시 parentId null로")
    @PostMapping
    public ResponseEntity<Void> addComment(@AuthenticationPrincipal CustomUserDetails user, @RequestBody AddCommentDto.Request reqDto) {
        commentService.addComment(user.getId(), reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "댓글 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@AuthenticationPrincipal CustomUserDetails user, @RequestBody UpdateCommentDto.Request reqDto, @PathVariable Long id) {
        commentService.updateComment(user.getId(), reqDto,id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long id) {
        commentService.deleteComment(user.getId(), id);
    }

    @ApiOperation(value = "내가쓴 댓글 목록 조회")
    @GetMapping("/myCommentList")
    public ResponseEntity<GetCommentListDto.Response> getMyCommentList(@AuthenticationPrincipal CustomUserDetails user, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy) {
        List<Comment> commentList = commentService.getMyCommentList(user.getId(), page, size, sortBy);
        System.out.println("내가 쓴 댓글 개수 : " + commentList.size());

        return ResponseEntity.ok().body(GetCommentListDto.Response.builder()
                .commentList(commentList.stream()
                        .map(comment -> modelMapper.map(comment, GetCommentListDto.Comment.class))
                        .collect(Collectors.toList()))
                .build());

    }

}

