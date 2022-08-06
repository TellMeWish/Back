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
        User user1 = new User();
        user1.setUser_id("user1id");
        user1.setPw("user1pw");
        user1.setNickname("닉네임1");
        user1.setPhone_num("010-1234-1234");
        userRepo.save(user1);

        User user2 = new User();
        user2.setUser_id("user2id");
        user2.setPw("user2pw");
        user2.setNickname("닉네임2");
        user2.setPhone_num("010-1234-5678");
        userRepo.save(user2);


        for(int i=0; i<5; i++){
            Post post = new Post();
            post.setPost_user_id(user1);
            post.setCategory("카테고리"+i);
            post.setContent("내용"+i);
            post.setIsCompleted(1);
            post.setTitle("제목"+i);
            post.setIsParticipate(1);
            post.setViewCount(10);
            post.setLikeCount(2);
            postRepo.save(post);
        }

        for(int i=0; i<2; i++){
            Post post = new Post();
            post.setPost_user_id(user2);
            post.setCategory("카테고리"+i);
            post.setContent("내용"+i);
            post.setIsCompleted(1);
            post.setTitle("제목"+i);
            post.setIsParticipate(1);
            post.setViewCount(10);
            post.setLikeCount(2);
            postRepo.save(post);
        }


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