import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAzione } from 'app/shared/model/azione.model';
import { AzioneService } from './azione.service';

@Component({
  templateUrl: './azione-delete-dialog.component.html',
})
export class AzioneDeleteDialogComponent {
  azione?: IAzione;

  constructor(protected azioneService: AzioneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.azioneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('azioneListModification');
      this.activeModal.close();
    });
  }
}
