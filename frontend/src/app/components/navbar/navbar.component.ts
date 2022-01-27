import { Component } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {

  isActive = false;

  possibleRoutes = ['/dashboard', '/settings'];

  isLoggedIn = false;

  constructor(private router: Router, private authService: AuthenticationService) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.isActive = this.possibleRoutes.includes(event.url);
      }
    })
    this.authService.getCurrentUser$().subscribe((user) => {
      this.isLoggedIn = user && user.token;
    })
  }

  isRoute(route: string): boolean {
    return this.router.url === route;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigateByUrl('/');
  }

  login(): void {
    this.router.navigateByUrl('/');
  }
}
