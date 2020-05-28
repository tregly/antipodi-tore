import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AntipodiStoreTestModule } from '../../../test.module';
import { RuoloUpdateComponent } from 'app/entities/ruolo/ruolo-update.component';
import { RuoloService } from 'app/entities/ruolo/ruolo.service';
import { Ruolo } from 'app/shared/model/ruolo.model';

describe('Component Tests', () => {
  describe('Ruolo Management Update Component', () => {
    let comp: RuoloUpdateComponent;
    let fixture: ComponentFixture<RuoloUpdateComponent>;
    let service: RuoloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AntipodiStoreTestModule],
        declarations: [RuoloUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RuoloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RuoloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RuoloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ruolo(123);
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
        const entity = new Ruolo();
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
