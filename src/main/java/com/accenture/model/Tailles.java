package com.accenture.model;

public enum Tailles {

    PETITE(Tarifs.DIX_EUROS),
    MOYENNE(Tarifs.QUINZE_EUROS),
    GRANDE(Tarifs.VINGT_EUROS);

    private Tarifs tarifs;

    Tailles(Tarifs tarifs) {
        this.tarifs = tarifs;
    }

    public Tarifs getTarifs() {
        return tarifs;
    }
}
