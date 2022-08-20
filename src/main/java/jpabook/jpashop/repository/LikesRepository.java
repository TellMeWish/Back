package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Likes;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.dto.post.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findLikesByPostAndUser(Post post, User user);

/*
    @Modifying
    @Query(value="INSERT INTO likes(post_id, user_id) VALUES(:postId, :userId)", nativeQuery = true)
    void likes(Long postId, long userId);

    @Modifying
    @Query(value="DELETE FROM likes WHERE post_id= :postId AND user_id = :userId", nativeQuery = true)
    void unLikes(Long postId, long userId);
*/

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount + 1 where p.id = :id")
    int updateLikesCount(Long id);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount - 1 where p.id = :id")
    int minusLikesCount(Long id);


}
