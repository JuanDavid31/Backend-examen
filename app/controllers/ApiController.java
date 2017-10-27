package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.CategoriaEntity;
import models.ProductoEntity;
import models.SucursalEntity;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ApiController extends Controller {

    public Result indice(){
        return ok("Holi");
    }

    public Result darSucursales(){
        List<SucursalEntity> sucursales = SucursalController.darTodos();
        return ok(Json.toJson(sucursales));
    }

    public Result adicionarSucursal(){
        JsonNode json = request().body().asJson();
        String nombre = json.findPath("nombre").textValue();
        SucursalEntity sucursal = new SucursalEntity();
        sucursal.setdNombre(nombre);
        String mensaje = sucursal.validate();
        if(nombre == null) {
            SucursalController.guardar(sucursal);
            return ok("Sucursal adicionado");
        }
        return badRequest(mensaje);
    }

    public Result actualizarSucursal(int id){
        JsonNode json = request().body().asJson();
        String nombre = json.findPath("nombre").textValue();
        SucursalEntity sucursal = SucursalController.darSucursal(id);
        sucursal.setdNombre(nombre);
        String mensaje = sucursal.validate();
        if(mensaje == null) {
            SucursalController.guardar(sucursal);
            return ok("Sucursal editado");
        }
        return badRequest(mensaje);
    }

    public Result eliminarSucursal(int id){
        SucursalEntity sucursal = SucursalController.darSucursal(id);
        if(sucursal.delete()){
            return ok("Sucursal eliminada");
        }
        return internalServerError("No se pudo eliminar la sucursal, intentelo de nuevo.");
    }

    public Result darCategorias(){
        List<CategoriaEntity> categorias = CategoriaController.darTodas();
        return ok(Json.toJson(categorias));
    }

    public Result adicionarCategoria(){
        JsonNode json = request().body().asJson();
        String nombre = json.findPath("nombre").textValue();
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setdNombre(nombre);
        String mensaje = categoria.validate();
        if(mensaje == null) {
            CategoriaController.guardar(categoria);
            return ok("Categoria adicionado");
        }
        return badRequest(mensaje);
    }

    public Result actualizarCategoria(int id){
        JsonNode json = request().body().asJson();
        String nombre = json.findPath("nombre").textValue();
        CategoriaEntity categoria = CategoriaController.darCategoria(id);
        categoria.setdNombre(nombre);
        String mensaje = categoria.validate();
        if(mensaje == null) {
            CategoriaController.guardar(categoria); 
            return ok("Categoria guardada");
        }
        return badRequest(mensaje);
    }

    public Result eliminarCategoria(int id){
        CategoriaEntity categoria = CategoriaController.darCategoria(id);
        if(categoria.delete()){
            return ok("Categoria eliminada");
        }
        return internalServerError("No se pudo eliminar la categoria, intentelo de nuevo.");
    }

    public Result adicionarProducto(int idSucursal, int idCategoria){
        JsonNode json = request().body().asJson();
        String nombre = json.findPath("nombre").textValue();
        String fecha = json.findPath("fecha").textValue();
        ProductoEntity producto = new ProductoEntity();
        producto.setdNombre(nombre);
        producto.setfLimite(fecha);
        String mensaje = producto.validate();
        if(mensaje == null){
            ProductoController.adicionarSucursalAProducto(idSucursal, producto);
            ProductoController.adicionarCategoriaAProducto(idCategoria, producto);
            ProductoController.guardar(producto);
            return ok("Producto adicionado correctamente");
        }
        return badRequest(mensaje);
    }

    public Result editarProductoConUrl(int id){
        JsonNode json = request().body().asJson();
        ProductoEntity producto = ProductoController.darProducto(id);
        String url = json.findPath("url").textValue();
        producto.setdUrlFoto(url);
        ProductoController.guardar(producto);
        return ok("Producto editado");
    }

    public Result editarProductoConArchivo(int id){
        MultipartFormData<File> body = request().body().asMultipartFormData();
        try {
            return ProductoController.subirFoto(body, id);
        } catch (IOException e) {
            e.printStackTrace();
            return badRequest("La cancion no se pudo subir");
        }
    }

    public Result eliminarProducto(int id){
        ProductoEntity producto = ProductoController.darProducto(id);
        if(ProductoController.eliminar(producto)){
            return ok("Se elimino el producto");
        }
        return internalServerError("No se pudo eliminar el producto, intentelo de nuevo");
    }
}
