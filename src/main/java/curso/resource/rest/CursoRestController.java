package curso.resource.rest;

import curso.domain.Curso;
import curso.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(
        value = "/cursos",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CursoRestController {

    @Autowired
    private CursoService cursoService;

    @GetMapping // o método de acesso é do tipo GET
    @ResponseStatus(HttpStatus.OK) // status 200
    public List<Curso> listarCursos() {
        return cursoService.listarTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Curso buscarCursoPorId(@PathVariable("id") Long id) {
        return cursoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody Curso curso) {
        cursoService.salvar(curso);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(curso.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Curso editarCurso(@PathVariable("id") Long id, @RequestBody Curso curso) {
        cursoService.atualizar(id, curso);
        return curso;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Curso editarDataInicio(@PathVariable("id") Long id, @RequestBody Curso curso) {
        return cursoService.atualizarDataInicio(id, curso.getDataInicio());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirCurso(@PathVariable("id") Long id) {
        cursoService.excluir(id);
    }
}
