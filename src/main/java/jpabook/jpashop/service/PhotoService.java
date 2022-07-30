package jpabook.jpashop.service;

import jpabook.jpashop.domain.wish.Photo;
import jpabook.jpashop.dto.PhotoResponseDTO;
import jpabook.jpashop.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final PhotoRepository photoRepo;

    //전제이미지조회
    @Transactional(readOnly = true)
    public List<PhotoResponseDTO> findAllByPost(Long postId){

        List<Photo> photoList = photoRepo.findAllByPostId(postId);

        return photoList.stream()
                .map(PhotoResponseDTO::new)
                .collect(Collectors.toList());
    }

}
