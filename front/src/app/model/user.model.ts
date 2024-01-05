export interface User {
    id?: string;
    email?: string;
    name?: string;
    nickName?: string;
    description?: string;
    password?: string;
    confirmPassword?: string;
    followers?: string[];
    followings?: string[];
    participating?: string[];
}