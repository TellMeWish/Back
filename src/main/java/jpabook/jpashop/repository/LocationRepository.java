package jpabook.jpashop.repository;


import jpabook.jpashop.domain.wish.Location;
import jpabook.jpashop.domain.wish.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Long> {


    @Query(value = "select l from Location l where l.post.id = :id")
    Location findLocationByPostId(Long id);

}
