/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ThirdPartyDto } from '../../models/third-party-dto';

export interface UpdateThirdParty$Params {
      body: ThirdPartyDto
}

export function updateThirdParty(http: HttpClient, rootUrl: string, params: UpdateThirdParty$Params, context?: HttpContext): Observable<StrictHttpResponse<ThirdPartyDto>> {
  const rb = new RequestBuilder(rootUrl, updateThirdParty.PATH, 'put');
  if (params) {
    rb.body(params.body, 'application/json');
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

updateThirdParty.PATH = '/api/third-party-management/third-parties';
