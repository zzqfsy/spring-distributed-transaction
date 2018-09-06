package com.zzqfsy.project.rpc;

import com.zzqfsy.api.resp.BaseResp;
import com.zzqfsy.api.rpc.IProjectFacade;
import com.zzqfsy.project.repository.ProjectRepository;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 10:48 2018/8/21
 * @Modified By:
 **/
@RestController
@RequestMapping(value = "/project")
public class ProjectFacade implements IProjectFacade{

    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST)
    @Override
    public BaseResp changeProjectAble(@PathVariable("projectId") String projectId, @RequestParam("changeAmount") String changeAmount) {
        BigDecimal dcmChangeAmount;
        Integer iProjectId;
        if (StringUtils.isEmpty(projectId) || (iProjectId = Integer.valueOf(projectId)) <= 0 ||
                StringUtils.isEmpty(changeAmount) ||
                (dcmChangeAmount = new BigDecimal(changeAmount)) ==  null ||
                BigDecimal.ZERO.compareTo(dcmChangeAmount) == 0){
            return BaseResp.getFailInstance("参数错误");
        }

        Pair<Boolean, String> result1 = projectRepository.changeProjectAble(iProjectId, dcmChangeAmount);
        if (!result1.getKey()){
            return BaseResp.getFailInstance(result1.getValue());
        }

        return BaseResp.getInstance("操作成功");
    }
}
