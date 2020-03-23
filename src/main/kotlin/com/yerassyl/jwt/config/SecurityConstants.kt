package com.yerassyl.jwt.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SecurityConstants {
    @Value("/${"jwtSecret"}")
    lateinit var jwtSecret : String

    @Value("/${"tokenHeader"}")
    lateinit var tokenHeader: String

    @Value("/${"tokenPrefix"}")
    lateinit var tokenPrefix: String
}