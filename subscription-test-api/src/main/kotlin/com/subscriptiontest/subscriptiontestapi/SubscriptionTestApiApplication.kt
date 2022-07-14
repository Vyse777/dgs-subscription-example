package com.subscriptiontest.subscriptiontestapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@SpringBootApplication
class SubscriptionTestApiApplication

fun main(args: Array<String>) {
    runApplication<SubscriptionTestApiApplication>(*args)
}
