/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { AuthResponse } from '../models/auth-response';
import { signIn } from '../fn/auth-controller/sign-in';
import { SignIn$Params } from '../fn/auth-controller/sign-in';
import { signUp } from '../fn/auth-controller/sign-up';
import { SignUp$Params } from '../fn/auth-controller/sign-up';

@Injectable({ providedIn: 'root' })
export class AuthControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `signUp()` */
  static readonly SignUpPath = '/auth/signUp';

  /**
   * Sign Up.
   *
   * Sign up givin Credentials Details
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `signUp()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  signUp$Response(params: SignUp$Params, context?: HttpContext): Observable<StrictHttpResponse<AuthResponse>> {
    return signUp(this.http, this.rootUrl, params, context);
  }

  /**
   * Sign Up.
   *
   * Sign up givin Credentials Details
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `signUp$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  signUp(params: SignUp$Params, context?: HttpContext): Observable<AuthResponse> {
    return this.signUp$Response(params, context).pipe(
      map((r: StrictHttpResponse<AuthResponse>): AuthResponse => r.body)
    );
  }

  /** Path part for operation `signIn()` */
  static readonly SignInPath = '/auth/signIn';

  /**
   * Sign Ip.
   *
   * Sign ip givin email and password
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `signIn()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  signIn$Response(params: SignIn$Params, context?: HttpContext): Observable<StrictHttpResponse<AuthResponse>> {
    return signIn(this.http, this.rootUrl, params, context);
  }

  /**
   * Sign Ip.
   *
   * Sign ip givin email and password
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `signIn$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  signIn(params: SignIn$Params, context?: HttpContext): Observable<AuthResponse> {
    return this.signIn$Response(params, context).pipe(
      map((r: StrictHttpResponse<AuthResponse>): AuthResponse => r.body)
    );
  }

}
