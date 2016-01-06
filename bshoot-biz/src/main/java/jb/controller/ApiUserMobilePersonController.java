package jb.controller;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.pageModel.SessionInfo;
import jb.pageModel.UserMobilePerson;
import jb.service.UserMobilePersonServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guxin on 2016/1/3.
 */
@Controller
@RequestMapping("/api/apiUserMobilePersonController")
public class ApiUserMobilePersonController extends BaseController {

    @Autowired
    private UserMobilePersonServiceI userMobilePersonService;

    @Autowired
    private TokenManage tokenManage;

    private SessionInfo getSessionInfo(HttpServletRequest request){
        SessionInfo s = tokenManage.getSessionInfo(request);
        return s;
    }

    /**
     * 添加通讯录(添加关注或把is_delete改为0)
     * @param userMobilePerson
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/addmobileperson")
    public Json addMobilePerson(UserMobilePerson userMobilePerson, HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            userMobilePerson.setUserId(s.getId());
            int r = userMobilePersonService.add(userMobilePerson);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("已经存在");
            }else{
                j.setSuccess(true);
                j.setMsg("添加成功");
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }

        return j;
    }

    /**
     * 删除通讯录(把is_delete参数改为1)
     * @param userMobilePerson
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/dismobileperson")
    public Json disMobilePerson(UserMobilePerson userMobilePerson,HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            userMobilePerson.setUserId(s.getId());
            int r = userMobilePersonService.deleteMobilePerson(userMobilePerson);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("该通讯录不存在");
            }else{
                j.setSuccess(true);
                j.setMsg("删除成功！");
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 查询通讯录
     * @param userMobilePerson
     * @param ph
     * @param request
     * @return
     */
    @RequestMapping("/userMobilePerson")
    @ResponseBody
    public Json dataGridMyMobilePerson(UserMobilePerson userMobilePerson, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userMobilePerson.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userMobilePerson.setUserId(s.getId());
            }
            j.setObj(userMobilePersonService.dataGrid(userMobilePerson, ph));
            j.success();
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

}
