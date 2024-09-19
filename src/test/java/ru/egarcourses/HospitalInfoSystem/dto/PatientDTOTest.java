package ru.egarcourses.HospitalInfoSystem.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PatientDTOTest {
    @Test
    public void testConstructor_Default() {
        PatientDTO patientDTO = new PatientDTO();
        assertNotNull(patientDTO);
        assertEquals(null, patientDTO.getFullName());
        assertEquals(0, patientDTO.getAge());
        assertEquals(null, patientDTO.getPolicyNumber());
        assertEquals(null, patientDTO.getPhoneNumber());
    }

    @Test
    public void testConstructor_Parameters() {
        PatientDTO patientDTO = new PatientDTO("Ivan Ivanov", 20, "11111111111",
                "89131111111");
        assertNotNull(patientDTO);
        assertEquals("Ivan Ivanov", patientDTO.getFullName());
        assertEquals(20, patientDTO.getAge());
        assertEquals("11111111111", patientDTO.getPolicyNumber());
        assertEquals("89131111111", patientDTO.getPhoneNumber());
    }

    @Test
    public void testGettersAndSetters() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFullName("Ivan Ivanov");
        patientDTO.setAge(20);
        patientDTO.setPolicyNumber("11111111111");
        patientDTO.setPhoneNumber("89131111111");
        assertEquals("Ivan Ivanov", patientDTO.getFullName());
        assertEquals(20, patientDTO.getAge());
        assertEquals("11111111111", patientDTO.getPolicyNumber());
        assertEquals("89131111111", patientDTO.getPhoneNumber());
    }
}
