import { ISpeise } from 'app/shared/model//speise.model';
import { IKunde } from 'app/shared/model//kunde.model';

export interface ISpeisekarte {
    id?: string;
    name?: string;
    kunde?: string;
    speise?: ISpeise;
    kundeids?: IKunde[];
}

export class Speisekarte implements ISpeisekarte {
    constructor(public id?: string, public name?: string, public kunde?: string, public speise?: ISpeise, public kundeids?: IKunde[]) {}
}
