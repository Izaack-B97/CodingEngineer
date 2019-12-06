package com.example.codingenginer.Modelos;

public class Modulo {
    //modulo text, comienzo text, final text, tiempo text, defectos text, encargado text, descripcion text, detalles_audio_path text, correo text

    int id;
    String modulo;
    String comienzo;
    String termino;
    String tiempo;
    String defectos;
    String encargado;
    String descripcion;
    String detalles_audio_path;
    String correo;

    public Modulo(int id, String modulo, String comienzo, String termino, String tiempo, String defectos, String encargado,String descripcion, String detalles_audio_path, String correo) {
        this.id = id;
        this.modulo = modulo;
        this.comienzo = comienzo;
        this.termino = termino;
        this.tiempo = tiempo;
        this.defectos = defectos;
        this.encargado = encargado;
        this.descripcion = descripcion;
        this.detalles_audio_path = detalles_audio_path;
        this.correo = correo;
    }


    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getComienzo() {
        return comienzo;
    }

    public void setComienzo(String comienzo) {
        this.comienzo = comienzo;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getDefectos() {
        return defectos;
    }

    public void setDefectos(String defectos) {
        this.defectos = defectos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalles_audio_path() {
        return detalles_audio_path;
    }

    public void setDetalles_audio_path(String detalles_audio_path) {
        this.detalles_audio_path = detalles_audio_path;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }



}
