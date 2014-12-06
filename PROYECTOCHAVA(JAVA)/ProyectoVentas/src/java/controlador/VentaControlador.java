/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import daos.VentaFacade;
import Entidades.Venta;
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
public class VentaControlador implements Serializable{

    private Venta actual;
    private DataModel items = null;
    
    private List<Venta> listaventas;
    @EJB
    private VentaFacade ejbFacade;
    private int selectItemIndex;
    private List<Venta> lst_l_a = new ArrayList();
    private Integer nventa;

    public List<Venta> getLst_l_a() {
        return lst_l_a;
    }

    public void setLst_l_a(List<Venta> lst_l_a) {
        this.lst_l_a = lst_l_a;
    }

    public Integer getNventa() {
        return nventa;
    }

    public void setNventa(Integer nventa) {
        this.nventa = nventa;
    }
    
    public List<Venta> getListaventas() {
           if (listaventas==null) {
            listaventas=getFacade().findAll();
        }
        return listaventas;
    }
    private PaginacionHelper paginacion;

    public VentaControlador() {
    }
    
    public Venta getSelected(){
        if(actual == null){
            actual = new Venta();
            selectItemIndex =-1;
        }
        return actual;
    }
    
    private VentaFacade getFacade(){
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
        actual =(Venta)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "vista";
    }
    
    public String prepararCrear(){
        actual = new Venta();
        selectItemIndex =-1;
        return "crear";
    }
    
    public String crear(){
        try {
            getFacade().create(actual);
            JsfUtil.agregarMensajeExito("Venta Guardada");
            return prepararCrear();
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al guardar la Venta");
            return null;
        }
    }
    
    public String prepararEditar(){
        actual = (Venta)getItems().getRowData();
        selectItemIndex = paginacion.getPrimerItemPagina() + 
                getItems().getRowIndex();
        return "editar";
    }
    
    public String editar(){
        try {
            getFacade().edit(actual);
            JsfUtil.agregarMensajeExito("Venta Actualizada");
            return "vista";
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"ERROR al editar la Venta");
            return null;
        }
    }
    
    public String borrado(){
        actual = (Venta)getItems().getRowData();
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
            JsfUtil.agregarMensajeExito("Venta Borrada");
        } catch (Exception e) {
            JsfUtil.agregarMensageDeError(e,"Error al borrar la Venta");
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
    
    @FacesConverter(forClass = Venta.class)
    public static class VentacontroladorConversor implements Converter{
        @Override
        public Object getAsObject(
                FacesContext facescontext,
                UIComponent componente, 
                String valor
        ){
           if(valor == null || valor.length() == 0){
               return null;
           }
           VentaControlador controlador = (VentaControlador) facescontext.
                   getApplication().
                   getELResolver().
                   getValue(
                           facescontext.getELContext(), 
                           null, "ventaControlador"
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

            if(objeto instanceof Venta){
                Venta o = (Venta) objeto;
                return getStringKey(o.getNVenta());
            }else{
                Logger.getLogger(
                        this.getClass().getName()).log(Level.SEVERE,
                                "objeto {0} es de tipo {1}; tipo esperado {2}",
                                new Object[]{
                                    objeto,objeto.getClass().getName(),
                                    Venta.class.getName()
                                });
                return null;
            }
        }
    }    
    public void obtener_numero_venta(){
    lst_l_a=ejbFacade.nventa(nventa);
    
    }
    
}
