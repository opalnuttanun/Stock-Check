package com.team22.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Data
@Getter @Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name="CheckHistory")
public class CheckHistory {
    @Id
    @SequenceGenerator(name="CheckHistory_seq",sequenceName="CheckHistory_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CheckHistory_seq")
    @Column(name="CheckHistory_ID",unique = true, nullable = false)
     
    private @NonNull Long checkhistoryId;
    private LocalDate checkhistorytDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Product.class)
    @JoinColumn(name = "Product_ID", insertable = true)
    private  Product product;
  
}