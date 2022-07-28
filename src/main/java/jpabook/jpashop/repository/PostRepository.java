package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {


    Page<Post> findAll(Pageable pageable);
}
