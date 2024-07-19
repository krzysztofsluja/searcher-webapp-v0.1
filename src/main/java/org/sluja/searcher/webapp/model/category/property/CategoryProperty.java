package org.sluja.searcher.webapp.model.category.property;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sluja.searcher.webapp.model.category.Category;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "category_properties")
public class CategoryProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "value", nullable = false)
    private String value;
    @Column(name = "active", nullable = true)
    private boolean active;
    @ManyToMany
    @JoinTable(
            name = "category_property_category",
            joinColumns = @JoinColumn(name = "category_property_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
    @CreationTimestamp
    @Column(name = "created_date")
    public Date createdDate;
    @UpdateTimestamp
    @Column(name = "updated_date")
    public Date updatedDate;
}
