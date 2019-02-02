package com.team22.backend.Entity;
import lombok.*;

import javax.persistence.*;


@Entity  //บอกว่าเป็น class entity class ที่เก็บขอมูล
@Data  // lombox จะสร้าง method getter setter ให้เอง
@Getter @Setter
@ToString
@EqualsAndHashCode
@Table(name=" CheckProduct") //ชื่อตาราง
public class CheckProduct {
    @Id  //  Annotations  @Id  บอกว่าเป็น  Primary  key
    @SequenceGenerator(name="checkproduct_seq",sequenceName="checkproduct_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="checkproduct_seq")
    @Column(name="CheckProduct_ID",unique = true, nullable = false)
  
    private @NonNull Long checkId;
    private @NonNull String checkComment;
    private @NonNull Integer checkLevel;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Product.class)
    @JoinColumn(name = "Product_ID", insertable = true)
    private  Product product;

    @OneToOne()
    @JoinColumn(name = "CheckHistory_ID", insertable = true)
    private  CheckHistory checkhistory;
}