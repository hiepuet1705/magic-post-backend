package com.MagicPost.example.BackendMagicPost.entity;

import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private String receiverName = "";

    private String type ="";

    private String status = ReceiptStatus.NOT_ARRIVE;
    @Transient
    private String sentPointAddress;

    @Transient
    private String receivePointAddress;
    @Transient
    private String packageName;

    @ManyToOne
    @JsonBackReference("collection_sender_ref")
    @JoinColumn(name = "collection_id_sent")
    private CollectionPoint collectionPointSender;



    @ManyToOne
    @JsonBackReference("collection_receiver_ref")
    @JoinColumn(name = "collection_id_receive")
    private CollectionPoint collectionPointReceiver;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "package_id")
    private Package aPackage;
}
