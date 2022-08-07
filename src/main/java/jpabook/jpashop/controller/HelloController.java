package jpabook.jpashop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        boolean isAuth = SecurityContextHolder.getContext().getAuthentication().isAuthenticated(); //인증된 유저인지

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal(); //현재 인증유저 정보 get
        String username = userDetails.getUsername(); //현재 인증유저의 username get
        Collection authorities = userDetails.getAuthorities(); //현재 인증유저의 권한종류(어드민, 일반유저) get



        System.out.println("tttt"+ username +authorities);
        return ResponseEntity.ok("hello");
    }
}
