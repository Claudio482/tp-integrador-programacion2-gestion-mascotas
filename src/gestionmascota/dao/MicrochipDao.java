
package gestionmascota.dao;

import gestionmascota.entities.Microchip;
import java.sql.Connection;
import java.sql.SQLException;

public interface MicrochipDao extends CrudDao<Microchip> {

    Microchip buscarPorMascotaId(Long mascotaId, Connection conn) throws SQLException;

    boolean existeCodigo(String codigo, Connection conn) throws SQLException;

void eliminarLogicoPorMascotaId(Long mascotaId, Connection conn) throws SQLException;

}