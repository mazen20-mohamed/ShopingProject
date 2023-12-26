package com.example.productService.model.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phones")
public class Phone {
    @Id
    @GeneratedValue
    private Long id;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "branchId",referencedColumnName = "id")
    private Branch branchPhone;
}
