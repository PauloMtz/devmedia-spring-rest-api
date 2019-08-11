package curso.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CargaHoraria {
    VINTE_HORAS("20H"),
    TRINTA_HORAS("30H"),
    QUARENTA_HORAS("40H"),
    SESSENTA_HORAS("60H");

    private String horas;

    CargaHoraria(String horas) {this.horas = horas;}

    @JsonValue
    public String getHoras() {
        return horas;
    }
}
