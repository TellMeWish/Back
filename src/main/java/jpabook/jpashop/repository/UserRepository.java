package jpabook.jpashop.repository;

import jpabook.jpashop.dto.post.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserId(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findUserByUsername(String username);


//    @Modifying
//    @Query("select u from User u where u.id = :id")
//    int updateView(Long id);

//    @Query
//    //postid를 가지고있는 userid를 찾는다
//    User findUserBy
}
