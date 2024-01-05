interface Poster {
  id: string,
  name: string,
  nickName: string,
}
export interface ChatCurrentTimeResponse {
  status: 'ok'
  type: 'timer'
  command: 'post',
  message: string,
}
export interface ChatTimeOverResponse {
  status: 'ok'
  type: 'timer',
  command: 'done'
}
export interface ChatJoinResponse {
  status: 'ok'
  type: 'message'
  command: 'join'
  poster: Poster
  participants: Array<Poster>,
}
export interface ChatLeaveResponse {
  status: 'ok'
  type: 'message'
  command: 'leave'
  poster: Poster
}
export interface ChatPostResponse {
  status: 'ok'
  type: 'message'
  command: 'post'
  poster: Poster,
  messageId: string
  message: string
}
export interface ChatDeleteResponse {
  status: 'ok'
  type: 'message'
  command: 'delete'
  poster: Poster,
  messageId: string
}
export interface ChatDoneResponse {
  status: 'ok'
  type: 'message'
  command: 'done'
  poster: Poster,
}
export interface ChatErrorResponse {
  status: 'error'
  type: 'message'
  message: string
}

export type chatResponse = ChatCurrentTimeResponse | ChatTimeOverResponse | ChatJoinResponse | ChatLeaveResponse | ChatPostResponse | ChatDeleteResponse | ChatDoneResponse | ChatErrorResponse