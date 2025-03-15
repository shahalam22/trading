package com.example.controller;

import com.example.modal.Asset;
import com.example.modal.User;
import com.example.service.AssetService;
import com.example.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

//    @Autowired
//    public AssetController(AssetService assetService){
//        this.assetService = assetService;
//    }

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(
            @PathVariable Long assetId
    ) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return ResponseEntity.ok(asset);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<User> getUserByUserIdAndCoinId(
            @PathVariable String coinId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.findAssetByUserIdAndCoinId(user.getId(), coinId);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping()
    public ResponseEntity<List<Asset>> getAllAssetsForUser(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Asset> assets = assetService.getUsersAssets(user.getId());
        return ResponseEntity.ok().body(assets);
    }
}
