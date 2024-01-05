import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ChatRoomListComponent } from './chatRoom/chat-room-list/chat-room-list.component';
import { ChatRoomCreateComponent } from './chatRoom/chat-room-create/chat-room-create.component';
import { authGuard } from './auth.guard';
import { UserListComponent } from './user/user-list/user-list.component';
import { ChatRoomEditComponent } from './chatRoom/chat-room-edit/chat-room-edit.component';
import { ChatComponent } from './chatRoom/chat/chat.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', component: ChatRoomListComponent },
  { path: 'chatRoom/create', component: ChatRoomCreateComponent, },
  { path: 'chatRoom/:roomId/chat', component: ChatComponent, },
  { path: 'chatRoom/:roomId/edit', component: ChatRoomEditComponent, },
  { path: 'user', component: UserListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
