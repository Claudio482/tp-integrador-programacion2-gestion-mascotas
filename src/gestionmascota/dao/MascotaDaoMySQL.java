package gestionmascota.dao;

import gestionmascota.config.ConeccionBD;
import gestionmascota.entities.Dueno;
import gestionmascota.entities.Mascota;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MascotaDaoMySQL implements MascotaDao {

    // ======================
    // SQLs
    // ======================
    private static final String INSERT_SQL =
            "INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, eliminado, nombre, especie, raza, fecha_nacimiento, duenio_id " +
            "FROM mascota WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT id, eliminado, nombre, especie, raza, fecha_nacimiento, duenio_id " +
            "FROM mascota";

    private static final String UPDATE_SQL =
            "UPDATE mascota SET eliminado = ?, nombre = ?, especie = ?, raza = ?, " +
            "fecha_nacimiento = ?, duenio_id = ? WHERE id = ?";

    private static final String DELETE_LOGICO_SQL =
            "UPDATE mascota SET eliminado = TRUE WHERE id = ?";

    private static final String SELECT_BY_NOMBRE_SQL =
            "SELECT id, eliminado, nombre, especie, raza, fecha_nacimiento, duenio_id " +
            "FROM mascota WHERE UPPER(nombre) LIKE UPPER(?)";

    private static final String SELECT_BY_DUENO_SQL =
            "SELECT id, eliminado, nombre, especie, raza, fecha_nacimiento, duenio_id " +
            "FROM mascota WHERE duenio_id = ?";

    // ======================
    // Métodos con Connection EXTERNA
    // ======================

    @Override
    public void crear(Mascota m, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, m.isEliminado());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getEspecie());
            ps.setString(4, m.getRaza());
            if (m.getFechaNacimiento() != null) {
                ps.setDate(5, Date.valueOf(m.getFechaNacimiento()));
            } else {
                ps.setDate(5, null);
            }
            // dueño
            if (m.getDueno() != null && m.getDueno().getId() != null) {
                ps.setLong(6, m.getDueno().getId());
            } else {
                throw new SQLException("La mascota debe tener un dueño válido (duenio_id no puede ser null)");
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Mascota buscarPorId(Long id, Connection conn) throws SQLException {
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
    public List<Mascota> buscarTodos(Connection conn) throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Mascota m, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setBoolean(1, m.isEliminado());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getEspecie());
            ps.setString(4, m.getRaza());
            if (m.getFechaNacimiento() != null) {
                ps.setDate(5, Date.valueOf(m.getFechaNacimiento()));
            } else {
                ps.setDate(5, null);
            }
            if (m.getDueno() != null && m.getDueno().getId() != null) {
                ps.setLong(6, m.getDueno().getId());
            } else {
                throw new SQLException("La mascota debe tener un dueño válido (duenio_id no puede ser null)");
            }
            ps.setLong(7, m.getId());

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
    public List<Mascota> buscarPorNombre(String nombre, Connection conn) throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_NOMBRE_SQL)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public List<Mascota> buscarPorDuenoId(Long duenoId, Connection conn) throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_DUENO_SQL)) {
            ps.setLong(1, duenoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        }
        return lista;
    }

    // ======================
    // Métodos cómodos (abren su propia conexión)
    // ======================

    public void crear(Mascota m) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            crear(m, conn);
        }
    }

    public Mascota buscarPorId(Long id) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return buscarPorId(id, conn);
        }
    }

    public List<Mascota> buscarTodos() throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return buscarTodos(conn);
        }
    }

    public void actualizar(Mascota m) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            actualizar(m, conn);
        }
    }

    public void eliminarLogico(Long id) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            eliminarLogico(id, conn);
        }
    }

    // ======================
    // Helper para mapear
    // ======================
    private Mascota mapRow(ResultSet rs) throws SQLException {
        Mascota m = new Mascota();
        m.setId(rs.getLong("id"));
        m.setEliminado(rs.getBoolean("eliminado"));
        m.setNombre(rs.getString("nombre"));
        m.setEspecie(rs.getString("especie"));
        m.setRaza(rs.getString("raza"));

        Date f = rs.getDate("fecha_nacimiento");
        if (f != null) {
            m.setFechaNacimiento(f.toLocalDate());
        }

        Long duenoId = rs.getLong("duenio_id");
        Dueno d = new Dueno();
        d.setId(duenoId);
        m.setDueno(d);

        return m;
    }
}
