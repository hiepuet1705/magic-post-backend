package com.MagicPost.example.BackendMagicPost.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "collection_point")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String district;

    @Column(unique = true)
    private String province;

    @JsonManagedReference
    @OneToMany(mappedBy = "collectionPoint",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<StaffCollection> staffCollections = new HashSet<>();



}
