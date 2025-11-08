package gestionmascota.entities;

import java.time.LocalDate;

public class Microchip {

    private Long id;
    private String codigo;
    private String observaciones;
    private String veterinaria;
    private LocalDate fechaImplantacion;
    private boolean eliminado;
    private Long mascotaId;

    
    
public Long getMascotaId() {
    return mascotaId;
}

public void setMascotaId(Long mascotaId) {
    this.mascotaId = mascotaId;
}
    
    
    
    
  
    
    public Microchip() {
    }

    public Microchip(Long id, String codigo, String observaciones, String veterinaria,
                     LocalDate fechaImplantacion, boolean eliminado) {
        this.id = id;
        this.codigo = codigo;
        this.observaciones = observaciones;
        this.veterinaria = veterinaria;
        this.fechaImplantacion = fechaImplantacion;
        this.eliminado = eliminado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getVeterinaria() {
        return veterinaria;
    }

    public void setVeterinaria(String veterinaria) {
        this.veterinaria = veterinaria;
    }

    public LocalDate getFechaImplantacion() {
        return fechaImplantacion;
    }

    public void setFechaImplantacion(LocalDate fechaImplantacion) {
        this.fechaImplantacion = fechaImplantacion;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "Microchip{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", veterinaria='" + veterinaria + '\'' +
                ", fechaImplantacion=" + fechaImplantacion +
                ", eliminado=" + eliminado +
                '}';
    }
}
