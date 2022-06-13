package com.example.securitywithjwt.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.securitywithjwt.constant.SwaggerConstant;
import com.example.securitywithjwt.models.dto.AddUserRoleDTO;
import com.example.securitywithjwt.models.entity.Role;
import com.example.securitywithjwt.models.entity.User;
import com.example.securitywithjwt.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Api(tags = {SwaggerConstant.API_TAG})
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "Get all users", notes = "Get all the users detail from the DB")
    @ApiResponses({@ApiResponse(code = 200, message = "All users fetched successfully"),
                @ApiResponse(code = 403, message = "You are not authorized. Please authenticate and try again")
    })
    @PreAuthorize("hasPermission('role_user','read')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }


    @ApiOperation(value = "Add user", notes = "Add a new user to the database", response = User.class)
    @PreAuthorize("hasPermission('role_admin','write')")
    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@ApiParam(value = "User to the saved", required = true) @RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @ApiOperation(value = "Add Role", notes = "Add a new Role to the database", response = Role.class)
    @PreAuthorize("hasPermission('role_admin','write')")
    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @ApiOperation(value = "Add Role to User", notes = "Add a role for the user")
    @PreAuthorize("hasPermission('role_admin','write')")
    @PostMapping("/user/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddUserRoleDTO userRoleDTO){
        userService.addRoleToUser(userRoleDTO.getUsername(), userRoleDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Refresh Token", notes = "Get a new refresh token")
    @GetMapping("/token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("TestSecret@1234".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 2*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception e) {
                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("status", String.valueOf(FORBIDDEN.value()));
                error.put("message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RuntimeException("refresh token missing!");
        }
    }
}
