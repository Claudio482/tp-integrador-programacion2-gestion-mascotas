
package gestionmascota.dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> {

    // Versión que usa una Connection externa (para transacciones)
    void crear(T entity, Connection conn) throws SQLException;

    T buscarPorId(Long id, Connection conn) throws SQLException;

    List<T> buscarTodos(Connection conn) throws SQLException;

    void actualizar(T entity, Connection conn) throws SQLException;

    void eliminarLogico(Long id, Connection conn) throws SQLException;

    // Podríamos agregar buscarPorCampo(...) según necesidad
}
