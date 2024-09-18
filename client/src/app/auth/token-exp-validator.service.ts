import { Injectable } from '@angular/core';

export class TokenExpirationValidator {
  static validateTokenExpirationDate(tokenExpiresIn): boolean {
    if (new Date().getTime() > new Date(tokenExpiresIn).getTime()) {
      return false;
    }

    return true;
  }
}
