package jpabook.jpashop.repository;


import jpabook.jpashop.domain.wish.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
