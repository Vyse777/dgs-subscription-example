package com.subscriptiontest.subscriptiontestapi.services


import com.jeniusbank.jeniusevolvedmicroservice.types.AggregationStatusResponse
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.ConnectableFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import javax.annotation.PostConstruct

interface AccountServiceDemo {
    var accountsAreAggregating: Boolean
    fun areAccountsStillAggregating(): Boolean
    fun getAggregationStatusPublisher(): Publisher<AggregationStatusResponse>
    fun pretendAggregationCompleted()
    fun toggleAggregation()
}

@Service
class AccountServiceDemoImpl : AccountServiceDemo {
    private val logger = LoggerFactory.getLogger(AccountServiceDemo::class.java)

    override var accountsAreAggregating = true

    private lateinit var aggregationStatusStream: FluxSink<AggregationStatusResponse>
    private lateinit var aggregationStatusPublisher: ConnectableFlux<AggregationStatusResponse>

    @PostConstruct
    fun setupPublisher() {
        val publisher = Flux.create<AggregationStatusResponse> { emitter ->
            aggregationStatusStream = emitter
        }

        this.aggregationStatusPublisher = publisher.publish()
        this.aggregationStatusPublisher.connect()

    }

    override fun areAccountsStillAggregating(): Boolean {
        return this.accountsAreAggregating
    }

    override fun pretendAggregationCompleted() {
        this.accountsAreAggregating = false
        logger.info("Aggregation completed")
    }

    override fun toggleAggregation() {
        this.accountsAreAggregating = !this.accountsAreAggregating
        logger.info("Aggregation is ${this.accountsAreAggregating}")
    }

    override fun getAggregationStatusPublisher(): Publisher<AggregationStatusResponse> {
        return aggregationStatusPublisher
    }
}
