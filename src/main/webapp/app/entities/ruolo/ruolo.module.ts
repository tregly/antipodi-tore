import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AntipodiStoreSharedModule } from 'app/shared/shared.module';
import { RuoloComponent } from './ruolo.component';
import { RuoloDetailComponent } from './ruolo-detail.component';
import { RuoloUpdateComponent } from './ruolo-update.component';
import { RuoloDeleteDialogComponent } from './ruolo-delete-dialog.component';
import { ruoloRoute } from './ruolo.route';

@NgModule({
  imports: [AntipodiStoreSharedModule, RouterModule.forChild(ruoloRoute)],
  declarations: [RuoloComponent, RuoloDetailComponent, RuoloUpdateComponent, RuoloDeleteDialogComponent],
  entryComponents: [RuoloDeleteDialogComponent],
})
export class AntipodiStoreRuoloModule {}
