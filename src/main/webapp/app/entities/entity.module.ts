import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'azione',
        loadChildren: () => import('./azione/azione.module').then(m => m.AntipodiStoreAzioneModule),
      },
      {
        path: 'ruolo',
        loadChildren: () => import('./ruolo/ruolo.module').then(m => m.AntipodiStoreRuoloModule),
      },
      {
        path: 'utente',
        loadChildren: () => import('./utente/utente.module').then(m => m.AntipodiStoreUtenteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AntipodiStoreEntityModule {}
