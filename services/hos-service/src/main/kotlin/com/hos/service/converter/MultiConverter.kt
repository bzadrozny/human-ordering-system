package com.hos.service.converter

interface MultiConverter<S1, S2, T> {

    fun create(source1: S1, source2: S2): T

    fun merge(source1: S1, source2: S2, target: T): T

}