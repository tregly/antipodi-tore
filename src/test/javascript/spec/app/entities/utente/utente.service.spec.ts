import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { UtenteService } from 'app/entities/utente/utente.service';
import { IUtente, Utente } from 'app/shared/model/utente.model';

describe('Service Tests', () => {
  describe('Utente Service', () => {
    let injector: TestBed;
    let service: UtenteService;
    let httpMock: HttpTestingController;
    let elemDefault: IUtente;
    let expectedResult: IUtente | IUtente[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UtenteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Utente(
        0,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            created: currentDate.format(DATE_FORMAT),
            modified: currentDate.format(DATE_FORMAT),
            dataBolocco: currentDate.format(DATE_FORMAT),
            registrationDate: currentDate.format(DATE_FORMAT),
            lastAccess: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Utente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            created: currentDate.format(DATE_FORMAT),
            modified: currentDate.format(DATE_FORMAT),
            dataBolocco: currentDate.format(DATE_FORMAT),
            registrationDate: currentDate.format(DATE_FORMAT),
            lastAccess: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            created: currentDate,
            modified: currentDate,
            dataBolocco: currentDate,
            registrationDate: currentDate,
            lastAccess: currentDate,
          },
          returnedFromService
        );

        service.create(new Utente()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Utente', () => {
        const returnedFromService = Object.assign(
          {
            created: currentDate.format(DATE_FORMAT),
            modified: currentDate.format(DATE_FORMAT),
            username: 'BBBBBB',
            password: 'BBBBBB',
            mail: 'BBBBBB',
            mobile: 'BBBBBB',
            facebook: 'BBBBBB',
            google: 'BBBBBB',
            instangram: 'BBBBBB',
            provider: 'BBBBBB',
            attivo: true,
            motivoBolocco: 'BBBBBB',
            dataBolocco: currentDate.format(DATE_FORMAT),
            registrationDate: currentDate.format(DATE_FORMAT),
            lastAccess: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            created: currentDate,
            modified: currentDate,
            dataBolocco: currentDate,
            registrationDate: currentDate,
            lastAccess: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Utente', () => {
        const returnedFromService = Object.assign(
          {
            created: currentDate.format(DATE_FORMAT),
            modified: currentDate.format(DATE_FORMAT),
            username: 'BBBBBB',
            password: 'BBBBBB',
            mail: 'BBBBBB',
            mobile: 'BBBBBB',
            facebook: 'BBBBBB',
            google: 'BBBBBB',
            instangram: 'BBBBBB',
            provider: 'BBBBBB',
            attivo: true,
            motivoBolocco: 'BBBBBB',
            dataBolocco: currentDate.format(DATE_FORMAT),
            registrationDate: currentDate.format(DATE_FORMAT),
            lastAccess: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            created: currentDate,
            modified: currentDate,
            dataBolocco: currentDate,
            registrationDate: currentDate,
            lastAccess: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Utente', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
