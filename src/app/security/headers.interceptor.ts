// demo.interceptor.ts

import { HttpInterceptorFn } from '@angular/common/http';

export const headersInterceptor : HttpInterceptorFn = (req, next) => {
  const authToken = req.url.startsWith(`http://localhost:8080/api/`) ? localStorage.getItem("jwt") : "NO-TOKEN";

  console.log(req.url);

  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${authToken}`
    }
  });

  return next(authReq);
};