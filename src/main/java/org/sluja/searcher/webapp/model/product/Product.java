package org.sluja.searcher.webapp.model.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sluja.searcher.webapp.model.context.Context;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String shopName;
    @ElementCollection
    @CollectionTable(name="images_addresses", joinColumns=@JoinColumn(name="product_id"))
    private List<String> imagesAddresses;
    private String productAddress;
    @ManyToOne
    @JoinColumn(name = "context_id")
    private Context context;
    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;



/*    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof Product p))
            return false;
        return ProductEqualizer.changeProduct(this, p);
    }*/

}
