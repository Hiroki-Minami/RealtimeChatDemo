import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Message } from '@stomp/stompjs';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth.service';
import { ChatWebsocketService } from 'src/app/chat-websocket.service';
import { ChatRequest } from 'src/app/model/chatRequest.interface';

// interface chatRequest {
//   command: 'join'| 'leave'| 'post'| 'delete'| 'done'
//   poster: {
//     id: string,
//     name: string,
//     nickName: string,
//   }
//   messageId?: string,
//   message?: string,
// }

interface chatResponse {
  type: 'message' | 'time'
  command: 'join'| 'leave'| 'post'| 'delete'| 'done'
  poster: {
    id: string,
    name: string,
    nickName: string,
  }
  messageId?: string,
  message?: string,
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
})
export class ChatComponent implements OnInit, OnDestroy {
    roomId = ''
    postMessage = ''
    messages: string[] = []

    private subscription: Subscription | undefined

    constructor(private chatWebsocketService: ChatWebsocketService, private activatedRoute: ActivatedRoute,
      private authService: AuthService){}

    ngOnInit(): void {
      this.roomId = this.activatedRoute.snapshot.paramMap.get('roomId')!
      if (!this.chatWebsocketService.active) { this.chatWebsocketService.activate() }
      this.subscription = this.chatWebsocketService
        // .watch('/chat/messages')
        .watch(`/chat/${this.roomId}/messages`)
        .subscribe((message: Message) => {
          // TODO: parse 
          // TODO: switch
          this.messages.push(message.body)
        })
        // TODO: post join message
    }

    ngOnDestroy(): void {
      // TODO: post leave message
        this.subscription!.unsubscribe()
        this.chatWebsocketService.deactivate()
    }

    post() {
      // console.log(`posted: ${this.postMessage}`)
      // this.chatWebsocketService.publish({ destination: `/ws-chat/${this.roomId}/send`, body: this.postMessage })
      const message = {
        message: this.postMessage,
        command: 'post',
      }
      this.chatWebsocketService.publish({ destination: `/ws-chat/${this.roomId}/send`, body: JSON.stringify(message) })
      // TODO: clear postMessage
    }

    private createJoinMessage(): ChatRequest {
      const user = this.authService.loggedInUser()!
      return {
        command: 'join',
        poster: {
          id: user.id!,
          name: user.name!,
          nickName: user.nickName!,
        },
      }
    }

    private createPostMessage(message: string): ChatRequest {
      const user = this.authService.loggedInUser()!
      return {
        command: 'post',
        poster: {
          id: user.id!,
          name: user.name!,
          nickName: user.nickName!,
        },
        message: message
      }
    }

    private createLeaveMessage(): ChatRequest {
      const user = this.authService.loggedInUser()!
      return {
        command: 'leave',
        poster: {
          id: user.id!,
          name: user.name!,
          nickName: user.nickName!,
        },
      }
    }

    private createDeleteMessage(messageId: string): ChatRequest {
      const user = this.authService.loggedInUser()!
      return {
        command: 'delete',
        poster: {
          id: user.id!,
          name: user.name!,
          nickName: user.nickName!,
        },
        messageId: messageId
      }
    }

    // TODO: check owner
    private createDoneMessage(): ChatRequest {
      const user = this.authService.loggedInUser()!
      return {
        command: 'done',
        poster: {
          id: user.id!,
          name: user.name!,
          nickName: user.nickName!,
        }
      }
    }
}
