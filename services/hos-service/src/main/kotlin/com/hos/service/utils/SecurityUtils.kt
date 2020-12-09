package com.hos.service.utils

import com.hos.service.security.UserDetailsContainer
import org.springframework.security.core.context.SecurityContextHolder

fun getCurrentUser() = SecurityContextHolder.getContext()?.authentication?.principal as UserDetailsContainer