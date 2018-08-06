package com.gbai;

import com.gbai.user.entity.UserEntity;
import com.gbai.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqProviderApplication.class)
public class RabbitmqProviderApplicationTests {

    /**
     * 模拟mvc测试对象
     */
    private MockMvc mockMvc;

    /**
     * web项目上下文
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    UserRepository userRepository;
    /**
     * 所有测试方法执行之前执行该方法
     */
    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * 测试添加用户
     * @throws Exception
     */
    @Test
    public void testUserAdd() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
                .param("userName","lisi")
                .param("name","李四")
                .param("age","25")
        )
                .andDo(MockMvcResultHandlers.log())
                .andReturn();
    }

    @Test
    public void test(){
        UserEntity user = new UserEntity();
        user.setAge(18);
        user.setUserName("libai");
        user.setName("李白");
        userRepository.save(user);
    }

}
