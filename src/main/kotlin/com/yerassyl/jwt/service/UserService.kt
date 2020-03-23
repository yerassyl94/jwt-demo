package com.yerassyl.jwt.service

import com.yerassyl.jwt.model.User
import com.yerassyl.jwt.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService (val userRepository: UserRepository) : UserDetailsService{

    fun saveUser(user: User){
        userRepository.save(user)
    }

    fun findByUsername(username: String): User?{
        return userRepository.findByUsername(username)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("Username: $username not found")

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.password)
                .authorities("USER")
                .build()
    }
}