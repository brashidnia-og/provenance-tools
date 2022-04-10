package com.brashidnia.provenance.tools.service.frameworks.web.api.v1

import com.brashidnia.provenance.tools.service.domain.api.StatusResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/status")
class StatusController {

    @GetMapping
    fun getStatus(): StatusResponse {
        return StatusResponse("WORKING")
    }
}