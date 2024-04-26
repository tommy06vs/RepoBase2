package edu.nur.edd.dao;

public class NotaDto {
    private String codigoId;
    private String estadoAcademico;
    private String descripcion;
    private String materiaId;

    public NotaDto(String codigoId, String estadoAcademico, String descripcion, String materiaId) {
        this.codigoId = codigoId;
        this.estadoAcademico = estadoAcademico;
        this.descripcion = descripcion;
        this.materiaId = materiaId;
    }

    public String getCodigoId() {
        return codigoId;
    }

    public void setCodigoId(String codigoId) {
        this.codigoId = codigoId;
    }

    public String getEstadoAcademico() {
        return estadoAcademico;
    }

    public void setEstadoAcademico(String estadoAcademico) {
        this.estadoAcademico = estadoAcademico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(String materiaId) {
        this.materiaId = materiaId;
    }

    @Override
    public String toString() {
        return "NotaDto{" +
                "codigoId='" + codigoId + '\'' +
                ", estadoAcademico='" + estadoAcademico + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", materiaId='" + materiaId + '\'' +
                '}';
    }
}
