package org.sluja.searcher.webapp.model.category;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sluja.searcher.webapp.model.context.Context;

import java.util.Date;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean active;
    @ManyToMany
    @JoinColumn(name = "context_id")
    private List<Context> contexts;
    @Column(name = "insert_user_id")
    private long insertUserId;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;

}
