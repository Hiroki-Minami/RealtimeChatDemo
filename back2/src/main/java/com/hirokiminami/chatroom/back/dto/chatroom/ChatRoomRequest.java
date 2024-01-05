package com.hirokiminami.chatroom.back.dto.chatroom;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatRoomRequest {
    @NotBlank(message = "Room Name is required")
    private String roomName;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Start Date is required")
//    @Pattern(regexp = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{2}:[0-9]{2}:[0-9]{2}")
    private String startDate;
    @NotBlank(message = "End Date is required")
//    @Pattern(regexp = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2} [0-9]{2}:[0-9]{2}:[0-9]{2}")
    private String endDate;
}
