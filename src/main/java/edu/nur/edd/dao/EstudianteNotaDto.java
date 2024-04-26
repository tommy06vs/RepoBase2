package edu.nur.edd.dao;

public class EstudianteNotaDto {
    private String estudianteId;
    private String notaId;
    private int totalNotas;
    private String materiaId;

    public EstudianteNotaDto(String estudianteId, String notaId, int totalNotas, String materiaId) {
        this.estudianteId = estudianteId;
        this.notaId = notaId;
        this.totalNotas = totalNotas;
        this.materiaId = materiaId;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(String estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getNotaId() {
        return notaId;
    }

    public void setNotaId(String notaId) {
        this.notaId = notaId;
    }

    public int getTotalNotas() {
        return totalNotas;
    }

    public void setTotalNotas(int totalNotas) {
        this.totalNotas = totalNotas;
    }

    public String getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(String materiaId) {
        this.materiaId = materiaId;
    }

    @Override
    public String toString() {
        return "EstudianteNota{" +
                "estudianteId='" + estudianteId + '\'' +
                ", notaId='" + notaId + '\'' +
                ", totalNotas=" + totalNotas +
                ", materiaId='" + materiaId + '\'' +
                '}';
    }
}
