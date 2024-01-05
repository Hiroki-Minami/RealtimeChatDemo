import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

import { AuthService } from './auth.service';
import { map } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  // FIXME: depending on the direction
  if (authService.isValid()) {
    return true;
  } else {
    router.navigateByUrl("/login");
  }
  return false; // redirect
};
