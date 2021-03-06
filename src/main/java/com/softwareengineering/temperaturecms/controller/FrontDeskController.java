package com.softwareengineering.temperaturecms.controller;

import com.softwareengineering.temperaturecms.service.RoomStatusService;
import com.softwareengineering.temperaturecms.utils.WebResultUtil;
import com.softwareengineering.temperaturecms.vo.InvoiceVo;
import com.softwareengineering.temperaturecms.vo.ResponseVo;
import com.softwareengineering.temperaturecms.vo.RoomDetailListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-06-01
 */
@RestController
@RequestMapping("/frontdesk")
@Api(tags = "前台相关")
public class FrontDeskController {

    @Autowired
    private RoomStatusService roomStatusService;

    @GetMapping("/RDR")
    @ApiOperation("RDR")
    public ResponseEntity<String> createRDR(@RequestParam Integer id){

        RoomDetailListVo roomDetail = roomStatusService.getRoomDetail(id);

        return WebResultUtil.buildResult(ResponseVo.success(roomDetail), HttpStatus.OK);
    }

    @GetMapping("/invoice")
    @ApiOperation("打印收据")
    public ResponseEntity<String> createInvoice(@RequestParam Integer id){

        InvoiceVo invoice = roomStatusService.getInvoice(id);

        return WebResultUtil.buildResult(ResponseVo.success(invoice),HttpStatus.OK);
    }
}
