/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dao.ClienteDAO;
import dao.UsuarioDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import modelo.Avaliacao;
import modelo.Cliente;
import modelo.Usuario;

/**
 * REST Web Service
 *
 * @author gunai
 */
@Path("cliente")
public class ClienteWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ClienteWS
     */
    public ClienteWS() {
    }

    /**
     * Retrieves representation of an instance of ws.ClienteWS
     *
     * @param content
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes({"application/json"})
    @Path("/Cadastrar")
    public Cliente EfeturarCadastro(Cliente u) {
        Gson g = new Gson();
        // Cliente u = new Cliente(); // = g.fromJson(content, Cliente.class);

        // ClienteDAO tem o metodo inserir especifico para o cliente
        // UsuarioDAO tem o metodo de buscar para pegar o id
        ClienteDAO dao = new ClienteDAO();

        if (dao.inserir(u) == true) {
            // busca id da tabela usuario
            Cliente usuario = dao.buscar(u);
            //seta o cpf
            usuario.setCPF(u.getCPF());
            // insert id usuario na tabela cliente
            dao.onCliente(usuario);
            //busca id da tabela cliente 
            usuario = dao.buscar(u);
            return usuario;

        } else {
            return null;
        }
    }

    @GET
    @Produces("application/json")
    @Path("/Listar")
    public String ListarCliente() {
        List<Cliente> lista;
        ClienteDAO dao = new ClienteDAO();
        lista = dao.listar();

        Gson g = new Gson();
        return g.toJson(lista);
    }

    @POST
    @Consumes({"application/json"})
    @Path("/Avaliacao/Inserir")
    public String FazerAvaliacao(String content) {
        Gson g = new Gson();
        Avaliacao a = g.fromJson(content, Avaliacao.class);

        ClienteDAO dao = new ClienteDAO();
        Avaliacao retorno = null;
        if (dao.fazerAvaliacao(a) == true) {
            retorno = dao.buscarIdAvaliacao(a);
        }
        return g.toJson(retorno);
    }

    @POST
    @Consumes({"application/json"})
    @Path("/Avaliacao/Responder")
    public boolean ResponderAvaliacao(String content) {
        Gson g = new Gson();
        Avaliacao a = g.fromJson(content, Avaliacao.class);

        ClienteDAO dao = new ClienteDAO();
        return dao.ResponderAvaliacao(a);
    }

    @GET
    @Produces("application/json")
    @Path("/Avaliacao/Listar")
    public String ListarAvaliacao() {
        List<Avaliacao> lista;
        ClienteDAO dao = new ClienteDAO();
        lista = dao.listarAvaliacao();

        Gson g = new Gson();
        return g.toJson(lista);
    }

    @GET
    @Produces("application/json")
    @Path("/Avaliacao/get/{conteudo}")
    public String getAvaliacao(@PathParam("conteudo") String content) {
        Avaliacao a = new Avaliacao();
        a.setConteudo("gostei muito do local, ótimas garotas");

        ClienteDAO dao = new ClienteDAO();
        a = dao.buscarIdAvaliacao(a);

        Gson g = new Gson();
        return g.toJson(a);
    }

    @PUT
    @Consumes("application/json")
    @Path("/Alterar")
    public boolean Alterar(String content) {
        Gson g = new Gson();
        Cliente c = g.fromJson(content, Cliente.class);

        ClienteDAO dao = new ClienteDAO();
        UsuarioDAO udao = new UsuarioDAO();
        if (udao.atualizar(c) == true) {
            // busca id usuario e altera cpf na tabela cliente
            Cliente u = dao.buscar(c);
            return dao.atualizarCPF(u);

        } else {
            return false;
        }
    }

    @GET
    @Produces("application/json")
    @Path("/get/{login}")
    public String getCliente(@PathParam("login") String login) {
        Cliente c = new Cliente();
        c.setLogin(login);

        ClienteDAO dao = new ClienteDAO();
        c = dao.buscar(c);

        Gson g = new Gson();
        return g.toJson(c);
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
}
