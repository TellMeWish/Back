/*
package jpabook.jpashop.controller;

import io.swagger.annotations.ApiOperation;
import jpabook.jpashop.dto.post.GetPostLocationDto;
import jpabook.jpashop.repository.LocationRepository;
import jpabook.jpashop.service.LocationService;
import jpabook.jpashop.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final PostService postService;

    @Autowired
    LocationRepository locationRepository;

    @ApiOperation(value = "특정 위치 반경 내 버킷리스트 장소 조회")
    @GetMapping("/locations")
    public ResponseEntity<GetPostLocationDto.Response> getPostLocations(@RequestParam Float lat, @RequestParam Float lng){
       // locationService.getLocationsByCenter(lat, lng);
        postService.getPostByCoord(lat, lng);
    }

}
*/
