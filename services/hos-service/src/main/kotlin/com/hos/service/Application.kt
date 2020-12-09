package com.hos.service

import com.hos.service.model.entity.*
import com.hos.service.model.enum.Authority
import com.hos.service.model.enum.EntityStatus
import com.hos.service.repository.DepartmentRepository
import com.hos.service.repository.LocationRepository
import com.hos.service.repository.OrganisationRepository
import com.hos.service.repository.UserRepository
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
        private val locationRepository: LocationRepository,
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
                            status = EntityStatus.ACTIVE
                    )
            )

            val department = departmentRepo.save(
                    DepartmentEntity(
                            name = "Management",
                            organisation = organisation,
                            status = EntityStatus.ACTIVE
                    )
            )

            val location = locationRepository.save(
                    LocationEntity(
                            name = "testLocation",
                            organisation = organisation,
                            registeredOffice = true,
                            address = AddressEntity(
                                    city = "Warsaw",
                                    postalCode = "00000",
                                    street = "Werbeny",
                                    number = "2",
                            ),
                            status = EntityStatus.ACTIVE
                    )
            )

            val user = UserEntity(
                    login = adminlogin,
                    email = adminemail,
                    password = adminpassw,
                    status = EntityStatus.ACTIVE,
                    name = "adminName",
                    surname = "adminSurname",
                    phone1 = "phone1",
                    organisation = organisation,
                    department = department,
                    location = location
            )
            user.authorities.addAll(listOf(
                    AuthorityRoleEntity(
                            user = user,
                            role = Authority.ADMIN
                    )
            ))
            userRepo.save(user)
        }
    }

}
