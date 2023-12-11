package com.MagicPost.example.BackendMagicPost.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String receiverFirstName;
    private String receiverLastName;
    private String receiverAddress;

    private String receiverPhoneNumber;

    @ManyToOne
    @JoinColumn( name = "sender_id")
    private Customer sender;








}
