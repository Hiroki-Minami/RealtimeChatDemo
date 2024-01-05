import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatRoomEditComponent } from './chat-room-edit.component';

describe('ChatRoomEditComponent', () => {
  let component: ChatRoomEditComponent;
  let fixture: ComponentFixture<ChatRoomEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChatRoomEditComponent]
    });
    fixture = TestBed.createComponent(ChatRoomEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
