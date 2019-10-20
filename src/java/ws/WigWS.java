package ws;

import com.google.gson.Gson;
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
import modelo.Usuario;

/**
 * REST Web Service
 *
 * @author Gustavo
 */
@Path("usuario")
public class WigWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WigWS
     */
    public WigWS() {
    }

    /**
     * Retrieves representation of an instance of ws.WigWS
     * @return an instance of java.lang.String
     */
    
    @GET
    @Produces("application/json")
    @Path("/get/{login}")
    public String getUsuario(@PathParam("login") String login) {
        Usuario u = new Usuario();
        u.setLogin(login);
        
        UsuarioDAO dao = new UsuarioDAO();
        u = dao.buscar(u);
        
        Gson g = new Gson();
        return g.toJson(u);
    }
    
    @GET
    @Produces("application/json")
    @Path("/Listar")
    public String ListarUsuarios() {
       List<Usuario> lista;
        UsuarioDAO dao = new UsuarioDAO();
        lista = dao.listar();
        Gson g = new Gson();
        return g.toJson(lista);
    }
    
    // metodo para validar login buscando login e senha
    @GET
    @Produces("application/json")
    @Path("/login/{login}/{senha}")
    public String ValidarLogin(@PathParam("login") String login,
            @PathParam("senha") String senha) {
        
        Usuario u = new Usuario();
        u.setLogin(login);
        u.setSenha(senha);
        
        // chama classe que possui os metodos para buscar no BD
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.validarLogin(u);
        
        String retorno;
        
        if(usuario == null)
            retorno = "Usuário não encontrado";
        else
            retorno = "Usuario encontrado";
        
        // converter para json
        Gson g = new Gson();
        return g.toJson(retorno);
    }
    
    
    @POST
    @Consumes({"application/json"})
    @Path("/Inserir")
    public boolean Inserir (String content) {
        Gson g = new Gson();
        Usuario u = (Usuario) g.fromJson(content, Usuario.class);
        
        UsuarioDAO dao = new UsuarioDAO();
        
        return dao.inserir(u);
        }
    
    
    /**
     * PUT method for updating or creating an instance of WigWS
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    @DELETE
    @Path("/Excluir/{login}")
    public boolean Excluir (@PathParam("login") String login) {
        Usuario u = new Usuario();
        u.setLogin(login);
        
        UsuarioDAO dao = new UsuarioDAO();
        u = dao.buscar(u);
        
        return dao.excluir(u);
    }
    
    @PUT
    @Consumes("application/json")
    @Path("/Alterar")
    public boolean Alterar(String content) {
        Gson g = new Gson();
        Usuario u = g.fromJson(content, Usuario.class);
        
        UsuarioDAO dao = new UsuarioDAO();
        return dao.atualizar(u);
        

    }
}
