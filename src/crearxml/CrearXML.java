/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crearxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author francis
 */
public class CrearXML 
{
    private static final String nombrefichero = "prueba.xml";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // Código para crear un fichero XML nuevo
        
        try
        {
            FicheroXML documentoXML = new FicheroXML(nombrefichero);
            documentoXML.crearFicheroXML("Documento");
            ArrayList<String> atributos = new ArrayList<>(), valoresatributos = new ArrayList<>();

            Element raiz = documentoXML.obtenerNodoRaiz();
            documentoXML.insertarComentario(raiz, "comentario en la raiz");

            atributos.add("atributo01");
            atributos.add("atributo02");
            valoresatributos.add("valoratributo01");
            valoresatributos.add("valoratributo02");
            Element item = documentoXML.crearNodoItem("nodoitem", atributos, valoresatributos);
            documentoXML.insertarComentario(item, "comentario");
            documentoXML.agregarNodo(item, "nodovalor01", "valor01", null, null);
            documentoXML.agregarNodo(item, "nodovalor02", "valor02", null, null);
            documentoXML.insertarComentario(item, "comentario en nodovalor02");
            documentoXML.agregarItemARaiz(item);
            
            Element item2 = documentoXML.crearNodoItem("nodoitem2", null, null);
            atributos.clear();
            valoresatributos.clear();
            atributos.add("atributo03");
            valoresatributos.add("valoratributo03");
            documentoXML.agregarNodo(item2, "nodokey01", "key01", atributos, valoresatributos);
            documentoXML.insertarComentario(item2, "comentario en nodokey01");
            documentoXML.agregarItemARaiz(item2);
            
            documentoXML.generarFicheroXML();
        } 
        catch (ParserConfigurationException error01) 
        {
            System.out.println("Error 01: " + error01.toString());
        } 
        catch (TransformerConfigurationException error02) 
        {
            System.out.println("Error 02: " + error02.toString());
        } 
        catch (TransformerException error03) 
        {
            System.out.println("Error 03: " + error03.toString());
        }
        
        /*
        // Código para agregar un nodo nuevo al final del fichero XML
        
        try 
        {
            FicheroXML documentoXML = new FicheroXML("/home/francis/Dropbox/Clases particulares/Modulo/Java/XML/discos.xml");
            documentoXML.cargarFicheroXML();
            
            Element item = documentoXML.crearNodoItem("CD");
            documentoXML.insertarComentario(item, "Este es el nuevo nodo agregado");
            documentoXML.agregarNodo(item, "TITULO", "Rust in Peace");
            documentoXML.agregarNodo(item, "ARTISTA", "Megadeth");
            documentoXML.agregarNodo(item, "ORIGEN", "US");
            documentoXML.agregarNodo(item, "PRECIO", "8.75");
            documentoXML.agregarNodo(item, "ANIO", "1990");
            documentoXML.agregarItemARaiz(item);
            
            documentoXML.cambiarNombreFicheroXML("/home/francis/Dropbox/Clases particulares/Modulo/Java/XML/discos_con_nuevo_nodo_al_final.xml");
            documentoXML.generarFicheroXML();
        } 
        catch (ParserConfigurationException error01) 
        {
            System.out.println("Error 01: " + error01.toString());
        } 
        catch (TransformerConfigurationException error02) 
        {
            System.out.println("Error 02: " + error02.toString());
        } 
        catch (TransformerException error03) 
        {
            System.out.println("Error 03: " + error03.toString());
        } 
        catch (SAXException error04) 
        {
            System.out.println("Error 04: " + error04.toString());
        } 
        catch (IOException error05) 
        {
            System.out.println("Error 05: " + error05.toString());
        }
        */
    }
}  
