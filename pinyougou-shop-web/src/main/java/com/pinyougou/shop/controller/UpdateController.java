package com.pinyougou.shop.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDFSClient;

/**
 * @Author smallmartial
 * @Date 2019/5/11
 * @Email smallmarital@qq.com
 */
@RestController
public class UpdateController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    @RequestMapping("/upload")
    public Result upload(MultipartFile file){
        //1 获取文件名
        String originalFilename = file.getOriginalFilename();
        String exName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {

            //2.创建一个fastDFS客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");

            //3执行上传过程
            String path = fastDFSClient.uploadFile(file.getBytes(), exName);
            //4.拼接url
            String url = FILE_SERVER_URL + path;
            return new Result(true,url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"上传失败");

        }

    }

}
