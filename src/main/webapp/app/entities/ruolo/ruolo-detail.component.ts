import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRuolo } from 'app/shared/model/ruolo.model';

@Component({
  selector: 'jhi-ruolo-detail',
  templateUrl: './ruolo-detail.component.html',
})
export class RuoloDetailComponent implements OnInit {
  ruolo: IRuolo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ruolo }) => (this.ruolo = ruolo));
  }

  previousState(): void {
    window.history.back();
  }
}
