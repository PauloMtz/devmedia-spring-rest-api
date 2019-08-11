package curso.exception;

/*
 * classe usada para prevenir consultas com id's inválidos. Ex.: com valor zero ou negativo
 */
public class IdNaoValidoServiceException extends RuntimeException {

    // construtor
    public IdNaoValidoServiceException(String message) {
        super(message);
    }
}
