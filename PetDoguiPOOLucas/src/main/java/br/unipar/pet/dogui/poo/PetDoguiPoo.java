/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.unipar.pet.dogui.poo;

import br.unipar.pet.dogui.poo.domain.Cachorro;
import br.unipar.pet.dogui.poo.domain.Cor;
import br.unipar.pet.dogui.poo.domain.Pelagem;
import br.unipar.pet.dogui.poo.domain.Raca;
import br.unipar.pet.dogui.poo.exceptions.NegocioException;
import br.unipar.pet.dogui.poo.services.CachorroService;
import br.unipar.pet.dogui.poo.services.CorService;
import br.unipar.pet.dogui.poo.services.PelagemService;
import br.unipar.pet.dogui.poo.services.RacaService;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author andersonbosing
 */
public class PetDoguiPoo {

    public static void main(String[] args) {
        
        try {
            //cor
            CorService corService = new CorService();
            Cor c = new Cor();
            c.setDescricao("Violeta");
            corService.insert(c);
            ArrayList<Cor> resultadoCor = corService.findAll();
            System.out.println(resultadoCor.toString());
            
            //pelagem
            PelagemService pelagemService = new PelagemService();
            Pelagem p = new Pelagem();
            p.setDescricao("Curta");
            pelagemService.insert(p);
            ArrayList<Pelagem> resultadoPelagem = pelagemService.findAll();
            System.out.println(resultadoPelagem.toString());
            
            //raca
            RacaService racaService = new RacaService();
            Raca r = new Raca();
            r.setDescricao("Dog Argentino");
            racaService.insert(r);
            ArrayList<Raca> resultadoRaca = racaService.findAll();
            System.out.println(resultadoRaca.toString());
            
            //cachorro
            CachorroService cachorroService = new CachorroService();
            Cachorro cao = new Cachorro();
            
            cao.setNome("Recruta");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            cao.setDtNascimento(format.parse("08/10/2017"));
            cao.setCor(c);
            cao.setPelagem(p);
            cao.setRaca(r);
            cao.setStPerfume(true);
            cao.setTamanho(40.0);
            
            cachorroService.insert(cao);
            ArrayList<Cachorro> resultadoCao = cachorroService.findAll();
            System.out.println(resultadoCao.toString());
            
            
        } catch (SQLException ex) {
            System.out.println("Ops, algo deu errado com o banco de dados\n"+ex.getMessage());
            ex.printStackTrace();
        } catch (NegocioException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {    
            System.out.println("Ops, algo deu errado contate o suporte\n"+ex.getMessage());
            ex.printStackTrace();
        }
        
    }
}
