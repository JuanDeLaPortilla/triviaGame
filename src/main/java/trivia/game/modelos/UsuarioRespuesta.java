package trivia.game.modelos;

public class UsuarioRespuesta {
    //Atributos
    private Usuario usuario;
    private Partida partida;
    private Pregunta pregunta;
    private Respuesta respuesta;

    //Constructores
    public UsuarioRespuesta() {
    }

    public UsuarioRespuesta(Usuario usuario, Partida partida, Pregunta pregunta, Respuesta respuesta) {
        this.usuario = usuario;
        this.partida = partida;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
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

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
}
