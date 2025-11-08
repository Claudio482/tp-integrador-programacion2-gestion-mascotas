package gestionmascota.entities;

import java.time.LocalDate;

public class Mascota {

    private Long id;
    private boolean eliminado;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;

    // Relación con Dueño (muchas mascotas de un dueño)
    private Dueno dueno;

    // Relación 1→1 unidireccional con Microchip
    private Microchip microchip;

    public Mascota() {
    }

    public Mascota(Long id, boolean eliminado, String nombre, String especie, String raza,
                   LocalDate fechaNacimiento, Dueno dueno, Microchip microchip) {
        this.id = id;
        this.eliminado = eliminado;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.dueno = dueno;
        this.microchip = microchip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    public Microchip getMicrochip() {
        return microchip;
    }

    public void setMicrochip(Microchip microchip) {
        this.microchip = microchip;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", dueno=" + (dueno != null ? dueno.getNombre() + " " + dueno.getApellido() : "SIN DUEÑO") +
                ", microchip=" + (microchip != null ? microchip.getCodigo() : "SIN CHIP") +
                '}';
    }
}
