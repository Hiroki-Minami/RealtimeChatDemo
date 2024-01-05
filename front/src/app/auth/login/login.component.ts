import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  REQUIRE_ERROR = 'You must enter a value';

  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  hidePassword = true;
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    const credentials = { email: this.email.value, password: this.password.value };

    this.authService.login(credentials).subscribe({
      next: () => {
        this.router.navigateByUrl("/");
      },
      error: (err) => {
        // TODO: display errors
        // status
        // 401 unauthorize
        // other errors
      },
    });
  }

  signUp(): void {
    this.router.navigateByUrl("/register");
  }
}
