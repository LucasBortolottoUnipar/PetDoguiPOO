package br.unipar.pet.dogui.poo.respositories;

import br.unipar.pet.dogui.poo.domain.Cachorro;
import br.unipar.pet.dogui.poo.infraestructure.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Lucas
 */
public class CachorroRepository {
    private static final String INSERT = 
            "INSERT INTO CACHORRO(nome, raca_id, pelagem_id , cor_id, tamanho, dtNascimento, stPerfume) VALUES(?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE = 
            "UPDATE cachorro SET nome=?, raca_id=?, pelagem_id=?, cor_id=?, tamanho=?, stPerfume=?, dtNascimento=? WHERE id=?, ?, ?, ?, ?, ?, ?";
    
    private static final String DELETE = 
            "DELETE FROM cachorro WHERE id=?";
    
    private static final String FIND_BY_ID = 
            "SELECT id, nome, raca_id, pelagem_id, cor_id, tamanho, stPerfume, dtNascimento " + 
            "FROM cachorro WHERE id = ?, ?, ?, ?, ?, ?, ?";
    
    private static final String FIND_ALL = 
            "SELECT id, nome, raca_id, pelagem_id, cor_id, tamanho, stPerfume, dtNascimento FROM cachorro";
    
    public Cachorro insert(Cachorro cachorro) throws SQLException{
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        
        try{
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cachorro.getNome());
            pstmt.setInt(2, cachorro.getPelagem().getId());
            pstmt.setInt(3, cachorro.getRaca().getId());
            pstmt.setInt(4, cachorro.getCor().getId());
            pstmt.setDouble(5, cachorro.getTamanho());          
            pstmt.setDate(6, new Date(cachorro.getDtNascimento().getYear(), cachorro.getDtNascimento().getMonth(), cachorro.getDtNascimento().getDay()));
            pstmt.setBoolean(7, cachorro.isStPerfume()); //is por ser boolean
            
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            rs.next();
            cachorro.setId(rs.getInt(1));
            
        } finally {
        if(rs!=null)
            rs.close();
        if(pstmt!=null)
            pstmt.close();
        if(conn!=null)
            conn.close();
        }
        return cachorro;
    }
    
    public Cachorro update(Cachorro cachorro) throws SQLException{
        Connection conn = null; 
        PreparedStatement ps = null;
        
        try {
            conn = new ConnectionFactory().getConnection();
            
            ps = conn.prepareStatement(UPDATE);
            ps.setString(1, cachorro.getNome());
            ps.setInt(2, cachorro.getPelagem().getId());
            ps.setInt(3, cachorro.getRaca().getId());
            ps.setInt(4, cachorro.getCor().getId());
            ps.setDouble(5, cachorro.getTamanho());          
            ps.setDate(6, new Date(cachorro.getDtNascimento().getYear(), cachorro.getDtNascimento().getMonth(), cachorro.getDtNascimento().getDay()));
            ps.setBoolean(7, cachorro.isStPerfume());
            
            ps.executeUpdate();
        } finally{
            if (ps!=null)
                ps.close();
            if(conn!=null)
                conn.close();
        }
        return cachorro;
    }
    
    public Cachorro findById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cachorro retorno = null;
        
        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()){
                retorno = new Cachorro();
                retorno.setId(rs.getInt("ID"));
                retorno.setNome(rs.getString("nome"));
                retorno.setStPerfume(rs.getBoolean("stPerfume"));
                retorno.setTamanho(rs.getDouble("tamanho"));
                retorno.setRaca(new RacaRepository().findById(rs.getInt("raca_id")));
                retorno.setPelagem(new PelagemRepository().findById(rs.getInt("pelagem_id")));
                retorno.setCor(new CorRepository().findById(rs.getInt("cor_id"))); // alt shift c (global) // alt shift v (interna)
                retorno.setDtNascimento(rs.getDate("dtNascimento"));
            }
        }finally{
            if (rs!=null)
                rs.close();
            if(pstmt!= null)
                pstmt.close();
            if(conn!= null)
                conn.close();
        }
        return retorno;
    }
    
     public ArrayList<Cachorro> findAll() throws SQLException {
        
        ArrayList<Cachorro> retorno = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(FIND_ALL);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                
                Cachorro cachorro = new Cachorro();
                cachorro.setId(rs.getInt("id"));
                cachorro.setNome(rs.getString("nome"));
                cachorro.setRaca(new RacaRepository().findById(rs.getInt("raca_id")));
                cachorro.setPelagem(new PelagemRepository().findById(rs.getInt("pelagem_id")));
                cachorro.setCor(new CorRepository().findById(rs.getInt("cor_id")));
                cachorro.setTamanho(rs.getDouble("tamanho"));
                cachorro.setStPerfume(rs.getBoolean("stPerfume"));
                cachorro.setDtNascimento(rs.getDate("dtNascimento"));
                //adiciono no arraylist de retorno
                retorno.add(cachorro);
            }
            
        } finally {
            
            if (rs != null)
                rs.close();
            
            if (pstmt != null)
                pstmt.close();
            
            if (conn != null) 
                conn.close();
        }
        
        return retorno;
    }
     
    public void delete(int id) throws SQLException {

        Connection conn = null;
        PreparedStatement ps = null;
                
        try {

            conn = new ConnectionFactory().getConnection();

            ps = conn.prepareStatement(DELETE);
            ps.setInt(1, id);
            ps.execute();

        } finally {
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
        }

    }
}
