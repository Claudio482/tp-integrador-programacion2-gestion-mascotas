
package gestionmascota.dao;

import gestionmascota.entities.Dueno;
import java.sql.Connection;
import java.sql.SQLException;

public interface DuenoDao extends CrudDao<Dueno> {

    // Búsqueda por campo clave (DNI) 
    Dueno buscarPorDni(String dni, Connection conn) throws SQLException;
}
