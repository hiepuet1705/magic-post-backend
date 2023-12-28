package com.MagicPost.example.BackendMagicPost.entity;

import com.MagicPost.example.BackendMagicPost.payload.PointDto;
import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private String type;
    private String status = PackageStatus.AT_TRANSACTION_POINT;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverProvince;
    private String receiverDistrict;
    private String receiverPhoneNumber;

    @Column(unique = true)
    private String hashKey="";



    @ManyToOne()
    @JoinColumn( name = "sender_id")
    private Customer sender;


    @Column(name = "current_transaction_id")
    private Long transactionPoint = 0L;

    @Column(name = "current_collection_id")
    private Long collectionPoint = 0L;


}
