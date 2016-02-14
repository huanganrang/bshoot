package jb.controller;

import component.implus.util.ImPlusUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * ImPlusController 容联云通讯鉴权控制器
 *
 * Created by guxin on 2016/2/14.
 */

@Controller
@RequestMapping("/imPlusController")
public class ImPlusController extends BaseController {

    private static final Logger log = Logger.getLogger(Controller.class.getName());

    /**
     * 容联云通讯鉴权
     * @return
     */
    @RequestMapping("/implusauth")
    public void imPlusAuth(HttpServletRequest request, HttpServletResponse response) {
        Document doc = null;
        String body = "";
        ImPlusUtil imPlusUtil = new ImPlusUtil();
        log.info(" =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= starts =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ");
        try {
            // 解析流
            InputStream in = request.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            String str = null;
            StringBuffer xmlfile = new StringBuffer();
            while ((str = bf.readLine()) != null) {
                xmlfile.append(str);
            }
            doc = DocumentHelper.parseText(xmlfile.toString());
        } catch (DocumentException e) {
            log.error(" *** DocumentException ***", e);
        } catch (IOException e1) {
            log.error(" *** IOException ***", e1);
        }
        Element root = doc.getRootElement();
        String action = root.elementTextTrim("action");
        if (action.equals("CallAuth")) {
            // 解析呼叫鉴权
            body = imPlusUtil.parseCallAuth(root);
        } else if (action.equals("CallEstablish")) {
            // 解析摘机请求
            body = imPlusUtil.parseCallEstablish(root);
        } else if (action.equals("Hangup")) {
            // 解析挂断请求
            body = imPlusUtil.parseHangup(root);
        }
        // 设置返回header
        response.setHeader("Status-Code", "HTTP/1.1 200 OK");
        response.setHeader("Date", new Date() + "");
        response.setHeader("Content-Length", body.length() + "");
        try {
            // 输出 ，返回到客户端
            OutputStream opt = response.getOutputStream();
            OutputStreamWriter out = new OutputStreamWriter(opt);
            out.write(body);
            out.flush();
        } catch (IOException e) {
            log.error(" *** IOException ***", e);
        }
        log.info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= end =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\r\n");
    }

}
