package com.MagicPost.example.BackendMagicPost.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "package")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int weight;
    private String name;
    private String description;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverAddress;

    private String receiverPhoneNumber;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "sender_id")
    private Customer sender;


    @Column(name = "transaction_id")
    private Long transactionPoint;

    @Column(name = "collection_id")
    private Long collectionPoint;




}
