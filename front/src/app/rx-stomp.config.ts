import { RxStompConfig } from "@stomp/rx-stomp"

export const rxStompConfig: RxStompConfig = {
    brokerURL: 'ws://localhost:8080/ws',
    // connectHeaders: {
    //   // authInfo
    // }
    heartbeatIncoming: 20000,
    heartbeatOutgoing: 20000,
    reconnectDelay: 10000,
}