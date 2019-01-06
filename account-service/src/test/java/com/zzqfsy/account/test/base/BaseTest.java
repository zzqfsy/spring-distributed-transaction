package com.zzqfsy.account.test.base;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: zzqfsy
 * @Description:
 * @date: Created in 11:08 AM 2018/12/31
 * @modified By:
 **/
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("test")
public abstract class BaseTest {

}
