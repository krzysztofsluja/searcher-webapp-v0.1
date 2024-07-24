package org.sluja.searcher.webapp.model.category.property;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sluja.searcher.webapp.model.category.Category;

import java.util.Date;

@Entity
@Table(name = "category_properties")
@Getter
@Setter
public class CategoryProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "value", nullable = false)
    private String value;
    @Column(name = "active", nullable = true)
    private boolean active;
    @ManyToOne
    @JoinTable(name = "category_property_category",
            joinColumns = @JoinColumn(name = "category_property_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Category category;
    @CreationTimestamp
    @Column(name = "created_date")
    public Date createdDate;
    @UpdateTimestamp
    @Column(name = "updated_date")
    public Date updatedDate;
}
