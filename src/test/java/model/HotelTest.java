package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class HotelTest {

    @Test
    public void testSituationOverbooked() {
        Double occupiedRooms = 0.81;
        Double checkIn = 1D;
        Double checkOut = 0D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Overbooked", result);
    }

    @Test
    public void testSituationAlmostOverbooked() {
        Double occupiedRooms = 0.81;
        Double checkIn = 1D;
        Double checkOut = 0D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Almost overbooked", result);
    }

    @Test
    public void testSituationHighRiskOfOverbooking() {
        Double occupiedRooms = 0.81;
        Double checkIn = 0D;
        Double checkOut = 0D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("High risk of overbooking", result);
    }

    @Test
    public void testSituationMediumRiskOfOverbooking() {
        Double occupiedRooms = 0.81;
        Double checkIn = 0D;
        Double checkOut = 0D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Medium risk of overbooking", result);
    }

    @Test
    public void testSituationSlightlyRiskOfOverbooking() {
        Double occupiedRooms = 0.81;
        Double checkIn = 0D;
        Double checkOut = 1D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Slightly risk of overbooking", result);
    }

    @Test
    public void testSituationLowRiskOfOverbooking() {
        Double occupiedRooms = 0.81;
        Double checkIn = 0D;
        Double checkOut = 1D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Low risk of overbooking", result);
    }

    @Test
    public void testSituationLowRiskOfLoss() {
        Double occupiedRooms = 0.80;
        Double checkIn = 1D;
        Double checkOut = 0D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Low risk of loss", result);
    }

    @Test
    public void testSituationSlightlyRiskOfLoss() {
        Double occupiedRooms = 0.80;
        Double checkIn = 1D;
        Double checkOut = 0D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Slightly risk of loss", result);
    }

    @Test
    public void testSituationMediumRiskOfLoss() {
        Double occupiedRooms = 0.80;
        Double checkIn = 0D;
        Double checkOut = 0D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Medium risk of loss", result);
    }

    @Test
    public void testSituationHighRiskOfLoss() {
        Double occupiedRooms = 0.80;
        Double checkIn = 0D;
        Double checkOut = 0D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("High risk of loss", result);
    }

    @Test
    public void testSituationAlmostLoss() {
        Double occupiedRooms = 0.80;
        Double checkIn = 0D;
        Double checkOut = 1D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Almost loss", result);
    }

    @Test
    public void testSituationLoss() {
        Double occupiedRooms = 0.80;
        Double checkIn = 0D;
        Double checkOut = 1D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Loss", result);
    }

    @Test
    public void testSituationLowRiskOfBankrupt() {
        Double occupiedRooms = 0.39;
        Double checkIn = 1D;
        Double checkOut = 0D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Low risk of bankrupt", result);
    }

    @Test
    public void testSituationSlightlyRiskOfBankrupt() {
        Double occupiedRooms = 0.39;
        Double checkIn = 1D;
        Double checkOut = 0D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Slightly risk of bankrupt", result);
    }

    @Test
    public void testSituationMediumRiskOfBankrupt() {
        Double occupiedRooms = 0.39;
        Double checkIn = 0D;
        Double checkOut = 0D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Medium risk of bankrupt", result);
    }

    @Test
    public void testSituationHighRiskOfBankrupt() {
        Double occupiedRooms = 0.39;
        Double checkIn = 0D;
        Double checkOut = 0D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("High risk of bankrupt", result);
    }

    @Test
    public void testSituationAlmostBankrupt() {
        Double occupiedRooms = 0.39;
        Double checkIn = 0D;
        Double checkOut = 1D;
        Double booking = 0.41;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Almost bankrupt", result);
    }

    @Test
    public void testSituationBankrupt() {
        Double occupiedRooms = 0.39;
        Double checkIn = 0D;
        Double checkOut = 1D;
        Double booking = 0.40;

        String result = Hotel.calculateSituation(occupiedRooms, checkIn, checkOut, booking);
        assertEquals("Bankrupt", result);
    }

}