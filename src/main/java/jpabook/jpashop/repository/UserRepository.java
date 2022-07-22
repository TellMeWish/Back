package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
