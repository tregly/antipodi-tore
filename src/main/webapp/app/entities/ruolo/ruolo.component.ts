import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRuolo } from 'app/shared/model/ruolo.model';
import { RuoloService } from './ruolo.service';
import { RuoloDeleteDialogComponent } from './ruolo-delete-dialog.component';

@Component({
  selector: 'jhi-ruolo',
  templateUrl: './ruolo.component.html',
})
export class RuoloComponent implements OnInit, OnDestroy {
  ruolos?: IRuolo[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected ruoloService: RuoloService,
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
      this.ruoloService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IRuolo[]>) => (this.ruolos = res.body || []));
      return;
    }

    this.ruoloService.query().subscribe((res: HttpResponse<IRuolo[]>) => (this.ruolos = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRuolos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRuolo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRuolos(): void {
    this.eventSubscriber = this.eventManager.subscribe('ruoloListModification', () => this.loadAll());
  }

  delete(ruolo: IRuolo): void {
    const modalRef = this.modalService.open(RuoloDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ruolo = ruolo;
  }
}
