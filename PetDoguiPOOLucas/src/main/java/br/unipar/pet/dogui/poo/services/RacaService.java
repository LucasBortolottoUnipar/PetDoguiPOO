package br.unipar.pet.dogui.poo.services;

import br.unipar.pet.dogui.poo.domain.Raca;
import br.unipar.pet.dogui.poo.exceptions.NegocioException;
import br.unipar.pet.dogui.poo.respositories.RacaRepository;
import java.sql.SQLException;
import java.util.ArrayList;


public class RacaService {
    
    private RacaRepository racaRepository = new RacaRepository();
    
    public Raca insert(Raca raca) throws SQLException, NegocioException{
        validate(raca);
        
        return racaRepository.insert(raca);
    }
    
    public Raca edit(Raca raca) throws SQLException, NegocioException{
        validate(raca);
        validateUpdate(raca);
        
        return raca;
    }
    
    public Raca findById(int id) throws SQLException{
        return racaRepository.findById(id);
    }
    
    public ArrayList<Raca> findAll() throws SQLException{        
        return racaRepository.findAll();
    }
    
    
    private void validate(Raca raca) throws NegocioException{
        if(raca.getDescricao()==null){
            throw new NegocioException("Uma descricao deve ser informada.");
        }
        if(raca.getDescricao().isBlank()){
            throw new NegocioException("Uma descricao deve ser informada.");
        }
        if(raca.getDescricao().length()>60){
            throw new NegocioException("A descricao nao deve passar de 60 caracteres.");
        }
    }
    private void validateUpdate(Raca raca) throws NegocioException{
        if(raca.getId()==0){
            throw new NegocioException("Informe um código válido!");
        }
    }
}
