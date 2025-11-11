
package gestionmascota.dao;

import gestionmascota.entities.Mascota;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MascotaDao extends CrudDao<Mascota> {

    // para búsquedas por nombre (o por especie) desde el menú
    List<Mascota> buscarPorNombre(String nombre, Connection conn) throws SQLException;

    // listar por dueño
    List<Mascota> buscarPorDuenoId(Long duenoId, Connection conn) throws SQLException;
    
   void eliminarLogico(Long id, Connection conn) throws SQLException; 
}