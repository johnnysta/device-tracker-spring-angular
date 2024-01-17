export interface AuthenticatedUserModel {

  userName: String;
  email: String;

  isLoggedIn(): boolean;
}
