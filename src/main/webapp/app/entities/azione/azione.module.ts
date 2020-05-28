import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AntipodiStoreSharedModule } from 'app/shared/shared.module';
import { AzioneComponent } from './azione.component';
import { AzioneDetailComponent } from './azione-detail.component';
import { AzioneUpdateComponent } from './azione-update.component';
import { AzioneDeleteDialogComponent } from './azione-delete-dialog.component';
import { azioneRoute } from './azione.route';

@NgModule({
  imports: [AntipodiStoreSharedModule, RouterModule.forChild(azioneRoute)],
  declarations: [AzioneComponent, AzioneDetailComponent, AzioneUpdateComponent, AzioneDeleteDialogComponent],
  entryComponents: [AzioneDeleteDialogComponent],
})
export class AntipodiStoreAzioneModule {}
