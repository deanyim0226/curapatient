import { Component } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from './data/employee-data';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html', //html file
  styleUrls: ['./app.component.css'] //style file
})
export class AppComponent {
  title = 'Appdemo';


  constructor() { }

}
