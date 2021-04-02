/// <reference types="cypress" />


describe('Admin', () => {
  beforeEach(() => {
    cy.visit('localhost:8080')
    cy.location('pathname').should('include', '/auth/login');

    cy.get('input[name="login"]').type('admin');
    cy.get('input[name="password"]').type('admin');

    cy.get('input[type="submit"]').contains('Login').click();
    
    cy.location('pathname').should('include', '/dashboard');
  })
  
  it('should succeed on create user', () => {
    cy.visit('localhost:8080/dashboard')

    cy.location('pathname').should('include', '/dashboard');

    cy.get('a').contains('Staff').click();

    cy.contains('Staff').should('be.visible');

    cy.get('button').contains('New Staff').click();

    cy.get('input[name="name"]').type('staff');
    cy.get('select[name="access_level"]').select('STAFF');
    cy.get('input[name="login"]').type('staff');
    cy.get('input[name="password"]').type('staff');

    cy.get('input[type="submit"]').contains('Submit').click();

    cy.visit('localhost:8080/staff');
    cy.location('pathname').should('include', '/staff');
    cy.get('table').contains('td', 'staff').should('be.visible');

  });

  it('should succeed on create room type', () => {
    cy.visit('localhost:8080/dashboard')

    cy.location('pathname').should('include', '/dashboard');

    cy.get('a').contains('Room Types').click();

    cy.contains('Room Types').should('be.visible');

    cy.get('button').contains('New Room Type').click();

    cy.get('input[name="name"]').type('mock room');
    cy.get('textarea').type('A beatiful mock room');
    cy.get('input[name="daily_price"]').type('999.99');

    cy.get('input[type="submit"]').contains('Submit').click();

    cy.visit('localhost:8080/roomTypes');
    cy.location('pathname').should('include', '/roomTypes');
    cy.get('table').contains('td', 'mock room').should('be.visible');

  });

  it('should succeed on create room', () => {
    cy.visit('localhost:8080/dashboard')

    cy.location('pathname').should('include', '/dashboard');

    cy.get('a').contains('Rooms').click();

    cy.contains('Rooms').should('be.visible');

    cy.get('button').contains('New Room').click();

    cy.get('input[name="number"]').type('999');
    cy.get('select[name="id_room_type"] option').eq(1).then(element => cy.get('select[name=id_room_type]').select(element.val()));

    cy.get('input[type="submit"]').contains('Submit').click();

    cy.visit('localhost:8080/rooms');
    cy.location('pathname').should('include', '/rooms');
    cy.get('table').contains('td', '999').scrollIntoView().should('be.visible');
  });

  it('should succeed on create guest', () => {
    cy.visit('localhost:8080/dashboard')

    cy.location('pathname').should('include', '/dashboard');

    cy.get('a').contains('Guests').click();

    cy.contains('Guests').should('be.visible');

    cy.get('button').contains('New Guest').click();

    cy.get('input[name="name"]').type('John Deere');
    cy.get('input[name="document"]').type('156415616915');
    cy.get('input[name="birth_date"]').type('1956-09-17');
    cy.get('input[name="email"]').type('john@deere.com');
    cy.get('input[name="phone_number"]').type('61956977412');

    cy.get('input[type="submit"]').contains('Submit').click();

    cy.visit('localhost:8080/guests');
    cy.location('pathname').should('include', '/guests');
    cy.get('table').contains('td', 'John Deere').scrollIntoView().should('be.visible');

  });

  it('should succeed on create booking', () => {
    cy.visit('localhost:8080/dashboard')

    cy.location('pathname').should('include', '/dashboard');

    cy.get('a').contains('Bookings').click();

    cy.contains('Bookings').should('be.visible');

    cy.get('button').contains('New Booking').click();

    cy.get('input[name="arrival"]').type('2022-04-01');
    cy.get('input[name="departure"]').type('2022-04-30');
    cy.get('select[name="room_type"] option').eq(1).then(element => cy.get('select[name=room_type]').select(element.val()));
    cy.get('select[name="id_room"] option').eq(1).then(element => cy.get('select[name=id_room]').select(element.val()));
    cy.get('select[name="id_guest"] option').eq(1).then(element => cy.get('select[name=id_guest]').select(element.val()));

    cy.get('input[type="submit"]').contains('Submit').click();

    cy.location('pathname').should('include', '/bookings');
    cy.get('div').contains('button', 'Checkin').should('be.visible');

  });

});

// Spec files cannot be compiled under '--isolatedModules' because it is considered a global script file.
// Add an import, export, or an empty 'export {}' statement to make it a module.
export {};
