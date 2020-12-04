package com.hos.service.model.converter

interface Converter<S, T> {

    fun create(source: S): T

    fun merge(source: S, target: T): T

}