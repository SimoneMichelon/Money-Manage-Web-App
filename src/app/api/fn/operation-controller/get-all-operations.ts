/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { OperationListsWrapper } from '../../models/operation-lists-wrapper';

export interface GetAllOperations$Params {
}

export function getAllOperations(http: HttpClient, rootUrl: string, params?: GetAllOperations$Params, context?: HttpContext): Observable<StrictHttpResponse<OperationListsWrapper>> {
  const rb = new RequestBuilder(rootUrl, getAllOperations.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<OperationListsWrapper>;
    })
  );
}

getAllOperations.PATH = '/api/operation-management/operations';
