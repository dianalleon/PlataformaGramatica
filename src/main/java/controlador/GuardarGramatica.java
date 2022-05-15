/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Gramatica;


/**
 *
 * @author User
 */
public class GuardarGramatica extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Normalizacion normalizacion = new Normalizacion();
        Gramatica gramatica = null;
        
        String terminales = request.getParameter("variableTerminal");
        String noTerminales = request.getParameter("variablesNoTerminales");
        String inicial = request.getParameter("variableInicial");
        String sigma = request.getParameter("producciones");
        
        /*if(!inicial.isEmpty() && !noTerminales.isEmpty() && !terminales.isEmpty() && !sigma.isEmpty()){
            gramatica = normalizacion.crearGramatica(noTerminales, terminales, inicial, sigma);
        }*/
        gramatica = normalizacion.crearGramatica(noTerminales, terminales, inicial, sigma);
        System.out.println("//////////////////////////////////////////////////////////////////");
        System.out.println("inicial="+gramatica.getInicial());
        System.out.println("no termonales="+gramatica.getNoTerminales().toString());
        System.out.println("terminales="+gramatica.getTerminales().toString());
        System.out.println("sigma="+gramatica.getSigma().toString());
        System.out.println("//////////////////////////////////////////////////////////////////");
      
        String msg = "terminales="+terminales+"noTerminales="+noTerminales+"iniciales="+inicial+"sigma="+sigma;
        request.setAttribute("msg",msg);
        
        request.getSession().setAttribute("sigma", gramatica.getSigma().toString());
        
        
        
        
        request.getRequestDispatcher("./index.jsp#ejercicio").forward(request, response);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
