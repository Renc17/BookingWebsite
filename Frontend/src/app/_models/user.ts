import {Role} from '@app/_models/role';

export class User {
  id: string;
  username: string;
  password: string;
  repassword: string;
  name: string;
  surname: string;
  email: string;
  cel: string;
  roles: Role;
}
