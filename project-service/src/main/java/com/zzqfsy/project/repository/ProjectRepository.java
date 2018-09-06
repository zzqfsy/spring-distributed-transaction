package com.zzqfsy.project.repository;

import com.zzqfsy.project.conf.filter.HeaderHolder;
import com.zzqfsy.project.dao.TProjectDAO;
import com.zzqfsy.project.domain.Project;
import com.zzqfsy.project.manager.LockOnRedisManager;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @Author: zzqfsy
 * @Description:
 * @Date: Created in 10:48 2018/8/21
 * @Modified By:
 **/
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class ProjectRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRepository.class);

    @Autowired
    private TProjectDAO tProjectDAO;
    @Autowired
    private LockOnRedisManager lockOnRedisManager;

    /**
     * 更改产品的可购额度
     * @param iProjectId
     * @param dcmChangeAmount
     * @return
     */
    @Transactional
    public Pair<Boolean, String> changeProjectAble(Integer iProjectId, BigDecimal dcmChangeAmount) {
        String key = "lock:term:account:balance:" + iProjectId;
        Pair pair = lockOnRedisManager.handleByFair(key, 5, 5, Pair.class, () -> {
            Project project1 = tProjectDAO.selectByPrimaryKey(iProjectId);

            if (project1 == null || project1.getId() <= 0) {
                return new Pair(false, "产品不存在");
            }

            BigDecimal beforeAble = project1.getDcmAble();
            BigDecimal afterAble = project1.getDcmAble().add(dcmChangeAmount);
            if (project1.getDcmAble().compareTo(BigDecimal.ZERO) <= 0 ||
                    afterAble.compareTo(BigDecimal.ZERO) < 0){
                return new Pair(false, "产品可购额度不足");
            }
            if (afterAble.compareTo(project1.getDcmTotal()) > 0){
                // notify
                return new Pair(false, "产品可用额度不能超过最大额度");
            }

            logger.info(String.format("Project %s able change %s to %s", project1.getId(), beforeAble, afterAble));

            Project temp2 = new Project();
            temp2.setId(project1.getId());
            temp2.setDcmAble(afterAble);
            tProjectDAO.updateByPrimaryKeySelective(temp2);

            return new Pair(true, null);
        });
        if (pair == null)
            return new Pair<>(false, "服务繁忙，请稍后访问");

        return pair;
    }
}
