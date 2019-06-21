export interface IKunde {
    id?: string;
    name?: string;
    vorname?: string;
}

export class Kunde implements IKunde {
    constructor(public id?: string, public name?: string, public vorname?: string) {}
}
