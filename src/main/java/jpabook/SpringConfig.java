package jpabook;

import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class SpringConfig {
    private final PostRepository postRepository;

    @Autowired
    public SpringConfig(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository);
    }
}
