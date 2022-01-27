import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../Model/user';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  public currentUser: Observable<any>;
  private currentUserSubject: BehaviorSubject<any>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUserToken') || '{}'));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public getCurrentUser$(): Observable<any> {
    return this.currentUserSubject.asObservable();
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${environment.apiUrl}auth`, { username, password }).pipe(
      map((user: any) => {
        if (user && user.data) {
          const token = { token: user.data };
          localStorage.setItem('currentUserToken', JSON.stringify(token));
          this.currentUserSubject.next(token);
        }

        return user;
      })
    );
  }

  logout() {
    localStorage.removeItem('currentUserToken');
    this.currentUserSubject.next(undefined);
  }
}
