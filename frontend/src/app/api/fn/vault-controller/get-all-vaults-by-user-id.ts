/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { VaultDto } from '../../models/vault-dto';

export interface GetAllVaultsByUserId$Params {
  id: number;
}

export function getAllVaultsByUserId(http: HttpClient, rootUrl: string, params: GetAllVaultsByUserId$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<VaultDto>>> {
  const rb = new RequestBuilder(rootUrl, getAllVaultsByUserId.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
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

getAllVaultsByUserId.PATH = '/api/vault-management/vaults/user/{id}';
