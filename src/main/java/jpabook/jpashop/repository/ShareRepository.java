package jpabook.jpashop.repository;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.Share;
import jpabook.jpashop.dto.post.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShareRepository extends JpaRepository<Share, Long> {
    Optional<Share> findByPostAndUser(Post post, User user);

    Optional<Share> findByPostAndUserUserId(Post post, Long userId);

    Share findUserById(Long id);
}
