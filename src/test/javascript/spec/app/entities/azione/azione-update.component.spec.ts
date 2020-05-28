import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AntipodiStoreTestModule } from '../../../test.module';
import { AzioneUpdateComponent } from 'app/entities/azione/azione-update.component';
import { AzioneService } from 'app/entities/azione/azione.service';
import { Azione } from 'app/shared/model/azione.model';

describe('Component Tests', () => {
  describe('Azione Management Update Component', () => {
    let comp: AzioneUpdateComponent;
    let fixture: ComponentFixture<AzioneUpdateComponent>;
    let service: AzioneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AntipodiStoreTestModule],
        declarations: [AzioneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AzioneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AzioneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AzioneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Azione(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Azione();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
