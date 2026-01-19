package com.example.productService.model.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int building_number;
    private String street;
    private String city;
    private String government;
    private String country;
    private String location;

    @OneToMany(mappedBy = "branchPhone" , cascade=CascadeType.ALL)
    private List<Phone> phoneList  = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "shopId",referencedColumnName = "id",nullable = false)
    private Shop shop;
}
