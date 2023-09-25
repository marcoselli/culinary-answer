package br.dev.marco.mapper;

import br.dev.marco.infra.repository.orm.QuestionAnsweredORM;
import br.dev.marco.infra.web.response.QuestionAnsweredResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface QuestionAnsweredResponseMapper {

    QuestionAnsweredResponse from(QuestionAnsweredORM questionAnsweredORM);

}

