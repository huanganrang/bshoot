package jb.controller;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.pageModel.Json;
import jb.pageModel.SessionInfo;
import jb.pageModel.UserPersonGroup;
import jb.service.UserPersonGroupServiceI;
import jb.service.UserPersonServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guxin on 2016/1/3.
 */
@Controller
@RequestMapping("/api/apiUserPersonGroupController")
public class ApiUserPersonGroupController extends BaseController {

    @Autowired
    private UserPersonGroupServiceI userPersonGroupService;

    @Autowired
    private UserPersonServiceI userPersonService;

    @Autowired
    private TokenManage tokenManage;

    private SessionInfo getSessionInfo(HttpServletRequest request){
        SessionInfo s = tokenManage.getSessionInfo(request);
        return s;
    }

    /**
     * 添加人脉圈好友分组(添加分组或把is_delete改为0)
     * @param userPersonGroup
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/addpersongroup")
    public Json addPersonGroup(UserPersonGroup userPersonGroup, HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            userPersonGroup.setUserId(s.getId());
            /*int r = userPersonGroupService.add(userPersonGroup);
            if(r==-1){
                j.setSuccess(false);
                j.setMsg("已存在该分组");
            }else{
                j.setSuccess(true);
                j.setMsg("添加分组成功");
            }*/
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }

        return j;
    }

    /**
     * 删除人脉圈好友分组(把is_delete参数改为1,并把关注好友表上的该分组id清除)
     * @param userPersonGroup
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/dispersongroup")
    public Json disPersonGroup(UserPersonGroup userPersonGroup, HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            userPersonGroup.setUserId(s.getId());
            if(!F.empty(userPersonGroup.getId())){//必需传进分组id，否则清除关注表上的分组时会有问题
                userPersonGroupService.delete(userPersonGroup.getId());
                //userPersonService.delUserPersonGroup(userPersonGroup.getId());
                j.setSuccess(true);
                j.setMsg("删除分组成功");
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
     * 修改人脉圈好友分组名称
     * @param userPersonGroup
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/editpersongroupname")
    public Json editPersonGroupName(UserPersonGroup userPersonGroup, HttpServletRequest request) {
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            userPersonGroup.setUserId(s.getId());
            if(!F.empty(userPersonGroup.getId())){
               /* int r = userPersonGroupService.edit(userPersonGroup);
                if(r==-1){
                    j.setSuccess(false);
                    j.setMsg("修改分组名称失败");
                }else{
                    j.setSuccess(true);
                    j.setMsg("修改分组名称成功");
                }*/
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
