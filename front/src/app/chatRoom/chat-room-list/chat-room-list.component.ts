import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { AuthService } from 'src/app/auth.service';
import { ChatRoomService } from 'src/app/chat-room.service';
import { ChatRoom } from 'src/app/model/chatRoom.model';

@Component({
  selector: 'app-chat-room-list',
  templateUrl: './chat-room-list.component.html'
})
export class ChatRoomListComponent implements OnInit {
  participatingSet = new Set<string>()
  // allChatRooms: ChatRoom[] = []
  // participatings: ChatRoom[] = []
  // hostings: ChatRoom[] = []

  allChatRooms$: Observable<ChatRoom[]> = of([]);
  participatings$: Observable<ChatRoom[]> = of([]);
  hostings$: Observable<ChatRoom[]> = of([]);


  constructor(private chatRoomService: ChatRoomService, private router: Router, private authService: AuthService) {}

  ngOnInit() {
    // this.allChatRooms$ = this.chatRoomService.list();
    // this.chatRoomService.list().subscribe({
    //   next: (res) => {
    //     // console.log(res.hosting)
    //     // console.log(res.others)
    //     // console.log(res.participating)
    //       this.allChatRooms$ = of(res.others);
    //       this.hostings$ = of(res.hosting);
    //       this.participatings$ = of(res.participating);

    //       // this.allChatRooms = res.others;
    //       // this.hostings = res.hosting;
    //       // this.participatings = res.participating;
    //   },
    // })
    this.initChatRooms()

    if (this.authService.isValid()) {
      this.participatingSet = new Set(this.authService.loggedInUser()?.participating)
    //   this.userId = this.authService.loggedInUser()?.id!
    }
  }

  initChatRooms() {
    this.chatRoomService.list().subscribe({
      next: (res) => {
        // console.log(res.hosting)
        // console.log(res.others)
        // console.log(res.participating)
          this.allChatRooms$ = of(res.others);
          this.hostings$ = of(res.hosting);
          this.participatings$ = of(res.participating);

          // this.allChatRooms = res.others;
          // this.hostings = res.hosting;
          // this.participatings = res.participating;
      },
    })
  }

  cancel(roomId: string) {
    this.chatRoomService.cancel(roomId).subscribe({
      next: (user) => { this.participatingSet = new Set(user.participating!) },
      error: (err) => {},
    })
  }

  attend(roomId: string) {
    this.chatRoomService.attend(roomId).subscribe({
      next: (user) => { this.participatingSet = new Set(user.participating!) },
      error: (err) => {},
    })
  }

  delete(roomId: string) {
    this.chatRoomService.delete(roomId).subscribe({
      next: () => { this.initChatRooms() },
      error: (err) => {},
    })
  }

  join(roomId: string) {
    this.router.navigateByUrl(`/chatRoom/${roomId}/chat`)
  }

  // isHost(ownerId: string): boolean {
  //   return ownerId === this.userId;
  // }
  edit(roomId: string) {
    this.router.navigateByUrl(`/chatRoom/${roomId}/edit`)
  }

  create() {
    this.router.navigateByUrl("/chatRoom/create");
  }
}
