import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AntipodiStoreTestModule } from '../../../test.module';
import { RuoloComponent } from 'app/entities/ruolo/ruolo.component';
import { RuoloService } from 'app/entities/ruolo/ruolo.service';
import { Ruolo } from 'app/shared/model/ruolo.model';

describe('Component Tests', () => {
  describe('Ruolo Management Component', () => {
    let comp: RuoloComponent;
    let fixture: ComponentFixture<RuoloComponent>;
    let service: RuoloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AntipodiStoreTestModule],
        declarations: [RuoloComponent],
      })
        .overrideTemplate(RuoloComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RuoloComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RuoloService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ruolo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.ruolos && comp.ruolos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
