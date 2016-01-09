package jb.controller;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.pageModel.*;
import jb.service.UserAttentionServiceI;
import jb.service.UserFriendTimeServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guxin on 2016/1/1.
 */
@Controller
@RequestMapping("/api/apiUserAttentionController")
public class ApiUserAttentionController extends BaseController {

    @Autowired
    private UserAttentionServiceI userAttentionService;

    @Autowired
    private UserFriendTimeServiceI userFriendTimeService;

    @Autowired
    private TokenManage tokenManage;

    private SessionInfo getSessionInfo(HttpServletRequest request){
        SessionInfo s = tokenManage.getSessionInfo(request);
        return s;
    }

    /**
     * 关注用户(添加关注或把is_delete改为0)
     * @param ua
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/user_attention")
    public Json userAttention(UserAttention ua, HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            ua.setUserId(s.getId());
            int r = userAttentionService.addAttention(ua);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("已经关注！");
            }else if(r==0){
                j.setSuccess(true);
                j.setMsg("成功，是单方关注！");
                addMessage("MT01",ua.getAttUserId(),ua.getUserId());
            }else{
                j.setSuccess(true);
                j.setMsg("成功，是双方互相关注的好友！");
                addMessage("MT01",ua.getAttUserId(),ua.getUserId());
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }

        return j;
    }

    /**
     * 取消关注用户(把is_delete参数改为1)
     * @param ua
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/user_disattention")
    public Json disUserAttention(UserAttention ua,HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            ua.setUserId(s.getId());
            int r = userAttentionService.deleteAttention(ua);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("不存在此关注信息！");
            }else{
                j.setSuccess(true);
                j.setMsg("取消关注成功！");
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 查询是否关注该用户
     * @param ua
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/user_isattention")
    public Json idAttention(UserAttention ua,HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            ua.setUserId(s.getId());
            int r = userAttentionService.idAttention(ua);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("未关注");
            }else{
                j.setSuccess(true);
                j.setMsg("已关注");
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 查询我关注的好友,可分组查询
     * @param userAttention
     * @param ph
     * @param request
     * @return
     */
    @RequestMapping("/user_myattention")
    @ResponseBody
    public Json dataGridMyAttention(UserAttention userAttention, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userAttention.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userAttention.setUserId(s.getId());
            }
            j.setObj(userAttentionService.dataGridUserByGroup(userAttention, ph));
            j.success();
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 修改关注好友到其他分组
     * @param userAttention
     * @param request
     * @return
     */
    @RequestMapping("/user_editattentiongroup")
    @ResponseBody
    public Json editAttentionGroup(UserAttention userAttention, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userAttention.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userAttention.setUserId(s.getId());
            }
            if(!F.empty(userAttention.getAttentionGroup())){
                int r = userAttentionService.editAttentionGroup(userAttention);
                if(r==-1){
                    j.setSuccess(false);
                    j.setMsg("好友分组时未带上好友关注id或者被关注用户id");
                }else{
                    j.setSuccess(true);
                    j.setMsg("修改好友分组成功");
                }
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 查询用户关注的朋友圈动态，参数包括userId,attentionGroup,bsFileType
     * @param userAttention
     * @param bshoot
     * @param ph
     * @param request
     * @return
     */
    @RequestMapping("/user_friendtime")
    @ResponseBody
    public Json dataGridUserFriendTime(UserAttention userAttention, Bshoot bshoot, PageHelper ph, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userAttention.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userAttention.setUserId(s.getId());
            }
            j.setObj(userFriendTimeService.dataGridUserFriendTime(userAttention, bshoot, ph));
            j.setSuccess(false);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

}