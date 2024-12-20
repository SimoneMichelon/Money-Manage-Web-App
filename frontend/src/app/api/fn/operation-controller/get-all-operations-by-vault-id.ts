/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { OperationDto } from '../../models/operation-dto';

export interface GetAllOperationsByVaultId$Params {
  id: number;
}

export function getAllOperationsByVaultId(http: HttpClient, rootUrl: string, params: GetAllOperationsByVaultId$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<OperationDto>>> {
  const rb = new RequestBuilder(rootUrl, getAllOperationsByVaultId.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<OperationDto>>;
    })
  );
}

getAllOperationsByVaultId.PATH = '/api/operation-management/operations/vault/{id}';
