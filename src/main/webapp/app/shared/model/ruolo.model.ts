import { Moment } from 'moment';
import { IAzione } from 'app/shared/model/azione.model';

export interface IRuolo {
  id?: number;
  created?: Moment;
  modified?: Moment;
  nomeAzione?: string;
  azioni?: IAzione;
}

export class Ruolo implements IRuolo {
  constructor(public id?: number, public created?: Moment, public modified?: Moment, public nomeAzione?: string, public azioni?: IAzione) {}
}
