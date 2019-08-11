package curso.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/*
 *  essa classe irá utilizar o padrão builder
 *  por isso, não terá setters nem contrutor
 *  para isso, utilizará uma classe interna (Builder)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalheErro implements Serializable {

    private Integer statusCode;
    private String statusMessage;
    private String httpMethod;
    private String erro;
    private String detalhe;
    private String path;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getErro() {
        return erro;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public String getPath() {
        return path;
    }

    // retorna instância interna builder (objeto da classe Builder - abaixo)
    public static Builder builder() {
        return new Builder();
    }

    // classe interna
    public static class Builder {

        private DetalheErro erro;

        // construtor
        Builder() {
            this.erro = new DetalheErro();
        }

        public Builder addStatus(HttpStatus status) {
            this.erro.statusCode = status.value(); // recupera o código
            this.erro.statusMessage = status.getReasonPhrase(); // mensagem
            return this; // retorna o status
        }

        public Builder addHttpMethod(String method) {
            this.erro.httpMethod = method;
            return this;
        }

        public Builder addErro(String erro) {
            this.erro.erro = erro;
            return this;
        }

        public Builder addDetalhe(String detalhe) {
            this.erro.detalhe = detalhe;
            return this;
        }

        public Builder addPath(String path) {
            this.erro.path = path;
            return this;
        }

        // retorna todos os detalhes de erro
        public DetalheErro build() {
            return this.erro;
        }
    }
}
