package com.brashidnia.provenance.tools.service.frameworks.web.api.v1

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/provenance")
@PreAuthorize("hasRole('ADMIN')")
class ProvenanceController {
}