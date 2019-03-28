package com.meux.icarbonx.service;

import com.meux.icarbonx.configuration.FeignMultipartSupportConfig;
import com.meux.icarbonx.entities.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(value = "CELL-WORLD-TEST-TOOLS", configuration = FeignMultipartSupportConfig.class)
public interface GmToolsFeignService {

    @RequestMapping(value = "/item/additem", method = {RequestMethod.GET})
    Result issueItems(@RequestParam("rid") long rid, @RequestParam("wid") int wid, @RequestParam("items") String items, @RequestParam("nums") String nums);

    @RequestMapping(value = "/item/setlevel", method = {RequestMethod.POST})
    Result setRoleLevel(@RequestParam("rid") long rid, @RequestParam("wid") int wid, @RequestParam("level") int level);

    @RequestMapping(value = "/item/sysmail", method = {RequestMethod.POST})
    Result sendSysMail(@RequestParam("wid") int wid, @RequestParam("tempId") int tempId);

    @RequestMapping(value = "/item/patchmail", method = {RequestMethod.POST})
    Result sendPatchMail(@RequestParam("rid") long rid, @RequestParam("wid") int wid, @RequestParam("tempId") int tempId);

    @RequestMapping(value = "/config/update", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    Result updateConfig(@RequestParam("wid") int wid, @RequestPart("file") MultipartFile[] file);

    @RequestMapping(value = "/config/test", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    Result testConfig(@RequestParam(value = "wid") int wid, @RequestPart(value = "file") MultipartFile file);
}
