package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.isPrivate = 0")
    Page<Post> findAll(Pageable pageable);

    @Query(value = "select p from Post p where p.post_user_id.userId = :id")
    Page<Post> findAllByUserId(Long id, Pageable pageable);

    @Query(value = "select p from Post p where p.title LIKE CONCAT('%',:keyword,'%')")
    Page<Post> findPostsByKeyword(String keyword, Pageable pageable);

    @Query(value = "select p from Post p where p.category = :category and p.title LIKE CONCAT('%',:keyword,'%')")
    Page<Post> findPostsByKeywordCategory(String category, String keyword, Pageable pageable);

    // "SELECT m FROM Member m INNER JOIN m.team t WHERE t.name = :teamName";
    // select m from Member m inner join m.team t"
    @Query(value = "select DISTINCT p from Post p INNER JOIN p.likesList l WHERE l.user.userId = :id")
    Page<Post> findLikedPostById(Long id, Pageable pageable);


    @Modifying
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int updateView(Long id);

}
