import { Component } from '@angular/core';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  onSubmit(form: NgForm): void {
    const formData = {
      email: form.value.email,
      password: form.value.password,
    };
    console.log(formData);
  }
}
