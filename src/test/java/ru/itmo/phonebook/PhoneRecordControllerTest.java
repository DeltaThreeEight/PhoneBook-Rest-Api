package ru.itmo.phonebook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.itmo.phonebook.controllers.PhoneRecordController;
import ru.itmo.phonebook.entities.PhoneRecord;
import ru.itmo.phonebook.entities.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneRecordController.class)
@RunWith(SpringRunner.class)
public class PhoneRecordControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void getRecord() throws Exception {

        User user = new User(1, "Delta");

        PhoneRecord record = new PhoneRecord(1L, "Company", "8903213123123", user);

        given(phoneRecordRepository.findById(1L)).willReturn(Optional.of(record));

        String answer = mvc.perform(get("/records/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo(record.toString());

    }

    @Test
    public void createRecord() throws Exception {

        User user = new User(1, "Delta");

        PhoneRecord record = new PhoneRecord(1L, "Company", "8903213123123", user);

        String inputJson = mapToJson(record);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(phoneRecordRepository.save(record)).willReturn(record);

        String answer = mvc.perform(post("/records")
                .contentType(APPLICATION_JSON).content(inputJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("Phone record successfully created");

    }

    @Test
    public void updateRecord() throws Exception {

        User user = new User(1, "Delta");

        PhoneRecord record = new PhoneRecord(1L, "Company", "8903213123123", user);

        PhoneRecord editedRecord = new PhoneRecord(1L, "Company", "890637567357", user);

        String inputJson = mapToJson(editedRecord);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(phoneRecordRepository.findById(1L)).willReturn(Optional.of(record));
        given(phoneRecordRepository.save(editedRecord)).willReturn(editedRecord);

        String answer = mvc.perform(put("/records/1")
                .contentType(APPLICATION_JSON).content(inputJson))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("Phone record successfully edited");

    }

    @Test
    public void deleteRecord() throws Exception {

        User user = new User(1, "Delta");

        PhoneRecord record = new PhoneRecord(1L, "Company", "8903213123123", user);


        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(phoneRecordRepository.findById(1L)).willReturn(Optional.of(record));

        String answer = mvc.perform(delete("/records/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo("Phone record successfully deleted");

    }

    @Test
    public void searchByPhoneNumber() throws Exception {

        User user = new User(1, "Delta");

        String phone = "8903213123123";

        PhoneRecord record = new PhoneRecord(1L, "Company", phone, user);


        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(phoneRecordRepository.findByPhoneNumberEquals(phone)).willReturn(record);

        String answer = mvc.perform(get("/records/search?phone="+phone)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(answer).isEqualTo(record.toString());

    }
}
