/// <reference types="cypress" />


describe('Admin', () => {
  it('should succeed on create user', () => {
    cy.visit('localhost:8080')
    cy.location('pathname').should('include', '/auth/login');

    cy.get('input[name="login"]').type('admin');
    cy.get('input[name="password"]').type('admin');

    cy.get('input[type="submit"]').contains('Login').click();
    
    cy.location('pathname').should('include', '/dashboard');

    cy.get('a').contains('Staff').click();

    cy.contains('Staff').should('be.visible');

    cy.get('button').contains('New Staff').click();

    cy.get('input[name="name"]').type('staff');
    cy.get('select[name="access_level"]').select('STAFF');
    cy.get('input[name="login"]').type('staff');
    cy.get('input[name="password"]').type('staff');

    cy.get('input[type="submit"]').contains('Submit').click();
  });
});

// Spec files cannot be compiled under '--isolatedModules' because it is considered a global script file.
// Add an import, export, or an empty 'export {}' statement to make it a module.
export {};
