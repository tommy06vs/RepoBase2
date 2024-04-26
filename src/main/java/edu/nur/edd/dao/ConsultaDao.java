package edu.nur.edd.dao;

import edu.nur.edd.listas.ListaDoble;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConsultaDao {
    private static Logger logger = LogManager.getRootLogger();

    public ConsultaDao() {
    }

    public boolean idYaExiste(String id) {// si el id ya existe para registrar
        String sql = "SELECT COUNT(*) AS count FROM estudiantes WHERE codigo_id=?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            logger.error("Error al registrar un estudiante ya existente: " + e.getMessage());
            e.printStackTrace();

        }
        // En caso de error o si el id no existe en la base de datos
        return false;
    }
    public EstudianteDto getById(String codigoId) {// obtener un dato especifico llamano al id_ llave perimaria
        EstudianteDto resultado = null;
        String sql = "SELECT codigo_id, snombre, sappaterno, sapmaterno, dtnacimiento, materia_id, carrera FROM estudiantes WHERE codigo_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String snombre = rs.getString("snombre");
                String sappaterno = rs.getString("sappaterno");
                String sapmaterno = rs.getString("sapmaterno");
                String dtnacimiento = rs.getString("dtnacimiento");
                String materiaId = rs.getString("materia_id");
                String carrera = rs.getString("carrera");

                resultado = new EstudianteDto(codigoId, snombre, sappaterno, sapmaterno, dtnacimiento, materiaId, carrera);
            }
            logger.info("Se obtiene la información de un estudiante en específico");
        } catch (SQLException e) {
            logger.error("Error al obtener el estudiante especificado: " + e.getMessage());
            // Aquí puedes manejar la excepción según tus requerimientos
        }
        return resultado;
    }
    public boolean verificarIniciarSesion(String id, String nombre) {
        String sql = "SELECT COUNT(*) AS count FROM estudiantes WHERE LOWER(codigo_id)=LOWER(?) AND LOWER(snombre)=LOWER(?)";
        boolean verificador = false;

        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                verificador = count > 0;
            }

        } catch (SQLException e) {
            logger.warn("error en la verificacion");
            logger.error("Error en la verificación de inicio de sesión: " + e.getMessage());
        }

        return verificador;
    }

    //1) Registrar un nuevo estudiante junto con sus materias inscritas y notas*******************************************************************
    public void registrarNuevoEstudiante2(String codigoId, String nombre, String apellidoPaterno, String fechaNacimiento, String carrera,
                                         String[] notasIds, int[] tnotas, String[] materiasIds) {
        String sql = "WITH nuevo_estudiante AS (" +
                "    INSERT INTO estudiantes (codigo_id, snombre, sappaterno, dtnacimiento, carrera) " +
                "    VALUES (?, ?, ?, ?, ?) " +
                "    RETURNING codigo_id " +
                ") " +
                "INSERT INTO estudiantesNotas (estudiante_id, nota_id, tnotas, materia_id) " +
                "SELECT ne.codigo_id, nota_id, tnotas, materia_id " +
                "FROM nuevo_estudiante ne, " +
                "(VALUES ";
        for (int i = 0; i < notasIds.length; i++) {
            sql += "(?, ?, ?)";
            if (i < notasIds.length - 1) {
                sql += ", ";
            }
        }
        sql += ") AS notas (nota_id, tnotas, materia_id);";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoId);
            ps.setString(2, nombre);
            ps.setString(3, apellidoPaterno);

            ps.setDate(4, Date.valueOf(fechaNacimiento));
            ps.setString(5, carrera);
            int parameterIndex = 6;
            for (int i = 0; i < notasIds.length; i++) {
                ps.setString(parameterIndex++, notasIds[i]);
                ps.setInt(parameterIndex++, tnotas[i]);
                ps.setString(parameterIndex++, materiasIds[i]);
            }
            ps.executeUpdate();
            logger.info("\u001B[32mSe registra un nuevo estudiante junto con sus materias inscritas y notas\u001B[0m");
        } catch (SQLException e) {
            logger.error("\u001B[31mError al registrar el nuevo estudiante: \u001B[0m" + e.getMessage());
        }
    }

    public ListaDoble<EstudianteNotaDto> getAllEstudiantesNotas() {
        ListaDoble<EstudianteNotaDto> resultado = new ListaDoble<>();
        String sql = "SELECT estudiante_id, nota_id, materia_id, tnotas FROM estudiantesnotas";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String estudianteId = rs.getString("estudiante_id");
                String notaID = rs.getString("nota_id");
                String materiaID = rs.getString("materia_id");
                int tnotas = Integer.parseInt(rs.getString("tnotas"));


                EstudianteNotaDto estudianteNotas = new EstudianteNotaDto(estudianteId, notaID, tnotas, materiaID );
                resultado.insertar(estudianteNotas);
            }
            logger.info("Se obtiene la lista de estudiantesNotas registrados");
        } catch (SQLException e) {
            logger.error("Error al obtener la lista de estudiantes: " + e.getMessage());
            // Aquí puedes manejar la excepción según tus requerimientos
        }
        return resultado;
    }


    //-- 2) ver la lista de estudiantes registrados**************************************************************************
    public ListaDoble<EstudianteDto> getAllEstudiantes() {
        ListaDoble<EstudianteDto> resultado = new ListaDoble<>();
        String sql = "SELECT codigo_id, snombre, sappaterno, sapmaterno, dtnacimiento, materia_id,carrera FROM estudiantes";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String codigoId = rs.getString("codigo_id");
                String snombre = rs.getString("snombre");
                String sappaterno = rs.getString("sappaterno");
                String sapmaterno = rs.getString("sapmaterno");
                String dtnacimiento = rs.getString("dtnacimiento");
                String materiaID = rs.getString("materia_id");
                String carrera = rs.getString("carrera");

                EstudianteDto estudiante = new EstudianteDto(codigoId, snombre, sappaterno, sapmaterno, dtnacimiento, materiaID, carrera);
                resultado.insertar(estudiante);
            }
            logger.info("Se obtiene la lista de estudiantes registrados");
        } catch (SQLException e) {
            logger.error("Error al obtener la lista de estudiantes: " + e.getMessage());
            // Aquí puedes manejar la excepción según tus requerimientos
        }
        return resultado;
    }

    // -- 3) ver las materias en las que esta inscrito un estudiante especifico*******************************************************************
    public ListaDoble<MateriaDto> getMateriasPorEstudiante(String codigoIdEstudiante) {
        ListaDoble<MateriaDto> resultado = new ListaDoble<>();
        String sql = "SELECT e.codigo_id, e.snombre, e.sappaterno,  en.materia_id, m.snombre " +
                "FROM estudiantes e " +
                "JOIN estudiantesNotas en ON e.codigo_id = en.estudiante_id " +
                "JOIN materias m ON en.materia_id = m.codigo_id " +
                "WHERE e.codigo_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoIdEstudiante);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String codigoId = rs.getString("codigo_id");
                String snombre = rs.getString("snombre");
                String sappaterno = rs.getString("sappaterno");
                String sapmaterno = rs.getString("sapmaterno");
                String materiaId = rs.getString("materia_id");
                String nombreMateria = rs.getString("snombre");

                EstudianteDto estudiante = new EstudianteDto(codigoId, snombre, sappaterno, sapmaterno, null, null, null);
                MateriaDto materia = new MateriaDto(materiaId, nombreMateria, 0, null);

                resultado.insertar(materia);
            }
            logger.info("Se obtienen las materias en las que está inscrito un estudiante específico");
        } catch (SQLException e) {
            logger.error("\u001B[31mError al obtener las materias del estudiante: \u001B[0m" + e.getMessage());
        }
        return resultado;
    }

    //4) ver las notas de un estudiante en una materia especifica*******************************************************************
    public ListaDoble<NotaDto> getNotasDeEstudianteEnMateria(String codigoIdEstudiante, String materiaId) {
        ListaDoble<NotaDto> resultado = new ListaDoble<>();
        String sql = "SELECT e.codigo_id, e.snombre, e.sappaterno, en.materia_id, n.eacademico " +
                "FROM estudiantes e " +
                "JOIN estudiantesNotas en ON e.codigo_id = en.estudiante_id " +
                "JOIN notas n ON en.nota_id = n.codigo_id " +
                "WHERE e.codigo_id = ? " +
                "AND en.materia_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoIdEstudiante);
            ps.setString(2, materiaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String codigoId = rs.getString("codigo_id");
                String snombre = rs.getString("snombre");
                String sappaterno = rs.getString("sappaterno");
                String materiaIdResult = rs.getString("materia_id");
                String eacademico = rs.getString("eacademico");

                EstudianteDto estudiante = new EstudianteDto(codigoId, snombre, sappaterno, null, null, null, null);
                NotaDto nota = new NotaDto(null, eacademico, null, null);

                resultado.insertar(nota);
            }
            logger.info("Se obtienen las notas de un estudiante en una materia específica");
        } catch (SQLException e) {
            logger.error("Error al obtener las notas del estudiante en la materia específica: " + e.getMessage());
            // Aquí puedes manejar la excepción según tus requerimientos
        }
        return resultado;
    }

    //5) calcular el promedio de notas de un estudiante en una materia especifica*******************************************************************
    public double calcularPromedioNotas(String codigoIdEstudiante, String materiaId) {
        double promedio = 0.0;
        String sql = "SELECT AVG(en.tnotas) AS promedio_notas " +
                "FROM estudiantesNotas en " +
                "WHERE en.estudiante_id = ? " +
                "AND en.materia_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoIdEstudiante);
            ps.setString(2, materiaId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                promedio = rs.getDouble("promedio_notas");
            }
            logger.info("Se calcula el promedio de notas de un estudiante en una materia específica");
        } catch (SQLException e) {
            logger.error("Error al calcular el promedio de notas del estudiante en la materia específica: " + e.getMessage());
            // Aquí puedes manejar la excepción según tus requerimientos
        }
        return promedio;
    }

    //6) actualizar la informacion de un estudiante o de una materia*******************************************************************
    public boolean actualizarEstudiante(EstudianteDto estudiante) {
        String sql = "UPDATE estudiantes " +
                "SET snombre = ?, sappaterno = ?, sapmaterno = ?, dtnacimiento = ?, materia_id = ?, carrera = ? " +
                "WHERE codigo_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellidoPaterno());
            ps.setString(3, estudiante.getApellidoMaterno());
            ps.setString(4, estudiante.getFechaNacimiento());
            ps.setString(5, estudiante.getMateriaId());
            ps.setString(6, estudiante.getCarrera());
            ps.setString(7, estudiante.getCodigoId());
            int filasActualizadas = ps.executeUpdate();
            logger.info("Se actualizó la información del estudiante con código: " + estudiante.getCodigoId());
            return filasActualizadas > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar la información del estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarMateria(MateriaDto materia) {
        String sql = "UPDATE materias " +
                "SET snombre = ?, nro_credito = ?, estudiante_id = ? " +
                "WHERE codigo_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getNumeroCredito());
            ps.setString(3, materia.getEstudianteId());
            ps.setString(4, materia.getCodigoId());
            int filasActualizadas = ps.executeUpdate();
            logger.info("Se actualizó la información de la materia con código: " + materia.getCodigoId());
            return filasActualizadas > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar la información de la materia: " + e.getMessage());
            return false;
        }
    }

    //7) elimnar un estudiante o una materia de la base de datos**************************************************************
    public boolean eliminarEstudiante(String codigoEstudiante) {
        String sql = "DELETE FROM estudiantesNotas WHERE estudiante_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoEstudiante);
            int filasEliminadas = ps.executeUpdate();
            logger.info("Se eliminaron las notas del estudiante con código: " + codigoEstudiante);

            sql = "DELETE FROM estudiantes WHERE codigo_id = ?";
            ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoEstudiante);
            filasEliminadas += ps.executeUpdate();
            logger.info("Se eliminó al estudiante con código: " + codigoEstudiante);

            return filasEliminadas > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar al estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarMateria(String codigoMateria) {
        String sql = "DELETE FROM materias WHERE codigo_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoMateria);
            int filasEliminadas = ps.executeUpdate();
            logger.info("Se eliminó la materia con código: " + codigoMateria);
            return filasEliminadas > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar la materia: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarNota(String codigoNota) {
        String sql = "DELETE FROM notas WHERE codigo_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, codigoNota);
            int filasEliminadas = ps.executeUpdate();
            logger.info("Se eliminó la nota con código: " + codigoNota);
            return filasEliminadas > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar la nota: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEstudianteNotas(String estudianteId, String materiaId) {
        String sql = "DELETE FROM estudiantesNotas WHERE estudiante_id = ? AND materia_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, estudianteId);
            ps.setString(2, materiaId);
            int filasEliminadas = ps.executeUpdate();
            logger.info("Se eliminaron las notas del estudiante con ID: " + estudianteId + " en la materia con ID: " + materiaId);
            return filasEliminadas > 0;
        } catch (SQLException e) {
            logger.error("Error al eliminar las notas del estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstudianteNotas(String estudianteId, String materiaId, int nuevasNotas) {
        String sql = "UPDATE estudiantesNotas SET tnotas = ? WHERE estudiante_id = ? AND materia_id = ?";
        try {
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setInt(1, nuevasNotas);
            ps.setString(2, estudianteId);
            ps.setString(3, materiaId);
            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas > 0) {
                logger.info("Se actualizaron las notas del estudiante con ID: " + estudianteId + " en la materia con ID: " + materiaId);
                return true;
            } else {
                logger.warn("No se encontraron registros para actualizar las notas del estudiante con ID: " + estudianteId + " en la materia con ID: " + materiaId);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar las notas del estudiante: " + e.getMessage());
            return false;
        }

    }

    public void insert(EstudianteDto nuevo){
        if (idYaExiste(nuevo.getCodigoId())){
            logger.info("Ha ocurrido un error: no se puede registrar dos datos con el mismo ID");
            System.out.println("No puedes registrar un dato con el mismo ID");
            return;
        }
        try {
            String sql = "INSERT INTO estudiantes (codigo_id, snombre, sappaterno, sapmaterno, dtnacimiento, materia_id, carrera) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            ConexionPostgreSQL con = ConexionPostgreSQL.getOrCreate();
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, nuevo.getCodigoId());
            ps.setString(2, nuevo.getNombre());
            ps.setString(3, nuevo.getApellidoPaterno());
            ps.setString(4, nuevo.getApellidoMaterno());
            ps.setDate(5, Date.valueOf(nuevo.getFechaNacimiento()));
            ps.setString(6, nuevo.getMateriaId());
            ps.setString(7, nuevo.getCarrera());
            ps.executeUpdate();

        } catch (SQLException e){
            logger.info("Ha ocurrido un error: no se puede registrar el dato: " + e.getMessage());
            System.out.println("No puedes registrar el dato: " + e.getMessage());
        }
    }
}
