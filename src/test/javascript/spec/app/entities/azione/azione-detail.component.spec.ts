import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AntipodiStoreTestModule } from '../../../test.module';
import { AzioneDetailComponent } from 'app/entities/azione/azione-detail.component';
import { Azione } from 'app/shared/model/azione.model';

describe('Component Tests', () => {
  describe('Azione Management Detail Component', () => {
    let comp: AzioneDetailComponent;
    let fixture: ComponentFixture<AzioneDetailComponent>;
    const route = ({ data: of({ azione: new Azione(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AntipodiStoreTestModule],
        declarations: [AzioneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AzioneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AzioneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load azione on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.azione).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
