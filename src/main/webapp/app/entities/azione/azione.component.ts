import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAzione } from 'app/shared/model/azione.model';
import { AzioneService } from './azione.service';
import { AzioneDeleteDialogComponent } from './azione-delete-dialog.component';

@Component({
  selector: 'jhi-azione',
  templateUrl: './azione.component.html',
})
export class AzioneComponent implements OnInit, OnDestroy {
  aziones?: IAzione[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected azioneService: AzioneService,
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
      this.azioneService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAzione[]>) => (this.aziones = res.body || []));
      return;
    }

    this.azioneService.query().subscribe((res: HttpResponse<IAzione[]>) => (this.aziones = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAziones();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAzione): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAziones(): void {
    this.eventSubscriber = this.eventManager.subscribe('azioneListModification', () => this.loadAll());
  }

  delete(azione: IAzione): void {
    const modalRef = this.modalService.open(AzioneDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.azione = azione;
  }
}
