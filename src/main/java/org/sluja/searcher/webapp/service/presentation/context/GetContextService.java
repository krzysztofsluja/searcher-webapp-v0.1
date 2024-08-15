package org.sluja.searcher.webapp.service.presentation.context;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodStartLog;
import org.sluja.searcher.webapp.dto.context.ContextDto;
import org.sluja.searcher.webapp.mapper.ContextMapper;
import org.sluja.searcher.webapp.repository.context.ContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GetContextService {

    private final ContextRepository contextRepository;
    private final ContextMapper contextMapper;

    @ObjectMethodStartLog
    @ObjectMethodEndLog
    public List<ContextDto> getAllContexts() {
        return contextRepository
                .findAll()
                .stream()
                .filter(Objects::nonNull)
                .map(contextMapper::map)
                .toList();
    }
}
