package com.MagicPost.example.BackendMagicPost.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.attribute.standard.MediaSize;

@Entity
@Table(name = "customer_receipt")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private String senderName;
    @Transient
    private String senderPhoneNumber;
    private String name = "";
    private String description = "";
    private String time = "00:00";
    private int weight = 0 ;
    private String receiverName;
    private String receiverPhoneNumber;

    private double fee;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customer customerSender;

    @ManyToOne
//    @JsonBackReference
    @JoinColumn(name = "transaction_id")
    private TransactionPoint transactionPointReceive;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    @JsonIgnore
    private Package aPackage;
}
