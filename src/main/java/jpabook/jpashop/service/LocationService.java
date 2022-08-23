//package jpabook.jpashop.service;
//
//import jpabook.jpashop.domain.wish.Post;
//import jpabook.jpashop.repository.LocationRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class LocationService {
//
//    private final LocationRepository locationRepository;
//
//    public List<Post> getLocationsByCenter(float lat, float lng) {
//
//        Page<Post> pagePost = postRepo.findAll(
//                PageRequest.of(
//                        page.orElse(0),
//                        size.orElse(30),
//                        Sort.Direction.DESC, sortBy.orElse("id")
//                )
//        );
//
//        return pagePost.getContent();
//
//    }
//}
