import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../api.service';
import { NgIf } from '@angular/common';
import {MatProgressBarModule} from '@angular/material/progress-bar';

@Component({
  selector: 'app-uploadfile',
  standalone: true,
  imports: [NgIf,MatProgressBarModule],
  templateUrl: './uploadfile.component.html',
  styleUrl: './uploadfile.component.css'
})
export class UploadfileComponent {
  loading:boolean=false;
  uploadButton:boolean=false;
  uploadFileErrMsg:boolean = false;
  ngOnInit(){
    if(localStorage.getItem("jwt")===null){
      this.route.navigate(["/login"]);
    }
    
  }

  selectedFile: File | null = null;
  constructor(private route:Router,private apiService:ApiService,private router:Router){}
  onFileSelected(event:Event):void{
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];
    }
    this.loading=true;
    this.uploadButton=false;
      
    setTimeout(()=>{
       this.uploadButton=true;
       this.loading=false;
    },5000)


   
  }

  upload(event:Event){
    event.preventDefault();
    
    if (this.selectedFile) {
    
      this.apiService.uploadFile(this.selectedFile,localStorage.getItem("jwt")).subscribe({
        next: (response) => {
          console.log('Upload success:', response),
          this.router.navigate(["/dashboard"],{state:{fileSuccess:true}})
        },
        error: (error) => 
        {   this.uploadFileErrMsg=true;
          console.error('Upload error:', error)
        }

    });
     
    }
    else{
      this.loading=false;
      this.uploadFileErrMsg=true;
      
    }
  }
  
}
