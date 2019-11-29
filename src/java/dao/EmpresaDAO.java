package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.Empresa;

/**
 *
 * @author GustavoBonfimS
 */
public class EmpresaDAO {

    public Empresa buscar(Empresa empresa) {
        String sql = "SELECT usuario.idusuario, usuario.username, usuario.senha, usuario.email, usuario.perfil,\n"
                + "empresa.idempresa, empresa.cnpj, empresa.tipo, empresa.endereco FROM usuario, empresa\n"
                + "WHERE empresa.cnpj =? and usuario.idusuario = empresa.idusuario";
        Empresa retorno = null;

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            pst.setString(1, empresa.getCNPJ());
            ResultSet res = pst.executeQuery();

            if (res.next()) {
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
    
    public Empresa buscarIdDaEmpresa(Empresa usuario)
    {
        String sql = "SELECT * from usuario where usuario.username = ?";
        Empresa retorno = null;

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            pst.setString(1, usuario.getLogin());
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                retorno = new Empresa();
                retorno.setIdusuario(res.getInt("idusuario"));
                retorno.setLogin(res.getString("username"));
                retorno.setSenha(res.getString("senha"));
                retorno.setPerfil(res.getString("perfil"));
                retorno.setEmail(res.getString("email"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return retorno;

    }

    public boolean onEmpresa(Empresa empresa) {
        String sql = "INSERT INTO empresa (idusuario, cnpj, tipo, endereco) VALUES (?,?,?,?)";
        boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setInt(1, empresa.getIdusuario());
            pst.setString(2, empresa.getCNPJ());
            pst.setString(3, empresa.getTipo());
            pst.setString(4, empresa.getEndereco());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }

        return retorno;
    }
    
    public List<Empresa> listar() {
        String sql = "select * from usuario, empresa where usuario.idusuario = empresa.idusuario and usuario.perfil = 'empresa'";
        List<Empresa> retorno = new ArrayList<Empresa>();

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Empresa item = new Empresa();
                item.setIdusuario(res.getInt("idusuario"));
                item.setIdempresa(res.getInt("idempresa"));
                item.setLogin(res.getString("username"));
                item.setSenha(res.getString("senha"));
                item.setEmail(res.getString("email"));
                item.setPerfil(res.getString("perfil"));
                item.setCNPJ(res.getString("cnpj"));
                item.setTipo(res.getString("tipo"));
                item.setEndereco(res.getString("endereco"));

                retorno.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return retorno;

    }
    
    public List<Empresa> atualizarPesquisa(String pesquisa) {
        String sql = "select * from usuario, empresa where usuario.username like'%" + pesquisa + "%' and usuario.idusuario = empresa.idusuario";
        List<Empresa> retorno = new ArrayList<Empresa>();

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            // pst.setString(1, pesquisa);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Empresa item = new Empresa();
                item.setIdusuario(res.getInt("idusuario"));
                item.setIdempresa(res.getInt("idempresa"));
                item.setLogin(res.getString("username"));
                item.setSenha(res.getString("senha"));
                item.setEmail(res.getString("email"));
                item.setPerfil(res.getString("perfil"));
                item.setCNPJ(res.getString("cnpj"));
                item.setTipo(res.getString("tipo"));
                item.setEndereco(res.getString("endereco"));

                retorno.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return retorno;

    }
}
