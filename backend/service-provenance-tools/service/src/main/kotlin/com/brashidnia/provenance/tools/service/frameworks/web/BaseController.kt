package com.brashidnia.provenance.tools.service.frameworks.web

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/")
class BaseController {
    companion object {
        val LOG = LoggerFactory.getLogger(BaseController::class.java.name)
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    fun disableFavicon() {
        //Method is void to avoid browser 404 issue by returning nothing in the response.
    }
}