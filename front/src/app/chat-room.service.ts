import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { ChatRoom } from './model/chatRoom.model';
import { User } from './model/user.model';
import { AuthService } from './auth.service';

interface ChatRoomListResponse {
  others: ChatRoom[],
  participating: ChatRoom[],
  hosting: ChatRoom[],
}

@Injectable({
  providedIn: 'root'
})

export class ChatRoomService {

  private privateBaseUrl = 'http://localhost:8080/api/chatRoom';
  private publicBaseUrl = 'http://localhost:8080/api/chatRoom';

  constructor(private http: HttpClient, private authService: AuthService) { }

  list(): Observable<ChatRoomListResponse> {
    return this.http.get<ChatRoomListResponse>(this.publicBaseUrl, {withCredentials: true });
  }

  // attendings(): Observable<ChatRoom[]> {
  //   return this.http.get<ChatRoom[]>(this.publicBaseUrl);
  // }

  // followings

  detail(id: string): Observable<ChatRoom> {
    return this.http.get<ChatRoom>(this.publicBaseUrl + `/${id}`, { withCredentials: true });
  }

  create(chatRoom: ChatRoom) {
    return this.http.post<ChatRoom>(this.privateBaseUrl, chatRoom, { withCredentials: true });
  }

  update(id: string, chatRoom: ChatRoom) {
    return this.http.put<ChatRoom>(this.privateBaseUrl + `/${id}`, chatRoom, { withCredentials: true });
  }

  delete(id: string) {
    return this.http.delete(this.privateBaseUrl + `/${id}`, { withCredentials: true });
  }

  cancel(roomId: string) {
    return this.http.delete<User>(this.privateBaseUrl + `/participate/${roomId}`, { withCredentials: true}).pipe(
      tap((user) => {
        this.authService.updateUser(user)
      })
    )
  }

  attend(roomId: string) {
    return this.http.post<User>(this.privateBaseUrl + `/participate/${roomId}`, {}, { withCredentials: true}).pipe(
      tap((user) => {
        this.authService.updateUser(user)
      })
    )
  }
}
