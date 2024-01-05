import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  isMobileMenuOpen = false;

  constructor(private authService: AuthService) {}

  isLogin(): boolean {
    return this.authService.isValid();
  }
}
