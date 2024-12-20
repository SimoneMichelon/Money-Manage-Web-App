/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { VaultDto } from '../../models/vault-dto';

export interface GetVaultById$Params {
  id: number;
}

export function getVaultById(http: HttpClient, rootUrl: string, params: GetVaultById$Params, context?: HttpContext): Observable<StrictHttpResponse<VaultDto>> {
  const rb = new RequestBuilder(rootUrl, getVaultById.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
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

getVaultById.PATH = '/api/vault-management/vaults/{id}';
