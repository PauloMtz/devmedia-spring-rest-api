package curso.dao;

import curso.domain.Curso;

import java.util.List;

public interface CursoDao {

    void salvar(Curso curso);

    void atualizar(Curso curso);

    void excluir(Long id);

    Curso buscarPorId(Long id);

    List<Curso> listarTodos();
}
