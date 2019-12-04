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

    @Test
    public void searchByPhoneNumberDifferentFormat() throws Exception {

        User user = new User(1, "Delta");

        String phone = "89004444127";

        String phoneWithSpaces = "8 900 44 44 127";
        String phoneWithPlus = "+89004444127";
        String phoneStartsWith7 = "79004444127";
        String phoneWithSpaceAndPlusAndStartsWith7 = "+7 900 44 44 127";

        PhoneRecord record = new PhoneRecord(1L, "Company", phone, user);


        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(phoneRecordRepository.findByPhoneNumberEquals(phone)).willReturn(record);

        String answerToDefaultPhone = mvc.perform(get("/records/search?phone="+phone)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        String answerToPhoneWithSpaces = mvc.perform(get("/records/search?phone="+phoneWithSpaces)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        String answerToPhoneWithPlus = mvc.perform(get("/records/search?phone="+phoneWithPlus)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        String answerToPhoneStartsWith7 = mvc.perform(get("/records/search?phone="+phoneStartsWith7)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        String answerToPhoneWithSpaceAndPlusAndStartsWith7 = mvc.perform(get("/records/search?phone="+phoneWithSpaceAndPlusAndStartsWith7)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        assertThat(answerToDefaultPhone).isEqualTo(record.toString());
        assertThat(answerToPhoneWithSpaces).isEqualTo(record.toString());
        assertThat(answerToPhoneWithPlus).isEqualTo(record.toString());
        assertThat(answerToPhoneStartsWith7).isEqualTo(record.toString());
        assertThat(answerToPhoneWithSpaceAndPlusAndStartsWith7).isEqualTo(record.toString());

    }
}
