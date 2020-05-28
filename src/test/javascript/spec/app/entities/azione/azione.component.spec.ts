import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AntipodiStoreTestModule } from '../../../test.module';
import { AzioneComponent } from 'app/entities/azione/azione.component';
import { AzioneService } from 'app/entities/azione/azione.service';
import { Azione } from 'app/shared/model/azione.model';

describe('Component Tests', () => {
  describe('Azione Management Component', () => {
    let comp: AzioneComponent;
    let fixture: ComponentFixture<AzioneComponent>;
    let service: AzioneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AntipodiStoreTestModule],
        declarations: [AzioneComponent],
      })
        .overrideTemplate(AzioneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AzioneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AzioneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Azione(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aziones && comp.aziones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
