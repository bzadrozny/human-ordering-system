package com.hos.service

import com.hos.service.model.entity.AuthoritisRoleEntity
import com.hos.service.model.entity.DepartmentEntity
import com.hos.service.model.entity.OrganisationEntity
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.Authority
import com.hos.service.repo.DepartmentRepository
import com.hos.service.repo.OrganisationRepository
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
class Application(
        private val organisationRepo: OrganisationRepository,
        private val departmentRepo: DepartmentRepository,
        private val userRepo: UserRepository
) {

    @Value("\${adminlogin}")
    private val adminlogin: String = ""

    @Value("\${adminpassw}")
    private val adminpassw: String = ""

    @Value("\${adminemail}")
    private val adminemail: String = ""

    @Bean
    fun initializeDb(): InitializingBean {
        return InitializingBean {
            val organisation = organisationRepo.save(
                    OrganisationEntity(
                            name = "Buffalo project",
                            nip = "92409812409124098",
                            status = "CREATED"
                    )
            )

            val department = departmentRepo.save(
                    DepartmentEntity(
                            name = "Management",
                            organisation = organisation,
                            status = "CREATED"
                    )
            )

            val user = userRepo.save(
                    UserEntity(
                            login = adminlogin,
                            email = adminemail,
                            passwrod = adminpassw,
                            department = department
                    )
            )

            user.authorities.addAll(listOf(
                    AuthoritisRoleEntity(
                            user = user.id,
                            role = Authority.ADMIN
                    ),
                    AuthoritisRoleEntity(
                            user = user.id,
                            role = Authority.MANAGER
                    )
            ))
            userRepo.save(user)
        }
    }

}
