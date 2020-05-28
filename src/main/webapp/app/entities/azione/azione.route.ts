import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAzione, Azione } from 'app/shared/model/azione.model';
import { AzioneService } from './azione.service';
import { AzioneComponent } from './azione.component';
import { AzioneDetailComponent } from './azione-detail.component';
import { AzioneUpdateComponent } from './azione-update.component';

@Injectable({ providedIn: 'root' })
export class AzioneResolve implements Resolve<IAzione> {
  constructor(private service: AzioneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAzione> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((azione: HttpResponse<Azione>) => {
          if (azione.body) {
            return of(azione.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Azione());
  }
}

export const azioneRoute: Routes = [
  {
    path: '',
    component: AzioneComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.azione.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AzioneDetailComponent,
    resolve: {
      azione: AzioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.azione.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AzioneUpdateComponent,
    resolve: {
      azione: AzioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.azione.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AzioneUpdateComponent,
    resolve: {
      azione: AzioneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'antipodiStoreApp.azione.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
