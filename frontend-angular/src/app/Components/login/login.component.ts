import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Authentication/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;


  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }


  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.errorMessage = 'Please enter your username and password.';
      return;
    }

    const { username, password } = this.loginForm.value;

    this.authService.authenticateAndGetToken(username, password).subscribe({
      next: (token: string) => {
        localStorage.setItem('token', token);
        localStorage.setItem('username', username);
        this.successMessage = 'Login successful!';
        this.router.navigate(['/admin-dashboard']);

      },
      error: (err) => {
        console.error('Login failed:', err);
        this.errorMessage = 'Login failed. Please check your credentials.';
      }
    });
  }
}
