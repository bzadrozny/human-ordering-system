package com.hos.service

import com.hos.service.model.entity.AuthoritisRoleEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.Authority
import com.hos.service.repo.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@SpringBootApplication
class Application(private val userRepository: UserRepository) {

    @Value("\${adminlogin}")
    private val adminlogin: String = ""

    @Value("\${adminpassw}")
    private val adminpassw: String = ""

    @Value("\${adminemail}")
    private val adminemail: String = ""

    @Bean
    fun initializeDb(): InitializingBean {
        return InitializingBean {
            val user = userRepository.save(UserEntity(adminlogin, adminemail, adminpassw))
            user.authorities.add(AuthoritisRoleEntity(user, Authority.ADMIN))
            user.authorities.add(AuthoritisRoleEntity(user, Authority.MANAGER))
            userRepository.save(user)
        }
    }

}
