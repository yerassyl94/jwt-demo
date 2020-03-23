package com.yerassyl.jwt.config

import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
        authenticationManager: AuthenticationManager,
        private val securityConstants: SecurityConstants
) : BasicAuthenticationFilter(authenticationManager){

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authentication = getAuthentication(request)
        if (authentication != null){
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest) : UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(securityConstants.tokenHeader)

        if (token.isNullOrEmpty() && token.startsWith(securityConstants.tokenPrefix)){
            try {
                val key = securityConstants.jwtSecret.toByteArray()

                val parsedToken = Jwts.parserBuilder()
                        .setSigningKey(key).build()
                        .parseClaimsJws(token.replace("Bearer", ""))

                val username = parsedToken.body.subject

                if (username.isNotEmpty()){
                    return UsernamePasswordAuthenticationToken(username,null)
                }
            } catch (e: Exception){
                print(e.message)
            }
        }

        return null
    }
}