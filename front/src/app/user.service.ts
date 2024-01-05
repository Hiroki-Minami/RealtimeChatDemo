import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './model/user.model';
import { AuthService } from './auth.service';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private privateBaseUrl = 'http://localhost:8080/api/private/user';
  private publicBaseUrl = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient, private authService: AuthService) {}

  followings(): Observable<User[]> {
    return this.http.get<User[]>(`${this.publicBaseUrl}/followings`, { withCredentials: true });
  }
  followers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.publicBaseUrl}/followers`, { withCredentials: true });
  }
  search(searchText: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.publicBaseUrl}?searchText=${encodeURIComponent(searchText)}`);
  }
  follow(id: string): Observable<User> {
    return this.http.put<User>(`${this.publicBaseUrl}/followings/${id}`, {}, { withCredentials: true }).pipe(
      tap((user) => {
        this.authService.updateUser(user);
      })
    );
  }
  unfollow(id: string): Observable<User> {
    return this.http.delete<User>(`${this.publicBaseUrl}/followings/${id}`, { withCredentials: true }).pipe(
      tap((user) => {
        this.authService.updateUser(user);
      })
    );
  }

  // TODO: detail
  detail(id: string): Observable<User> {
    return this.http.get<User>(this.publicBaseUrl + `/${id}`);
  }
}
