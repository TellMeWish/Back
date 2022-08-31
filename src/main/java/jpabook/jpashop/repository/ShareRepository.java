package jpabook.jpashop.repository;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.Share;
import jpabook.jpashop.dto.post.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ShareRepository extends JpaRepository<Share, Long> {
    Optional<Share> findByPostAndUser(Post post, User user);

    Optional<Share> findByPostAndUserUserId(Post post, Long userId);

   /* Share findUserById(Long id);
*/
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Share s SET s.progress= :progress WHERE s.user.userId= :userId and s.post.id = :postId")
    void updateProgress(Long userId, Long postId, int progress);


    @Query("SELECT s FROM Share s WHERE s.user.userId =:userId and s.post=:post")
    Share findByPostAndUserId(Post post, Long userId);


}
