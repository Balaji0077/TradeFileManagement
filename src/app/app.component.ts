import { NgIf } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { ApiService } from './api.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,HttpClientModule,RouterLink,NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {

  adminCheck:boolean=false;
  constructor(private route:Router,private apiService:ApiService){}
  isLoggedIn:boolean = false

  ngOnInit(){
    
    if(localStorage.getItem("jwt")===null){

      this.route.navigate(["/login"]);
      this.isLoggedIn=false
    }
    else{
      this.route.navigate(['/']);
      this.isLoggedIn=true
    }

     this.apiService.getRole(localStorage.getItem("jwt"),localStorage.getItem("username")).subscribe({
        next:(response)=>{
           if(response.role.toLowerCase()==="Role_Admin".toLowerCase()){
               this.adminCheck=true;
           }
           else{
            this.adminCheck=false;
           }
        },
        error:(error)=>{
           console.log(error)
        }
     });


  }

  title = 'Trade-File';
  displayStyleText:string="";
  text:string="Welcome to TradeSync File Management Solutions!"
  i:number = 0;
  
 interval = setInterval(()=>{
     this.displayStyleText+=this.text.charAt(this.i);
     if(this.i>=this.text.length)
    {
      clearInterval(this.interval);
    }
    this.i++;
  },100)


  logout(){
    localStorage.removeItem("jwt");
    localStorage.removeItem("username")
    this.route.navigate(["/"]).then(()=>
    {
    window.location.reload();
     })
  }
 
}
