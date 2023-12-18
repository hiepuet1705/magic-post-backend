package com.MagicPost.example.BackendMagicPost.entity;


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
    @JoinColumn(name = "customer_id")
    private Customer customerSender;
}
