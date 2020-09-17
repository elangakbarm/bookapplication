import { IShelf } from 'app/shared/model/shelf.model';

export interface IBook {
  id?: number;
  title?: string;
  author?: string;
  publisher?: string;
  price?: number;
  shelf?: IShelf;
}

export const defaultValue: Readonly<IBook> = {};
