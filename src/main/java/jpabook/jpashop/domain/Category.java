package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.wish.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }

    @Entity
    @Getter @Setter
    public static class Comment {

        @Id @GeneratedValue
        @Column(name="comment_id")
        private Long id;

        @ManyToOne(fetch=LAZY)
        @JoinColumn(name="post_id")
        private Post post;

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "parent_id")
        private Comment parent;

        @OneToMany(mappedBy = "parent")
        private List<Comment> child = new ArrayList<>();

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name="user_id")
        private Address.User user;

        @Column(columnDefinition = "TEXT", nullable = false)
        private String content;

    }
}
