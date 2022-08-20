package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
