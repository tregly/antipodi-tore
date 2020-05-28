import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AntipodiStoreTestModule } from '../../../test.module';
import { UtenteDetailComponent } from 'app/entities/utente/utente-detail.component';
import { Utente } from 'app/shared/model/utente.model';

describe('Component Tests', () => {
  describe('Utente Management Detail Component', () => {
    let comp: UtenteDetailComponent;
    let fixture: ComponentFixture<UtenteDetailComponent>;
    const route = ({ data: of({ utente: new Utente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AntipodiStoreTestModule],
        declarations: [UtenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UtenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UtenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load utente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.utente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
