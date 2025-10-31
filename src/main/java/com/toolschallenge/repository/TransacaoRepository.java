package com.toolschallenge.repository;

import com.toolschallenge.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface responsável pela comunicação com o banco de dados.
 * Herda toda a funcionalidade CRUD básica do Spring Data JPA.
 */
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, String> {

}
