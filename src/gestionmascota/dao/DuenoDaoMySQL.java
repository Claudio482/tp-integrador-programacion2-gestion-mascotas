package gestionmascota.dao;

import gestionmascota.config.ConeccionBD;
import gestionmascota.entities.Dueno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DuenoDaoMySQL implements DuenoDao {

    // ======================
    // SQL
    // ======================
    private static final String INSERT_SQL =
            "INSERT INTO duenio (dni, nombre, apellido, telefono, email, direccion, eliminado) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, dni, nombre, apellido, telefono, email, direccion, eliminado " +
            "FROM duenio WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT id, dni, nombre, apellido, telefono, email, direccion, eliminado " +
            "FROM duenio";

    private static final String UPDATE_SQL =
            "UPDATE duenio SET dni = ?, nombre = ?, apellido = ?, telefono = ?, email = ?, " +
            "direccion = ?, eliminado = ? WHERE id = ?";

    private static final String DELETE_LOGICO_SQL =
            "UPDATE duenio SET eliminado = TRUE WHERE id = ?";

    private static final String SELECT_BY_DNI_SQL =
            "SELECT id, dni, nombre, apellido, telefono, email, direccion, eliminado " +
            "FROM duenio WHERE dni = ?";

    // ======================
    // Métodos con Connection EXTERNA
    // ======================

    @Override
    public void crear(Dueno d, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getDni());
            ps.setString(2, d.getNombre());
            ps.setString(3, d.getApellido());
            ps.setString(4, d.getTelefono());
            ps.setString(5, d.getEmail());
            ps.setString(6, d.getDireccion());
            ps.setBoolean(7, d.isEliminado());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    d.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Dueno buscarPorId(Long id, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Dueno> buscarTodos(Connection conn) throws SQLException {
        List<Dueno> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Dueno d, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, d.getDni());
            ps.setString(2, d.getNombre());
            ps.setString(3, d.getApellido());
            ps.setString(4, d.getTelefono());
            ps.setString(5, d.getEmail());
            ps.setString(6, d.getDireccion());
            ps.setBoolean(7, d.isEliminado());
            ps.setLong(8, d.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarLogico(Long id, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(DELETE_LOGICO_SQL)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Dueno buscarPorDni(String dni, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_DNI_SQL)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // ======================
    // Métodos  (sin pasar Connection)
    // ======================

    public void crear(Dueno d) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            crear(d, conn);
        }
    }

    public Dueno buscarPorId(Long id) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return buscarPorId(id, conn);
        }
    }

    public List<Dueno> buscarTodos() throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return buscarTodos(conn);
        }
    }

    public void actualizar(Dueno d) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            actualizar(d, conn);
        }
    }

    public void eliminarLogico(Long id) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            eliminarLogico(id, conn);
        }
    }

    public Dueno buscarPorDni(String dni) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return buscarPorDni(dni, conn);
        }
    }

    // ======================
    // Helper para mapear ResultSet -> Dueno
    // ======================
    private Dueno mapRow(ResultSet rs) throws SQLException {
        Dueno d = new Dueno();
        d.setId(rs.getLong("id"));
        d.setDni(rs.getString("dni"));
        d.setNombre(rs.getString("nombre"));
        d.setApellido(rs.getString("apellido"));
        d.setTelefono(rs.getString("telefono"));
        d.setEmail(rs.getString("email"));
        d.setDireccion(rs.getString("direccion"));
        d.setEliminado(rs.getBoolean("eliminado"));
        return d;
    }
}