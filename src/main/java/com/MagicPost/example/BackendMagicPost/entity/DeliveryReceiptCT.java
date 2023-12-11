package com.MagicPost.example.BackendMagicPost.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deliveryReceiptCT")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryReceiptCT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shipperName;

    private String transport;

    private String time;
    private String receiverName;

    private String type;


    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private CollectionPoint transactionPointSender;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private TransactionPoint transactionPointReceiver;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "package_id")
    private Package aPackage;


}
