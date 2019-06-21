import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IKunde } from 'app/shared/model/kunde.model';

type EntityResponseType = HttpResponse<IKunde>;
type EntityArrayResponseType = HttpResponse<IKunde[]>;

@Injectable({ providedIn: 'root' })
export class KundeService {
    public resourceUrl = SERVER_API_URL + 'api/kundes';

    constructor(protected http: HttpClient) {}

    create(kunde: IKunde): Observable<EntityResponseType> {
        return this.http.post<IKunde>(this.resourceUrl, kunde, { observe: 'response' });
    }

    update(kunde: IKunde): Observable<EntityResponseType> {
        return this.http.put<IKunde>(this.resourceUrl, kunde, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IKunde>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IKunde[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
