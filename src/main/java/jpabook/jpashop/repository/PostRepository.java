package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.isPrivate = 0")
    Page<Post> findAll(Pageable pageable);

    @Query(value = "select p from Post p where p.post_user_id.userId = :id")
    Page<Post> findAllByUserId(Long id, Pageable pageable);

    @Query(value = "select p from Post p where p.title LIKE CONCAT('%',:keyword,'%')")
    Page<Post> findPostsByKeyword(String keyword, Pageable pageable);

    @Query(value = "select p from Post p where p.category = :category and p.title LIKE CONCAT('%',:keyword,'%')")
    Page<Post> findPostsByKeywordCategory(String category, String keyword, Pageable pageable);

    @Query(value = "select DISTINCT p from Post p INNER JOIN p.likesList l WHERE l.user.userId = :id")
    Page<Post> findLikedPostById(Long id, Pageable pageable);

    @Query( value = "select distinct p.* from comment c, post p where p.post_id = c.post_id and c.user_id = :id",
            nativeQuery = true)
    Page<Post> findMyCommentedPostListById(Long id, Pageable pageable);

    @Query(value = "select p from Post p where p.post_user_id.userId = :userId and p.id = :postId")
    Optional<Post> findByPostAndUserId(Long postId, Long userId);

    @Modifying
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int updateView(Long id);

}
