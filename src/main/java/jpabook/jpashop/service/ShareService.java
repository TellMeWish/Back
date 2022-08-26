package jpabook.jpashop.service;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.Likes;
import jpabook.jpashop.domain.wish.Post;
import jpabook.jpashop.domain.wish.Share;
import jpabook.jpashop.dto.post.LikesDto;
import jpabook.jpashop.dto.post.ShareDto;
import jpabook.jpashop.dto.post.User;
import jpabook.jpashop.repository.LikesRepository;
import jpabook.jpashop.repository.PostRepository;
import jpabook.jpashop.repository.ShareRepository;
import jpabook.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShareService {
    private final ShareRepository shareRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public String getShare(Long userId, ShareDto shareDto){

        User user = userRepository.findById(userId).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        Post post = postRepository.findById(shareDto.getPostId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));


        //공유받은 적 없는 글 일경우
        if (findShareByPostAndUser(userId, shareDto).isEmpty()) {
            //내 글을 공유받으려고 했을 경우
            if(post.getPost_user_id().getUserId().equals(userId)) {
                return "자신의 버킷리스트는 공유받을 수 없습니다";
            }
            Share share = Share.builder()
                    .post(post)
                    .user(user)
                    .build();
            shareRepository.save(share);
            return "버킷리스트를 공유받았습니다";
        }
        else{   // 이미 공유받은 글일 경우
            Optional<Share> shareOptional = findShareByPostAndUser(userId, shareDto);
            if (shareOptional.isEmpty()) {
                throw new SowonException("share/post empty error");
            }
            return "이미 공유받은 버킷리스트 입니다";
        }
    }

    public void deleteShare(Long userId, ShareDto shareDto) {
        //내가 공유받았던 글이면
        if (findShareByPostAndUser(userId, shareDto).isPresent()) {
            Optional<Share> shareOptional = findShareByPostAndUser(userId, shareDto);
            if (shareOptional.isEmpty()) {
                throw new SowonException("share/delete empty error");
            }
            shareRepository.delete(shareOptional.get());
        }
    }

    public Optional<Share> findShareByPostAndUser(Long userId, ShareDto shareDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        Post post = postRepository.findById(shareDto.getPostId()).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));

        return shareRepository.findByPostAndUser(post, user);
    }

}
