package org.sluja.searcher.webapp.mapper;

import org.mapstruct.*;
import org.sluja.searcher.webapp.dto.context.ContextDto;
import org.sluja.searcher.webapp.model.context.Context;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContextMapper {

    ContextDto map(final Context context);
}