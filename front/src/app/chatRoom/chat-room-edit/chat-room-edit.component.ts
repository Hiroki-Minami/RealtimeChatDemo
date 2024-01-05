import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ChatRoomService } from 'src/app/chat-room.service';
import { ChatRoom } from 'src/app/model/chatRoom.model';

@Component({
  selector: 'app-chat-room-edit',
  templateUrl: './chat-room-edit.component.html',
})
export class ChatRoomEditComponent implements OnInit {
  REQUIRE_ERROR = 'You must enter a value';

  roomId = ''
  editRoomForm = new FormGroup({
    roomName: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    startDate: new FormControl(new Date(), [Validators.required]),
    startTime: new FormControl(this.getTime(), [Validators.required]),
    endDate: new FormControl(new Date(), [Validators.required]),
    endTime: new FormControl(this.getTime(), [Validators.required]),
  })
  timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;

  // date time picker
  minDate = new Date();

  constructor(private chatRoomService: ChatRoomService, private router: Router, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.roomId = this.activatedRoute.snapshot.paramMap.get('roomId')!
    this.chatRoomService.detail(this.roomId).subscribe({
      next: (chatRoom) => {
        this.editRoomForm.patchValue({
          roomName: chatRoom.roomName,
          description: chatRoom.description,
          startDate: new Date(chatRoom.startDate!),
          startTime: this.getTime(new Date(chatRoom.startDate!)),
          endDate: new Date(chatRoom.endDate!),
          endTime: this.getTime(new Date(chatRoom.endDate!)),
        })
      },
      error: (err) => {},
    })
    // console.log(this.router.url)
    // const roomId = this.router.
  }

  onSubmit() {
    const chatRoom: ChatRoom = {
      roomName: this.editRoomForm.get("roomName")?.value?.toString(),
      description: this.editRoomForm.get("description")?.value?.toString(),
      startDate: this.convertDate(this.editRoomForm.get("startDate")?.value, this.editRoomForm.get("startTime")?.value),
      endDate: this.convertDate(this.editRoomForm.get("endDate")?.value, this.editRoomForm.get("endTime")?.value),
    }
    this.chatRoomService.update(this.roomId ,chatRoom).subscribe({
      next: (data) => {
        console.log("Success");
        this.router.navigateByUrl("/");
      },
      error: (err) => {
        console.log(err);
      },
    })
  }
  back() {
    this.router.navigateByUrl('/');
  }

  convertDate(date: Date|null|undefined, time: string|null|undefined): string|undefined {
    if (!date || !time) { return; }
    let year = date.getFullYear();
    let month = String(date.getMonth() + 1).padStart(2, "0");
    let day = String(date.getDate()).padStart(2, "0");
    let timeComponents = time.split(":");

    let timezoneOffsetMinutes = date.getTimezoneOffset();
    let offsetHours = Math.floor(Math.abs(timezoneOffsetMinutes) / 60);
    let offsetMinutes = Math.abs(timezoneOffsetMinutes) % 60;
    let offsetSign = timezoneOffsetMinutes > 0 ? '-' : '+';

    // Construct the timezone string
    let timezoneString = timezoneOffsetMinutes !== 0 ?
    `${offsetSign}${offsetHours.toString().padStart(2, '0')}:${offsetMinutes.toString().padStart(2, '0')}`:"Z";
    let timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;

    return `${year}-${month}-${day}T${timeComponents[0]}:${timeComponents[1]}:00${timezoneString}[${timezone}]`;
  }

  getTime(date: Date = new Date()): string {
    const hours = String(date.getHours()).padStart(2, "0");
    const seconds = String(date.getMinutes()).padStart(2, "0");
    return `${hours}:${seconds}`;
  }
}
