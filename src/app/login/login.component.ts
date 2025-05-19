import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {MatButtonModule} from '@angular/material/button'
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../api.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [MatButtonModule,RouterLink,NgIf,FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
   
  registerDialog:boolean=false;
  constructor(private apiService:ApiService,private route:Router){
  }

  ngOnInit(){
    if(localStorage.getItem("jwt")!==null){
      this.route.navigate(["/"]);
    }
      
    this.registerDialog=window.history.state.message;
    setTimeout(()=>{
      this.registerDialog=false;
    },5000)
  }

 
 
  username:string="";
  password:string="";
 
 passwordNull:boolean=false;
 userNameNull:boolean=false;
 usernamepasswordCheck:boolean=false;
 userNameNullCheck(){
  if(this.username===""){
    this.userNameNull=true;
  }
  else{
    this.userNameNull=false;
  }
 }
 passwordNullCheck(){
  if(this.password===""){
    this.passwordNull=true;
  }
  else{
    this.passwordNull=false;
  }
 }

 
login(){
  
  if(this.username===""){
    this.userNameNull=true;
   

  }
  else if(this.password===""){
    this.passwordNull=true;
  }
  else if(this.username===""||this.password===""){
    this.userNameNull=true;
    this.passwordNull=true;
  }

  else 
  {
  this.apiService.login(this.username,this.password).subscribe({
    next:(response:HttpResponse<any>)=>{ 
       if(response.status===200){
         localStorage.setItem("jwt",response.body.jwt)
         localStorage.setItem("username",this.username)
         this.route.navigate(["/"]).then(()=>{
          window.location.reload();
         });
       }
       
    },
    error:(error)=>{
      console.log(error.status)
      if(error.status===502){
        this.usernamepasswordCheck=true;
      }
    }
  })
}
}
 
}
