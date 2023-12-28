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
@Table(name = "receiptReceiver")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryReceiptToReceiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shipperName;

    private String transport;

    private String time;
    private String receiverName="";
    private String receiverPhoneNumber="";

    private String type="";

    private String status = ReceiptStatus.NOT_ARRIVE;
    @Transient
    private String sentPointAddress;
    @Transient
    private String packageName;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "transaction_id")
    private TransactionPoint transactionPointSender;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "package_id")
    private Package aPackage;
}
