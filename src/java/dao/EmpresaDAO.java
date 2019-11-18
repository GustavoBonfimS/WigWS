package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.Empresa;

/**
 *
 * @author GustavoBonfimS
 */
public class EmpresaDAO {
    public Empresa buscar(Empresa empresa)
    {
        String sql = "SELECT usuario.idusuario, usuario.username, usuario.senha, usuario.email, usuario.perfil,\n" +
"empresa.idempresa, empresa.cnpj, empresa.tipo, empresa.endereco FROM usuario, empresa\n" +
"WHERE empresa.cnpj =? and usuario.idusuario = empresa.idusuario";
        Empresa retorno = null;
        
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
           
            pst.setString(1, empresa.getCNPJ());
            ResultSet res = pst.executeQuery();
            
            if(res.next())
            {
                retorno = new Empresa();
                retorno.setIdusuario(res.getInt("idusuario"));
                retorno.setIdempresa(res.getInt("idempresa"));
                retorno.setCNPJ(res.getString("CNPJ"));
                retorno.setTipo(res.getString("tipo"));
                retorno.setEndereco(res.getString("endereco"));
                retorno.setEmail(res.getString("email"));
                retorno.setLogin(res.getString("username"));
                retorno.setSenha(res.getString("senha"));
                retorno.setPerfil(res.getString("perfil"));
                
            }
              
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return retorno;
    
    
    }
    
    public boolean inserir(Empresa empresa) {
        String sql = "INSERT INTO usuario(username,senha,perfil,email) VALUES(?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, empresa.getLogin());
            pst.setString(2, empresa.getSenha());
            pst.setString(3, "Empresa");
            pst.setString(4, empresa.getEmail());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }

        return retorno;

    }
}
