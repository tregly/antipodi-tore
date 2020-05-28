import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IRuolo } from 'app/shared/model/ruolo.model';

type EntityResponseType = HttpResponse<IRuolo>;
type EntityArrayResponseType = HttpResponse<IRuolo[]>;

@Injectable({ providedIn: 'root' })
export class RuoloService {
  public resourceUrl = SERVER_API_URL + 'api/ruolos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/ruolos';

  constructor(protected http: HttpClient) {}

  create(ruolo: IRuolo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ruolo);
    return this.http
      .post<IRuolo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ruolo: IRuolo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ruolo);
    return this.http
      .put<IRuolo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRuolo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRuolo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRuolo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(ruolo: IRuolo): IRuolo {
    const copy: IRuolo = Object.assign({}, ruolo, {
      created: ruolo.created && ruolo.created.isValid() ? ruolo.created.format(DATE_FORMAT) : undefined,
      modified: ruolo.modified && ruolo.modified.isValid() ? ruolo.modified.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
      res.body.modified = res.body.modified ? moment(res.body.modified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ruolo: IRuolo) => {
        ruolo.created = ruolo.created ? moment(ruolo.created) : undefined;
        ruolo.modified = ruolo.modified ? moment(ruolo.modified) : undefined;
      });
    }
    return res;
  }
}
