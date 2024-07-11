/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { CategoryDto } from '../../models/category-dto';

export interface DeleteCategoryById$Params {
  id: number;
}

export function deleteCategoryById(http: HttpClient, rootUrl: string, params: DeleteCategoryById$Params, context?: HttpContext): Observable<StrictHttpResponse<CategoryDto>> {
  const rb = new RequestBuilder(rootUrl, deleteCategoryById.PATH, 'delete');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<CategoryDto>;
    })
  );
}

deleteCategoryById.PATH = '/api/category-management/categories/{id}';
