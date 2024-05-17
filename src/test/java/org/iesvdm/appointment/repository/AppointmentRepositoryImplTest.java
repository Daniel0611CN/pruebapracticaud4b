package org.iesvdm.appointment.repository;

import org.iesvdm.appointment.entity.Appointment;
import org.iesvdm.appointment.entity.Customer;
import org.iesvdm.appointment.repository.impl.AppointmentRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.iesvdm.appointment.entity.AppointmentStatus.SCHEDULED;

public class AppointmentRepositoryImplTest {

    private Set<Appointment> appointments;

    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setup() {
        appointments = new HashSet<>();
        appointmentRepository = new AppointmentRepositoryImpl(appointments);
    }

    /**
     * Crea 2 citas (Appointment) una con id 1 y otra con id 2,
     * resto de valores inventados.
     * Agrégalas a las citas (appointments) con la que
     * construyes el objeto appointmentRepository bajo test.
     * Comprueba que cuando invocas appointmentRepository.getOne con uno
     * de los id's anteriores recuperas obtienes el objeto.
     * Pero si lo invocas con otro id diferente recuperas null
     */
    @Test
    void getOneTest() {
        //When
        AppointmentRepositoryImpl appointmentRepository1 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository2 = new AppointmentRepositoryImpl(appointments);

        //Do
        Appointment appointment1 = appointmentRepository1.getOne(1);
        Appointment appointment2 = appointmentRepository2.getOne(2);

        //Then
        assertThat(appointmentRepository1.getOne(1)).isEqualTo(appointment1);
        assertThat(appointmentRepository2.getOne(2)).isEqualTo(appointment2);
        assertThat(appointmentRepository1.getOne(2)).isNull();
        assertThat(appointmentRepository2.getOne(1)).isNull();

    }

    /**
     * Crea 2 citas (Appointment) y guárdalas mediante
     * appointmentRepository.save.
     * Comprueba que la colección appointments
     * contiene sólo esas 2 citas.
     */
    @Test
    void saveTest() {
        //When
        AppointmentRepositoryImpl appointmentRepository1 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository2 = new AppointmentRepositoryImpl(appointments);

        //Do
        appointmentRepository1.save(new Appointment());
        appointmentRepository2.save(new Appointment());

        //Then
        assertThat(appointments.size() == 2);
    }

    /**
     * Crea 2 citas (Appointment) una cancelada por un usuario y otra no,
     * (atención al estado de la cita, lee el código) y agrégalas mediante
     * appointmentRepository.save a la colección de appointments
     * Comprueba que mediante appointmentRepository.findCanceledByUser
     * obtienes la cita cancelada.
     */
    @Test
    void findCanceledByUserTest() {
        //When
        AppointmentRepositoryImpl appointmentRepository1 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository2 = new AppointmentRepositoryImpl(appointments);

        //Do
        appointmentRepository1.findCanceledByUser(1);
        appointmentRepository1.save(new Appointment());
        appointmentRepository2.save(new Appointment());

        //Then
        assertThat(appointments.size() == 1);

    }

    /**
     * Crea 3 citas (Appointment), 2 para un mismo cliente (Customer)
     * con sólo una cita de ellas presentando fecha de inicio (start)
     * y fin (end) dentro del periodo de búsqueda (startPeriod,endPeriod).
     * Guárdalas mediante appointmentRepository.save.
     * Comprueba que appointmentRepository.findByCustomerIdWithStartInPeroid
     * encuentra la cita en cuestión.
     * Nota: utiliza LocalDateTime.of(...) para crear los LocalDateTime
     */
    @Test
    void findByCustomerIdWithStartInPeroidTest() {
        //When
        AppointmentRepositoryImpl appointmentRepository1 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository2 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository3 = new AppointmentRepositoryImpl(appointments);

        //Do
        appointmentRepository1.save(new Appointment());
        appointmentRepository2.save(new Appointment());
        appointmentRepository3.save(new Appointment());
        appointmentRepository1.findByCustomerIdWithStartInPeroid(1, LocalDateTime.of(2022, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 1, 0));
        appointmentRepository2.findByCustomerIdWithStartInPeroid(1, LocalDateTime.of(2022, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 1, 0));
        appointmentRepository3.findByCustomerIdWithStartInPeroid(2, LocalDateTime.of(2022, 1, 1, 0, 0), LocalDateTime.of(2022, 1, 1, 1, 0));


        //Then
        assertThat(appointments.size() == 3);

    }


    /**
     * Crea 2 citas (Appointment) una planificada (SCHEDULED) con tiempo fin
     * anterior a la tiempo buscado por appointmentRepository.findScheduledWithEndBeforeDate
     * guardándolas mediante appointmentRepository.save para la prueba de findScheduledWithEndBeforeDate
     *
     */
    @Test
    void findScheduledWithEndBeforeDateTest() {
        //When
        AppointmentRepositoryImpl appointmentRepository1 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository2 = new AppointmentRepositoryImpl(appointments);

        //Do
        Appointment ap1 = new Appointment();
        ap1.setStatus(SCHEDULED);
        ap1.setEnd(LocalDateTime.of(2022, 1, 1, 0, 0));
        appointmentRepository1.save(ap1);
        Appointment ap2 = new Appointment();
        ap2.setStatus(SCHEDULED);
        ap2.setEnd(LocalDateTime.of(2022, 1, 1, 1, 0));
        appointmentRepository2.save(ap2);

        //Then
        assertThat(appointments.size() == 2);
        assertThat(ap1.getStatus()==SCHEDULED);
        assertThat(ap2.getStatus()==SCHEDULED);

    }


    /**
     * Crea 3 citas (Appointment) planificadas (SCHEDULED)
     * , 2 para un mismo cliente, con una elegible para cambio (con fecha de inicio, start, adecuada)
     * y otra no.
     * La tercera ha de ser de otro cliente.
     * Guárdalas mediante appointmentRepository.save
     * Comprueba que getEligibleAppointmentsForExchange encuentra la correcta.
     */
    @Test
    void getEligibleAppointmentsForExchangeTest() {
        //When
        AppointmentRepositoryImpl appointmentRepository1 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository2 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository3 = new AppointmentRepositoryImpl(appointments);

        //Do
        appointmentRepository1.getEligibleAppointmentsForExchange(LocalDateTime.of(2022, 1, 1, 0, 0), 1);
        appointmentRepository2.getEligibleAppointmentsForExchange(LocalDateTime.of(2022, 1, 1, 0, 0), 1);
        appointmentRepository3.getEligibleAppointmentsForExchange(LocalDateTime.of(2022, 1, 1, 0, 0), 2);
        appointmentRepository1.save(new Appointment());
        appointmentRepository2.save(new Appointment());
        appointmentRepository3.save(new Appointment());

        //Then
        assertThat(appointments.size() == 3);
    }


    /**
     * Igual que antes, pero ahora las 3 citas tienen que tener
     * clientes diferentes y 2 de ellas con fecha de inicio (start)
     * antes de la especificada en el método de búsqueda para
     * findExchangeRequestedWithStartBefore
     */
    @Test
    void findExchangeRequestedWithStartBeforeTest() {
        //When
        AppointmentRepositoryImpl appointmentRepository1 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository2 = new AppointmentRepositoryImpl(appointments);
        AppointmentRepositoryImpl appointmentRepository3 = new AppointmentRepositoryImpl(appointments);


        //Do
        appointmentRepository1.findExchangeRequestedWithStartBefore(LocalDateTime.of(2022, 1, 1, 0, 0));
        appointmentRepository2.findExchangeRequestedWithStartBefore(LocalDateTime.of(2022, 1, 1, 0, 0));
        appointmentRepository3.findExchangeRequestedWithStartBefore(LocalDateTime.of(2022, 1, 1, 0, 0));
        appointmentRepository1.save(new Appointment());
        appointmentRepository2.save(new Appointment());
        appointmentRepository3.save(new Appointment());


        //Then
        assertThat(appointments.size() == 3);
    }
}
