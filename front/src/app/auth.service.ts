import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { User } from './model/user.model';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  // FIXME: url
  private baseUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}

  register(user: User) {
    return this.http.post(`${this.baseUrl}/register`, user, { withCredentials: true }).pipe(
      tap((response: any) => {
        console.log(response);
        this.saveAuth(response);
      })
    );
  };

  login(credentials: any) {
    return this.http.post(`${this.baseUrl}/login`, credentials, { withCredentials: true }).pipe(
      tap((response: any) => {
        console.log(response);
        this.saveAuth(response);
      })
    );
  };
  update(user: User) {
    return this.http.put(`${this.baseUrl}/profile`, user, { withCredentials: true });
  }
  delete() {
    return this.http.delete(`${this.baseUrl}/profile`, { withCredentials: true });
  }

  saveAuth(authResponse: any) {
    localStorage.setItem("loggedIn", authResponse.loggedIn);
    // localStorage.setItem('token', authResponse.token);
    localStorage.setItem('user', JSON.stringify(authResponse.user));
  }

  isValid(): boolean {
    return localStorage.getItem('loggedIn') === 'true';
    // return this.getToken() != null;
  }

  loggedInUser(): User| null {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr): null;
  }

  updateUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  deleteAuth() {
    localStorage.removeItem('loggedIn');
    localStorage.removeItem('user');
  }

  logout() {
    return this.http.post(`${this.baseUrl}/logout`, {}, { withCredentials: true });
  }
}
