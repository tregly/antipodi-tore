import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AntipodiStoreTestModule } from '../../../test.module';
import { RuoloDetailComponent } from 'app/entities/ruolo/ruolo-detail.component';
import { Ruolo } from 'app/shared/model/ruolo.model';

describe('Component Tests', () => {
  describe('Ruolo Management Detail Component', () => {
    let comp: RuoloDetailComponent;
    let fixture: ComponentFixture<RuoloDetailComponent>;
    const route = ({ data: of({ ruolo: new Ruolo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AntipodiStoreTestModule],
        declarations: [RuoloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RuoloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RuoloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ruolo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ruolo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
