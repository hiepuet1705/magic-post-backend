package com.MagicPost.example.BackendMagicPost.entity;


import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.attribute.standard.MediaSize;
import java.util.Date;

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
    private int weight = 0 ;
    private String receiverName="";
    private String receiverPhoneNumber="";
    private String hashKey="";

    private String fee="";

    private String status = ReceiptStatus.TRANSFERED;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customer customerSender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "transaction_id")
    private TransactionPoint transactionPointReceive;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "package_id")
    @JsonIgnore
    private Package aPackage;
}
