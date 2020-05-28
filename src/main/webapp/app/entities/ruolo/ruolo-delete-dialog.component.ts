import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRuolo } from 'app/shared/model/ruolo.model';
import { RuoloService } from './ruolo.service';

@Component({
  templateUrl: './ruolo-delete-dialog.component.html',
})
export class RuoloDeleteDialogComponent {
  ruolo?: IRuolo;

  constructor(protected ruoloService: RuoloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ruoloService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ruoloListModification');
      this.activeModal.close();
    });
  }
}
