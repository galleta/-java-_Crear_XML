/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crearxml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * @author francis
 * 
 * Esta clase permite crear un fichero XML
 */
public class FicheroXML 
{
    // ***** Variables de la clase *****
    
    private org.w3c.dom.Document documentoXML = null;
    private String rutaFicheroXML = "nombrepordefecto";
    private int cantidadtotalnodos;
    
    // *********************************
    // ***** Métodos públicos de la clase *****
    
    /**
     * Constructor
     * @param rutaFicheroXML Ruta del fichero XML a crear
     */
    public FicheroXML(String rutaFicheroXML)
    {
        this.rutaFicheroXML = rutaFicheroXML;
        cantidadtotalnodos = 0;
    }
    
    /**
     * Carga un fichero XML
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public void cargarFicheroXML() throws ParserConfigurationException, SAXException, IOException
    {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        documentoXML = documentBuilder.parse(new File(this.rutaFicheroXML));
    }
    
    /**
     * Cambia el nombre del fichero XML para almacenarlo
     * @param nuevonombre Nuevo nombre
     */
    public void cambiarNombreFicheroXML(String nuevonombre)
    {
        this.rutaFicheroXML = nuevonombre;
    }
    
    /**
     * Crea el fichero XML
     * @param etiquetaraiz Etiqueta del nodo raíz del fichero XML
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    public void crearFicheroXML(String etiquetaraiz) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        documentoXML = implementation.createDocument(null, etiquetaraiz, null);
        documentoXML.setXmlVersion("1.0");
    }
    
    /**
     * Inserta un comentario en un nodo del fichero XML
     * @param nodoItem Nodo donde se insertará el comentario
     * @param comentario Comentario
     */
    public void insertarComentario(Element nodoItem, String comentario)
    {
        Comment comenta = documentoXML.createComment(comentario);
        nodoItem.appendChild(comenta);
    }
    
    /**
     * Obtiene el nodo raiz del fichero XML
     * @return Nodo raíz del fichero XML
     */
    public Element obtenerNodoRaiz()
    {
        return documentoXML.getDocumentElement();
    }
    
    /**
     * Agrega un nodo item a la raíz del fichero XML
     * @param item Nodo a agregar a la raíz
     */
    public void agregarItemARaiz(Element item)
    {
        Node raiz = obtenerNodoRaiz();
        raiz.appendChild(item);
    }
    
    /**
     * Crea un nodo item
     * @param nombreITEM Nombre de la etiqueta de ese nodo
     * @return Nodo
     * @param atributos Lista de atributos del nodo
     * @param valoresatributos Lista de los valores de los atributos del nodo
     */
    public Element crearNodoItem(String nombreITEM, ArrayList<String> atributos, ArrayList<String> valoresatributos)
    {
        Element item = documentoXML.createElement(nombreITEM);
        
        if( atributos != null && valoresatributos != null )
        {
            for(int i = 0; i < atributos.size(); i++)
            {
                item.setAttribute(atributos.get(i), valoresatributos.get(i));
            }
        }
        
        return item;
    }
    
    /**
     * Agrega un nodo a un nodo item
     * @param nodoItem Nodo item
     * @param etiquetanodo Etiqueta del nodo
     * @param valor Valor del nodo
     * @param atributos Lista de atributos del nodo
     * @param valoresatributos Lista de los valores de los atributos del nodo
     */
    public void agregarNodo(Element nodoItem, String etiquetanodo, String valor, ArrayList<String> atributos, ArrayList<String> valoresatributos)
    {
        Element nodo = documentoXML.createElement(etiquetanodo); 
        Text nodeKeyValue = documentoXML.createTextNode(valor);
        nodo.appendChild(nodeKeyValue);
        
        if( atributos != null && valoresatributos != null )
        {
            for(int i = 0; i < atributos.size(); i++)
            {
                nodo.setAttribute(atributos.get(i), valoresatributos.get(i));
            }
        }
        
        nodoItem.appendChild(nodo);
    }
    
    /**
     * Elimina un nodo del documento XML
     * @param nodo Nodo a eliminar
     */
    public void eliminarNodo(Element nodo)
    {
        Node eliminado = nodo.getParentNode().removeChild(nodo);
    }
    
    /**
     * Genera y guarda el fichero XML
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    public void generarFicheroXML() throws TransformerConfigurationException, TransformerException
    {
        // Generamos XML
        Source source = new DOMSource(documentoXML);
        // Indicamos donde lo queremos almacenar
        Result result = new StreamResult(new java.io.File(rutaFicheroXML));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(source, result);
    }
    
    /**
     * Cuenta el total de nodos del fichero XML
     * @return Cantidad total de nodos del fichero
     */
    public int contarNodosFichero()
    {
        this.contarNodosDesde(obtenerNodoRaiz());
        return cantidadtotalnodos;
    }
    
    /**
     * Muestra todos los nodos del fichero XML en un TextArea
     * @param dondemostrar TextArea para mostrar los nodos del fichero
     */
    public void mostrarNodosFichero(JTextArea dondemostrar)
    {
        mostrarNodosHijos(dondemostrar, this.obtenerNodoRaiz(), 1);
    }
    
    // ****************************************
    // ***** Métodos privados de la clase *****
    
    /**
     * Cuenta los nodos hijos de un nodoaux
     * @param nodo Nodo para contar sus hijos
     * @return Cantidad de cantidadtotalnodos hijos del nodoaux
     */
    private int contarNodosHijos(Node nodo)
    {
        int cantidadnodos = 0;
        
        NodeList lista = nodo.getChildNodes();
        for(int i = 0; i < lista.getLength(); i++)
        {
            if( lista.item(i) instanceof Element )
                cantidadnodos++;
        }
        
        return cantidadnodos;
    }
    
    /**
     * Recorre y muestra todos los cantidadtotalnodos listanodoshijos de un nodoaux
     * @param dondemostrar JTextArea donde se mostrarán los cantidadtotalnodos del fichero XML
     * @param nodo Nodo para recorrer y mostrar sus hijos 
     * @param nivel Nivel del nodo, si es la raíz es 0 y así sucesivamente
     */
    private void mostrarNodosHijos(JTextArea dondemostrar, Node nodo, int nivel)
    {
        NodeList listanodoshijos = nodo.getChildNodes();
        NamedNodeMap atributos = null;
        
        if( nodo.isEqualNode(this.obtenerNodoRaiz()) )
        {
            atributos = nodo.getAttributes();
            if( atributos.getLength() > 0 )
                dondemostrar.append(nodo.getNodeName() + ", hijos: " + contarNodosHijos(nodo) + ", atributos: ");
            else
                dondemostrar.append(nodo.getNodeName() + ", hijos: " + contarNodosHijos(nodo));
            for(int j = 0; j < atributos.getLength(); j++)
            {
                dondemostrar.append(atributos.item(j).getNodeName() + " = " + atributos.item(j).getNodeValue() + " ");
            }
            dondemostrar.append("\n");
        }
        
        for(int i = 0; i < listanodoshijos.getLength(); i++)
        {
            Node nodoaux = listanodoshijos.item(i);
            if (nodoaux instanceof Element)
            { 
                // Pongo un separador para distinguir los niveles
                for(int j = 0; j < nivel; j++)
                    dondemostrar.append("   ");
                
                atributos = nodoaux.getAttributes();
                if( nodoaux.getChildNodes().getLength() > 1 )
                {
                    int nodoshijos = contarNodosHijos(nodoaux);
                    if( atributos.getLength() > 0 )
                        dondemostrar.append(nodoaux.getNodeName() + ", hijos: " + nodoshijos + ", atributos: ");
                    else
                        dondemostrar.append(nodoaux.getNodeName() + ", hijos: " + nodoshijos);
                }
                else
                {
                    if( atributos.getLength() > 0 )
                        dondemostrar.append(nodoaux.getNodeName() + ", atributos: ");
                    else
                        dondemostrar.append(nodoaux.getNodeName());
                }
                for(int j = 0; j < atributos.getLength(); j++)
                {
                    dondemostrar.append(atributos.item(j).getNodeName() + " = " + atributos.item(j).getNodeValue() + " ");
                }
                dondemostrar.append("\n");
                
                mostrarNodosHijos(dondemostrar, nodoaux, nivel+1);
            }
        }
    }
    
    /**
     * Cuenta el total de nodos hijos que cuelgan de un nodo
     * @param nodo Nodo para contar sus hijos colgantes
     */
    private void contarNodosDesde(Node nodo)
    {
        if( nodo.isEqualNode(this.obtenerNodoRaiz()) )
        {
            cantidadtotalnodos++;
            cantidadtotalnodos += contarNodosHijos(nodo);
        }
        
        NodeList listanodoshijos = nodo.getChildNodes();
        
        for(int i = 0; i < listanodoshijos.getLength(); i++)
        {
            Node nodoaux = listanodoshijos.item(i);
            if (nodoaux instanceof Element)
            {                 
                cantidadtotalnodos += contarNodosHijos(nodoaux);
                contarNodosDesde(nodoaux);
            }
        }
    }
    
    /**
     * Añade un atributo a un nodo
     * @param nodo Nodo para agregarle el atributo
     * @param atributo Atributo
     * @param valor Valor del atributo
     */
    private void agregarAtributoANodo(Element nodo, String atributo, String valor)
    {
        nodo.setAttribute(atributo, valor);
    }
    
    // ****************************************
}

