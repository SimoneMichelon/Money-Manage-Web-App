/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ThirdPartyDto } from '../../models/third-party-dto';

export interface GetAllThirdParties$Params {
}

export function getAllThirdParties(http: HttpClient, rootUrl: string, params?: GetAllThirdParties$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ThirdPartyDto>>> {
  const rb = new RequestBuilder(rootUrl, getAllThirdParties.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<ThirdPartyDto>>;
    })
  );
}

getAllThirdParties.PATH = '/api/third-party-management/third-parties';
