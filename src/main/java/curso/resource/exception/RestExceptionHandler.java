package curso.resource.exception;

import curso.domain.DetalheErro;
import curso.exception.IdNaoValidoServiceException;
import curso.exception.NaoExisteDaoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * essa anotação @ControllerAdvice é um listener
 * sempre que uma exceção for lançada, será chamada essa classe
 * que identificará o tipo de exceção conforme a anotação do método
 * além disso, ela extende da classe ResponseEntityExceptionHandler
 * que fornece o método handleExceptionInternal que permite enviar
 * para a aplicação cliente o objeto com a exceção tratada
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // será chamado quando ocorrer uma requisição com id inválido: valor zero ou negativo
    // a requisição será detectada na classe CursoServiceImplementa, que chama a classe exception
    // e aqui envia os ddos para a aplicação cliente
    @ExceptionHandler({IdNaoValidoServiceException.class})
    public ResponseEntity<Object> idInvalido(IdNaoValidoServiceException ex,
                                             WebRequest request) {

        return handleExceptionInternal(
                ex, DetalheErro.builder()
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.BAD_REQUEST)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // será chamado quando tentar salvar registro já existente
    // exceção enviada pelo hiberante
    @ExceptionHandler({org.hibernate.exception.ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolada(org.hibernate.exception.ConstraintViolationException ex, WebRequest request) {

        return handleExceptionInternal(
                ex, DetalheErro.builder()
                        .addDetalhe("Constraint violada: " + ex.getConstraintName())
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.CONFLICT) // status 409
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    // será chamado quando um atributo for nulo. Ex.: falta do título
    // essas exceções capturadas são enviadas pelo hibernate
    @ExceptionHandler({org.hibernate.PropertyValueException.class})
    public ResponseEntity<Object> propriedadeNula(org.hibernate.PropertyValueException ex, WebRequest request) {

        return handleExceptionInternal(
                ex, DetalheErro.builder()
                        .addDetalhe("O atributo '"+ ex.getPropertyName() +"' não pode ser nulo.")
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.BAD_REQUEST)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // será chamado quando não existir o objeto requisitado
    @ExceptionHandler({NaoExisteDaoException.class})
    public ResponseEntity<Object> entidadeNaoEncontrada(NaoExisteDaoException ex, WebRequest request) {

        return handleExceptionInternal(
                ex, DetalheErro.builder()
                        .addDetalhe("Recurso não encontrado na base de dados.")
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.NOT_FOUND)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    // será chamado se for lançado uma dessas exceções da anotação
    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> serverException(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(
                ex, DetalheErro.builder()
                        .addDetalhe("Um exceção foi lançada.")
                        .addErro(ex.getMessage())
                        .addStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .addHttpMethod(getHttpMethod(request))
                        .addPath(getPath(request))
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // recupera a uri que gerou o erro
    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    // recupera o tipo de método (GET, POST, PATCH etc.)
    private String getHttpMethod(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getMethod();
    }
}