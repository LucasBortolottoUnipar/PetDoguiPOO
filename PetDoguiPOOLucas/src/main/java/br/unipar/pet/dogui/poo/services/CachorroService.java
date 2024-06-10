package br.unipar.pet.dogui.poo.services;

import br.unipar.pet.dogui.poo.domain.Cachorro;
import br.unipar.pet.dogui.poo.exceptions.NegocioException;
import br.unipar.pet.dogui.poo.respositories.CachorroRepository;
import java.sql.SQLException;
import java.util.ArrayList;

public class CachorroService {
    
    private CachorroRepository cachorroRepository = new CachorroRepository();
    
    public Cachorro insert (Cachorro cachorro) throws SQLException, NegocioException{
        validate(cachorro);
        cachorro = cachorroRepository.insert(cachorro);
        
        return cachorro;
    }
    
    public Cachorro edit(Cachorro cachorro) throws SQLException, NegocioException{
        validate(cachorro);
        validateUpdate(cachorro);
        cachorro = cachorroRepository.update(cachorro);
        
        return cachorro;
    }
    
    public Cachorro findById(int id) throws SQLException{
        return cachorroRepository.findById(id);
    }
    
    public ArrayList<Cachorro> findAll() throws SQLException{        
        return cachorroRepository.findAll();
    }
    
    private void validate(Cachorro cachorro) throws NegocioException{
        if(cachorro.getNome() == null){
            throw new NegocioException("O nome deve ser informado.");
        }
        if(cachorro.getNome().isBlank()){
            throw new NegocioException("O nome deve ser informado.");
        }
        if (cachorro.getNome().length()<=3){
            throw new NegocioException("O nome deve ter pelo menos 2 letras.");
        }
    }
    
    private void validateUpdate(Cachorro cachorro) throws NegocioException{
        if(cachorro.getId()==0){
            throw new NegocioException("Informe um código válido!");
        }
    }
    public void delete(int id) throws SQLException {
        cachorroRepository.delete(id);
    }
}
