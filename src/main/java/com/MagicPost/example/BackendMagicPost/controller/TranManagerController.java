package com.MagicPost.example.BackendMagicPost.controller;

import com.MagicPost.example.BackendMagicPost.controller.dtos.user.EditStaffRequest;
import com.MagicPost.example.BackendMagicPost.entity.Package;
import com.MagicPost.example.BackendMagicPost.payload.StaffDto;
import com.MagicPost.example.BackendMagicPost.payload.StaffRegisterDto;
import com.MagicPost.example.BackendMagicPost.service.StaffTranService;
import com.MagicPost.example.BackendMagicPost.service.TranManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/manager/tran")
public class TranManagerController {
    private TranManagerService tranManagerService;
    private StaffTranService staffTranService;

    public TranManagerController(TranManagerService tranManagerService, StaffTranService staffTranService) {
        this.tranManagerService = tranManagerService;
        this.staffTranService = staffTranService;
    }

    @GetMapping("/sent-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getSentPackagesInATransactionPoint(){

        List<Package> packages = staffTranService.getSentPackageInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/curr-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getCurrPackagesInATransactionPoint(){
        List<Package> packages = staffTranService.getCurrentPackagesInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/rec-packages")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<Package>> getReceivePackagesInATransactionPoint(){
        List<Package> packages = staffTranService.getReceivePackagesInATransactionPoint();
        return new ResponseEntity<>(packages, HttpStatus.OK);

    }
    @GetMapping("/staff")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<List<StaffDto>> getAllStaffInATransactionPoint(){
        List<StaffDto> staffDtos = tranManagerService.getAllStaffInATransactionPoint();
        return new ResponseEntity<>(staffDtos, HttpStatus.OK);
    }

    @GetMapping("/staff/{staffId}")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable("staffId") Long staffId){
        StaffDto staffDto = tranManagerService.getStaffById(staffId);
        return new ResponseEntity<>(staffDto,HttpStatus.OK);
    }

    @PutMapping("staff/{staffId}")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<String> editStaff(@PathVariable("staffId") Long staffId,@RequestBody EditStaffRequest editStaffRequest){
        String response = tranManagerService.editStaff(staffId, editStaffRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("staff/{staffId}")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<String> deleteStaff(@PathVariable("staffId") Long staffId){
        String response = tranManagerService.deleteStaff(staffId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/register/staff")
    @PreAuthorize("hasRole('HEADTRAN')")
    public ResponseEntity<String> createAccountForStaffTran(@RequestBody StaffRegisterDto staffRegisterDto){
        String response = tranManagerService.createAccountForStaffTran(staffRegisterDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
