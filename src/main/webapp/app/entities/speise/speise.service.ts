import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpeise } from 'app/shared/model/speise.model';

type EntityResponseType = HttpResponse<ISpeise>;
type EntityArrayResponseType = HttpResponse<ISpeise[]>;

@Injectable({ providedIn: 'root' })
export class SpeiseService {
    public resourceUrl = SERVER_API_URL + 'api/speises';

    constructor(protected http: HttpClient) {}

    create(speise: ISpeise): Observable<EntityResponseType> {
        return this.http.post<ISpeise>(this.resourceUrl, speise, { observe: 'response' });
    }

    update(speise: ISpeise): Observable<EntityResponseType> {
        return this.http.put<ISpeise>(this.resourceUrl, speise, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ISpeise>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISpeise[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
