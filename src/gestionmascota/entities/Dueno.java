
package gestionmascota.entities;

import java.util.ArrayList;
import java.util.List;

public class Dueno {

    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String direccion;
    private boolean eliminado;

    // Relación 1 -> * (opcional para el modelo OO)
    // No la vamos a persistir directamente ahora
    private List<Mascota> mascotas;

    public Dueno() {
        this.mascotas = new ArrayList<>();
    }

    public Dueno(Long id, String dni, String nombre, String apellido, String telefono,
                 String email, String direccion, boolean eliminado) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.eliminado = eliminado;
        this.mascotas = new ArrayList<>();
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public void agregarMascota(Mascota mascota) {
        this.mascotas.add(mascota);
    }

    @Override
    public String toString() {
        return "Dueno{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", eliminado=" + eliminado +
                '}';
    }
}
