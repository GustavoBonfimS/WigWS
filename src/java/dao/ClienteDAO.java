package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Avaliacao;
import modelo.Cliente;
import modelo.Usuario;

public class ClienteDAO {

    public ClienteDAO() {

    }

    public boolean inserir(Cliente usuario) {
        String sql = "INSERT INTO usuario(username,senha,perfil,email) VALUES(?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            pst.setString(3, "cliente");
            pst.setString(4, usuario.getEmail());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }

        return retorno;

    }

    public boolean onCliente(Cliente usuario) {
        String sql = "INSERT INTO cliente (idusuario, CPF) VALUES (?,?)";
        boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setInt(1, usuario.getIdusuario());
            pst.setString(2, usuario.getCPF());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }

        return retorno;
    }

    public Cliente buscarIdDoCliente(Cliente usuario) // mesma coisa só que com objeto cliente
    {
        String sql = "SELECT * from usuario where usuario.username = ?";
        Cliente retorno = null;

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            pst.setString(1, usuario.getLogin());
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                retorno = new Cliente();
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

    public boolean atualizarCPF(Cliente usuario) {
        String sql = "UPDATE cliente set CPF=? where idusuario=?";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            pst.setString(1, usuario.getCPF());
            pst.setInt(2, usuario.getIdusuario());
            if (pst.executeUpdate() > 0) {
                retorno = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }

        return retorno;

    }

    public Cliente buscar(Cliente cliente) {
        String sql = "select usuario.idusuario, cliente.idcliente, usuario.username, usuario.senha, usuario.email, usuario.perfil,\n"
                + "cliente.CPF from usuario, cliente where usuario.username =? and usuario.idusuario = cliente.idusuario";
        Cliente retorno = null;

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            pst.setString(1, cliente.getLogin());
            ResultSet res = pst.executeQuery();

            if (res.next()) {
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

    public List<Cliente> listar() {
        String sql = "select * from usuario, cliente where usuario.idusuario = cliente.idusuario and usuario.perfil = 'cliente'";
        List<Cliente> retorno = new ArrayList<Cliente>();

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Cliente item = new Cliente();
                item.setIdusuario(res.getInt("idusuario"));
                item.setIdcliente(res.getInt("idcliente"));
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
    
    public List<Avaliacao> atualizarIndex(Date lastCheck, Date dataAtual) {
        String sql = "select * from avaliacao where data between ? and ? ";
        List<Avaliacao> retorno = new ArrayList<Avaliacao>();

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setDate(1, lastCheck);
            pst.setDate(2, dataAtual);
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Avaliacao item = new Avaliacao();
                item.setIdavaliacao(res.getInt("idavaliacao"));
                item.setConteudo(res.getString("conteudo"));
                item.setAutor(res.getString("autor"));
                item.setIdcliente(res.getInt("idcliente"));
                item.setIdempresa(res.getInt("idempresa"));
                item.setData(res.getDate("data"));
                item.setHora(res.getTime("hora"));

                retorno.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return retorno;

    }

    public boolean fazerAvaliacao(Avaliacao avaliacao) {

        String sql = "INSERT INTO avaliacao(autor, conteudo, idcliente, idempresa, data, hora) VALUES(?,?,?,?,?,?)";
        boolean retorno = false;
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, avaliacao.getAutor());
            pst.setString(2, avaliacao.getConteudo());
            pst.setInt(3, avaliacao.getIdcliente());
            pst.setInt(4, avaliacao.getIdempresa());
            pst.setDate(5, avaliacao.getData());
            pst.setTime(6, avaliacao.getHora());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return retorno;
    }

    public boolean ResponderAvaliacao(Avaliacao avaliacao) {

        String sql = "INSERT INTO resposta(autor, conteudo, idavaliacao, idempresa, idcliente,data, hora) VALUES(?,?,?,?,?,?,?)";
        Boolean retorno = false;
        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {
            pst.setString(1, avaliacao.getAutor());
            pst.setString(2, avaliacao.getConteudo());
            pst.setInt(3, avaliacao.getIdavaliacao());
            pst.setInt(4, avaliacao.getIdempresa());
            pst.setInt(5, avaliacao.getIdcliente());
            pst.setDate(6, avaliacao.getData());
            pst.setTime(7, avaliacao.getHora());

            if (pst.executeUpdate() > 0) {
                retorno = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            retorno = false;
        }

        return retorno;
    }

    public List<Avaliacao> listarAvaliacao() {
        String sql = "SELECT * FROM avaliacao";
        List<Avaliacao> retorno = new ArrayList<Avaliacao>();

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Avaliacao item = new Avaliacao();
                item.setIdavaliacao(res.getInt("idavaliacao"));
                item.setConteudo(res.getString("conteudo"));
                item.setAutor(res.getString("autor"));
                item.setIdcliente(res.getInt("idcliente"));
                item.setIdempresa(res.getInt("idempresa"));
                item.setData(res.getDate("data"));
                item.setHora(res.getTime("hora"));

                retorno.add(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return retorno;

    }

    public Avaliacao buscarIdAvaliacao(Avaliacao avaliacao) {
        String sql = "SELECT * FROM avaliacao where conteudo =?";
        Avaliacao retorno = null;

        PreparedStatement pst = Conexao.getPreparedStatement(sql);
        try {

            pst.setString(1, avaliacao.getConteudo());
            ResultSet res = pst.executeQuery();

            if (res.next()) {
                retorno = new Avaliacao();
                retorno.setIdavaliacao(res.getInt("idavaliacao"));
                retorno.setAutor(res.getString("autor"));
                retorno.setIdcliente(res.getInt("idcliente"));
                retorno.setIdempresa(res.getInt("idempresa"));
                retorno.setData(res.getDate("data"));
                retorno.setHora(res.getTime("hora"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return retorno;

    }

}
