package curso.service;

import curso.dao.CursoDao;
import curso.domain.Curso;
import curso.exception.IdNaoValidoServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CursoServiceImplementa implements CursoService {

    @Autowired
    private CursoDao dao;

    @Override
    public void salvar(Curso curso) {
        dao.salvar(curso);
    }

    @Override
    public void atualizar(Long id, Curso curso) {
        curso.setId(idValido(id));
        dao.buscarPorId(id);
        dao.atualizar(curso);
    }

    @Override
    public void excluir(Long id) {
        dao.excluir(idValido(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Curso buscarPorId(Long id) {
        return dao.buscarPorId(idValido(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarTodos() {
        return dao.listarTodos();
    }

    @Override
    public Curso atualizarDataInicio(Long id, Date dataInicio) {
        Curso curso = dao.buscarPorId(idValido(id)); // consulta um curso
        curso.setDataInicio(dataInicio); // atualiza sua data no banco
        return curso; // retorna objeto alterado
    }

    // método que chama a classe IdNaoValidoServiceException
    private Long idValido(Long id) {
        if (id <= 0) {
            throw new IdNaoValidoServiceException("Valor do campo id está invalido."
                    + "Deve ser uma valor inteiro maior que zero.");
        }
        return id;
    }
}
