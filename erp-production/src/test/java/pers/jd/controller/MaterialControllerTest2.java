//package pers.jd.controller;
//
//import static org.hamcrest.Matchers.equalTo;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import javax.annotation.Resource;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//
//@SpringBootTest
//// 让测试运行于Spring测试环境
//@RunWith(SpringJUnit4ClassRunner.class)
////@RunWith(SpringRunner.class)
//// AutoConfigureMockMvc配置MockMvc,相当于new MockMvc
//@AutoConfigureMockMvc
//public class MaterialControllerTest2 {
//
//  // Mock配置类注入
//  @Resource
//  private MockMvc mockMvc;
//
//  @Before
//  public void setUp() throws Exception {
//  }
//
//  @After
//  public void tearDown() throws Exception {
//  }
//
//  //测试方法
//  @Test
//  public void hello_world_test() throws Exception {
//    /**
//     *    MockMvc对象：用于模拟调用Controller的接口发起请求，
//     *          在@Test定义的hello测试用例中，
//     *          perform函数执行一次请求调用，
//     *          accept用于执行接收的数据类型，
//     *          andExpect用于判断接口返回的期望值。
//     */
//    String url = "http://localhost:8991/material/hello";
//
//    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
//            .get(url)
//            .accept(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(content().string(equalTo("hello world!")))
//        .andReturn();
//
//    int status = mvcResult.getResponse().getStatus();
//    // 断言
//    Assert.assertEquals(status, 200);
//  }
//
//  @Test
//  public void should_get_material_list() throws Exception {
//
//    String url_prefix = "http://localhost:8991";
//    String url_content = "/material/getAll";
//
//    String url = url_prefix + url_content;
//
//    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
//            .get(url))
//        .andDo(MockMvcResultHandlers.print())
//        .andReturn();
//
//    int status = mvcResult.getResponse().getStatus();
//
//    // 断言
//    Assert.assertEquals(status, 200);
//  }
//}
