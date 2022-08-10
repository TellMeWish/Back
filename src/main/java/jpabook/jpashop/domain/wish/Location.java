package jpabook.jpashop.domain.wish;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Location {

    @Id
    @GeneratedValue
    @Column(name="location_id")
    private Long id;

    private float latitude;

    private float longitude;

    @OneToOne(mappedBy = "location")
    private Post post;
}
