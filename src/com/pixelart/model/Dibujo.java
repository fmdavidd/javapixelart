package com.pixelart.model;

import java.util.*;


public class Dibujo {
    private int idDibujo;
    private int idPropietario;
    private String nombreDibujo;
    private boolean activo;
    private int anchoCuadricula;
    private TreeMap<Integer, String> clavesColores;
    private HashSet<Cuadricula> cuadriculas;

    public Dibujo() {
        this.idDibujo = 0;
        this.idPropietario = 0;
        this.nombreDibujo = "Sin título";
        this.activo = true;
        this.anchoCuadricula = 32;
        this.clavesColores = new TreeMap<>();
        this.cuadriculas = new HashSet<>();
        inicializarColoresPorDefecto();
    }

    public Dibujo(int idDibujo, int idPropietario, String nombreDibujo,
                  boolean activo, int anchoCuadricula) {
        this.idDibujo = idDibujo;
        this.idPropietario = idPropietario;
        this.nombreDibujo = nombreDibujo;
        this.activo = activo;
        this.anchoCuadricula = anchoCuadricula;
        this.clavesColores = new TreeMap<>();
        this.cuadriculas = new HashSet<>();
        inicializarColoresPorDefecto();
    }

    private void inicializarColoresPorDefecto() {
        clavesColores.put(0, "#FFFFFF"); // Blanco
        clavesColores.put(1, "#000000"); // Negro
    }

    // Getters y Setters
    public int getIdDibujo() {
        return idDibujo;
    }

    public void setIdDibujo(int idDibujo) {
        this.idDibujo = idDibujo;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombreDibujo() {
        return nombreDibujo;
    }

    public void setNombreDibujo(String nombreDibujo) {
        this.nombreDibujo = nombreDibujo;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getAnchoCuadricula() {
        return anchoCuadricula;
    }

    public void setAnchoCuadricula(int anchoCuadricula) {
        this.anchoCuadricula = anchoCuadricula;
    }

    public Map<Integer, String> getClavesColores() {
        return clavesColores;
    }

    public void setClavesColores(Map<Integer, String> clavesColores) {
        this.clavesColores = new TreeMap<>(clavesColores);
    }

    public Set<Cuadricula> getCuadriculas() {
        return cuadriculas;
    }

    public void setCuadriculas(Set<Cuadricula> cuadriculas) {
        this.cuadriculas = new HashSet<>(cuadriculas);
    }

    // Métodos de lógica de negocio
    public boolean insertarColor(String color) {
        if (color == null || !color.matches("^#[0-9A-Fa-f]{6}$")) {
            return false;
        }
        if (estaColorEnMap(color)) {
            return false;
        }
        int nuevaClave = clavesColores.isEmpty() ? 0 : clavesColores.lastKey() + 1;
        clavesColores.put(nuevaClave, color.toUpperCase());
        return true;
    }

    public boolean eliminarColor(String color) {
        if (color == null) return false;

        Integer claveAEliminar = null;
        for (Map.Entry<Integer, String> entry : clavesColores.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(color)) {
                claveAEliminar = entry.getKey();
                break;
            }
        }

        if (claveAEliminar != null) {
            clavesColores.remove(claveAEliminar);
            return true;
        }
        return false;
    }

    public boolean estaColorEnMap(String color) {
        if (color == null) return false;
        return clavesColores.containsValue(color.toUpperCase());
    }

    public String colorCuadricula(int indiceX, int indiceY) {
        for (Cuadricula c : cuadriculas) {
            if (c.getIndiceX() == indiceX && c.getIndiceY() == indiceY) {
                return c.getColor();
            }
        }
        return null;
    }

    public boolean cambiarColorCuadricula(int indiceX, int indiceY, String nuevoColor) {
        if (nuevoColor == null || !nuevoColor.matches("^#[0-9A-Fa-f]{6}$")) {
            return false;
        }

        Cuadricula existente = null;
        for (Cuadricula c : cuadriculas) {
            if (c.getIndiceX() == indiceX && c.getIndiceY() == indiceY) {
                existente = c;
                break;
            }
        }

        if (existente != null) {
            existente.setColor(nuevoColor.toUpperCase());
        } else {
            cuadriculas.add(new Cuadricula(indiceX, indiceY, nuevoColor.toUpperCase()));
        }
        return true;
    }

    public boolean eliminarColorCuadricula(int indiceX, int indiceY) {
        return cuadriculas.removeIf(c ->
                c.getIndiceX() == indiceX && c.getIndiceY() == indiceY
        );
    }

    public boolean buscarCuadricula(int indiceX, int indiceY) {
        for (Cuadricula c : cuadriculas) {
            if (c.getIndiceX() == indiceX && c.getIndiceY() == indiceY) {
                return true;
            }
        }
        return false;
    }

    public void limpiarDibujo() {
        cuadriculas.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dibujo dibujo = (Dibujo) o;
        return idDibujo == dibujo.idDibujo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDibujo);
    }

    @Override
    public String toString() {
        return "Dibujo{" +
                "id=" + idDibujo +
                ", nombre='" + nombreDibujo + '\'' +
                ", cuadriculas=" + cuadriculas.size() +
                '}';
    }
}