package br.dev.marco.domain;

import br.dev.marco.enums.QuestionType;
import lombok.Data;

@Data
public class CulinaryQuestion {
    private String message;
    private Double randomness;

    public String promptMessage() {
        var sb = new StringBuilder();
        sb.append("Diante da seguinte pergunta: \n\"");
        sb.append(message + "\"");
        sb.append("\nResponda caso essa pergunta seja do tema culinário, caso contrário escreva ");
        sb.append(QuestionType.UNSUPPORTED_QUESTION.name());
        return sb.toString();
    }

}
