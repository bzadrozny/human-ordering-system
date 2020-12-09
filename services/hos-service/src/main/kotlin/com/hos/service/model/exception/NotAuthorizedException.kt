package com.hos.service.model.exception

import com.hos.service.model.enum.QualifierType
import com.hos.service.model.enum.Resource

class NotAuthorizedException(
        val resourceType: Resource,
        qualifierType: QualifierType,
        qualifierValue: String
) : RuntimeException("Current user is not authorized to access ${resourceType.desc} with $qualifierType: $qualifierValue")
