import { Injectable } from '@angular/core';
import { Observable, Subject, Subscription, SubscriptionLike } from 'rxjs';
import { RxStomp, RxStompConfig } from '@stomp/rx-stomp';


@Injectable({
  providedIn: 'root'
})
export class ChatWebsocketService extends RxStomp {
  // private isOpen = false
  // private retryNumber = 0
  // wsConnectionTimeout = 10000
  // receivedMessage = ''

  // private stompClient: Stomp.Client | null = null
  // private wsSubscription: Subscription | null = null
  // private readonly SOCKET_URL = 'https://localhost:8080/ws'
  // private readonly SENDING_TOPIC = '/ws-chat'
  // private readonly SUBSCRIBING_TOPIC = '/chat/messages'

  // private messageSubject: Subject<any> = new Subject<any>()
  // public message$: Observable<any> = this.messageSubject.asObservable()
 
  constructor() { super() }

  // private configureSocketClient(roomId: string): RxStompConfig {
  //     const config: RxStompConfig = {
  //       brokerURL: `'ws://localhost:8080/ws'/${roomId}`,
  //       // connectHeaders: {
  //       //   // authInfo
  //       // }
  //       heartbeatIncoming: 0,
  //       heartbeatOutgoing: 20000,
  //       reconnectDelay: 500,
  //     }
  //     return config
  // }

  // private initSocket(url: string): void {
  //   console.log("initialize websocket")
  //   const ws = new SockJS(url)
  //   this.stompClient = Stomp.over(ws)
  //   this.stompClient = this.configureSocketClient(this.stompClient)
  // }

  // openConnection(roomId: string): void {
  //   this.close()
  //   this.initSocket(`${this.SOCKET_URL}/${roomId}`)
  //   this.stompClient
  // }

  // private errorCallBack(error: any): void {
  //   this.isOpen = false
  //   console.log("Connection failure" + error)
  // }

  // getMessages() {
  //   this.wsSubscription = this.stompClient!.subscribe(this.SUBSCRIBING_TOPIC,
  //     (message: any) => {
  //       this.isOpen = true
  //       console.log("Connection sucess")
  //       this.receivedMessage = message.body
  //     })
  // }

  // retryConnection() {
  //   setTimeout(() => {
  //     console.warn("Retry Connection: " + this.retryNumber + " at " + new Date())
  //     // this.openConnection()
  //   }, this.wsConnectionTimeout)
  // }

  // close() {
  //   if (this.stompClient !== null) {
  //     this.stompClient!.disconnect(() => {})
  //     this.stompClient = null
  //     this.isOpen = false
  //   }
  //   console.log("Websocket disconnected")
  // }

  // send(message: any) {
  //   this.isOpen = true
  //   console.log("sending messages")
  //   this.stompClient.send()
  // }



  // private initWebSocket(): void {
  //   this.socket = new WebSocket(this.SOCKET_URL)

  // }
}
