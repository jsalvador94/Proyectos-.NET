/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import javax.ejb.EJB;
import daos.TipoFacade;
import Entidades.Tipo;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author URRUTIA
 */
@ManagedBean
@SessionScoped
public class TipoControlador implements Serializable{

    /**
     * Creates a new instance of TipoControlador
     */
     private List<Tipo> listatipo;
    @EJB
    private TipoFacade ejbFacade;
    private int selectItemIndex;

    public List<Tipo> getListatipo() {
           if (listatipo==null) {
            listatipo=getFacade().findAll();
        }
        return listatipo;
    }
    
    private TipoFacade getFacade(){
        return ejbFacade;
    }
    public TipoControlador() {
    }
     @FacesConverter(forClass = Tipo.class)
    public static class TipocontroladorConversor implements Converter{
        @Override
        public Object getAsObject(
                FacesContext facescontext,
                UIComponent componente, 
                String valor
        ){
           if(valor == null || valor.length() == 0){
               return null;
           }
           TipoControlador controlador = (TipoControlador) facescontext.
                   getApplication().
                   getELResolver().
                   getValue(
                           facescontext.getELContext(), 
                           null, "tipoControlador"
                   );
           return controlador.getFacade().find(getKey(valor));
        }
        
            
    
        java.lang.Integer getKey(String valor){
            java.lang.Integer clave;
            clave = Integer.valueOf(valor);
            return clave;
        }
    
        String getStringKey(java.lang.Integer valor){
            StringBuilder sb = new StringBuilder();
            sb.append(valor);
            return sb.toString();
        }
        
        @Override
        public String getAsString(
                FacesContext facescontext,
                UIComponent componente,
                Object objeto
        ){
            if(objeto == null){
                return null;
            }

            if(objeto instanceof Tipo){
                Tipo o = (Tipo) objeto;
                return getStringKey(o.getId());
            }else{
                Logger.getLogger(
                        this.getClass().getName()).log(Level.SEVERE,
                                "objeto {0} es de tipo {1}; tipo esperado {2}",
                                new Object[]{
                                    objeto,objeto.getClass().getName(),
                                    Tipo.class.getName()
                                });
                return null;
            }
        }
    }    
    
}
