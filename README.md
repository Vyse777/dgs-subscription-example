# dgs-subscription-example
A server and web client (Angular) example of how to implement a @DgsSubscription method in Kotlin using the DGS Framework. This example aims to provide insight into how to attach a web client to a DGS backend using Subscriptions via WebSockets.

Some notes:
- The DGS API framework in this project is configured with `com.netflix.graphql.dgs:graphql-dgs-subscriptions-websockets-autoconfigure` but it is worth mentioning that Netflix DGS states there is no requirement to use web sockets. 
- This demo project is currently using Angular for the web client and is utilizing a package named `apollo-angular`. This package is basically an Angular/Typescript wrapper for the Apollo Client (see here for more info for use in React: https://www.apollographql.com/docs/react/ and here for subscription docs: https://www.apollographql.com/docs/react/data/subscriptions)
