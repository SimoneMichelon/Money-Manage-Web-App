/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { VaultDto } from '../../models/vault-dto';

export interface CreateVault$Params {
      body: VaultDto
}

export function createVault(http: HttpClient, rootUrl: string, params: CreateVault$Params, context?: HttpContext): Observable<StrictHttpResponse<VaultDto>> {
  const rb = new RequestBuilder(rootUrl, createVault.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<VaultDto>;
    })
  );
}

createVault.PATH = '/api/vault-management/vaults';
