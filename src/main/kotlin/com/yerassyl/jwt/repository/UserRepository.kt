package com.yerassyl.jwt.repository

import com.yerassyl.jwt.model.User
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @Query("Select * from users where username=:username")
    fun findByUsername(username: String) : User?
}