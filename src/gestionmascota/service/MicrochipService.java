package gestionmascota.service;

import gestionmascota.config.ConeccionBD;
import gestionmascota.dao.MicrochipDao;
import gestionmascota.dao.MicrochipDaoMySQL;
import gestionmascota.entities.Microchip;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MicrochipService {

    private final MicrochipDao microchipDao;

    public MicrochipService() {
        this.microchipDao = new MicrochipDaoMySQL();
    }

    public Microchip obtenerPorId(Long id) throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return microchipDao.buscarPorId(id, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al obtener microchip por id", e);
        }
    }

    public List<Microchip> listarTodos() throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return microchipDao.buscarTodos(conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al listar microchips", e);
        }
    }

    public void eliminarLogico(Long id) throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            microchipDao.eliminarLogico(id, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al eliminar lógicamente el microchip", e);
        }
    }
}
