package gestionmascota.service;

import gestionmascota.config.ConeccionBD;
import gestionmascota.dao.MascotaDao;
import gestionmascota.dao.MascotaDaoMySQL;
import gestionmascota.dao.MicrochipDao;
import gestionmascota.dao.MicrochipDaoMySQL;
import gestionmascota.entities.Mascota;
import gestionmascota.entities.Microchip;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MascotaService {

    private final MascotaDao mascotaDao;
    private final MicrochipDao microchipDao;

    public MascotaService() {
        this.mascotaDao = new MascotaDaoMySQL();
        this.microchipDao = new MicrochipDaoMySQL();
    }

    /**
     * Crea mascota y microchip
     * Todo en una sola transacción
     */
    public void crearMascotaConMicrochip(Mascota mascota) throws ServiceException {
        // validaciones mínimas de negocio
        if (mascota == null) {
            throw new ServiceException("La mascota no puede ser null");
        }
        if (mascota.getDueno() == null || mascota.getDueno().getId() == null) {
            throw new ServiceException("La mascota debe tener un dueño válido (duenio_id no puede ser null)");
        }

        Connection conn = null;
        try {
            conn = ConeccionBD.getConnection();
            conn.setAutoCommit(false); // ---- INICIO TRANSACCIÓN ----

            // 1) Crear la mascota primero
            mascotaDao.crear(mascota, conn); // esto  setea el ID generado

            // 2) Si vino un microchip lo creamos
            Microchip microchip = mascota.getMicrochip();
            if (microchip != null) {

                // Validar que no exista el código
                if (microchipDao.existeCodigo(microchip.getCodigo(), conn)) {
                    throw new ServiceException("Ya existe un microchip con el código: " + microchip.getCodigo());
                }

                // Seteamos el id de la mascota recién creada
                microchip.setMascotaId(mascota.getId());

                // Insertamos el microchip
                microchipDao.crear(microchip, conn);
            }

            conn.commit(); // ---- FIN TRANSACCIÓN OK ----

        } catch (SQLException e) {
            // rollback si hubo error de SQL
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    // lo registr en consola
                    ex.printStackTrace();
                }
            }
            throw new ServiceException("Error al crear la mascota y/o su microchip", e);

        } catch (ServiceException e) {
            // rollback si hubo error de negocio
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;

        } finally {
            // restaurar autocommit y cerrar
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // CRUD  para menu

    public Mascota obtenerPorId(Long id) throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return mascotaDao.buscarPorId(id, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al obtener mascota por id", e);
        }
    }

    public List<Mascota> listarTodas() throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return mascotaDao.buscarTodos(conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al listar mascotas", e);
        }
    }

    public List<Mascota> buscarPorNombre(String nombre) throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return mascotaDao.buscarPorNombre(nombre, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al buscar mascotas por nombre", e);
        }
    }

    public void actualizarMascota(Mascota mascota) throws ServiceException {
        if (mascota == null || mascota.getId() == null) {
            throw new ServiceException("La mascota a actualizar debe tener id");
        }

        try (Connection conn = ConeccionBD.getConnection()) {
            mascotaDao.actualizar(mascota, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al actualizar la mascota", e);
        }
    }

    public void eliminarLogico(Long id) throws ServiceException {
        try (Connection conn = ConeccionBD.getConnection()) {
            mascotaDao.eliminarLogico(id, conn);
        } catch (SQLException e) {
            throw new ServiceException("Error al eliminar lógicamente la mascota", e);
        }
    }
    
   public void eliminarMascotaYMicrochip(Long mascotaId) throws ServiceException {
    if (mascotaId == null) {
        throw new ServiceException("Debe indicar el ID de la mascota a eliminar.");
    }

    try (Connection conn = ConeccionBD.getConnection()) {
        conn.setAutoCommit(false);
        try {
            // 1) Verificamos que la mascota exista
            Mascota mascota = mascotaDao.buscarPorId(mascotaId, conn);
            if (mascota == null) {
                throw new ServiceException("No existe una mascota con ID " + mascotaId);
            }

            // 2) Baja lógica del microchip asociado (si hay)
            microchipDao.eliminarLogicoPorMascotaId(mascotaId, conn);

            // 3) Baja lógica de la mascota
            mascotaDao.eliminarLogico(mascotaId, conn);

            // 4) Todo OK  commit
            conn.commit();

        } catch (SQLException | ServiceException ex) {
            conn.rollback(); // deshacemos todo
            if (ex instanceof ServiceException se) {
                throw se;
            }
            throw new ServiceException("Error al eliminar mascota y microchip.", ex);
        }
    } catch (SQLException e) {
        throw new ServiceException("Error al conectar con la base para eliminar mascota.", e);
    }
} 
    
    
    
    
    
}