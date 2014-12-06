/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import daos.ProductoFacade;
import Entidades.Producto;
import java.io.Serializable;
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
public class ProductoControlador implements Serializable{
private Producto actual;
    private DataModel items = null;
    private List<Producto> listaproductos;
    @EJB
    
    private ProductoFacade ejbFacade;
    private int selectItemIndex;
    public List<Producto> getListaproductos() {
           if (listaproductos==null) {
            listaproductos=getFacade().findAll();
        }
        return listaproductos;
    }
    private PaginacionHelper paginacion;

    public ProductoControlador() {
    }
    
    public Producto getSelected(){
        if(actual == null){
            actual = new Producto();
            selectItemIndex =-1;
        }
        return actual;
    }
    
    private ProductoFacade getFacade(){
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
        actual =(Producto)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "vista";
    }
    
    public String prepararCrear(){
        actual = new Producto();
        selectItemIndex =-1;
        return "crear";
    }
    
    public String crear(){
        try {
            getFacade().create(actual);
            JsfUtil.agregarMensajeExito("Producto Creado");
            return prepararCrear();
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al guardar Producto");
            return null;
        }
    }
    
    public String prepararEditar(){
        actual = (Producto)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "editar";
    }
    
    public String editar(){
        try {
            getFacade().edit(actual);
            JsfUtil.agregarMensajeExito("Producto Actualizado");
            return "vista";
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al editar Producto");
            return null;
        }
    }
    
    public String borrado(){
        actual = (Producto)getItems().getRowData();
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
            JsfUtil.agregarMensajeExito("Producto Borrado");
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"Error al borrar Producto");
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
    
    @FacesConverter(forClass = Producto.class)
    public static class ProductocontroladorConversor implements Converter{
        @Override
        public Object getAsObject(
                FacesContext facescontext,
                UIComponent componente, 
                String valor
        ){
           if(valor == null || valor.length() == 0){
               return null;
           }
           ProductoControlador controlador = (ProductoControlador) facescontext.
                   getApplication().
                   getELResolver().
                   getValue(
                           facescontext.getELContext(), 
                           null, "productoControlador"
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

            if(objeto instanceof Producto){
                Producto o = (Producto) objeto;
                return getStringKey(o.getCodigo());
            }else{
                Logger.getLogger(
                        this.getClass().getName()).log(Level.SEVERE,
                                "objeto {0} es de tipo {1}; tipo esperado {2}",
                                new Object[]{
                                    objeto,objeto.getClass().getName(),
                                    Producto.class.getName()
                                });
                return null;
            }
        }
    }    
    
}
