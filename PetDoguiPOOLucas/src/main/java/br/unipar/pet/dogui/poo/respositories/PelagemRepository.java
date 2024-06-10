package br.unipar.pet.dogui.poo.respositories;

import br.unipar.pet.dogui.poo.domain.Pelagem;
import br.unipar.pet.dogui.poo.infraestructure.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PelagemRepository {
    
    private static final String INSERT = 
            "INSERT INTO PELAGEM(ds_pelagem) VALUES(?)";
    
    private static final String UPDATE = 
            "UPDATE pelagem SET ds_pelagem=? WHERE id=?";
    
    private static final String DELETE = 
            "DELETE FROM pelagem WHERE id=?";
    
    private static final String FIND_BY_ID = 
            "SELECT id, ds_pelagem " + 
            "FROM pelagem WHERE id = ?";
    
    private static final String FIND_ALL = 
            "SELECT id, ds_pelagem FROM pelagem";
    
    public Pelagem insert(Pelagem pelagem) throws SQLException{
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        
        try{
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, pelagem.getDescricao());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            rs.next();
            pelagem.setId(rs.getInt(1));
            
        } finally {
        if(rs!=null)
            rs.close();
        if(pstmt!=null)
            pstmt.close();
        if(conn!=null)
            conn.close();
        }
        return pelagem;
    }
    
    public Pelagem update(Pelagem pelagem) throws SQLException{
        Connection conn = null; 
        PreparedStatement ps = null;
        
        try {
            conn = new ConnectionFactory().getConnection();
            
            ps = conn.prepareStatement(UPDATE);
            ps.setString(1, pelagem.getDescricao());
            ps.setInt(2, pelagem.getId());
            ps.executeUpdate();
        } finally{
            if (ps!=null)
                ps.close();
            if(conn!=null)
                conn.close();
        }
        return pelagem;
    }
    
    public Pelagem findById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Pelagem retorno = null;
        
        try {
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            if(rs.next()){
                retorno = new Pelagem();
                retorno.setId(rs.getInt("id"));
                retorno.setDescricao(rs.getString("ds_pelagem"));
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
    
     public ArrayList<Pelagem> findAll() throws SQLException {
        
        ArrayList<Pelagem> retorno = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            
            conn = new ConnectionFactory().getConnection();
            pstmt = conn.prepareStatement(FIND_ALL);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                
                Pelagem pelagem = new Pelagem();
                pelagem.setId(rs.getInt("id"));
                pelagem.setDescricao(rs.getString("ds_pelagem"));
                //adiciono no arraylist de retorno
                retorno.add(pelagem);
                
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
