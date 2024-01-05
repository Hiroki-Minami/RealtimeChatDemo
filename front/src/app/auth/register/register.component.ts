import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth.service';
import { User } from 'src/app/model/user.model';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  REQUIRE_ERROR = 'You must enter a value';
  hidePassword = true;

  email = new FormControl('', [Validators.required, Validators.email]);
  name = new FormControl('', [Validators.required]);
  nickName = new FormControl('');
  password = new FormControl('', [Validators.required]);
  confirmPassword = new FormControl('', [Validators.required]);

  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}
  
  onSubmit() {
    const user: User = {
      name: this.name.value?.toString(),
      email: this.email.value?.toString(),
      nickName: this.nickName.value?.toString(),
      password: this.password.value?.toString(),
      confirmPassword: this.confirmPassword.value?.toString(),
    };

    this.authService.register(user).subscribe({
      next: () => {
        this.router.navigateByUrl("/");
      },
      error: (err) => {
        // TODO: display errors
        // status
        // already exist
        // other errors
      },
    });
  }
}
