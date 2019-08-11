package curso.service;

import curso.domain.Curso;

import java.util.Date;
import java.util.List;

public interface CursoService {

    void salvar(Curso curso);

    void atualizar(Long id, Curso curso);

    void excluir(Long id);

    Curso buscarPorId(Long id);

    List<Curso> listarTodos();

    Curso atualizarDataInicio(Long id, Date dataInicio);
}
