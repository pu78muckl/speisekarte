import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Kunde } from 'app/shared/model/kunde.model';
import { KundeService } from './kunde.service';
import { KundeComponent } from './kunde.component';
import { KundeDetailComponent } from './kunde-detail.component';
import { KundeUpdateComponent } from './kunde-update.component';
import { KundeDeletePopupComponent } from './kunde-delete-dialog.component';
import { IKunde } from 'app/shared/model/kunde.model';

@Injectable({ providedIn: 'root' })
export class KundeResolve implements Resolve<IKunde> {
    constructor(private service: KundeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Kunde> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Kunde>) => response.ok),
                map((kunde: HttpResponse<Kunde>) => kunde.body)
            );
        }
        return of(new Kunde());
    }
}

export const kundeRoute: Routes = [
    {
        path: 'kunde',
        component: KundeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kunde/:id/view',
        component: KundeDetailComponent,
        resolve: {
            kunde: KundeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kunde/new',
        component: KundeUpdateComponent,
        resolve: {
            kunde: KundeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'kunde/:id/edit',
        component: KundeUpdateComponent,
        resolve: {
            kunde: KundeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const kundePopupRoute: Routes = [
    {
        path: 'kunde/:id/delete',
        component: KundeDeletePopupComponent,
        resolve: {
            kunde: KundeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Kundes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
