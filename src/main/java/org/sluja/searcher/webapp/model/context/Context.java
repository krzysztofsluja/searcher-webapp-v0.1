package org.sluja.searcher.webapp.model.context;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sluja.searcher.webapp.model.attribute.ShopAttribute;
import org.sluja.searcher.webapp.model.shop.Shop;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
public class Context {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "insert_user_id")
    private long insertUserId;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;
    private boolean active;
    @ManyToMany
    public List<Shop> shops;
    @ManyToMany
    public List<ShopAttribute> attributes;
}
