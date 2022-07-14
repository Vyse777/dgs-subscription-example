import { APOLLO_OPTIONS, ApolloModule } from 'apollo-angular';
import { HttpLink } from 'apollo-angular/http';

import { NgModule } from '@angular/core';
import { ApolloClientOptions, InMemoryCache, split } from '@apollo/client/core';
import { WebSocketLink } from '@apollo/client/link/ws';
import { getMainDefinition } from '@apollo/client/utilities';

// const uri = 'localhost:8080/graphql'; // <-- add the URL of the GraphQL server here
export function createApollo(httpLink: HttpLink): ApolloClientOptions<any> {
  {
    // Create an http link:
    const http = httpLink.create({
      uri: 'http://localhost:8080/graphql',
    });

    // Create a WebSocket link with GraphQLWsLink:
    // const ws = new GraphQLWsLink(
    //   createClient({
    //     url: 'ws://localhost:8080/subscriptions'
    //   })
    // );

    // TODO: Should eventually replace with GraphQLWsLink but the docs are mighty slim on how to do this. Right now a simple swap breaks things...
    const ws = new WebSocketLink({
      uri: `ws://localhost:8080/subscriptions`,
      options: {
        reconnect: false,
      },
    });

    // using the ability to split links, you can send data to each link
    // depending on what kind of operation is being sent
    const link = split(
      // split based on operation type
      ({ query }) => {
        let definition = getMainDefinition(query);
        return (
          definition.kind === 'OperationDefinition' && definition.operation === 'subscription'
        );
      },
      ws,
      http,
    );

    return {
      link,
      cache: new InMemoryCache()
      // ... other options
    };
  }
}

@NgModule({
  exports: [ApolloModule],
  providers: [
    {
      provide: APOLLO_OPTIONS,
      useFactory: createApollo,
      deps: [HttpLink],
    },
  ],
})
export class GraphQLModule { }
