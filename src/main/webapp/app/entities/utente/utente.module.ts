import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AntipodiStoreSharedModule } from 'app/shared/shared.module';
import { UtenteComponent } from './utente.component';
import { UtenteDetailComponent } from './utente-detail.component';
import { UtenteUpdateComponent } from './utente-update.component';
import { UtenteDeleteDialogComponent } from './utente-delete-dialog.component';
import { utenteRoute } from './utente.route';

@NgModule({
  imports: [AntipodiStoreSharedModule, RouterModule.forChild(utenteRoute)],
  declarations: [UtenteComponent, UtenteDetailComponent, UtenteUpdateComponent, UtenteDeleteDialogComponent],
  entryComponents: [UtenteDeleteDialogComponent],
})
export class AntipodiStoreUtenteModule {}
