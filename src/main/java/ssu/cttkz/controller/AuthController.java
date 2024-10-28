package ssu.cttkz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.cttkz.authentication.JWT.JWTTokenProvider;
import ssu.cttkz.authentication.LdapUtils;
import ssu.cttkz.dto.AuthRequest;
import ssu.cttkz.model.Department;
import ssu.cttkz.model.User;
import ssu.cttkz.service.DepartmentService;
import ssu.cttkz.service.UserService;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final DepartmentService departmentService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserService userService, DepartmentService departmentService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.departmentService = departmentService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (userService.findByUsername(request.getUsername()) == null) {
                User newUser = new User();
                newUser.setUsername(request.getUsername());
                newUser.setFullname(LdapUtils.getFullName());
                if (departmentService.findByTitle(LdapUtils.getDepartment()) == null) {
                    Department newDepartment = new Department();
                    newDepartment.setTitle(LdapUtils.getDepartment());
                    Department savedDepartment = departmentService.save(newDepartment);
                    newUser.setDepartment(savedDepartment);
                    userService.save(newUser);
                }
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwt = jwtTokenProvider.createToken(request.getUsername(), authentication.getAuthorities());
        HashMap<String, String> response = new HashMap<>();
        response.put("token", jwt);
        response.put("username", jwtTokenProvider.getUsername(jwt));
        response.put("fullname", LdapUtils.getFullName());
        response.put("department", LdapUtils.getDepartment());

        return ResponseEntity.ok(response);
    }

//    @PostMapping("/checklogin")
//    public ResponseEntity<?> checkAuthentication(@RequestBody String token) {
//        JWTTokenProvider provider = new JWTTokenProvider();
//        try {
//            provider.validateToken(token);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch (JwtException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
}
