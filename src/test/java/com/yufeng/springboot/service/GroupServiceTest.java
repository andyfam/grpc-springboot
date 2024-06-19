package com.yufeng.springboot.service;

import com.yufeng.springboot.model.Group;
import com.yufeng.springboot.model.GroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @MockBean
    private GroupRepository groupRepository;

    @Test
    public void test_groups(){
        List<Group> list = new ArrayList<Group>();
        Stream.of("Seattle JUG", "Denver JUG", "Dublin JUG",
                "London JUG").forEach(name ->
                list.add(new Group(name)));
        when(groupRepository.findAll()).thenReturn(list);

        Collection<Group> list2 = groupService.groups();
        assertEquals(list2, list);
    }

    @Test
    public void test_getGroup(){
        Group group = new Group("Seattle JUG");
        when(groupRepository.findById(1L)).thenReturn(Optional.of((group)));
        when(groupRepository.findById(2L)).thenReturn(null);

        Optional<Group> group2 = groupService.getGroup(1L);
        assertEquals(group2.get(), group);

        Optional<Group> group3 = groupService.getGroup(2L);
        assertNull(group3);
    }

    @Test
    public void test_createGroup(){
        Group group1 = new Group("Seattle JUG");
        Group group2 = new Group("Seattle JUG");
        group2.setId(1L);
        when(groupRepository.save(group1)).thenReturn((group2));

        Group group3 = groupService.createGroup(group1);
        assertEquals(group3, group2);
    }

    @Test
    public void test_updateGroup(){
        Group group1 = new Group("Seattle JUG");
        group1.setId(1L);
        Group group2 = new Group("Seattle JUG");
        group2.setId(1L);
        when(groupRepository.save(group1)).thenReturn((group2));

        Group group3 = groupService.updateGroup(group1);
        assertEquals(group3, group2);
    }

    @Test
    public void test_deleteGroup(){
        Long id = 1L;
        doNothing().when(groupRepository).deleteById(id);

        groupService.deleteGroup(id);
        verify(groupRepository, times(1)).deleteById(id);
    }
}
