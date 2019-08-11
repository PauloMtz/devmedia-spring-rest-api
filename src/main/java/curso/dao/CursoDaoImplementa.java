package curso.dao;

import curso.domain.Curso;
import curso.exception.NaoExisteDaoException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CursoDaoImplementa implements CursoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void salvar(Curso curso) {
        entityManager.persist(curso);
    }

    @Override
    public void atualizar(Curso curso) {
        entityManager.merge(curso);
    }

    @Override
    public void excluir(Long id) {
        //entityManager.remove(entityManager.getReference(Curso.class, id));
        try {
            entityManager.remove(entityManager.getReference(Curso.class, id));
        } catch (EntityNotFoundException ex) {
            throw new NaoExisteDaoException("Curso não encontrado para id = " + id + ".");
        }
    }

    @Override
    public Curso buscarPorId(Long id) {
        //return entityManager.find(Curso.class, id);
        Curso curso = entityManager.find(Curso.class, id);
        if (curso == null) {
            throw new NaoExisteDaoException("Curso não encontrado para id = " + id + ".");
        }
        return curso;
    }

    @Override
    public List<Curso> listarTodos() {
        return entityManager
                .createQuery("select c from Curso c", Curso.class)
                .getResultList();
    }
}
