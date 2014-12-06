/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import Entidades.Categoria;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.convert.Converter;
/**
 *
 * @author URRUTIA
 */
public class JsfUtil {
    public static SelectItem[] getSelectItems(List<?> entidades, 
            boolean seleccion){
        int size = seleccion ? entidades.size()+1:entidades.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if(seleccion){
            items[0] = new SelectItem("","-----");
            i++;
        }
        
        for(Object x : entidades){
            items[i++] = new SelectItem(x,x.toString());
        }
        return items;
    }
    
    public static SelectItem[] getSelectItemsCategoria(List<Categoria> entidades, 
            boolean seleccion){
        int size = seleccion ? entidades.size()+1:entidades.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if(seleccion){
            items[0] = new SelectItem("","Seleccione una opcion");
            i++;
        }
        
        for(Categoria x : entidades){
            items[i++] = new SelectItem(x, x.getNombre());
        }
        return items;
    }
    
    public static boolean validacionFallida(){
        return FacesContext.getCurrentInstance().isValidationFailed();
    }
    
    public static void agregarMensageDeError(Exception ex,String msgDefault){
        String msg = ex.getLocalizedMessage();
        if(msg != null && msg.length() >0) agregarMensageDeError(msg);
        else agregarMensageDeError(msgDefault);
    }
    
    public static void agregarMensageDeError(String msg){
        FacesMessage facesmsg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                msg,
                msg
        );
        FacesContext.getCurrentInstance().addMessage(null, facesmsg);
    }
    
    public static void agregarMensagesDeErrores(List<String> mensajes){
        for(String mensaje : mensajes){
            agregarMensageDeError(mensaje);
        }
    }
    
    public static void agregarMensajeExito(String mensaje){
        FacesMessage facesmsg = new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                mensaje,
                mensaje
        );
        FacesContext.getCurrentInstance().addMessage(
                "infoExito", 
                facesmsg
        );
    }
    
    public static String getParametrodeSolicitud(String key){
        return FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().
                get(key);
    }
    
    public static Object getObjetoDeParametrosdeSolicitud(
            String parametroSolicitud,
            Converter converter,
            UIComponent component
    ){
        String elId = JsfUtil.getParametrodeSolicitud(parametroSolicitud);
        return converter.getAsObject(
                FacesContext.getCurrentInstance(),
                component,
                elId
        );
    }
    
    public static enum AccionPersistencia{
        CREAR,
        BORRAR,
        ACTUALIZAR
    }
}
