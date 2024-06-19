package com.yufeng.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yufeng.springboot.model.Group;
import com.yufeng.springboot.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
public class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GroupService groupService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void test_groups() throws Exception {
        List<Group> list = new ArrayList<Group>();
        Stream.of("Seattle JUG", "Denver JUG", "Dublin JUG",
                "London JUG").forEach(name ->
                list.add(new Group(name))
        );
        when(groupService.groups()).thenReturn(list);

        mvc.perform(get("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // $ refer to root element
                .andExpect(jsonPath("$", hasSize(4)))
                // $[0] refer to first element of the list
                .andExpect(jsonPath("$[0].name").value("Seattle JUG"))
                .andExpect(jsonPath("$[1].name").value("Denver JUG"))
                .andExpect(jsonPath("$[2].name").value("Dublin JUG"))
                .andExpect(jsonPath("$[3].name").value("London JUG"));
    }

    @Test
    public void test_getGroup() throws Exception {
        Group group = new Group("Seattle JUG");
        when(groupService.getGroup(1L)).thenReturn(Optional.of(group));

        mvc.perform(get("/api/group/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Seattle JUG"));

        mvc.perform(get("/api/group/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_createGroup() throws Exception {
        Group group1 = new Group("Seattle JUG");
        Group group2 = new Group(group1.getName());
        group2.setId(1L);
        when(groupService.createGroup(group1)).thenReturn(group2);

        mvc.perform(post("/api/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group1)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/group/" + group2.getId())))
                .andExpect(jsonPath("$.name").value(group1.getName()));
    }

    @Test
    public void test_updateGroup() throws Exception {
        Group group1 = new Group("Seattle JUG");
        group1.setId(1L);
        when(groupService.updateGroup(group1)).thenReturn(group1);

        mvc.perform(put("/api/group/" + group1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(group1.getId()))
                .andExpect(jsonPath("$.name").value(group1.getName()));
    }

    @Test
    public void test_deleteGroup() throws Exception {
        Long id = 1L;
        doNothing().when(groupService).deleteGroup(id);

        mvc.perform(delete("/api/group/" + id))
                .andExpect(status().isOk());
    }
}
