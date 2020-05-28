import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRuolo, Ruolo } from 'app/shared/model/ruolo.model';
import { RuoloService } from './ruolo.service';
import { RuoloComponent } from './ruolo.component';
import { RuoloDetailComponent } from './ruolo-detail.component';
import { RuoloUpdateComponent } from './ruolo-update.component';

@Injectable({ providedIn: 'root' })
export class RuoloResolve implements Resolve<IRuolo> {
  constructor(private service: RuoloService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRuolo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ruolo: HttpResponse<Ruolo>) => {
          if (ruolo.body) {
            return of(ruolo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ruolo());
  }
}

export const ruoloRoute: Routes = [
  {
    path: '',
    component: RuoloComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.ruolo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RuoloDetailComponent,
    resolve: {
      ruolo: RuoloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.ruolo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RuoloUpdateComponent,
    resolve: {
      ruolo: RuoloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.ruolo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RuoloUpdateComponent,
    resolve: {
      ruolo: RuoloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.ruolo.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
