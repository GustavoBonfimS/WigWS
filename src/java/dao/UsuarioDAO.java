/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.Usuario;

/**
 *
 * @author marcelosiedler
 */
public class UsuarioDAO {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    
    public UsuarioDAO()
    {
    
    }
    
    public boolean inserir(Usuario usuario)
    {
        String sql = "INSERT INTO usuario(username,senha,perfil,email) VALUES(?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            pst.setString(3, usuario.getPerfil());
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
    
    
    
    public boolean atualizar(Usuario usuario)
    {
        String sql = "UPDATE usuario set senha=?,perfil=?,email=? where username=?";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
          
            pst.setString(1, usuario.getSenha());
            pst.setString(2, usuario.getPerfil());
            pst.setString(3, usuario.getEmail());
            pst.setString(4, usuario.getLogin());
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
    public boolean excluir(Usuario usuario)
    {
        String login = usuario.getLogin();
        
        String sql = "DELETE FROM usuario where username='"
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
    
    public List<Usuario> listar()
    {
        String sql = "SELECT * FROM usuario";
        List<Usuario> retorno = new ArrayList<Usuario>();
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            
            ResultSet res = pst.executeQuery();
            while(res.next())
            {
                Usuario item = new Usuario();
                item.setIdusuario(res.getInt("idusuario"));
                item.setLogin(res.getString("username"));
                item.setSenha(res.getString("senha"));
                item.setEmail(res.getString("email"));
                item.setPerfil(res.getString("perfil"));
                
                retorno.add(item);
            }
               
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
    
    
    }
    
    public Usuario buscar(Usuario usuario)
    {
        String sql = "SELECT * FROM usuario where username=?";
        Usuario retorno = null;
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            pst.setString(1, usuario.getLogin());
            ResultSet res = pst.executeQuery();
            
            if(res.next())
            {
                retorno = new Usuario();
                retorno.setIdusuario(res.getInt("idusuario"));
                retorno.setLogin(res.getString("username"));
                retorno.setSenha(res.getString("senha"));
                retorno.setPerfil(res.getString("perfil"));
                retorno.setEmail(res.getString("email"));
                
                
            }
               
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
    
    
    }
    
    public Usuario validarLogin(Usuario usuario)
    {
        String login = usuario.getLogin();
        String senha = usuario.getSenha();
        
        Usuario retorno = null;
        
        try {
        Statement st = (Statement)Conexao.getConexao().createStatement();

        String sql = "SELECT * FROM usuario where username='" + login + "'" 
                + " and senha='" + senha + "'";
            ResultSet res = st.executeQuery(sql);
            
            if(res.next())
            {
                retorno = new Usuario();
                retorno.setLogin(res.getString("username"));
                retorno.setSenha(res.getString("senha"));
                retorno.setIdusuario(res.getInt("idusuario"));
                retorno.setPerfil(res.getString("perfil"));
                retorno.setEmail(res.getString("email"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }
}
