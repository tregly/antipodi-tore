import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAzione, Azione } from 'app/shared/model/azione.model';
import { AzioneService } from './azione.service';

@Component({
  selector: 'jhi-azione-update',
  templateUrl: './azione-update.component.html',
})
export class AzioneUpdateComponent implements OnInit {
  isSaving = false;
  createdDp: any;
  modifiedDp: any;

  editForm = this.fb.group({
    id: [],
    created: [],
    modified: [],
    nomeAzione: [],
    descrizione: [],
  });

  constructor(protected azioneService: AzioneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ azione }) => {
      this.updateForm(azione);
    });
  }

  updateForm(azione: IAzione): void {
    this.editForm.patchValue({
      id: azione.id,
      created: azione.created,
      modified: azione.modified,
      nomeAzione: azione.nomeAzione,
      descrizione: azione.descrizione,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const azione = this.createFromForm();
    if (azione.id !== undefined) {
      this.subscribeToSaveResponse(this.azioneService.update(azione));
    } else {
      this.subscribeToSaveResponse(this.azioneService.create(azione));
    }
  }

  private createFromForm(): IAzione {
    return {
      ...new Azione(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value,
      modified: this.editForm.get(['modified'])!.value,
      nomeAzione: this.editForm.get(['nomeAzione'])!.value,
      descrizione: this.editForm.get(['descrizione'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAzione>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
