import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.scss'],
})
export class LogInComponent implements OnInit {
  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  error = false;

  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthenticationService) {
  }

  ngOnInit() {
    const currentUser = this.authService.currentUserValue;
    if (currentUser !== undefined && Object.keys(currentUser).length !== 0) {
      this.router.navigate(['/dashboard']);
    }
  }

  login() {
    this.authService.login(this.loginForm.value.username, this.loginForm.value.password).subscribe((_) => {
      this.router.navigate([`/dashboard`]);
    }, (_) => {
      this.error = true;
    });
  }

  loginAsGuest() {
    this.authService.logout();
    this.router.navigate(['/dashboard']);
  }
}
