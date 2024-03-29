package jpabook.jpashop.controller;

import jpabook.jpashop.dto.LoginDto;
import jpabook.jpashop.dto.TokenDTO;
import jpabook.jpashop.dto.TokenUserDTO;
import jpabook.jpashop.dto.post.User;
import jpabook.jpashop.jwt.JwtFilter;
import jpabook.jpashop.jwt.TokenProvider;
import jpabook.jpashop.repository.UserRepository;
import jpabook.jpashop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    UserRepository userRepository;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @CrossOrigin("*")
    @PostMapping("/authenticate")
    public ResponseEntity<TokenUserDTO> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        User user = userRepository.findUserByUsername(loginDto.getUsername());


        //findUserByUsername
        //String id = id, String userid = userid, String nickname = nicknameß
        return new ResponseEntity<TokenUserDTO>(
                new TokenUserDTO(jwt, user.getUserId(), user.getUsername(), user.getNickname()), httpHeaders, HttpStatus.OK);
    }
}