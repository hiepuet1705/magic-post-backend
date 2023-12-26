package com.MagicPost.example.BackendMagicPost.payload;

import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackageDto {
    private Long id;
    private int weight;
    private String name;
    private String description;
    private String type;
    private String status = PackageStatus.AT_TRANSACTION_POINT;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverAddress;
    private String receiverPhoneNumber;
    private String hashKey="";

    private UserDto userDto;

    private Long transactionPoint = 0L;

    private Long collectionPoint = 0L;

    private PointDto pointDto;
}
