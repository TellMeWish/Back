package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Comment;
import jpabook.jpashop.domain.wish.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c from Comment c where c.user.userId = :id")
    Page<Comment> findMyCommentListById(Long id, Pageable pageable);
}
