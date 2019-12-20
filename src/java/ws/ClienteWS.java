/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ClienteDAO;
import dao.UsuarioDAO;
import java.sql.Date;
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
            Cliente usuario = dao.buscarIdDoCliente(u);
            //seta o cpf
            usuario.setCPF(u.getCPF());
            // insert id usuario e cpf na tabela cliente
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
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Avaliacao a = gson.fromJson(content, Avaliacao.class);

        ClienteDAO dao = new ClienteDAO();
        Avaliacao retorno = null;
        if (dao.fazerAvaliacao(a) == true) {
            retorno = dao.buscarIdAvaliacao(a);
        }
        return gson.toJson(retorno);
    }

    @POST
    @Consumes({"application/json"})
    @Path("/Avaliacao/Responder")
    public boolean ResponderAvaliacao(String content) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Avaliacao a = gson.fromJson(content, Avaliacao.class);

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

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(lista);
    }

    @GET
    @Produces("application/json")
    @Path("/Avaliacao/Listar/{idempresa}")
    public String ListarAvaliacaoDaEmpresa(@PathParam("idempresa") int idempresa) {
        List<Avaliacao> lista;
        ClienteDAO dao = new ClienteDAO();
        lista = dao.listarAvaliacaoDaEmpresa(idempresa);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(lista);
    }

    @GET
    @Produces("application/json")
    @Path("/Avaliacao/get/{conteudo}")
    public String getAvaliacao(@PathParam("conteudo") String conteudo) {
        Avaliacao a = new Avaliacao();
        a.setConteudo(conteudo);

        ClienteDAO dao = new ClienteDAO();
        a = dao.buscarIdAvaliacao(a);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(a);
    }
    
    @GET
    @Produces("application/json")
    @Path("/Avaliacao/Resposta/get/{idavaliacao}")
    public String getResposta(@PathParam("idavaliacao") int idavaliacao) {
        Avaliacao a = new Avaliacao();
        
        ClienteDAO dao = new ClienteDAO();
        a = dao. buscarResposta(idavaliacao);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(a);
    }

    @GET
    @Produces("application/json")
    @Path("/Avaliacao/minhas/{idcliente}")
    public String minhasAvaliacoes(@PathParam("idcliente") int idcliente) {
        ClienteDAO dao = new ClienteDAO();
        List<Avaliacao> lista = dao.listarMinhasAvaliacao(idcliente);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(lista);
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

    @GET
    @Produces("application/json")
    @Path("/atualizarIndex/{login}")
    public String atualizarIndex(@PathParam("login") String login) {
        Date dataAtual;
        java.util.Date dataUtil = new java.util.Date();
        dataAtual = new Date(dataUtil.getTime());
        ClienteDAO dao = new ClienteDAO();

        Date lastCheck = dao.buscarLastCheck(login);
        if (lastCheck == null) {
            dao.inserirLoginIndex(login, dataAtual);
            lastCheck = dao.buscarLastCheck(login);
        }

        List<Avaliacao> lista = dao.atualizarIndex(lastCheck, dataAtual);
        dao.atualizarLastCheck(login, dataAtual);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        return gson.toJson(lista);
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
