package com.subscriptiontest.subscriptiontestapi.mutators

import com.jeniusbank.jeniusevolvedmicroservice.types.UpdateThingResponse
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import com.subscriptiontest.subscriptiontestapi.services.AccountServiceDemo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin

@DgsComponent
@CrossOrigin(origins = ["http://localhost:8080"])
class UpdateThing @Autowired constructor(private val accountServiceDemo: AccountServiceDemo)  {

    @DgsMutation
    suspend fun updateThing(): UpdateThingResponse {
        accountServiceDemo.toggleAggregation()
        return UpdateThingResponse(message="Done")
    }
}
