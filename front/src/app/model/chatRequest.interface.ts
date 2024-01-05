interface JoinCommand {
    command: 'join';
    poster: {
      id: string;
      name: string;
      nickName: string;
    };
  }
  interface LeaveCommand {
    command: 'leave';
    poster: {
      id: string;
      name: string;
      nickName: string;
    };
  }
  interface PostCommand {
    command: 'post';
    poster: {
      id: string;
      name: string;
      nickName: string;
    };
    message: string;
  }
  interface DeleteCommand {
    command: 'delete';
    poster: {
      id: string;
      name: string;
      nickName: string;
    };
    messageId: string;
  }
  interface DoneCommand {
    command: 'done';
    poster: {
      id: string;
      name: string;
      nickName: string;
    };
  }
  export type ChatRequest = JoinCommand | LeaveCommand | PostCommand | DeleteCommand | DoneCommand;