/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AuthResponse } from '../../models/auth-response';
import { LoginRequest } from '../../models/login-request';

export interface SignIn$Params {
      body: LoginRequest
}

export function signIn(http: HttpClient, rootUrl: string, params: SignIn$Params, context?: HttpContext): Observable<StrictHttpResponse<AuthResponse>> {
  const rb = new RequestBuilder(rootUrl, signIn.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<AuthResponse>;
    })
  );
}

signIn.PATH = '/auth/signIn';
