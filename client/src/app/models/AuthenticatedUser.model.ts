export class AuthenticatedUser {
  constructor(
    private _userId: string,
    private _username: string,
    private _firstName: string,
    private _lastName: string,
    private _email: string,
    private _roles: string[],
    private _token: string,
    private _tokenExpiresIn: Date,
    private _refreshToken: string,
    private _refreshTokenExpiresIn: Date
  ) {}

  public get userId() {
    return this._userId;
  }

  public get username() {
    return this._username;
  }

  public get firstName() {
    return this._firstName;
  }

  public get lastName() {
    return this._lastName;
  }

  public get roles() {
    return this._roles;
  }

  public get token() {
    return this._token;
  }

  public get refreshToken() {
    return this._refreshToken;
  }

  public get tokenExpiresIn() {
    return this._tokenExpiresIn;
  }

  public get refreshTokenExpiresIn() {
    return this._refreshTokenExpiresIn;
  }
}
