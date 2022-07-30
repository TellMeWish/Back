package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByPostId(Long postId);
}
