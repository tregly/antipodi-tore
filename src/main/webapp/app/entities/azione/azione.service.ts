import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAzione } from 'app/shared/model/azione.model';

type EntityResponseType = HttpResponse<IAzione>;
type EntityArrayResponseType = HttpResponse<IAzione[]>;

@Injectable({ providedIn: 'root' })
export class AzioneService {
  public resourceUrl = SERVER_API_URL + 'api/aziones';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/aziones';

  constructor(protected http: HttpClient) {}

  create(azione: IAzione): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(azione);
    return this.http
      .post<IAzione>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(azione: IAzione): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(azione);
    return this.http
      .put<IAzione>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAzione>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAzione[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAzione[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(azione: IAzione): IAzione {
    const copy: IAzione = Object.assign({}, azione, {
      created: azione.created && azione.created.isValid() ? azione.created.format(DATE_FORMAT) : undefined,
      modified: azione.modified && azione.modified.isValid() ? azione.modified.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((azione: IAzione) => {
        azione.created = azione.created ? moment(azione.created) : undefined;
        azione.modified = azione.modified ? moment(azione.modified) : undefined;
      });
    }
    return res;
  }
}
