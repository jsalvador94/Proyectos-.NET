/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import daos.MarcaFacade;
import Entidades.Marca;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author URRUTIA
 */
@ManagedBean
@SessionScoped
public class MarcaControlador implements Serializable{

   private Marca actual;
    private DataModel items = null;
    private List<Marca> listamarca;
    @EJB
    private MarcaFacade ejbFacade;
    private int selectItemIndex;
    private List<Marca> lst_l_a = new ArrayList();
    private String nombre_marca;

    public List<Marca> getLst_l_a() {
        return lst_l_a;
    }

    public void setLst_l_a(List<Marca> lst_l_a) {
        this.lst_l_a = lst_l_a;
    }

    public String getNombre_marca() {
        return nombre_marca;
    }

    public void setNombre_marca(String nombre_marca) {
        this.nombre_marca = nombre_marca;
    }
    
    public List<Marca> getListamarca() {
       if (listamarca==null) {
            listamarca=getFacade().findAll();
       }
        return listamarca;
    }
    
    
    private PaginacionHelper paginacion;

    public MarcaControlador() {
    }
    
    public Marca getSelected(){
        if(actual == null){
            actual = new Marca();
            selectItemIndex =-1;
        }
        return actual;
    }
    
    private MarcaFacade getFacade(){
        return ejbFacade;
    }
    
    public PaginacionHelper getPaginacion(){
        if(paginacion == null){
            paginacion = new PaginacionHelper(10){
                @Override
                public int getConteoItems(){
                    return getFacade().count();
                }
                @Override
                public DataModel crearPaginaModeloDeDatos(){
                    return new ListDataModel(getFacade().findRange(
                            new int[]{getPrimerItemPagina(),
                                getPrimerItemPagina()+getPaginasSize()})
                    );
                }
            };
        }
        return paginacion;
    }
    
    public String prepararLista(){
        recrearModelo();
        return "lista";
    }
    
    public String prepararVista(){
        actual =(Marca)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "vista";
    }
    
    public String prepararCrear(){
        actual = new Marca();
        selectItemIndex =-1;
        return "crear";
    }
    
    public String crear(){
        try {
            getFacade().create(actual);
            JsfUtil.agregarMensajeExito("Marca Creada");
            return prepararCrear();
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al Crear Marca");
            return null;
        }
    }
    
    public String prepararEditar(){
        actual = (Marca)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "editar";
    }
    
    public String editar(){
        try {
            getFacade().edit(actual);
            JsfUtil.agregarMensajeExito("Marca Actualizada");
            return "vista";
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al editar Marca");
            return null;
        }
    }
    
    public String borrado(){
        actual = (Marca)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        realizarBorrado();
        recrearPaginacion();
        recrearModelo();
        return "lista";
    }
    
    public String borraryVer(){        
        recrearModelo();
        realizarBorrado();
        actualizarActualItem();
        
        if(selectItemIndex >=0){
            return "vista";
        }else{
            recrearModelo();
            return "lista";
        }
    }
    
    private void realizarBorrado(){
        try {
            getFacade().remove(actual);
            JsfUtil.agregarMensajeExito("Marca Borrada");
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"Error al borrar Marca");
        }
    }
    
    private void actualizarActualItem(){
        int conteo = getFacade().count();
        
        if(selectItemIndex >= conteo){
            selectItemIndex = conteo -1;
            
            if(paginacion.getPrimerItemPagina() >=conteo){
                paginacion.anteriorPagina();
            }
        }
        if(selectItemIndex >= 0){
            actual = getFacade().findRange(
                    new int[]{selectItemIndex,
                        selectItemIndex+1}).get(0);
        }
    }
    
       
    public DataModel getItems(){
        if(items == null){
            items = getPaginacion().crearPaginaModeloDeDatos();
        }
        return items;
    }
    
    private void recrearModelo(){
        items = null;
    }
    
    private void recrearPaginacion(){
        paginacion = null;
    }
    
    private String siguiente(){
        getPaginacion().siguientePagina();
        recrearModelo();
        return "lista";
    }
    
    private String anterior(){
        getPaginacion().anteriorPagina();
        recrearModelo();
        return "lista";
    }
    
    public SelectItem[] obtenerItemsDisponiblesSeleccionandoMuchos()
    {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    } 
    
    public SelectItem[] obtenerItemsDisponiblesSeleccionandoUno()
    {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    } 
    
    @FacesConverter(forClass = Marca.class)
    public static class MarcacontroladorConversor implements Converter{
        @Override
        public Object getAsObject(
                FacesContext facescontext,
                UIComponent componente, 
                String valor
        ){
           if(valor == null || valor.length() == 0){
               return null;
           }
           MarcaControlador controlador = (MarcaControlador) facescontext.
                   getApplication().
                   getELResolver().
                   getValue(
                           facescontext.getELContext(), 
                           null, "marcaControlador"
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

            if(objeto instanceof Marca){
                Marca o = (Marca) objeto;
                return getStringKey(o.getId());
            }else{
                Logger.getLogger(
                        this.getClass().getName()).log(Level.SEVERE,
                                "objeto {0} es de tipo {1}; tipo esperado {2}",
                                new Object[]{
                                    objeto,objeto.getClass().getName(),
                                    Marca.class.getName()
                                });
                return null;
            }
        }
    }    
    
     public void obtener_nombre_marca(){
    lst_l_a=ejbFacade.marca_nombre(nombre_marca);
    
    }
    
}
