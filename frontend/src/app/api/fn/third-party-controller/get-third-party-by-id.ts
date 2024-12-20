/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ThirdPartyDto } from '../../models/third-party-dto';

export interface GetThirdPartyById$Params {
  id: number;
}

export function getThirdPartyById(http: HttpClient, rootUrl: string, params: GetThirdPartyById$Params, context?: HttpContext): Observable<StrictHttpResponse<ThirdPartyDto>> {
  const rb = new RequestBuilder(rootUrl, getThirdPartyById.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ThirdPartyDto>;
    })
  );
}

getThirdPartyById.PATH = '/api/third-party-management/third-parties/{id}';
