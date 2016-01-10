package jb.controller;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.pageModel.*;
import jb.service.UserPersonServiceI;
import jb.service.UserPersonTimeServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guxin on 2016/1/3.
 */
@Controller
@RequestMapping("/api/apiUserPersonController")
public class ApiUserPersonController extends BaseController {

    @Autowired
    private UserPersonServiceI userPersonService;

    @Autowired
    private UserPersonTimeServiceI userPersonTimeService;

    @Autowired
    private TokenManage tokenManage;

    private SessionInfo getSessionInfo(HttpServletRequest request){
        SessionInfo s = tokenManage.getSessionInfo(request);
        return s;
    }

    /**
     * 添加人脉圈好友(添加好友或把is_delete改为0)，需要的参数为:userId,attUserId
     * @param userPerson
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/user_person")
    public Json userPerson(UserPerson userPerson, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userPerson.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userPerson.setUserId(s.getId());
            }
            int r = userPersonService.addUserPerson(userPerson);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("已经存在好友");
            }else{
                j.setSuccess(true);
                j.setMsg("成功！");
                addMessage("MT01",userPerson.getAttUserId(),userPerson.getUserId());
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }

        return j;
    }

    /**
     * 删除人脉圈好友(把is_delete参数改为1)，需要的参数为:userId,attUserId
     * @param userPerson
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/user_disuserperson")
    public Json disUserPerson(UserPerson userPerson, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userPerson.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userPerson.setUserId(s.getId());
            }
            int r = userPersonService.deleteUserPerson(userPerson);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("不存在此好友");
            }else{
                j.setSuccess(true);
                j.setMsg("成功！");
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 查询是否人脉圈好友，需要的参数为:userId,attUserId
     * @param userPerson
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/user_isuserperson")
    public Json idUserPerson(UserPerson userPerson, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userPerson.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userPerson.setUserId(s.getId());
            }
            int r = userPersonService.isUserPerson(userPerson);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("不是人脉圈好友");
            }else{
                j.setSuccess(true);
                j.setMsg("是人脉圈好友");
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 查询人脉圈好友，需要的参数为:userId,page,rows,sort=createDatetime,order=asc
     * @param userPerson
     * @param ph
     * @param request
     * @return
     */
    @RequestMapping("/user_myuserperson")
    @ResponseBody
    public Json dataGridMyUserPerson(UserPerson userPerson, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userPerson.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userPerson.setUserId(s.getId());
            }
            j.setObj(userPersonService.dataGridMyUserPerson(userPerson, ph));
            j.success();
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 修改人脉圈好友到其他分组，现未用到分组
     * @param userPerson
     * @param request
     * @return
     */
    @RequestMapping("/user_edituserpersongroup")
    @ResponseBody
    public Json editUserPersonGroup(UserPerson userPerson, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userPerson.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userPerson.setUserId(s.getId());
            }
            if(!F.empty(userPerson.getPersonGroup())){
                /*int r = userPersonService.editUserPersonGroup(userPerson);
                if(r==-1){
                    j.setSuccess(false);
                    j.setMsg("人脉圈好友分组时未带上好友关注id或者被关注用户id");
                }else{
                    j.setSuccess(true);
                    j.setMsg("修改人脉圈好友分组成功");
                }*/
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 查询人脉圈好友动态,需要的参数为:userId,personType,bsFileType,page,rows,sort=createDatetime,order=desc
     * @param bshoot
     * @param userPersonTime
     * @param request
     * @return
     */
    @RequestMapping("/user_persontime")
    @ResponseBody
    public Json dataGriduserPersonTime(UserPersonTime userPersonTime, Bshoot bshoot, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userPersonTime.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userPersonTime.setUserId(s.getId());
            }
            j.setObj(userPersonTimeService.dataGridUserPersonTime(userPersonTime, bshoot, ph));
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }


}
