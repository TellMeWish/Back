package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Like;
import jpabook.jpashop.domain.wish.LikeID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, LikeID> {
    List<Like> findListByLikeID(LikeID likeId);
}
