package br.unipar.pet.dogui.poo.services;

import br.unipar.pet.dogui.poo.domain.Pelagem;
import br.unipar.pet.dogui.poo.exceptions.NegocioException;
import br.unipar.pet.dogui.poo.respositories.PelagemRepository;
import java.sql.SQLException;
import java.util.ArrayList;

public class PelagemService {
    private PelagemRepository pelagemRepository = new PelagemRepository();
    
    public Pelagem insert (Pelagem pelagem) throws SQLException, NegocioException{
        validate(pelagem);
        pelagem = pelagemRepository.insert(pelagem);
        
        return pelagem;
    }
    
    public Pelagem edit(Pelagem pelagem) throws SQLException, NegocioException{
        validate(pelagem);
        validateUpdate(pelagem);
        pelagem = pelagemRepository.update(pelagem);
        
        return pelagem;
    }
    
    public Pelagem findById(int id) throws SQLException{
        return pelagemRepository.findById(id);
    }
    
    public ArrayList<Pelagem> findAll() throws SQLException{        
        return pelagemRepository.findAll();
    }
    
    private void validate(Pelagem pelagem) throws NegocioException{
        if(pelagem.getDescricao() == null){
            throw new NegocioException("O nome deve ser informado.");
        }
        if(pelagem.getDescricao().isBlank()){
            throw new NegocioException("O nome deve ser informado.");
        }
        if (pelagem.getDescricao().length()<=3){
            throw new NegocioException("O nome deve ter pelo menos 2 letras.");
        }
    }
    
    private void validateUpdate(Pelagem pelagem) throws NegocioException{
        if(pelagem.getId()==0){
            throw new NegocioException("Informe um código válido!");
        }
    }
    
    public void delete(int id) throws SQLException {
        pelagemRepository.delete(id);
    }
}
