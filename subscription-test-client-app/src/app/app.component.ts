import { Apollo, gql } from 'apollo-angular';
import { Subscription } from 'rxjs';

import { Component } from '@angular/core';

const helloHeartbeatSubscriptionQuery = gql`
  subscription {
    aggregationStatus(customerId: "abc123") {
      customerId
      status
    }
  }
`;

const updateTrigger = gql`
mutation {
  updateThing(whatever:"Eh") {
    message
  }
}
`;

export enum AggrigationState {
  Unknown = "Uninitialized",
  Connected = "Connected",
  Disconnected = "Disconnected",
  Error = "Error"
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'subscription-test-app';

  // TODO: Ideally these state variables would be 1 instead of two. Just remenence of screwing around
  aggregationState: AggrigationState = AggrigationState.Unknown;
  aggregationStatus = "Unknown";

  subscription: Subscription | null = null    // To allow unsubscribing when this component destroyed

  constructor(private apollo: Apollo) { }

  subscriptionButtonClick() {
    this.subscription = this.apollo.subscribe({
      query: helloHeartbeatSubscriptionQuery
    }).subscribe({
      next: (result: any) => {
        if (result.data) {
          console.log('Heartbeat:', result.data.aggregationStatus);
          this.aggregationStatus = result.data.aggregationStatus.status

          // Optionally a client can "disconnect" from a stream once it reaches a specific status.
          // if (this.aggregationStatus === "DONE") {
          //   this.subscription?.unsubscribe();
          // }

        }
      },
      error: (error) => { this.aggregationState = AggrigationState.Error },
      complete: () => { this.aggregationState = AggrigationState.Disconnected }
    });

    this.aggregationState = AggrigationState.Connected
  }

  toggleAggregationStatus() {
    this.apollo.mutate({
      mutation: gql`
      mutation {
        updateThing {
          message
        }
      }`
    }).subscribe(result => console.log(result));
  }
}
