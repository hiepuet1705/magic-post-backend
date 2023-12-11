package com.MagicPost.example.BackendMagicPost.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deliveryReceiptTC")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeliveryReceiptTC {
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
    private TransactionPoint transactionPointSender;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private CollectionPoint collectionPointReceiver;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "package_id")
    private Package aPackage;


}
