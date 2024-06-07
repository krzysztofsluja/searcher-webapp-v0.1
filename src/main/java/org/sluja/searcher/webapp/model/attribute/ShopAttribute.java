package org.sluja.searcher.webapp.model.attribute;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sluja.searcher.webapp.model.context.Context;
import org.sluja.searcher.webapp.model.shop.Shop;

import java.util.Date;

@Entity(name = "shop_attribute")
public class ShopAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
    @ManyToOne
    @JoinColumn(name = "context_id")
    private Context context;
    @Column(nullable = false, length = 255)
    private String name;
    private String value;
    @Column(name = "insert_user_id")
    private long insertUserId;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;
    private boolean active;
}
