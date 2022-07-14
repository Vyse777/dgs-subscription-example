package com.subscriptiontest.subscriptiontestapi.subscriptions

import com.jeniusbank.jeniusevolvedmicroservice.types.AggregationStatus
import com.jeniusbank.jeniusevolvedmicroservice.types.AggregationStatusResponse
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsSubscription
import com.netflix.graphql.dgs.InputArgument
import com.subscriptiontest.subscriptiontestapi.services.AccountServiceDemo
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import java.time.Duration

@DgsComponent
class AggregatingAccountsSubscription @Autowired constructor(private val accountServiceDemo: AccountServiceDemo) {
    private val logger = LoggerFactory.getLogger(AggregatingAccountsSubscription::class.java)

    @DgsSubscription
    fun aggregationStatus(@InputArgument customerId: String): Publisher<AggregationStatusResponse> {
        return Flux.interval(Duration.ofSeconds(3))
            .map {
                if (this.accountServiceDemo.areAccountsStillAggregating())
                    AggregationStatusResponse("abc123", AggregationStatus.AGGREGATING)
                else
                    AggregationStatusResponse("abc123", AggregationStatus.DONE)
            }
        // Optionally the server can terminate/complete this Flux once the aggregation status is DONE
//            .takeUntil { it.status == AggregationStatus.DONE }
    }
}
