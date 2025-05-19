import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  username:string|null="";
  constructor(private route:Router){
    
  }
  ngOnInit(){
    this.username=localStorage.getItem("username");
  }
  getStarted(){
    if(localStorage.getItem("jwt")===null){

      this.route.navigate(["/login"]);
    }
    else{
      this.route.navigate(["/upload"]);
    }
  }
  
}
