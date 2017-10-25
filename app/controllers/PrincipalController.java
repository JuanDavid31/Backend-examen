package controllers;

import play.mvc.*;

public class PrincipalController extends Controller {

    public Result indice(){
        return ok("Holi");
    }
}
