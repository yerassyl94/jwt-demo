package com.yerassyl.jwt.controller

import com.yerassyl.jwt.data.SignupRequestData
import com.yerassyl.jwt.model.User
import com.yerassyl.jwt.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class UserController(
        val passwordEncoder: PasswordEncoder,
        val userService: UserService
) {
    @PostMapping("/signup")
    fun register(@RequestBody signupRequestData: SignupRequestData): ResponseEntity<String>{
        if (userService.findByUsername(signupRequestData.username) != null){
            return ResponseEntity.badRequest().body("Username already exists")
        }

        val user = User(
                username = signupRequestData.username,
                password = passwordEncoder.encode(signupRequestData.password)
        )

        userService.saveUser(user)

        return ResponseEntity.ok("User registered successfully")
    }
}