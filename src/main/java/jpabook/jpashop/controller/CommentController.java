package jpabook.jpashop.controller;

import jpabook.jpashop.domain.wish.Comment;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.post.AddCommentDto;
import jpabook.jpashop.dto.post.UpdateCommentDto;
import jpabook.jpashop.dto.post.UpdatePostDto;
import jpabook.jpashop.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody AddCommentDto.Request reqDto) {
        commentService.addComment(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@RequestBody UpdateCommentDto.Request reqDto, @PathVariable Long id) {
        commentService.updateComment(reqDto,id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
