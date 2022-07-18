package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findListById(Long postId);

}
