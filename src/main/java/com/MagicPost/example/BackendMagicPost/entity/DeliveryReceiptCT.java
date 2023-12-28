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
    private String receiverName="";

    private String type="";

    private String status = ReceiptStatus.NOT_ARRIVE;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
    @Transient
    private String sentPointAddress;

    @Transient
    private String receivePointAddress;
    @Transient
    private String packageName;


    @ManyToOne
    @JsonBackReference("col_sent")
    @JoinColumn(name = "collection_id")
    private CollectionPoint collectionPointSender;

    @ManyToOne
    @JsonBackReference("tran_receive")
    @JoinColumn(name = "transaction_id")
    private TransactionPoint transactionPointReceiver;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "package_id")
    private Package aPackage;


}
