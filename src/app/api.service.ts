import { HttpClient, HttpHeaders, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  
 
  constructor(private http:HttpClient) { }

  // getData():Observable<any>{
  //   return this.http.get<any>("");
  // }

  login(username:string,password:string):Observable<HttpResponse<any>>{
   return this.http.post<any>("http://localhost:8080/user/login",{username,password},{observe:'response'});
  }

  register(username:string,password:string,role:string):Observable<HttpResponse<any>>{
    return this.http.post<any>("http://localhost:8080/user/register",{username,password,role},{observe:'response'});
  }

  uploadFile(file: File,token:string|null):Observable<HttpResponse<any>>{
    const formData = new FormData();
    formData.append('file', file);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
     return this.http.post<any>("http://localhost:8080/jobs/importfile", formData,{headers,observe: 'response'});
  }

  getFileList(token:string|null){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>("http://localhost:8080/api/file-loads/search",{headers})
  }

  getFileById(token:string|null , id:number){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`http://localhost:8080/api/file-loads/+${id}`,{headers})
  }
  
  updateStatusById(token:string|null , id:number,status:string){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.put<any>(`http://localhost:8080/api/file-loads/${id}/status`,status,{headers})
  }

  deleteById(token:string|null,id:number){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete(`http://localhost:8080/api/file-loads/${id}`,{headers})
  }

  searchByField(token:string|null,filename:string,status:string,id:any,fromDate:any,toDate:any){
  const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
  
 
  let params = new HttpParams();

  if(filename!==""){
    filename=filename.includes(".csv")?filename:filename+".csv"
    params=params.set('filename',filename)
    
  }
  if(status!==""){
    params=params.set('status',status)
  }
  if(id!==undefined && id!==null){
    params=params.set("id",id)
  }
  if(fromDate!==undefined && fromDate!==null){
    params=params.set("fromDate",fromDate)
  }
  if(toDate!==undefined && toDate!==null){
    params=params.set("toDate",toDate)
  }
 
  return this.http.get<any>("http://localhost:8080/api/file-loads/search?",{params:params,headers:headers})
  }

  getRole(token:string|null,username:string|null){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>(`http://localhost:8080/user/details/${username}`,{headers});
  }

  getAllUsers(token:string|null){

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any>("http://localhost:8080/user/all",{headers});
  }

  deleteUserByAdmin(token:string|null,id:any){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.delete<any>(`http://localhost:8080/user/${id}`,{headers});

  }

  addUserByAdmin(token:string|null,username:string,password:string,role:string){
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<any>(`http://localhost:8080/user/add`,{username,password,role,headers});
  }

 }
