package trivia.game.modelos;

public class UsuarioPregunta {
    //Atributos
    private Usuario usuario;
    private Partida partida;
    private Pregunta pregunta;
    private int puntaje;

    //Constructores
    public UsuarioPregunta() {
    }

    public UsuarioPregunta(Usuario usuario, Partida partida, Pregunta pregunta, int puntaje) {
        this.usuario = usuario;
        this.partida = partida;
        this.pregunta = pregunta;
        this.puntaje = puntaje;
    }

    //Getters y Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
