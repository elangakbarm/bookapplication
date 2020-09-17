import { Book } from 'app/entities/book/book';
import shelf from 'app/entities/shelf/shelf';
import { IBook } from 'app/shared/model/book.model';
import { constants } from 'buffer';

export interface IShelf {
  id?: number;
  number?: number;
  desc?: string;
  books?: IBook[];
}

export const defaultValue: Readonly<IShelf> = {};
