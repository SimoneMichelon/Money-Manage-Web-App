/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { VaultDto } from '../../models/vault-dto';

export interface GetAllVaultsByPrincipal$Params {
}

export function getAllVaultsByPrincipal(http: HttpClient, rootUrl: string, params?: GetAllVaultsByPrincipal$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<VaultDto>>> {
  const rb = new RequestBuilder(rootUrl, getAllVaultsByPrincipal.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<VaultDto>>;
    })
  );
}

getAllVaultsByPrincipal.PATH = '/api/vault-management/vaults/user/';
