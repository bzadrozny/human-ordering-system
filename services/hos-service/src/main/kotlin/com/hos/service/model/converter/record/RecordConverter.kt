package com.hos.service.model.converter.record

import com.hos.service.model.converter.Converter
import com.hos.service.utils.loggerFor

abstract class RecordConverter<S, T> : Converter<S, T> {

    private val logger = loggerFor(javaClass)

    override fun merge(source: S, target: T): T {
        logger.warn("merge not allowed for converting object to record")
        return target
    }

}