package trivia.game.modelos;

import java.time.LocalDate;

public class Partida {
    //Atributos
    private Long id;
    private String nombre;
    private LocalDate fechaCreacion;

    //Constructores
    public Partida() {
    }

    public Partida(Long id, String nombre, LocalDate fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
