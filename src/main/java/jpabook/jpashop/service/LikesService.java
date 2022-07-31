package jpabook.jpashop.service;

import jpabook.jpashop.common.exception.SowonException;
import jpabook.jpashop.common.exception.Status;
import jpabook.jpashop.domain.wish.User;
import jpabook.jpashop.repository.LikesRepository;
import jpabook.jpashop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likes(Long postId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        likesRepository.likes(postId, user.getUserId());
    }

    @Transactional
    public void unLikes(Long postId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new SowonException(Status.ACCESS_DENIED));
        likesRepository.unLikes(postId, user.getUserId());
    }
}
