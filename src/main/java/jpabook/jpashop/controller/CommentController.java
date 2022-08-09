package jpabook.jpashop.controller;

import io.swagger.annotations.ApiOperation;
import jpabook.jpashop.domain.wish.Comment;
import jpabook.jpashop.domain.wish.CustomUserDetails;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.post.AddCommentDto;
import jpabook.jpashop.dto.post.UpdateCommentDto;
import jpabook.jpashop.dto.post.UpdatePostDto;
import jpabook.jpashop.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

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

}
