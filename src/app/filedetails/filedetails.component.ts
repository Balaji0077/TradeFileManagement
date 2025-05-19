import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../api.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-filedetails',
  standalone: true,
  imports: [NgIf],
  templateUrl: './filedetails.component.html',
  styleUrl: './filedetails.component.css'
})
export class FiledetailsComponent {
  
  deleteConfiramtion:boolean=false;
  data:any={}
  constructor(private route: ActivatedRoute,private apiService:ApiService,private router:Router) {
    let id:any = this.route.snapshot.paramMap.get('id');
     this.apiService.getFileById(localStorage.getItem("jwt") ,id).subscribe(
      {
        next: (response) => {
          this.data=response;
          console.log(this.data)},
        error: (error) => console.error(error)
      }
     )
  }
  update(id:any){
    console.log(id)
    this.router.navigate(['/update',id])
  }   

  ngOnInit(){
    if(localStorage.getItem("jwt")===null){
      this.router.navigate(["/login"]);
    }
  }

  delete(){
    
    this.deleteConfiramtion = true;
  }
  delteOnSuccess(id:any){
      this.apiService.deleteById(localStorage.getItem("jwt"),id).subscribe({
      
        next: (response) => {console.log(response)
          this.router.navigate(["/dashboard"]);
        },
        error: (error) => {
          if(error.status===200)
          { console.log(error)
            this.router.navigate(["/dashboard"],{state:{deleteSuccessPopup:true}});
          }
          console.error(error)}
      
    })
  }
  deleteOnCancel(){
    this.router.navigate(["/dashboard"],{state:{deleteCancelPopup:true}})
  }
}


