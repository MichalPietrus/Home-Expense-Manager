package com.expense.manager;

import com.expense.manager.model.Transaction;
import com.expense.manager.repository.UserRepository;
import com.expense.manager.web.DashboardController;
import com.expense.manager.web.LoginController;
import com.expense.manager.web.RegistrationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {DashboardController.class, LoginController.class, RegistrationController.class, Transaction.class})
@AutoConfigureMockMvc(addFilters = false)
public class DashboardControllerTest {

    /*
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void DashboardHTTPRequestMatchingTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void LoginPageHTTPRequestMatchingTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void RegistrationPageHTTPRequestMatchingTest() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk());
    }

    @Test
    public void TransactionPageHTTPRequestMatchingTest() throws Exception {
        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk());
    }

     */

}
