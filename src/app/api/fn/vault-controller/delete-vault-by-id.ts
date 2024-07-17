/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { VaultDto } from '../../models/vault-dto';

export interface DeleteVaultById$Params {
  id: number;
}

export function deleteVaultById(http: HttpClient, rootUrl: string, params: DeleteVaultById$Params, context?: HttpContext): Observable<StrictHttpResponse<VaultDto>> {
  const rb = new RequestBuilder(rootUrl, deleteVaultById.PATH, 'delete');
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

deleteVaultById.PATH = '/api/vault-management/vaults/{id}';
