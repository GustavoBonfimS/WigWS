package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Avaliacao;
import modelo.Cliente;
import modelo.Usuario;

public class ClienteDAO {
    
    public ClienteDAO(){
        
    }
    
    public boolean inserir(Cliente usuario)
    {
        String sql = "INSERT INTO usuario(username,senha,perfil,email) VALUES(?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            pst.setString(3, "cliente");
            pst.setString(4, usuario.getEmail());
            
            if(pst.executeUpdate()>0)
            {
                retorno = true;
            }
                
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    
    }
    
    public boolean atualizarCPF(Cliente usuario)
    {
        String sql = "UPDATE cliente set CPF=? where idusuario=?";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
          
            pst.setString(1, usuario.getCPF());
            pst.setInt(2, usuario.getIdusuario());
            if(pst.executeUpdate()>0)
            {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    
    }
    
    public Cliente buscar(Cliente cliente)
    {
        String sql = "select usuario.idusuario, cliente.idcliente, usuario.username, usuario.senha, usuario.email, usuario.perfil,\n" +
"cliente.CPF from usuario, cliente where usuario.username =? and usuario.idusuario = cliente.idusuario";
        Cliente retorno = null;
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            pst.setString(1, cliente.getLogin());
            ResultSet res = pst.executeQuery();
            
            if(res.next())
            {
                retorno = new Cliente();
                retorno.setIdusuario(res.getInt("idusuario"));
                retorno.setIdcliente(res.getInt("idcliente"));
                retorno.setLogin(res.getString("username"));
                retorno.setSenha(res.getString("senha"));
                retorno.setPerfil(res.getString("perfil"));
                retorno.setEmail(res.getString("email"));
                retorno.setCPF(res.getString("CPF"));
                
            }
              
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
    
    
    }
    
    /*
    // mudar login para username antes de aplicar
    public boolean excluir(Usuario usuario)
    {
        String login = usuario.getLogin();
        
        String sql = "DELETE FROM usuario where login='"
                + login + "'";
        Boolean retorno = false;

        try {
            Statement st = (Statement)Conexao.getConexao().createStatement();
            if(st.executeUpdate(sql) > 0)
                retorno = true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    
    }
    */
    
    public List<Cliente> listar()
    {
        String sql = "SELECT usuario.idusuario, usuario.username, usuario.senha,\n" +
"usuario.email, usuario.perfil, cliente.CPF FROM usuario, cliente\n" +
"where usuario.perfil = 'cliente' and usuario.idusuario = cliente.idusuario";
        List<Cliente> retorno = new ArrayList<Cliente>();
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            
            ResultSet res = pst.executeQuery();
            while(res.next())
            {
                Cliente item = new Cliente();
                item.setIdusuario(res.getInt("idusuario"));
                item.setLogin(res.getString("username"));
                item.setSenha(res.getString("senha"));
                item.setEmail(res.getString("email"));
                item.setPerfil(res.getString("perfil"));
                item.setCPF(res.getString("cpf"));
                
                retorno.add(item);
            }
               
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
    
    
    }
     
    public boolean fazerAvaliacao(Avaliacao avaliacao) {
        
        String sql = "INSERT INTO avaliacao(autor, conteudo, idcliente, idempresa) VALUES(?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, avaliacao.getAutor());
            pst.setString(2, avaliacao.getConteudo());
            pst.setInt(3, avaliacao.getIdcliente());
            pst.setInt(4, avaliacao.getIdempresa());
            
            if(pst.executeUpdate()>0)
            {
                retorno = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    }
    
    public boolean ResponderAvaliacao(Avaliacao avaliacao) {
        
        String sql = "INSERT INTO resposta(autor, conteudo, idavaliacao, idempresa, idcliente) VALUES(?,?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, avaliacao.getAutor());
            pst.setString(2, avaliacao.getConteudo());
            pst.setInt(3, avaliacao.getIdavaliacao());
            pst.setInt(4, avaliacao.getIdempresa());
            pst.setInt(5, avaliacao.getIdcliente());
            
            if(pst.executeUpdate()>0)
            {
                retorno = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }
        
        return retorno;
    }
    
    public List<Avaliacao> listarAvaliacao()
    {
        String sql = "SELECT * FROM avaliacao";
        List<Avaliacao> retorno = new ArrayList<Avaliacao>();
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            
            ResultSet res = pst.executeQuery();
            while(res.next())
            {
                Avaliacao item = new Avaliacao();
                item.setIdavaliacao(res.getInt("idavaliacao"));
                item.setConteudo(res.getString("conteudo"));
                item.setAutor(res.getString("autor"));
                item.setIdcliente(res.getInt("idcliente"));
                item.setIdempresa(res.getInt("idempresa"));
                
                retorno.add(item);
            }
               
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
    
    
    }
    
}
