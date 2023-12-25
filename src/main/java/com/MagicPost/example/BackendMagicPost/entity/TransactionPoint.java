package com.MagicPost.example.BackendMagicPost.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transaction_point")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class TransactionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @JsonManagedReference
    @OneToMany(mappedBy = "transactionPoint",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<StaffTransaction> staffTransactions = new HashSet<>();
}
