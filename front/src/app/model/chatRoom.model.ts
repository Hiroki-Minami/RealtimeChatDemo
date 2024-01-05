import { User } from "./user.model";

export interface ChatRoom {
    id?: string;
    roomName?: string;
    description?: string;
    startDate?: string;
    endDate?: string;
    status?: number;
    owner?: User;
}