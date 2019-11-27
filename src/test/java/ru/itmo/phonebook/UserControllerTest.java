package ru.itmo.phonebook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.itmo.phonebook.controllers.UserController;
import ru.itmo.phonebook.entities.PhoneRecord;
import ru.itmo.phonebook.entities.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUsers() throws Exception {
        User user = new User(1, "Delta");

        List<User> allUsers = singletonList(user);

        given(userRepository.findAll()).willReturn(allUsers);

        String answer = mvc.perform(get("/users")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("["+user.toString()+"]");
    }

    @Test
    public void getUserRecords() throws Exception {
        User user = new User(1, "Delta");

        PhoneRecord record1 = new PhoneRecord(1L, "Company", "8900123123", user);
        PhoneRecord record2 = new PhoneRecord(2L, "Second", "8900412341", user);

        List<PhoneRecord> allRecords = Arrays.asList(record1, record2);

        given(phoneRecordRepository.findAllByUser(user)).willReturn(allRecords);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        String answer = mvc.perform(get("/users/1/records")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("["+record1.toString()+","+record2.toString() +"]");
    }

    @Test
    public void getUsersByName() throws Exception {
        User user = new User(1, "Delta");

        List<User> allUsers = singletonList(user);

        given(userRepository.findUsersByNameLike("D%")).willReturn(allUsers);

        String answer = mvc.perform(get("/users/search?name=D%")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("["+user.toString()+"]");

    }

    @Test
    public void createUser() throws Exception {
        User user = new User(1, "Delta");

        String inputJson = mapToJson(user);

        String answer = mvc.perform(post("/users")
                .contentType(APPLICATION_JSON).content(inputJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("User successfully created");

    }

    @Test
    public void getUser() throws Exception {
        User user = new User(1, "Delta");

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        String answer = mvc.perform(get("/users/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo(user.toString());

    }

    @Test
    public void updateUser() throws Exception {
        User user = new User(1, "Delta");
        User editedUser = new User(1, "Test");

        String inputJson = mapToJson(editedUser);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(editedUser)).willReturn(editedUser);

        String answer = mvc.perform(put("/users/1")
                .contentType(APPLICATION_JSON).content(inputJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("User successfully edited");

    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User(1, "Delta");

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        String answer = mvc.perform(delete("/users/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("User successfully deleted");

    }

}
