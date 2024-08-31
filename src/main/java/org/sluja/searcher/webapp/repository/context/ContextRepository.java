package org.sluja.searcher.webapp.repository.context;

import org.sluja.searcher.webapp.model.context.Context;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContextRepository extends JpaRepository<Context, Long> {
}