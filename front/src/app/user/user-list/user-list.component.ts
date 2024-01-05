import { Component, OnInit } from '@angular/core';
import { tick } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { AuthService } from 'src/app/auth.service';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
})
export class UserListComponent implements OnInit {
  followings$: Observable<User[]> = of([]);
  followers$: Observable<User[]> = of([]);
  searchResult$: Observable<User[]> = of([]);

  followingSet: Set<string> = new Set();

  searchText = '';

  constructor(private userService: UserService, private authService: AuthService) {}

  ngOnInit(): void {
      if (this.isLoggedIn()) {
        this.followings$ = this.userService.followings();
        this.followers$ = this.userService.followers();
        const you = this.authService.loggedInUser();
        this.followingSet = new Set(you?.followings);
      }
  }

  handleKeyInput(event: KeyboardEvent) {
    if (event.key === 'Enter' && this.searchText.length > 0) {
      this.search();
    }
  }

  search() {
    this.userService.search(this.searchText).subscribe({
      next: (data) => {
        this.searchResult$ = of(data);
      },
      error: (err) => {

      },
    })
  }

  isLoggedIn(): boolean {
    return this.authService.isValid();
  }

  follow(id: string): void {
    this.userService.follow(id).subscribe({
      next: (user: User) => {
        this.followingSet = new Set(user.followings);
      },
      error: (err) => {
        // TODO: display errors
        // status
        // 401 unauthorize
        // other errors
      },
    })
  }

  unfollow(id: string): void {
    this.userService.unfollow(id).subscribe({
      next: (user: User) => {
        this.followingSet = new Set(user.followings);
      },
      error: (err) => {
        // TODO: display errors
        // status
        // 401 unauthorize
        // other errors
      },
    })
  }
}
