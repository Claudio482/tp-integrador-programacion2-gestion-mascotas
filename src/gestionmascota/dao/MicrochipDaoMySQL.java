
package gestionmascota.dao;


import gestionmascota.config.ConeccionBD;
import gestionmascota.entities.Microchip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MicrochipDaoMySQL implements MicrochipDao {

    private static final String INSERT_SQL =
            "INSERT INTO microchip (eliminado, codigo, observaciones, veterinaria, fecha_implantacion, mascota_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, eliminado, codigo, observaciones, veterinaria, fecha_implantacion, mascota_id " +
            "FROM microchip WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT id, eliminado, codigo, observaciones, veterinaria, fecha_implantacion, mascota_id " +
            "FROM microchip";

    private static final String UPDATE_SQL =
            "UPDATE microchip SET eliminado = ?, codigo = ?, observaciones = ?, veterinaria = ?, " +
            "fecha_implantacion = ?, mascota_id = ? WHERE id = ?";

    private static final String DELETE_LOGICO_SQL =
            "UPDATE microchip SET eliminado = TRUE WHERE id = ?";

    private static final String SELECT_BY_MASCOTA_ID_SQL =
            "SELECT id, eliminado, codigo, observaciones, veterinaria, fecha_implantacion, mascota_id " +
            "FROM microchip WHERE mascota_id = ?";

    private static final String EXISTS_CODIGO_SQL =
            "SELECT 1 FROM microchip WHERE codigo = ?";

    /* ==========================
       MÉTODOS CON COM EXTERNA
       ========================== */

    @Override
    public void crear(Microchip m, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, m.isEliminado());
            ps.setString(2, m.getCodigo());
            ps.setString(3, m.getObservaciones());
            ps.setString(4, m.getVeterinaria());
            if (m.getFechaImplantacion() != null) {
                ps.setDate(5, Date.valueOf(m.getFechaImplantacion()));
            } else {
                ps.setDate(5, null);
            }
            ps.setLong(6, m.getMascotaId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Microchip buscarPorId(Long id, Connection conn) throws SQLException {
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
    public List<Microchip> buscarTodos(Connection conn) throws SQLException {
        List<Microchip> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Microchip m, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setBoolean(1, m.isEliminado());
            ps.setString(2, m.getCodigo());
            ps.setString(3, m.getObservaciones());
            ps.setString(4, m.getVeterinaria());
            if (m.getFechaImplantacion() != null) {
                ps.setDate(5, Date.valueOf(m.getFechaImplantacion()));
            } else {
                ps.setDate(5, null);
            }
            ps.setLong(6, m.getMascotaId());
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
    public Microchip buscarPorMascotaId(Long mascotaId, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(SELECT_BY_MASCOTA_ID_SQL)) {
            ps.setLong(1, mascotaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean existeCodigo(String codigo, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(EXISTS_CODIGO_SQL)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /* ==========================
       MÉTODOS  (abren com)
       ========================== */

    public void crear(Microchip m) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            crear(m, conn);
        }
    }

    public Microchip buscarPorId(Long id) throws SQLException {
        try (Connection conn = ConeccionBD.getConnection()) {
            return buscarPorId(id, conn);
        }
    }

    /* ==========================
       para mapear
       ========================== */
    private Microchip mapRow(ResultSet rs) throws SQLException {
        Microchip m = new Microchip();
        m.setId(rs.getLong("id"));
        m.setEliminado(rs.getBoolean("eliminado"));
        m.setCodigo(rs.getString("codigo"));
        m.setObservaciones(rs.getString("observaciones"));
        m.setVeterinaria(rs.getString("veterinaria"));
        Date f = rs.getDate("fecha_implantacion");
        if (f != null) {
            m.setFechaImplantacion(f.toLocalDate());
        }
        m.setMascotaId(rs.getLong("mascota_id"));
        return m;
    }
    
private static final String DELETE_LOGICO_POR_MASCOTA_SQL =
        "UPDATE microchip SET eliminado = TRUE WHERE mascota_id = ?";

@Override
public void eliminarLogicoPorMascotaId(Long mascotaId, Connection conn) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(DELETE_LOGICO_POR_MASCOTA_SQL)) {
        ps.setLong(1, mascotaId);
        ps.executeUpdate();
    }
}

// método sin pasar Connection (opcional)
public void eliminarLogicoPorMascotaId(Long mascotaId) throws SQLException {
    try (Connection conn = ConeccionBD.getConnection()) {
        eliminarLogicoPorMascotaId(mascotaId, conn);
    }
}      
    
}
