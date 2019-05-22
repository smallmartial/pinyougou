package com.pinyougou.page.service.impl;

import com.pinyougou.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @Author smallmartial
 * @Date 2019/5/21
 * @Email smallmarital@qq.com
 */
@Component
public class PageListener implements MessageListener {

    @Autowired
    private ItemPageService itemPageService;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try{
            String text = textMessage.getText();
            System.out.println("receive"+textMessage);
            boolean b = itemPageService.genItemHtml(Long.parseLong(text));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
