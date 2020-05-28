import { Moment } from 'moment';
import { IRuolo } from 'app/shared/model/ruolo.model';

export interface IUtente {
  id?: number;
  created?: Moment;
  modified?: Moment;
  username?: string;
  password?: string;
  mail?: string;
  mobile?: string;
  facebook?: string;
  google?: string;
  instangram?: string;
  provider?: string;
  attivo?: boolean;
  motivoBolocco?: string;
  dataBolocco?: Moment;
  registrationDate?: Moment;
  lastAccess?: Moment;
  ruolo?: IRuolo;
}

export class Utente implements IUtente {
  constructor(
    public id?: number,
    public created?: Moment,
    public modified?: Moment,
    public username?: string,
    public password?: string,
    public mail?: string,
    public mobile?: string,
    public facebook?: string,
    public google?: string,
    public instangram?: string,
    public provider?: string,
    public attivo?: boolean,
    public motivoBolocco?: string,
    public dataBolocco?: Moment,
    public registrationDate?: Moment,
    public lastAccess?: Moment,
    public ruolo?: IRuolo
  ) {
    this.attivo = this.attivo || false;
  }
}
