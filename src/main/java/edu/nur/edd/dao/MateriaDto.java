package edu.nur.edd.dao;

public class MateriaDto {
    private String codigoId;
    private String nombre;
    private int numeroCredito;
    private String estudianteId;

    public MateriaDto(String codigoId, String nombre, int numeroCredito, String estudianteId) {
        this.codigoId = codigoId;
        this.nombre = nombre;
        this.numeroCredito = numeroCredito;
        this.estudianteId = estudianteId;
    }

    public String getCodigoId() {
        return codigoId;
    }

    public void setCodigoId(String codigoId) {
        this.codigoId = codigoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroCredito() {
        return numeroCredito;
    }

    public void setNumeroCredito(int numeroCredito) {
        this.numeroCredito = numeroCredito;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(String estudianteId) {
        this.estudianteId = estudianteId;
    }

    @Override
    public String toString() {
        return "MateriaDto{" +
                "codigoId='" + codigoId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", numeroCredito=" + numeroCredito +
                ", estudianteId='" + estudianteId + '\'' +
                '}';
    }
}
