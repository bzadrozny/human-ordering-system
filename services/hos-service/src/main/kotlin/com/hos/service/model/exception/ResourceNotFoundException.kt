package com.hos.service.model.exception

import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource

class ResourceNotFoundException(
        val resourceType: Resource,
        qualifierType: QualifierType,
        qualifierValue: String
) : RuntimeException("${resourceType.desc} with $qualifierType: $qualifierValue did not found")
