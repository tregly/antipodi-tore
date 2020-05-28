import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRuolo, Ruolo } from 'app/shared/model/ruolo.model';
import { RuoloService } from './ruolo.service';
import { IAzione } from 'app/shared/model/azione.model';
import { AzioneService } from 'app/entities/azione/azione.service';

@Component({
  selector: 'jhi-ruolo-update',
  templateUrl: './ruolo-update.component.html',
})
export class RuoloUpdateComponent implements OnInit {
  isSaving = false;
  aziones: IAzione[] = [];
  createdDp: any;
  modifiedDp: any;

  editForm = this.fb.group({
    id: [],
    created: [],
    modified: [],
    nomeAzione: [],
    azioni: [],
  });

  constructor(
    protected ruoloService: RuoloService,
    protected azioneService: AzioneService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ruolo }) => {
      this.updateForm(ruolo);

      this.azioneService.query().subscribe((res: HttpResponse<IAzione[]>) => (this.aziones = res.body || []));
    });
  }

  updateForm(ruolo: IRuolo): void {
    this.editForm.patchValue({
      id: ruolo.id,
      created: ruolo.created,
      modified: ruolo.modified,
      nomeAzione: ruolo.nomeAzione,
      azioni: ruolo.azioni,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ruolo = this.createFromForm();
    if (ruolo.id !== undefined) {
      this.subscribeToSaveResponse(this.ruoloService.update(ruolo));
    } else {
      this.subscribeToSaveResponse(this.ruoloService.create(ruolo));
    }
  }

  private createFromForm(): IRuolo {
    return {
      ...new Ruolo(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value,
      modified: this.editForm.get(['modified'])!.value,
      nomeAzione: this.editForm.get(['nomeAzione'])!.value,
      azioni: this.editForm.get(['azioni'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRuolo>>): void {
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

  trackById(index: number, item: IAzione): any {
    return item.id;
  }
}
