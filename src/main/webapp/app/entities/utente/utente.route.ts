import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUtente, Utente } from 'app/shared/model/utente.model';
import { UtenteService } from './utente.service';
import { UtenteComponent } from './utente.component';
import { UtenteDetailComponent } from './utente-detail.component';
import { UtenteUpdateComponent } from './utente-update.component';

@Injectable({ providedIn: 'root' })
export class UtenteResolve implements Resolve<IUtente> {
  constructor(private service: UtenteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUtente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((utente: HttpResponse<Utente>) => {
          if (utente.body) {
            return of(utente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Utente());
  }
}

export const utenteRoute: Routes = [
  {
    path: '',
    component: UtenteComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.utente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UtenteDetailComponent,
    resolve: {
      utente: UtenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.utente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UtenteUpdateComponent,
    resolve: {
      utente: UtenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.utente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UtenteUpdateComponent,
    resolve: {
      utente: UtenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.utente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
