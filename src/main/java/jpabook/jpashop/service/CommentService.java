package jpabook.jpashop.service;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.Comment;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.post.AddCommentDto;
import jpabook.jpashop.dto.post.UpdateCommentDto;
import jpabook.jpashop.dto.post.User;
import jpabook.jpashop.repository.CommentRepository;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addComment(Long userId, AddCommentDto.Request reqDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        Post post = postRepository.findById(reqDto.getPostId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));

        Comment c = null;

        if (!Objects.isNull(reqDto.getParentId())) {//대댓글 작성 parentId가 속한 post 검증?
            Comment parent = commentRepository.findById(reqDto.getParentId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
            if (parent.getParent() != null) {//대대댓글 요청시 EXCEPTION
                throw new SowonException(Status.CANT_WRITE_RE_RE_COMMENT);
            }

            if (parent.getPost().getId() != post.getId()) {
                throw new SowonException(Status.ACCESS_DENIED);
            }
            c = parent;
        }

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(reqDto.getContent())
                .parent(c)
                .secret(reqDto.getSecret())
                .build();
        commentRepository.save(comment);
    }

    public void deleteComment(Long userId, Long id) {
      /*  Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("없는 댓글 아이디"));
*/
        Comment comment = commentRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));
        if(!comment.getUser().getUserId().equals(userId)){
            throw new SowonException(Status.ACCESS_DENIED);
        }
        commentRepository.deleteById(id);
    }

    public void updateComment(Long userId, UpdateCommentDto.Request reqDto, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        if(!comment.getUser().getUserId().equals(userId)){
            throw new SowonException(Status.ACCESS_DENIED);
        }

        comment.setContent(reqDto.getContent());
        comment.setSecret(reqDto.getSecret());

        commentRepository.save(comment);
    }


}
