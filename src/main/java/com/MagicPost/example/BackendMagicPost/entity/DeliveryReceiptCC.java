package com.MagicPost.example.BackendMagicPost.entity;

import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deliveryReceiptCC")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryReceiptCC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shipperName;

    private String transport;

    private String time;
    private String receiverName;

    private String type;

    private String status = ReceiptStatus.NOT_ARRIVE;
    @Transient
    private String sentPointAddress;

    @Transient
    private String receivePointAddress;
    @Transient
    private String packageName;


    @ManyToOne
    @JoinColumn(name = "collection_id1")
    private CollectionPoint collectionPointSender;



    @ManyToOne
    @JoinColumn(name = "collection_id2")
    private CollectionPoint collectionPointReceiver;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "package_id")
    private Package aPackage;
}
