/* tslint:disable */
/* eslint-disable */
import { User } from '../models/user';
export interface Vault {
  capital: number;
  createdDate?: string;
  id?: number;
  image: string;
  lastModifiedDate?: string;
  name: string;
  user?: User;
}
