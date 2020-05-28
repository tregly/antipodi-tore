import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUtente } from 'app/shared/model/utente.model';
import { UtenteService } from './utente.service';
import { UtenteDeleteDialogComponent } from './utente-delete-dialog.component';

@Component({
  selector: 'jhi-utente',
  templateUrl: './utente.component.html',
})
export class UtenteComponent implements OnInit, OnDestroy {
  utentes?: IUtente[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected utenteService: UtenteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.utenteService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IUtente[]>) => (this.utentes = res.body || []));
      return;
    }

    this.utenteService.query().subscribe((res: HttpResponse<IUtente[]>) => (this.utentes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUtentes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUtente): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUtentes(): void {
    this.eventSubscriber = this.eventManager.subscribe('utenteListModification', () => this.loadAll());
  }

  delete(utente: IUtente): void {
    const modalRef = this.modalService.open(UtenteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.utente = utente;
  }
}
