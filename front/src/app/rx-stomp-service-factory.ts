import { ChatWebsocketService } from "./chat-websocket.service";
import { rxStompConfig } from "./rx-stomp.config";

export function rxStompServiceFactory(): ChatWebsocketService {
    const rxStomp = new ChatWebsocketService()
    rxStomp.configure(rxStompConfig)
    rxStomp.activate()
    return rxStomp
}