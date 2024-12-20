/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CredentialDto } from '../../models/credential-dto';

export interface CreateCredential$Params {
      body: CredentialDto
}

export function createCredential(http: HttpClient, rootUrl: string, params: CreateCredential$Params, context?: HttpContext): Observable<StrictHttpResponse<CredentialDto>> {
  const rb = new RequestBuilder(rootUrl, createCredential.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CredentialDto>;
    })
  );
}

createCredential.PATH = '/auth-management/credentials';
