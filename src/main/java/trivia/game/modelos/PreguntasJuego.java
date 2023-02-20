package trivia.game.modelos;

import java.util.ArrayList;

public class PreguntasJuego {
    //Atributos
    private Long id;
    private CategoriaPregunta categoria;
    private String contenido;
    private ArrayList<Respuesta> respuestas;

    //Constructores
    public PreguntasJuego() {
    }

    public PreguntasJuego(Long id, CategoriaPregunta catogoria, String contenido, ArrayList<Respuesta> respuestas) {
        this.id = id;
        this.categoria = catogoria;
        this.contenido = contenido;
        this.respuestas = respuestas;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaPregunta getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaPregunta categoria) {
        this.categoria = categoria;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public ArrayList<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }
}
