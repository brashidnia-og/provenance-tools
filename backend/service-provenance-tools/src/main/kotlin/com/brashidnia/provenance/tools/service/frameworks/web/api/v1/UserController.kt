package com.brashidnia.provenance.tools.service.frameworks.web.api.v1

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.security.Principal

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('USER')")
class UserController {

    @GetMapping()
    fun greetUser(principal: Mono<Principal>): Mono<String> =
        principal
            .map(Principal::getName)
            .map { name -> String.format("Hello, %s", name) }

    @GetMapping("/welcome")
    fun welcome(): Mono<String> = Mono.just("Welcome !")
}