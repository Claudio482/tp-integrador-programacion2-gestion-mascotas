package gestionmascota.service;

import gestionmascota.config.ConeccionBD;
import gestionmascota.dao.DuenoDao;
import gestionmascota.dao.DuenoDaoMySQL;
import gestionmascota.entities.Dueno;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DuenoService {

    private final DuenoDao duenoDao;

    public DuenoService() {
        this.duenoDao = new DuenoDaoMySQL();
    }

    // =============== ALTA ===============

    public void crearDueno(Dueno d) throws ServiceException {
        if (d == null) {
            throw new ServiceException("El dueño no puede ser null");
        }
        if (d.getDni() == null || d.getDni().trim().isEmpty()) {
            throw new ServiceException("El DNI es obligatorio");
        }
        if (d.getNombre() == null || d.getNombre().trim().isEmpty()) {
            throw new ServiceException("El nombre es obligatorio");
        }
        if (d.getApellido() == null || d.getApellido().trim().isEmpty()) {
            throw new ServiceException("El apellido es obligatorio");
        }

        try (Connection conn = ConeccionBD.getConnection()) {

            // verificar que no exista DNI duplicado
            Dueno existente = duenoDao.buscarPorDni(d.getDni(), conn);
            if (existente != null && !existente.isEliminado()) {
                throw new ServiceException("Ya existe un dueño con ese DNI");
            }

            d.setEliminado(false);
            duenoDao.crear(d, conn);

        } catch (SQLException e) {
            throw new ServiceException("Error al crear dueño: " + e.getMessage(), e);
        }
    }

    // =============== CONSULTAS ===============

    public Dueno obtenerPorId(Long id) throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return duenoDao.buscarPorId(id, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al buscar dueño por id", e);
        }
    }

    public Dueno obtenerPorDni(String dni) throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return duenoDao.buscarPorDni(dni, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al buscar dueño por DNI", e);
        }
    }

    public List<Dueno> listarTodos() throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return duenoDao.buscarTodos(conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al listar dueños", e);
        }
    }

    // =============== ACTUALIZAR ===============

    public void actualizarDueno(Dueno d) throws ServiceException {
        if (d == null || d.getId() == null) {
            throw new ServiceException("El dueño a actualizar debe tener id");
        }

        try (Connection conn = ConeccionBD.getConnection()) {
            duenoDao.actualizar(d, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al actualizar dueño", e);
        }
    }

    // =============== ELIMINAR LÓGICO ===============

    public void eliminarLogico(Long id) throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            duenoDao.eliminarLogico(id, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al eliminar lógicamente el dueño", e);
        }
    }
}
