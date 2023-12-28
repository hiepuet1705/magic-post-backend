package com.MagicPost.example.BackendMagicPost.payload;

import com.MagicPost.example.BackendMagicPost.utils.PackageStatus;
import com.MagicPost.example.BackendMagicPost.utils.ReceiptStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PackageInfoDto {
    private Long packageId;
    private int weight;
    private String name;
    private String description;
    private String type;

    private String receiverFirstName;
    private String receiverLastName;
    private String receiverProvince;
    private String receiverDistrict;
    private String receiverPhoneNumber;
    private String hashKey="";

    private List<PointDto> pointHistoryDtoList = new ArrayList<>();
    private PointDto currentPoint;
    private String status = PackageStatus.AT_TRANSACTION_POINT;

    private PointDto firstTranPoint;
    private String firstTranPointStatus = ReceiptStatus.NOT_ARRIVE;
    private String timeArriveFirstPoint;

    private PointDto firstColPoint;
    private String firstColPointStatus = ReceiptStatus.NOT_ARRIVE;
    private String timeArriveFirstColPoint;

    private PointDto secondColPoint;
    private String secondColPointStatus = ReceiptStatus.NOT_ARRIVE;
    private String timeArriveSecondColPoint;

    private PointDto secondTranPoint;
    private String secondTranPointStatus = ReceiptStatus.NOT_ARRIVE;
    private String timeArriveSecondTranPoint;

}
