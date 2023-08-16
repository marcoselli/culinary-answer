package br.dev.marco.mapper;

import br.dev.marco.domain.CulinaryQuestion;
import br.dev.marco.infra.web.request.QuestionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CulinaryQuestionMapper {

    CulinaryQuestion from(QuestionRequest questionRequest);
}
