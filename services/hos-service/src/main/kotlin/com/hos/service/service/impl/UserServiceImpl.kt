package com.hos.service.service.impl

import com.hos.service.model.converter.Converter
import com.hos.service.model.entity.UserEntity
import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource
import com.hos.service.model.exception.ResourceNotFoundException
import com.hos.service.model.exception.ValidationException
import com.hos.service.model.form.user.UserForm
import com.hos.service.model.record.UserBasicsRecord
import com.hos.service.model.record.UserDetailsRecord
import com.hos.service.model.validator.FormValidator
import com.hos.service.repo.UserRepository
import com.hos.service.service.UserService
import com.hos.service.usecase.uc001.LoginUser
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserServiceImpl(
        private val uc001: LoginUser,
        private val userFormValidator: FormValidator<UserForm, UserEntity>,
        private val userConverter: Converter<UserForm, UserEntity>,
        private val userBasicsConverter: Converter<UserEntity, UserBasicsRecord>,
        private val userDetailsConverter: Converter<UserEntity, UserDetailsRecord>,
        private val userRepository: UserRepository
) : UserService {

    override fun loginUser(login: String?, password: String?): String? {
        return uc001.login(login, password)
    }

    override fun findAllUsers(): List<UserBasicsRecord> {
        return userRepository.findAll().map(userBasicsConverter::create)
    }

    override fun findUserById(id: Long): UserDetailsRecord? {
        return userRepository.findById(id)
                .map(userDetailsConverter::create)
                .orElseThrow { ResourceNotFoundException(Resource.USER, QualifierType.ID, "$id") }
    }

    @Transactional
    override fun registerUser(userForm: UserForm): UserDetailsRecord? {
        userFormValidator.validateBeforeRegistration(userForm).let {
            if (it.hasBlocker() || (it.hasWarning() && !userForm.acceptWarning)) {
                throw ValidationException(it)
            }
        }
        return userConverter.create(userForm)
                .let { userRepository.save(it) }
                .let { userDetailsConverter.create(it) }
    }

    @Transactional
    override fun modifyUser(userForm: UserForm): UserDetailsRecord? {
        userFormValidator.validateInitiallyModification(userForm).let {
            if (it.hasBlocker() || (it.hasWarning() && !userForm.acceptWarning)) {
                throw ValidationException(it)
            }
        }

        val userEntity = userRepository.findById(userForm.id!!).orElseThrow {
            ResourceNotFoundException(
                    Resource.USER,
                    QualifierType.ID,
                    "${userForm.id}"
            )
        }

        userFormValidator.validateBeforeRegisterModification(userForm, userEntity).let {
            if (it.hasBlocker() || (it.hasWarning() && !userForm.acceptWarning)) {
                throw ValidationException(it)
            }
        }

        return userConverter.merge(userForm, userEntity)
                .let { userRepository.save(it) }
                .let { userDetailsConverter.create(it) }
    }

    override fun deleteUser(userForm: UserForm): UserBasicsRecord? {
        TODO("Not yet implemented")

        // change status
    }

}