export class User {
  public username;
  public firstName;
  public lastName;
  public email;
  public password;
  public role;

  constructor(username, firstName, lastName, email, password, role) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
