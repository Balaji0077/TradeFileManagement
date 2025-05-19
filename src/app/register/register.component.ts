import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import {MatButtonModule} from '@angular/material/button'
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { ApiService } from '../api.service';
import { HttpResponse } from '@angular/common/http';
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink,MatButtonModule,FormsModule,NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
   username:string="";
   password:string="";
   role:string="Role_User";
   adminpassword:string="";
   warningadmin:boolean=false;
   userwarning:boolean=false;
   adminBox:boolean=false;
   usernameNull:boolean=false;
   passwordNull:boolean=false;
   constructor(private route:Router,private apiService:ApiService){}
   usernameNullCheck(){
    if(this.username===""){
      this.usernameNull=true;
    }
    else{
      this.usernameNull=false;
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
  
   register(){
     
     if(this.role==="Role_Admin")
     {  if(this.username===""){
      this.usernameNull=true;
    }
    else if(this.password===""){
      this.passwordNull=true;
    }
    else if(this.username===""||this.password===""){
      this.usernameNull=true;
      this.passwordNull=true;
    }
    else{
       this.adminBox=true;
    }
     }


     else 
     {
      if(this.username===""){
        this.usernameNull=true;
      }
      else if(this.password===""){
        this.passwordNull=true;
      }
      else if(this.username===""||this.password===""){
        this.usernameNull=true;
        this.passwordNull=true;
      }
      else
      {
     this.apiService.register(this.username,this.password,this.role).subscribe({
         next:(response:HttpResponse<any>)=>{ 
            if(response.status===200){
              this.warningadmin=false;
              this.route.navigate(["login"],{state:{message:true}})
            }   
         },
         error:(error)=>{
           console.log(error.status)
           if(error.status===200){
            this.warningadmin=false;
            this.route.navigate(["/login"],{state:{message:true}})
          }  
           else if(error.status===400){
            this.userwarning=true;
           }
           
         }
       })
      }
    }
   }
   admincheck(){
      if(this.adminpassword==="Admin@712")
      {
        this.adminBox=false;
        this.apiService.register(this.username,this.password,this.role).subscribe({
          next:(response:HttpResponse<any>)=>{ 
             if(response.status===200){
              
               this.route.navigate(["/login"],{state:{message:true}})
               
             }
             
          },
          error:(error)=>{
            console.log(error.status)
            if(error.status===200){
              this.warningadmin=false;
              this.route.navigate(["/login"],{state:{message:true}})
            } 
            else if(error.status===400){
              this.warningadmin=true;
            }
          }
        })
       
      }
      else{
        this.adminBox=false;
        this.warningadmin=true;
      }
   }

   adminClose(){
    this.adminBox=false;
   }

}
