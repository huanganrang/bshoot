package jb.controller;

import jb.absx.F;
import jb.pageModel.Json;
import jb.pageModel.PageHelper;
import jb.pageModel.SessionInfo;
import jb.pageModel.UserAttentionGroup;
import jb.service.UserAttentionGroupServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guxin on 2016/1/2.
 */
@Controller
@RequestMapping("/api/apiUserAttentionGroupController")
public class ApiUserAttentionGroupController extends BaseController {

    @Autowired
    private UserAttentionGroupServiceI userAttentionGroupService;

    /**
     * 查询用户好友分组信息(添加分组或把is_delete改为0)，参数:userId,groupName
     * @param userAttentionGroup
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/attentiongroup")
    public Json dataGridAttentionGroup(UserAttentionGroup userAttentionGroup, HttpServletRequest request, PageHelper ph) {
        Json j = new Json();
        try {
            if(F.empty(userAttentionGroup.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userAttentionGroup.setUserId(s.getId());
            }
            j.setObj(userAttentionGroupService.dataGrid(userAttentionGroup, ph));
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }

        return j;
    }

    /**
     * 添加用户好友分组(添加分组或把is_delete改为0)，参数:userId,groupName
     * @param userAttentionGroup
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/addattentiongroup")
    public Json addAttentionGroup(UserAttentionGroup userAttentionGroup, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userAttentionGroup.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userAttentionGroup.setUserId(s.getId());
            }
            j = userAttentionGroupService.addAttention(userAttentionGroup);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }

        return j;
    }

    /**
     * 删除用户好友分组(把is_delete参数改为1,并把关注好友表上的该分组id清除)，参数:userId,id
     * @param userAttentionGroup
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/disattentiongroup")
    public Json disAttentionGroup(UserAttentionGroup userAttentionGroup, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userAttentionGroup.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userAttentionGroup.setUserId(s.getId());
            }
            if(!F.empty(userAttentionGroup.getId())){
                int r = userAttentionGroupService.deleteAttentionGroup(userAttentionGroup);
                if(r==-1){
                    j.setSuccess(false);
                    j.setMsg("删除分组失败");
                }else{
                        j.setSuccess(true);
                        j.setMsg("删除分组及关注表上的分组信息成功");
                }
            }else{
                j.setSuccess(false);
                j.setMsg("删除分组失败,未传入分组id");
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 修改用户好友分组名称，参数:id,userId,groupName
     * @param userAttentionGroup
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/editattentiongroupname")
    public Json editAttentionGroupName(UserAttentionGroup userAttentionGroup, HttpServletRequest request) {
        Json j = new Json();
        try {
            if(F.empty(userAttentionGroup.getUserId())){
                SessionInfo s = getSessionInfo(request);
                userAttentionGroup.setUserId(s.getId());
            }
            if(!F.empty(userAttentionGroup.getId())){
                int r = userAttentionGroupService.edit(userAttentionGroup);
                if(r==-1){
                    j.setSuccess(false);
                    j.setMsg("修改分组名称失败");
                }else{
                    j.setSuccess(true);
                    j.setMsg("修改分组名称成功");
                }
            }else{
                j.setSuccess(false);
                j.setMsg("修改分组名称失败,未传入分组id");
            }
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }

}
