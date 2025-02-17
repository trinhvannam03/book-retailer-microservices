import { Component } from '@angular/core';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  onSubmit(form: NgForm): void {
    const formData = {
      email: form.value.email,
      password: form.value.password,
    };
    console.log(formData);
  }
}
