package com.jdeleon.challengeliteratura.service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);

}
