package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findListById(Long id);
    List<User> findListById(String userId);
}
