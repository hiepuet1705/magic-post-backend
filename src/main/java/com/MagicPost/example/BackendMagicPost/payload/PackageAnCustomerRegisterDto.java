package com.MagicPost.example.BackendMagicPost.payload;


import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackageAnCustomerRegisterDto {
    private int weight;
    private String name;
    private String description;
    private String type;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverProvince;
    private String receiverDistrict;
    private String receiverPhoneNumber;
    private CustomerRegisterDto customerRegisterDto;
}
