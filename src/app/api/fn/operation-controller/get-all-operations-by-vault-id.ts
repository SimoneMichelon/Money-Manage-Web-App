/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { OperationListsWrapper } from '../../models/operation-lists-wrapper';

export interface GetAllOperationsByVaultId$Params {
  id: number;
}

export function getAllOperationsByVaultId(http: HttpClient, rootUrl: string, params: GetAllOperationsByVaultId$Params, context?: HttpContext): Observable<StrictHttpResponse<OperationListsWrapper>> {
  const rb = new RequestBuilder(rootUrl, getAllOperationsByVaultId.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<OperationListsWrapper>;
    })
  );
}

getAllOperationsByVaultId.PATH = '/api/operation-management/operations/vault/{id}';
