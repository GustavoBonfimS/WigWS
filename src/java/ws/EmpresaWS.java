/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dao.ClienteDAO;
import dao.EmpresaDAO;
import dao.UsuarioDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import modelo.Cliente;
import modelo.Empresa;
import modelo.Usuario;

/**
 * REST Web Service
 *
 * @author GustavoBonfimS
 */
@Path("empresa")
public class EmpresaWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EmpresaWS
     */
    public EmpresaWS() {
    }

    /**
     * Retrieves representation of an instance of ws.EmpresaWS
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ClienteWS
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    // -------------------------------------------------------------------------

    @GET
    @Produces("application/json")
    @Path("/get/{CNPJ}")
    public String getEmpresa(@PathParam("CNPJ") String CNPJ) {
        Empresa e = new Empresa();
        e.setCNPJ(CNPJ);

        EmpresaDAO dao = new EmpresaDAO();
        e = dao.buscar(e);

        Gson g = new Gson();
        return g.toJson(e);
    }
   
    @POST
    @Consumes({"application/json"})
    @Path("/Inserir")
    public Empresa Inserir(String content) {
        Gson g = new Gson();
        Empresa e = g.fromJson(content, Empresa.class);

        EmpresaDAO dao = new EmpresaDAO();
                
        if (dao.inserir(e) == true) {
            // busca id da tabela usuario
            Empresa empresa = dao.buscarIdDaEmpresa(e);
            //seta o cnpj
            empresa.setCNPJ(e.getCNPJ());
            // insert id usuario e cnpj na tabela empresa
            dao.onEmpresa(empresa);
            //busca id da tabela cliente 
            empresa = dao.buscar(e);
            return empresa;

        } else {
            return null;
        }
    }
    
    @GET
    @Produces("application/json")
    @Path("/Listar")
    public String ListarEmpresa() {
        List<Empresa> lista;
        EmpresaDAO dao = new EmpresaDAO();
        lista = dao.listar();

        Gson g = new Gson();
        return g.toJson(lista);
    }
    
    @GET
    @Produces("application/json")
    @Path("/pesquisa/{empresa}")
    public String pesquisa (@PathParam("empresa") String empresa) {
        List<Empresa> lista;
        EmpresaDAO dao = new EmpresaDAO();
        lista = dao.atualizarPesquisa(empresa);

        Gson g = new Gson();
        return g.toJson(lista);
    }
    
}
