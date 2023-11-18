package com.example.agenda_panaderia.Objetos;


    public class Pedido {
        String id_pedido, uid_usuario,nombre, fecha_actual, titulo, descripcion,fecha_pedido,forma_entrega, estado;

        public Pedido(String id_pedido, String fecha_actual, String uid_usuario, String nombre, String titulo, String descrip, String fecha, String formaEntrega) {

        }

        public Pedido(String id_pedido, String fecha_actual,  String uid_usuario, String nombre, String titulo, String descripcion, String fecha_pedido, String forma_entrega, String estado) {
            this.id_pedido = id_pedido;
            this.uid_usuario = uid_usuario;
            this.nombre = nombre;
            this.fecha_actual = fecha_actual;
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.fecha_pedido = fecha_pedido;
            this.forma_entrega = forma_entrega;
            this.estado = estado;

        }


    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getId_pedido() {
            return id_pedido;
        }

    public String getUid_usuario() {
        return uid_usuario;
    }

    public void setUid_usuario(String uid_usuario) {
        this.uid_usuario = uid_usuario;
    }

    public String getFecha_actual() {
        return fecha_actual;
    }

    public void setFecha_actual(String fecha_actual) {
        this.fecha_actual = fecha_actual;
    }
    public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getEstado() {
            return estado;
        }

    public void setEstado(String estado) {
            this.estado = estado;
        }

    public String getForma_entrega() {return forma_entrega;}
        public void setForma_entrega(String forma_entrega) {this.forma_entrega = forma_entrega;}

    }


