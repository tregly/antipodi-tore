import { Moment } from 'moment';
import { IRuolo } from 'app/shared/model/ruolo.model';

export interface IAzione {
  id?: number;
  created?: Moment;
  modified?: Moment;
  nomeAzione?: string;
  descrizione?: string;
  ruolos?: IRuolo[];
}

export class Azione implements IAzione {
  constructor(
    public id?: number,
    public created?: Moment,
    public modified?: Moment,
    public nomeAzione?: string,
    public descrizione?: string,
    public ruolos?: IRuolo[]
  ) {}
}
