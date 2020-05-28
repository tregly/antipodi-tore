import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUtente, Utente } from 'app/shared/model/utente.model';
import { UtenteService } from './utente.service';
import { IRuolo } from 'app/shared/model/ruolo.model';
import { RuoloService } from 'app/entities/ruolo/ruolo.service';

@Component({
  selector: 'jhi-utente-update',
  templateUrl: './utente-update.component.html',
})
export class UtenteUpdateComponent implements OnInit {
  isSaving = false;
  ruolos: IRuolo[] = [];
  createdDp: any;
  modifiedDp: any;
  dataBoloccoDp: any;
  registrationDateDp: any;
  lastAccessDp: any;

  editForm = this.fb.group({
    id: [],
    created: [],
    modified: [],
    username: [],
    password: [],
    mail: [],
    mobile: [],
    facebook: [],
    google: [],
    instangram: [],
    provider: [],
    attivo: [],
    motivoBolocco: [],
    dataBolocco: [],
    registrationDate: [],
    lastAccess: [],
    ruolo: [],
  });

  constructor(
    protected utenteService: UtenteService,
    protected ruoloService: RuoloService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ utente }) => {
      this.updateForm(utente);

      this.ruoloService
        .query({ filter: 'utente-is-null' })
        .pipe(
          map((res: HttpResponse<IRuolo[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRuolo[]) => {
          if (!utente.ruolo || !utente.ruolo.id) {
            this.ruolos = resBody;
          } else {
            this.ruoloService
              .find(utente.ruolo.id)
              .pipe(
                map((subRes: HttpResponse<IRuolo>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRuolo[]) => (this.ruolos = concatRes));
          }
        });
    });
  }

  updateForm(utente: IUtente): void {
    this.editForm.patchValue({
      id: utente.id,
      created: utente.created,
      modified: utente.modified,
      username: utente.username,
      password: utente.password,
      mail: utente.mail,
      mobile: utente.mobile,
      facebook: utente.facebook,
      google: utente.google,
      instangram: utente.instangram,
      provider: utente.provider,
      attivo: utente.attivo,
      motivoBolocco: utente.motivoBolocco,
      dataBolocco: utente.dataBolocco,
      registrationDate: utente.registrationDate,
      lastAccess: utente.lastAccess,
      ruolo: utente.ruolo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const utente = this.createFromForm();
    if (utente.id !== undefined) {
      this.subscribeToSaveResponse(this.utenteService.update(utente));
    } else {
      this.subscribeToSaveResponse(this.utenteService.create(utente));
    }
  }

  private createFromForm(): IUtente {
    return {
      ...new Utente(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value,
      modified: this.editForm.get(['modified'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      mail: this.editForm.get(['mail'])!.value,
      mobile: this.editForm.get(['mobile'])!.value,
      facebook: this.editForm.get(['facebook'])!.value,
      google: this.editForm.get(['google'])!.value,
      instangram: this.editForm.get(['instangram'])!.value,
      provider: this.editForm.get(['provider'])!.value,
      attivo: this.editForm.get(['attivo'])!.value,
      motivoBolocco: this.editForm.get(['motivoBolocco'])!.value,
      dataBolocco: this.editForm.get(['dataBolocco'])!.value,
      registrationDate: this.editForm.get(['registrationDate'])!.value,
      lastAccess: this.editForm.get(['lastAccess'])!.value,
      ruolo: this.editForm.get(['ruolo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUtente>>): void {
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

  trackById(index: number, item: IRuolo): any {
    return item.id;
  }
}
