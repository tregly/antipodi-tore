import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IUtente } from 'app/shared/model/utente.model';

type EntityResponseType = HttpResponse<IUtente>;
type EntityArrayResponseType = HttpResponse<IUtente[]>;

@Injectable({ providedIn: 'root' })
export class UtenteService {
  public resourceUrl = SERVER_API_URL + 'api/utentes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/utentes';

  constructor(protected http: HttpClient) {}

  create(utente: IUtente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(utente);
    return this.http
      .post<IUtente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(utente: IUtente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(utente);
    return this.http
      .put<IUtente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUtente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUtente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUtente[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(utente: IUtente): IUtente {
    const copy: IUtente = Object.assign({}, utente, {
      created: utente.created && utente.created.isValid() ? utente.created.format(DATE_FORMAT) : undefined,
      modified: utente.modified && utente.modified.isValid() ? utente.modified.format(DATE_FORMAT) : undefined,
      dataBolocco: utente.dataBolocco && utente.dataBolocco.isValid() ? utente.dataBolocco.format(DATE_FORMAT) : undefined,
      registrationDate:
        utente.registrationDate && utente.registrationDate.isValid() ? utente.registrationDate.format(DATE_FORMAT) : undefined,
      lastAccess: utente.lastAccess && utente.lastAccess.isValid() ? utente.lastAccess.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.modified = res.body.modified ? moment(res.body.modified) : undefined;
      res.body.dataBolocco = res.body.dataBolocco ? moment(res.body.dataBolocco) : undefined;
      res.body.registrationDate = res.body.registrationDate ? moment(res.body.registrationDate) : undefined;
      res.body.lastAccess = res.body.lastAccess ? moment(res.body.lastAccess) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((utente: IUtente) => {
        utente.created = utente.created ? moment(utente.created) : undefined;
        utente.modified = utente.modified ? moment(utente.modified) : undefined;
        utente.dataBolocco = utente.dataBolocco ? moment(utente.dataBolocco) : undefined;
        utente.registrationDate = utente.registrationDate ? moment(utente.registrationDate) : undefined;
        utente.lastAccess = utente.lastAccess ? moment(utente.lastAccess) : undefined;
      });
    }
    return res;
  }
}
