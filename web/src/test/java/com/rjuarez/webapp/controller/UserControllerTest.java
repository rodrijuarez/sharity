package com.rjuarez.webapp.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rjuarez.common.Constants;
import com.rjuarez.core.manager.UserManager;

public class UserControllerTest extends BaseControllerTestCase {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testHandleRequest() throws Exception {
        mockMvc.perform(get("/admin/users")).andExpect(status().isOk()).andExpect(model().attributeExists(Constants.USER_LIST))
                .andExpect(view().name("admin/userList"));
    }

    @Test
    public void testSearch() throws Exception {
        // reindex before searching
        final UserManager userManager = (UserManager) applicationContext.getBean("userManager");
        userManager.reindex();

        final Map<String, Object> model = mockMvc.perform((get("/admin/users")).param("q", "admin")).andExpect(status().isOk())
                .andExpect(model().attributeExists(Constants.USER_LIST)).andExpect(view().name("admin/userList")).andReturn().getModelAndView()
                .getModel();

        final List<?> results = (List<?>) model.get(Constants.USER_LIST);
        assertNotNull(results);
        assertTrue(results.size() >= 1);
    }
}
