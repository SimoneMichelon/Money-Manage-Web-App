/* tslint:disable */
/* eslint-disable */
import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration, ApiConfigurationParams } from './api-configuration';

import { VaultControllerService } from './services/vault-controller.service';
import { UserControllerService } from './services/user-controller.service';
import { ThirdPartyControllerService } from './services/third-party-controller.service';
import { RevenueControllerService } from './services/revenue-controller.service';
import { ExpenseControllerService } from './services/expense-controller.service';
import { CategoryControllerService } from './services/category-controller.service';
import { AuthControllerService } from './services/auth-controller.service';
import { CredentialControllerService } from './services/credential-controller.service';
import { OperationControllerService } from './services/operation-controller.service';

/**
 * Module that provides all services and configuration.
 */
@NgModule({
  imports: [],
  exports: [],
  declarations: [],
  providers: [
    VaultControllerService,
    UserControllerService,
    ThirdPartyControllerService,
    RevenueControllerService,
    ExpenseControllerService,
    CategoryControllerService,
    AuthControllerService,
    CredentialControllerService,
    OperationControllerService,
    ApiConfiguration
  ],
})
export class ApiModule {
  static forRoot(params: ApiConfigurationParams): ModuleWithProviders<ApiModule> {
    return {
      ngModule: ApiModule,
      providers: [
        {
          provide: ApiConfiguration,
          useValue: params
        }
      ]
    }
  }

  constructor( 
    @Optional() @SkipSelf() parentModule: ApiModule,
    @Optional() http: HttpClient
  ) {
    if (parentModule) {
      throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
    }
    if (!http) {
      throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
      'See also https://github.com/angular/angular/issues/20575');
    }
  }
}
