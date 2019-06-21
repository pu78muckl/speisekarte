import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpeisekarte } from 'app/shared/model/speisekarte.model';

type EntityResponseType = HttpResponse<ISpeisekarte>;
type EntityArrayResponseType = HttpResponse<ISpeisekarte[]>;

@Injectable({ providedIn: 'root' })
export class SpeisekarteService {
    public resourceUrl = SERVER_API_URL + 'api/speisekartes';

    constructor(protected http: HttpClient) {}

    create(speisekarte: ISpeisekarte): Observable<EntityResponseType> {
        return this.http.post<ISpeisekarte>(this.resourceUrl, speisekarte, { observe: 'response' });
    }

    update(speisekarte: ISpeisekarte): Observable<EntityResponseType> {
        return this.http.put<ISpeisekarte>(this.resourceUrl, speisekarte, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ISpeisekarte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISpeisekarte[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
