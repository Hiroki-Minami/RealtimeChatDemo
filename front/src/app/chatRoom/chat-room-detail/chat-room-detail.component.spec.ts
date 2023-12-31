import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatRoomDetailComponent } from './chat-room-detail.component';

describe('ChatRoomDetailComponent', () => {
  let component: ChatRoomDetailComponent;
  let fixture: ComponentFixture<ChatRoomDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChatRoomDetailComponent]
    });
    fixture = TestBed.createComponent(ChatRoomDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
