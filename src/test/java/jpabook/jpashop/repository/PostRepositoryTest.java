package jpabook.jpashop.repository;

import jpabook.jpashop.domain.wish.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    // ==글 등록 테스트== //
    @Test
    public void testInsert() {


    }

    // ==게시글 아이디로 검색 테스트== //
 /*   @Test
    public void testGetPost(){
        Post post = postRepo.findById(1L).get();

        System.out.println(post.getPost_user_id().getNickname()+"가 작성한 글");
    }*/

    // == 회원으로 게시글 검색 테스트== ///
 /*   @Test
    public void testGetPostList(){
        User user = userRepo.findById(String.valueOf(1)).get();

        System.out.println(user.getNickname()+"가 등록한 게시글");
        for(Post post: user.getPosts()){
            System.out.println("--> "+post.getTitle());
        }
    }*/

}