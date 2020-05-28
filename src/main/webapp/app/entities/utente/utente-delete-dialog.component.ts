import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUtente } from 'app/shared/model/utente.model';
import { UtenteService } from './utente.service';

@Component({
  templateUrl: './utente-delete-dialog.component.html',
})
export class UtenteDeleteDialogComponent {
  utente?: IUtente;

  constructor(protected utenteService: UtenteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.utenteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('utenteListModification');
      this.activeModal.close();
    });
  }
}
