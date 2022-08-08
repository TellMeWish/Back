package jpabook.jpashop.service;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.Likes;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.User;
import jpabook.jpashop.dto.post.GetPostDto;
import jpabook.jpashop.dto.post.LikesDto;
import jpabook.jpashop.repository.LikesRepository;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void likes(LikesDto likesDto){

        User user = userRepository.findById(likesDto.getUserId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        Post post = postRepository.findById(likesDto.getPostId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));


        // 이미 좋아요 된 글일 경우
        if (findLikesByPostAndUser(likesDto).isPresent()) {
            /*throw new SowonException("이미 좋아요 된 글입니다.");*/
            Optional<Likes> likesOptional = findLikesByPostAndUser(likesDto);
            if (likesOptional.isEmpty()) {
                throw new SowonException("좋아요가 존재하지 않습니다.");
            }
            // 좋아요 삭제
            likesRepository.delete(likesOptional.get());
            minusLikesCount(post.getId()); // 좋아요 수 -1
        }
        else{ // 좋아요 추가
            updateLikesCount(post.getId()); // 좋아요 수 +1
            Likes likes = Likes.builder()
                    .post(post)
                    .user(user)
                    .build();
            likesRepository.save(likes);

        }
    }
/*
    @Transactional
    public void unLikes(Long postId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        likesRepository.unLikes(postId, user.getUserId());
    }
*/

    public Optional<Likes> findLikesByPostAndUser(LikesDto likesDto) {

        User user = userRepository.findById(likesDto.getUserId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        Post post = postRepository.findById(likesDto.getPostId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));

        return likesRepository
                .findLikesByPostAndUser(post, user);
    }

    @Transactional
    public int updateLikesCount(Long id) {
        return likesRepository.updateLikesCount(id);
    }
    @Transactional
    public int minusLikesCount(Long id) {
        return likesRepository.minusLikesCount(id);
    }



}