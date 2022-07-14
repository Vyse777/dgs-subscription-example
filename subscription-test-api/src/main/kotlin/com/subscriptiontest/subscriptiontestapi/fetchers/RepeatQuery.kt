package com.subscriptiontest.subscriptiontestapi.fetchers

import com.jeniusbank.jeniusevolvedmicroservice.types.QueryResponse
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class RepeatQuery {

    @DgsQuery
    suspend fun repeatAfterMe(@InputArgument message: String): QueryResponse {
        return QueryResponse(message)
    }
}
