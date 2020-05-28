import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAzione } from 'app/shared/model/azione.model';

@Component({
  selector: 'jhi-azione-detail',
  templateUrl: './azione-detail.component.html',
})
export class AzioneDetailComponent implements OnInit {
  azione: IAzione | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ azione }) => (this.azione = azione));
  }

  previousState(): void {
    window.history.back();
  }
}
